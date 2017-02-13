#!/bin/bash
mvn clean install

java -jar target/dp-publish-pipeline-enrich-search.jar -Xmx2gb -Xms2gb