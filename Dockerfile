FROM gradle:8.5

COPY app app

RUN gradle installDist

RUN gradle bootRun
