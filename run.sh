#!/bin/bash
mvn clean install

#Source AWS Credentials
currentDir=`dirname $0`
. $currentDir/loadCredentials.sh
export S3_SECRET_ACCESS_KEY=$aws_access_key_id
export S3_ACCESS_KEY=$aws_secret_access_key


#Run Enrichment Service
target/dp-publish-pipeline-enrich-search.jar start
