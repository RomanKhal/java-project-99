FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY app app

RUN gradle --no-daemon installDist

CMD gradle --no-daemon bootRun