# ---- Build stage ------------------------------------------------------------
FROM sbt:1.10.2-jdk17 AS builder

WORKDIR /build

# Copier en deux étapes pour profiter du cache
COPY build.sbt .
COPY project ./project
RUN sbt -batch update

COPY . .
RUN sbt -batch api/stage

# ---- Runtime stage ----------------------------------------------------------
FROM eclipse-temurin:17-jre

ENV PLAY_HTTP_PORT=9000 \
    JAVA_OPTS="-Dplay.server.pidfile.path=/dev/null"

WORKDIR /opt/app

# Copier le stage généré par sbt
COPY --from=builder /build/api/target/universal/stage/ /opt/app/

# Utilisateur non-root
RUN adduser --disabled-password --gecos "" appuser \
    && chown -R appuser:appuser /opt/app
USER appuser

EXPOSE 9000

CMD ["/bin/sh", "-lc", "/opt/app/bin/compare-pdf-api -Dhttp.port=${PLAY_HTTP_PORT} $JAVA_OPTS"]
