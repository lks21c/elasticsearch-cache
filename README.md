# Intro
Elasticsearch-Cache(ESC) is a cache server working as a reverse proxy of Elasticsearch and caches past aggregation data to enhance speed and to save computing cost on Elasticsearch. It Boosts speed of aggregation on Elasticsearch and Kibana. This should be hlepful who performs massive aggregation using Elasticsearch.

# How To Run

Running ESC is super simple. You can just checkout the repo and run the java jar. You can just run the jar, otherwise you can also manually build source code using Maven.

## Run ESC using binary distribution.

    $ git clone https://github.com/lks21c/elasticsearch-cache
    $ cd elasticsearch-cache
    $ java -server -Xms256m -Xmx1024m -XX:+UseG1GC -Dhostname=my_hostname -Desc.cache=true -Desc.warmup=true -Desc.cache.index.name=esc_cache_prd -Desc.cache.terms=true -Dzuul.routes.*.url=http://ES_URL:ES_PORT -jar dist/elasticsearch-cache.jar

## Run ESC using source code

    $ git clone https://github.com/lks21c/elasticsearch-cache
    $ cd elasticsearch-cache
    $ mvn -Dmaven.test.skip=true clean package
    $ java -server -Xms256m -Xmx1024m -XX:+UseG1GC -Dhostname=my_hostname -Desc.cache=true -Desc.warmup=true -Desc.cache.index.name=esc_cache_prd -Desc.cache.terms=true -Dzuul.routes.*.url=http://ES_URL:ES_PORT -jar target/elasticsearch-cache-0.0.1-SNAPSHOT.jar

# Core Concept

ESC intercepts aggregation query and caches responses with each interval. It only caches past periods transparently, so it ensures stale cache info doesn't exist.

# Design Purpose
* Speed
Within date histogram aggregation and terms aggregation without Cardinaly. It saves time cost of aggregation up to 90% with empirical evidence.


* Transparency
  ESC doesnt' interfere with plain response data itself. ESC shouldn't cahnge original numbers, it only speeds up the performance of aggregation.


* Compatibility
  ESC supports 5.x ES and hopefully supports 6.x ASAP.


* Extensibility
ESC provides Extensibility on cache repository and query parser. It has options to choose implementaion case by case.

  * cache repository
    * in memory cache repository
    * ES cache repository
  * query parser
    * native query parser
    * JSON parser

# Features

* Cache
ESC caches date histogram and terms aggregation. It stores past data into cache repository. It saves time cost of aggregation up to 90% with empirical evidence.

* Performance Profile
ESC profiles Kibana profile such as kibana request count and latency. It provides which dashboard is popular or which dashboard(or visualization) is bottle-neck.
![](https://github.com/lks21c/elasticsearch-cache/blob/master/screenshot/border/screenshot0200.png)

* Warm-Up Cache
ESC supports warm-up service which make necessary in advance.
