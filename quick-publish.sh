#!/bin/bash
./gradlew clean build publishToMavenLocal -x test $@

PROJECTREPO=~/.m2/repository/functionalj
PUBLISHREPO=../nawaman-maven-repository

if [ -d $PUBLISHREPO ]; then
    cp -Rf $PROJECTREPO $PUBLISHREPO
else
    (>&2 echo "Publish repositoy does not exist!")
fi
