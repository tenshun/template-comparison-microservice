# Microservice for template comparison

## Theory

### String matching problem
* Given a text T and a pattern P, find all occurrences of P within T
* Notations:
- n and m: lengths of P and T
- Σ: set of alphabets (of constant size)
- Pi: ith letter of P (1-indexed)
- a, b, c: single letters in Σ
- x, y, z: strings


### Suffix Trie

* Suffix trie of a string T is a rooted tree that stores all the suffixes (thus all the substrings)
* Each node corresponds to some substring of T
* Each edge is associated with an alphabet
* For each node that corresponds to ax, there is a special pointer called suffix link that leads to the node corresponding to x
* Surprisingly easy to implement!

### Suffix Array

### String algorithms
#### Knuth - Morris - Pratt (KMP) Matcher
* A linear time (!) algorithm that solves the string matching problem by preprocessing P in Θ(m) time
- Main idea is to skip some comparisons by using the previous comparison result
* Uses an auxiliary array π that is defined as the following:
- π[i] is the largest integer smaller than i such that P1 . . . Pπ[i] is a suffix of P1 . . . Pi

####
####


## Two ways to communicate with the service

### RabbitMQ
#### In development...
#### TODO
* configure rabbitmq
* something else

### RESTful API
### API Endpoints
#### Template Request
#### /v1: stable
##### Get All Templates
Request
```code 
GET /api/v1/templates

Host: localhost:8080
Auth: 
Content-type: application/json
Accept: application/json
```
Returns
```code
HTTP 200 OK
[some collection] todo
```

Error
```code
HTTP 204 NO CONTENT
```

##### Get Template By Guid
Request
``` code
GET  /api/v1/template/{guid}
```
Returns
```code
HTTP 200 OK
```
Error
```code
HTTP 404 NOT FOUND
```

#### TODO
```code
POST /api/v1/process
POST /api/v1/template/{guid}
PUT  /api/v1/template
```

#### /v2: experimental features
``` code
GET /api/v2/templates/processing
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
https://web.stanford.edu/class/cs97si/10-string-algorithms.pdf
https://www.cs.princeton.edu/~rs/AlgsDS07/21PatternMatching.pdf
