#Build step
FROM docker.pkg.github.com/erickmoreno/quarkus-build-cache/quarkus-build-cache:0.1.2 as build
USER root
ADD ./ /project
RUN mvn clean package -Pnative

RUN ls -la ./target
RUN mv ./target/reservoirs-watch-1.1-SNAPSHOT-runner /reservoirs-watch

# final
FROM ubuntu:latest
COPY --from=build ./reservoirs-watch /reservoirs-watch
CMD ["./reservoirs-watch"]