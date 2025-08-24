# ---- Build stage ------------------------------------------------------------
FROM eclipse-temurin:17-jdk AS builder

# Installer sbt à partir du .deb officiel
RUN apt-get update && apt-get install -y curl gnupg \
    && curl -L -o sbt.deb https://github.com/sbt/sbt/releases/download/v1.10.2/sbt-1.10.2.deb \
    && apt-get install -y ./sbt.deb \
    && rm sbt.deb \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /build

# Pré-chauffer les dépendances
COPY build.sbt .
COPY project ./project
RUN sbt -batch update

# Copier le reste et builder
COPY . .
RUN sbt -batch api/stage

# ---- Runtime stage ----------------------------------------------------------
FROM eclipse-temurin:17-jre

ENV PLAY_HTTP_PORT=9000 \
    JAVA_OPTS="-Dplay.server.pidfile.path=/dev/null"

WORKDIR /opt/app

COPY --from=builder /build/api/target/universal/stage/ /opt/app/

RUN adduser --disabled-password --gecos "" appuser \
    && chown -R appuser:appuser /opt/app
USER appuser

EXPOSE 9000

CMD ["/bin/sh", "-lc", "/opt/app/bin/compare-pdf-api -Dhttp.port=${PLAY_HTTP_PORT} $JAVA_OPTS"]
