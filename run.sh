#!/bin/bash
mvn clean install
export S3_SECRET_ACCESS_KEY=II/8TmtrxZ/YenymB6T211KgCvx1oS+pm3bHJ1US
export S3_ACCESS_KEY=AKIAIX65FEZKPY3H6AHQ
target/dp-publish-pipeline-enrich-search.jar start
