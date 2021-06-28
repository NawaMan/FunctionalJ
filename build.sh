#!/bin/bash

./gradlew clean build publishToMavenLocal

./gradlew uploadArchives --no-daemon -PossrhPassword="$NAWA_SONATYPE_PASSWORD" -Psigning.password="$NAWA_SIGNING_PASSWORD"

