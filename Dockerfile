FROM gradle:8.5

WORKDIR /app

COPY /app .

RUN gradle installDist

CMD gradle bootRun