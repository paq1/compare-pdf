# ---- Build stage ------------------------------------------------------------
FROM eclipse-temurin:17-jdk AS builder

# Installer sbt
RUN apt-get update && apt-get install -y curl gnupg \
    && curl -L https://repo.scala-sbt.org/scalasbt/debian/sbt-1.10.x.deb -o sbt.deb \
    && apt-get install -y ./sbt.deb \
    && rm sbt.deb \
    && apt-get clean

WORKDIR /build

# Copier et pré-chauffer sbt (cache des dépendances)
COPY build.sbt .
COPY project ./project
RUN sbt -batch update

# Copier le reste du code et builder
COPY . .
RUN sbt -batch api/stage

# ---- Runtime stage ----------------------------------------------------------
FROM eclipse-temurin:17-jre

ENV PLAY_HTTP_PORT=9000 \
    JAVA_OPTS="-Dplay.server.pidfile.path=/dev/null"

WORKDIR /opt/app

# Copier le stage généré
COPY --from=builder /build/api/target/universal/stage/ /opt/app/

# Créer un utilisateur non-root
RUN adduser --disabled-password --gecos "" appuser \
    && chown -R appuser:appuser /opt/app
USER appuser

EXPOSE 9000

CMD ["/bin/sh", "-lc", "/opt/app/bin/compare-pdf-api -Dhttp.port=${PLAY_HTTP_PORT} $JAVA_OPTS"]
