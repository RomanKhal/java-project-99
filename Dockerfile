FROM gradle:8.9.0-jdk21

COPY app/ .

# RUN gradle --no-daemon installDist

CMD gradle bootRun