FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY app app

RUN ./gradlew --no-daemon installDist

CMD ./gradlew --no-daemon bootRun