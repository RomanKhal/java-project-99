FROM gradle:8.9.0-jdk21

COPY app/ .

RUN gradle installDist

ENTRYPOINT ./build/install/app/bin/app

EXPOSE 8080