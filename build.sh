#!/bin/bash

set -e

VERSION_FILE="version"
VERSION_SUFFIX_FILE="version-suffix"

function main() {
    COMMAND="$1"
    shift || true
    
    if [[ "$COMMAND" == ""              ]]; then build-full    "$@"; exit 0; fi
    if [[ "$COMMAND" == "quick"         ]]; then build-quick   "$@"; exit 0; fi
    if [[ "$COMMAND" == "test"          ]]; then build-test    "$@"; exit 0; fi
    if [[ "$COMMAND" == "package"       ]]; then build-package "$@"; exit 0; fi
    if [[ "$COMMAND" == "release"       ]]; then build-release "$@"; exit 0; fi
    if [[ "$COMMAND" == "set-version"   ]]; then set-version   "$@"; exit 0; fi
    if [[ "$COMMAND" == "reset-version" ]]; then reset-version "$@"; exit 0; fi
    if [[ "$COMMAND" == "inc-version"   ]]; then inc-version   "$@"; exit 0; fi
    if [[ "$COMMAND" == "help"          ]]; then show-help;          exit 0; fi
    
    echo "Unknown command: $COMMAND"
    show-help
    exit 1
}

function build-quick() {
    ensure-java-version
    ./mvnw \
        --no-transfer-progress    \
        --batch-mode              \
        -Dmaven.test.skip=true    \
        -Dmaven.source.skip=true  \
        -Dmaven.javadoc.skip=true \
        -Dgpg.signing.skip=true   \
        -Dsona.staging.skip=true  \
        clean install
}

function build-test() {
    ensure-java-version
    ./mvnw \
        --no-transfer-progress    \
        --batch-mode              \
        -Dmaven.test.skip=false   \
        -Dmaven.source.skip=true  \
        -Dmaven.javadoc.skip=true \
        -Dgpg.signing.skip=true   \
        -Dsona.staging.skip=true  \
        clean compile test
}

function build-full() {
    ensure-java-version
    ./mvnw \
        --no-transfer-progress     \
        --batch-mode               \
        -Dmaven.test.skip=false    \
        -Dmaven.source.skip=false  \
        -Dmaven.javadoc.skip=false \
        -Dgpg.signing.skip=true    \
        -Dsona.staging.skip=true   \
        clean install
}

function build-package() {
    run-prepackage-hook
    
    ensure-variable NAWAMAN_SIGNING_PASSWORD
    ensure-variable NAWAMAN_SONATYPE_PASSWORD
    ensure-variable "$(cat key-var-name)"
    
    ensure-java-version
    set-version
    ./mvnw \
        --no-transfer-progress \
        --batch-mode           \
        -Dgpg.passphrase=$NAWAMAN_SIGNING_PASSWORD \
        -Dmaven.test.skip=false    \
        -Dmaven.source.skip=false  \
        -Dmaven.javadoc.skip=false \
        -Dgpg.signing.skip=false   \
        -Dsona.staging.skip=true   \
        clean install package gpg:sign
}

function act() {
    ACTION="$*"
    echo ""
    echo -e "\033[32m$ACTION\033[0m"
    eval "$ACTION"
}

