# Microservice for template comparison

## Two ways to communicate with the service

### RabbitMQ
### RESTful API

#### /v1: stable
```code
POST /api/v1/process
POST /api/v1/template/{guid}
PUT  /api/v1/template
GET  /api/v1/template/{guid}
GET  /api/v1/templates
```
#### /v2: experimental features
``` code
GET /api/v2/process
```

## Templates location

src/main/resources/templates.txt

## JMeter load testing plan

src/main/resources/jmeter/

## Usage

```code
./gradlew bootRun
```
## Links
https://www.cs.princeton.edu/~rs/AlgsDS07/21PatternMatching.pdf
