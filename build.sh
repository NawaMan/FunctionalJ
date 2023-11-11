#!/bin/bash

set -e

VERSION_NUMBER_FILE=project-version-number
BUILD_NUMBER_FILE=project-build-number

function main() {
    COMMAND="$1"
    shift || true
    
    if [[ "$COMMAND" == ""        ]]; then build-full    "$@"; exit 0; fi
    if [[ "$COMMAND" == "quick"   ]]; then build-quick   "$@"; exit 0; fi
    if [[ "$COMMAND" == "test"    ]]; then build-test    "$@"; exit 0; fi
    if [[ "$COMMAND" == "package" ]]; then build-package "$@"; exit 0; fi
    if [[ "$COMMAND" == "release" ]]; then build-release "$@"; exit 0; fi
    if [[ "$COMMAND" == "help"    ]]; then show-help;          exit 0; fi
    
    echo "Unknown command: $COMMAND"
    show-help
    exit 1
}

function build-quick() {
    ensure-java-version
    set-version
    ./mvnw \
        --no-transfer-progress    \
        --batch-mode              \
        -Dmaven.test.skip=true    \
        -Dmaven.javadoc.skip=true \
        -Dmaven.source.skip=true  \
        clean install
}

function build-test() {
    ensure-java-version
    set-version
    ./mvnw \
        --no-transfer-progress    \
        --batch-mode              \
        -Dmaven.source.skip=true  \
        -Dmaven.javadoc.skip=true \
        clean compile test  
}

function build-full() {
    ensure-java-version
    set-version
    ./mvnw \
        --no-transfer-progress    \
        --batch-mode              \
        clean install
}

function build-package() {
    prepackage-action
    ensure-variable NAWAMAN_SIGNING_PASSWORD
    ensure-variable NAWAMAN_SONATYPE_PASSWORD
    
    KEY_VAR_NAME=$(cat key-var-name)
    ensure-variable "$KEY_VAR_NAME"
    
    ensure-java-version
    set-version
    ./mvnw \
        --no-transfer-progress \
        --batch-mode           \
        -Dgpg.passphrase=$NAWAMAN_SIGNING_PASSWORD \
        clean install package deploy -Ppackage
}

function build-release() {
    prepackage-action
    ensure-release
    ensure-files-tracked
    
    if [[ ! -f "key-var-name" ]]; then
        echo "The file 'key-var-name' does not exist or not accessible."
        show-help
        exit -1
    fi
    
    ensure-variable NAWAMAN_SIGNING_PASSWORD
    ensure-variable NAWAMAN_SONATYPE_PASSWORD
    ensure-variable "$(cat key-var-name)"
    
    ensure-java-version
    set-version
    ./mvnw \
        --no-transfer-progress \
        --batch-mode           \
        -Dgpg.passphrase=$NAWAMAN_SIGNING_PASSWORD \
        clean install package deploy -Ppublish
    
    set -x
    increment-build-number
    push-release-branch
    push-release-tag
}

function show-help() {
    echo "Build this project."
    echo "Commands"
    echo "  ''     : Full build with all the tests (clean, install, tests but no sign, no publish)."
    echo "  quick  : Quick build skipping tests."
    echo "  test   : Compile and test."
    echo "  package: Compile, test, install and package (signed)."
    echo "  release: Build and release. Must be run while on 'release' branch only."
    echo "  help   : Show this message."
    echo ""
    echo "All command requires the follow files"
    echo "  project-version-number: Contains the major and minor version. For example: 2.0 for '2.0.6' version."
    echo "  project-build-number  : Contains the build number. For example: 6 for '2.0.6' version."
    echo "  key-var-name          : Contains the environmental variable name that holds the key name, e.g., FUNCTIONALJ_KEYNAME."
    echo ""
    echo "Release comand requires the following environmental variable."
    echo "  NAWAMAN_SIGNING_PASSWORD : The password for the signing key. Make sure the user name is in '~/.m2/settings.xml'".
    echo "  NAWAMAN_SONATYPE_PASSWORD: The password for SONATYPE account."
    echo "  <what-in-key-var-name>   : The name of the environmental variable holding the key name for signing."
    exit 0
}