function build-release() {
    run-prepackage-hook
    ensure-java-version
    ensure-master
    ensure-no-files-tracked
    
    if [[ ! -f "key-var-name" ]]; then
        echo "The file 'key-var-name' does not exist or not accessible."
        show-help
        exit -1
    fi
    
    # Start of the subshell because the build.sh will be replaced with the one from release (older code).
    (
        VERSION=$(current-version)
        
        act git checkout release
        act git merge master --no-ff -m '"Release: v$VERSION"'
        
        ensure-variable NAWAMAN_SIGNING_PASSWORD
        ensure-variable NAWAMAN_SONATYPE_PASSWORD
        ensure-variable "$(cat key-var-name)"
        
        set-version
        act ./mvnw \
            --no-transfer-progress \
            --batch-mode           \
            -Dgpg.passphrase=$NAWAMAN_SIGNING_PASSWORD \
            -Dmaven.test.skip=false    \
            -Dmaven.source.skip=false  \
            -Dmaven.javadoc.skip=false \
            -Dgpg.signing.skip=false   \
            -Dsona.staging.skip=false  \
            clean install package gpg:sign deploy
        act git push
        
        act git tag -a v$VERSION -m '"Release: v$VERSION"'
        act git push --tags origin
        
        act git reset --hard
        act git checkout master
        
        act inc-version --build
        NEW_VERSION=$(current-version)
        act git add version
        act git commit -m '"Update the version to: v$NEW_VERSION"'
        act git push
        
        echo -e "\033[32mDone for now.\033[0m"
        exit
    )
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

function run-prepackage-hook() {
    if [[ "$PREPACKAGE_HOOK" != "" ]]; then
        source "$PREPACKAGE_HOOK"
    fi
}

function ensure-master() {
    local CURRENT_BRANCH=$(git branch --show-current)
    if [[ "$CURRENT_BRANCH" != "master" && "$CURRENT_BRANCH" != "main" ]]; then
        echo "You are not on the 'master'/'main' branch!"
        echo "Releasing is only allowed on the main branch."
        exit -1
    fi
}

function ensure-no-files-tracked() {
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

function is-release() {
    if [[ "$IS_RELEASE" == "true" ]]; then
        echo "true"
        return
    fi
    if [[ "$IS_RELEASE" == "false" ]]; then
        echo "false"
        return
    fi
    
    local CURRENT_BRANCH=$(git branch --show-current)
    if [[ "$CURRENT_BRANCH" == "release" ]]; then
        echo "true"
        return
    fi
    
    local ON_RELEASE=$(git --no-pager branch --contains HEAD | grep -Eo "^.*[ ]release$")
    if [[ "$ON_RELEASE" != "" ]]; then
        echo "true"
        return
    fi
    
    local ON_ANY_BRANCH=$(git --no-pager branch --contains HEAD | grep -v "HEAD detached" || true)
    if [[ "$ON_ANY_BRANCH" == "" ]]; then
        echo "true"
        return
    fi
    
    echo "false"
}

function current-version() {
    if [[ ! -f "$VERSION_FILE" ]]; then
        echo "The file '$VERSION_FILE' does not exists or not accessible."
        exit -1
    fi
    
    local IS_RELEASE=$(is-release)
    local SNAPSHOT='-SNAPSHOT'
    if [[ "$IS_RELEASE" == "true" ]]; then
        SNAPSHOT=""
    fi
    
    local PROJECT_VERSION=$(cat "$VERSION_FILE")$(cat "$VERSION_SUFFIX_FILE")"$SNAPSHOT"
    echo -n "$PROJECT_VERSION"
}

function set-version() {
    local IS_RELEASE=$(is-release)
    echo "On release: $IS_RELEASE"
    
    CURRENT_VERSION=$(current-version)
    VERSION="${1:-$CURRENT_VERSION}"
    echo ./mvnw versions:set -DnewVersion="$VERSION"
    ./mvnw versions:set -DnewVersion="$VERSION"
}

function reset-version() {
    set-version "0.0.1-SNAPSHOT"
}

function inc-version() {
    if [[ ! -f "$VERSION_FILE" ]]; then
        echo "Version file not found!" 2>&1
        exit -1
    fi
    
    IFS='.' read -a VERSION_PARTS <<< "$(cat $VERSION_FILE)"
    MAJOR=${VERSION_PARTS[0]}
    MINOR=${VERSION_PARTS[1]}
    BUILD=${VERSION_PARTS[2]}
    echo "Current Version: $MAJOR.$MINOR.$BUILD"
    
    case "$1" in
        --major)  MAJOR=$((MAJOR + 1)); MINOR=0; BUILD=0 ;;
        --minor)  MINOR=$((MINOR + 1)); BUILD=0          ;;
        --build)  BUILD=$((BUILD + 1));                  ;;
        *)
            echo "Invalid argument. Use --major, --minor or --build."
            return 1
            ;;
    esac
    
    echo -n "$MAJOR.$MINOR.$BUILD" > $VERSION_FILE
    echo "New Version: $MAJOR.$MINOR.$BUILD"
}

main "$@"
