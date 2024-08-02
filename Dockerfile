FROM gradle:8.9.0-jdk21

COPY app/ .

RUN gradle installDist

EXPOSE 8080

ENTRYPOINT ./build/install/app/bin/app --spring.profiles.active=prod

