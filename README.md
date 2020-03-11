# Reservoirs Watch

Service to retrieve and store Brazilian water reservoirs data

## Reservoirs Watch basic info

RW is a Quarkus/Graalvm projetc so, these are the basic cmds you need to know:

- Run locally:

    ``` bash
    mvn compile quarkus:dev
    ```

- Generate runnable fat jar:

    ``` bash
    mvn clean package
    java -jar <target/executable>-runner.jar
    ```

- Generate native native AOT compiled version

    ``` bash
    mvn clean package -Pnative
    ./<target/executable>
    ```

## How to Run Locally with Docker

To run the scraper locally it is necessary to authenticate to the GitHub Package Registry, where the image of the quarkus-build-cache used in the Dockerfile was published.

### Docker Login in GitHub Package Registry

You can authenticate to GitHub Package Registry with Docker using the docker command. Replace USERNAME with the name of your user account on GitHub and TOKEN with your personal access token.

``` bash
docker login -u USERNAME -p TOKEN docker.pkg.github.com
```

For more information, see "[Configuring Docker for use with GitHub Packages.](https://help.github.com/pt/packages/using-github-packages-with-your-projects-ecosystem/configuring-docker-for-use-with-github-packages)"

### Running scraper locally

``` bash
docker build -t <name:tag> .
docker run -p 8080:8080 <name:tag>
```

- To run the Scraper with the Conductor and the Schellar

``` bash
docker-compose up --build
```

## Usage

Regardless of how you run (locally or using docker) you should trigger scrapper task on Conductor using the following request using your required **Code**, **From** and **To**:

```
curl --request POST \
  --url http://localhost:8080/api/workflow \
  --header 'content-type: application/json' \
  --data '{
    "name": "reservoir_retrieve",
    "version": 1,
    "input": {
			"code": "12001",
            "from": "2019-07-02",
            "to": "2020-02-02"
    }
}'
```