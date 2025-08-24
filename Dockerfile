FROM sbtscala/scala-sbt:eclipse-temurin-17.0.15_6_1.11.4_3.3.6 as builder

ARG MODULE=api
ARG APP_NAME=compare-pdf-api

WORKDIR /build

COPY . .

RUN sbt api/stage

EXPOSE 9000

CMD ["./modules/${MODULE}/target/universal/stage/bin/${APP_NAME}", "-Dhttp.port=9000"]
