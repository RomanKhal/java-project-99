FROM gradle:8.5

WORKDIR /app

COPY /app .

RUN ./gradlew installDist

CMD ./gradlew bootRun