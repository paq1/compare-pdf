
# TODO : PS : j'ai générer ce docker file via IA, a double check

# ---- Build stage ------------------------------------------------------------
FROM sbtscala/scala-sbt:eclipse-temurin-17.0.15_6_1.11.4_3.3.6 as builder

ARG MODULE=api
ARG APP_NAME=compare-pdf-api

WORKDIR /build

# COPY build.sbt ./
# COPY project ./project
# RUN sbt -batch update


COPY . .

# Compiler + stage le module Play correctement
# RUN sbt -batch ";project api; stage"

RUN sbt api/stage

EXPOSE 9000

CMD ["./modules/api/target/universal/stage/bin/compare-pdf-api"]

# ---- Runtime stage ----------------------------------------------------------
# FROM eclipse-temurin:17-jre
#
# ENV PLAY_HTTP_PORT=9000 \
#     JAVA_OPTS="-Dplay.server.pidfile.path=/dev/null"
#
# WORKDIR /opt/app
#
# COPY --from=builder /build/${MODULE}/target/universal/stage/ /opt/app/
#
# EXPOSE 9000
#
# CMD ["/bin/sh", "-lc", "/opt/app/bin/$APP_NAME -Dhttp.port=${PLAY_HTTP_PORT} $JAVA_OPTS"]
