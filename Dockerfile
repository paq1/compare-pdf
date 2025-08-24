
# TODO : PS : j'ai générer ce docker file via IA, a double check

# ---- Build stage ------------------------------------------------------------
# (image sbt + JDK 17)
FROM sbt:1.10.2-eclipse-temurin-17 as builder

# Paramètres build-time (adapte à ton projet)
ARG MODULE=api
ARG APP_NAME=compare-pdf-api

WORKDIR /build

# 1) Pré-chauffer le cache sbt : on ne copie que la méta d'abord
COPY build.sbt ./
COPY project ./project

# Télécharge les deps (cache layer)
RUN sbt -batch update

# 2) Copier le reste du code et builder
COPY . .

# Compile + stage uniquement le module Play (démarreur dans target/universal/stage)
RUN sbt -batch "${MODULE}/stage"

# ---- Runtime stage ----------------------------------------------------------
FROM eclipse-temurin:17-jre

ENV PLAY_HTTP_PORT=9000 \
    JAVA_OPTS="-Dplay.server.pidfile.path=/dev/null"

WORKDIR /opt/app

# Copie l'app "stagée" (scripts + conf + libs)
# (le stage de sbt-native-packager est auto-exécutable via /opt/app/bin/<APP_NAME>)
ARG MODULE=api
ARG APP_NAME=compare-pdf-api
COPY --from=builder /build/${MODULE}/target/universal/stage/ /opt/app/

# (optionnel) Utilisateur non-root
# RUN adduser --disabled-password --gecos "" appuser && chown -R appuser:appuser /opt/app
# USER appuser

EXPOSE 9000

# On passe par un shell pour permettre l'expansion des variables d'env (APP_NAME, JAVA_OPTS, PLAY_HTTP_PORT)
CMD ["/bin/sh", "-lc", "/opt/app/bin/$APP_NAME -Dhttp.port=${PLAY_HTTP_PORT} $JAVA_OPTS"]