function ensure-variable() {
    local VAR_NAME=$1
    local VAR_VALUE=${!VAR_NAME}
    if [[ "$VAR_VALUE" == "" ]]; then
        echo "$VAR_NAME is not set."
        exit -1
    fi
}

function current-version() {
    if [[ ! -f $VERSION_NUMBER_FILE ]]; then
        echo "The file '$VERSION_NUMBER_FILE' does not exists or not accessible."
        show-help
        exit -1
    fi
    if [[ ! -f $BUILD_NUMBER_FILE ]]; then
        echo "The file '$BUILD_NUMBER_FILE' does not exists or not accessible."
        show-help
        exit -1
    fi
    
    local CURRENT_BRANCH=$(git branch --show-current)
    local SNAPSHOT='-SNAPSHOT'
    if [[ "$CURRENT_BRANCH" == "release" ]]; then
        SNAPSHOT=""
    fi
    
    local PROJECT_VERSION=$(cat $VERSION_NUMBER_FILE)
    local PROJECT_BUILD=$(cat $BUILD_NUMBER_FILE)
    echo -n "$PROJECT_VERSION"."$PROJECT_BUILD""$SNAPSHOT"
}

function set-version() {
    mvn versions:set -DnewVersion="$(current-version)"
}

function ensure-java-version() {
    REQUIRED=$(cat .java-version)
    CURRENT=$(javac -version 2>&1 | awk '{print $2}')
    
    if [[ ! "$CURRENT" == "$REQUIRED"* ]]; then
        echo "Java Compiler version is not what required."
        echo "  Required: $REQUIRED"
        echo "  Current : $CURRENT"
        exit -1
    fi
}

function prepackage-action() {
    if [[ "$PREPACKAGE_ACTION" != "" ]]; then
        source "$PREPACKAGE_ACTION"
    fi
}

function ensure-release() {
    local CURRENT_BRANCH=$(git branch --show-current)
    if [[ "$CURRENT_BRANCH" != "release" ]]; then
        echo "You are not on the release branch!"
        echo "Publish only allow on the release branch."
        exit -1
    fi
}

function increment-build-number() {
    BUILD_NUMBER=$(cat $BUILD_NUMBER_FILE | grep -E "^[0-9]+$")
    if [[ "$BUILD_NUMBER" == "" ]]; then BUILD_NUMBER=0; fi
    ((BUILD_NUMBER++)) || true
    echo -n "$BUILD_NUMBER" > $BUILD_NUMBER_FILE
    echo "Up the build number to: $BUILD_NUMBER"
}

function push-release-tag() {
    VERSION=$(current-version)
    git tag -a v$VERSION -m "Release: v$VERSION"
    git push --tags origin
}

function ensure-files-tracked() {
    UNTRACKED_FILES=$(untracted-files)
    if [[ -n "$UNTRACKED_FILES" ]]; then
        echo "There are untracked files. Please make sure all files are tracked."
        git status
        echo $UNTRACKED_FILES
        exit 1
    fi
}

function untracted-files() {
    git status --porcelain           \
    | grep -v "pom.xml"              \
    | grep -v "$VERSION_NUMBER_FILE" \
    | grep -v "$BUILD_NUMBER_FILE"   \
    | grep -E '(^\?\? |^MM |^ M )' || true
}

function push-release-branch() {
    ensure-files-tracked
    
    # Add all pom.xml
    find . -type f -name "pom.xml" -exec git add {} + 2> /dev/null || true
    git add $VERSION_NUMBER_FILE || true
    git add $BUILD_NUMBER_FILE || true
    
    VERSION=$(current-version)
    git commit -m "Release: v$VERSION"
    git push
}

main "$@"
