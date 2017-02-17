#!/bin/bash
mvn clean install

#Run Enrichment Service
target/dp-publish-pipeline-enrich-search.jar
