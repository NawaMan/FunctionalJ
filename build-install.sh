#!/bin/bash -e

if [[ -z "$NAWAMAN_SONATYPE_PASSWORD" ]]; then
    echo "NAWAMAN_SONATYPE_PASSWORD is not set! Abort publishing ..."
fi
if [[ -z "$NAWAMAN_SIGNING_PASSWORD" ]]; then
    echo "NAWAMAN_SIGNING_PASSWORD is not set! Abort publishing ..."
fi

./gradlew clean build publishToMavenLocal "$@"     \
    -PossrhPassword="$NAWAMAN_SONATYPE_PASSWORD"   \
    -Psigning.password="$NAWAMAN_SIGNING_PASSWORD"
