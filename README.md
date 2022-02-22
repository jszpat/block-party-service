## Block Parties Service

It's a simple API providing information about Brandenburg's block parties.


### Endpoint details:
Method: GET

Content Type: application/json

URL: http://localhost:9098/api/v1/block-parties 

Query parameters:

| Name | Mandatory | Format | Description |
| ------ | ------ | ------ | ----------- |
| keyword | false | Any string | Allows searching parties by a given keyword. |
| fromDate | false | yyyy-mm-dd | Allows searching parties taking place at a given date or at a later date. |
| toDate | false | yyyy-mm-dd | Allows searching parties taking place at a given date or at an earlier date. |

In order to build and run the service you will need Java 17. If you don't want to mess with java distributions on your local machine - please use docker (instructions below). 
#### Building the service:
```sh
gradlew clean build
```

#### Running the service:

```sh
cd build/libs
java -jar block.party-local.jar
```
or just:
```sh
gradlew bootRun
```

#### Using Docker:
```sh
docker build -t "block-party-service" .
docker run -dp 9098:9098 block-party-service
```

#### Side notes:

- Seems that data provider which I was supposed to use provides only historical data for block parties from 2020, so the API I created does the same :|