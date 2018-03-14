#!/bin/bash
gradle clean build publishToMavenLocal

PROJECTREPO=~/.m2/repository/nawaman
PUBLISHREPO=../nawaman-maven-repository

if [ -d $PUBLISHREPO ]; then
    cp -Rf $PROJECTREPO $PUBLISHREPO
else
    (>&2 echo "Publish repositoy does not exist!")
fi
