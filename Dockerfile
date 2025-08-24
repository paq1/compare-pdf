
# TODO : PS : j'ai générer ce docker file via IA, a double check
# ---- Build stage ------------------------------------------------------------
FROM hseeberger/scala-sbt:17.0.13_1.10.2_3.3.3 AS builder

WORKDIR /build

# Copier les fichiers sbt en premier pour profiter du cache Docker
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

# Copier le stage généré depuis la phase builder
COPY --from=builder /build/api/target/universal/stage/ /opt/app/

# Ajouter un utilisateur non-root
RUN adduser --disabled-password --gecos "" appuser \
    && chown -R appuser:appuser /opt/app
USER appuser

EXPOSE 9000

CMD ["/bin/sh", "-lc", "/opt/app/bin/compare-pdf-api -Dhttp.port=${PLAY_HTTP_PORT} $JAVA_OPTS"]
