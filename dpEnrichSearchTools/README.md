# dpEnrichSearchTools
The `dpEnrichSearchTools` module is the start of a tool to aid with testing, at the moment it helps with generating test data 
from local babbage files on the local machine (i.e. not on S3).  The help explains this as

```java
Usage: enrich [options]
  Options:
    --help

      Default: false
  * --output, -f
      The file write to
  * --root, -d
      The root directory to search from
```

With an example of 

```
java -jar dpEnrichSearchTools-0.0.1-SNAPSHOT-jar-with-dependencies.jar enrich --output /data/s3/enrichmentRequests.json --root /data/s3/master
```

Which results in the `enrichmentRequests.json` file contain the following first few lines.

```json
{"fileContent":"{\"downloads\":[{\"title\":\"UK pension ...}}","fileLocation":"/economy/investmentspensionsandtrusts/adhocs/005977ukpensionfundliabilityderivativecontracts2014/data.json"}
{"fileLocation":"/economy/investmentspensionsandtrusts/adhocs/005977ukpensionfundliabilityderivativecontracts2014","s3Location":"file:/data/s3/master/economy/investmentspensionsandtrusts/adhocs/005977ukpensionfundliabilityderivativecontracts2014/negativevaluederivativescontractsbypensionfunds.xls"}
```

This file can then be piped into Kafka&trade; locally via the producer shell script.
 ```json
 $KAFKA/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic dp.enrichment < /data/s3/datas.json 
```
