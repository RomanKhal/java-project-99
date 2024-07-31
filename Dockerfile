FROM gradle:8.5

COPY . /app

RUN gradle installDist

RUN gradle bootRun
