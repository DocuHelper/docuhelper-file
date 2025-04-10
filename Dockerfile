FROM openjdk:17-jdk-slim-buster
LABEL maintainer=docuhelper-api

ARG S3_ACCESS_KEY
ARG S3_SECRET_KEY

ENV DB_HOST=192.168.0.7
ENV DB_PORT=5432
ENV DB_USER=postgres
ENV DB_PASSWD=password
ENV KAFKA_HOST=192.168.0.7
ENV KAFKA_PORT=9092
ENV S3_BUCKET=docuhelper
ENV S3_ENDPOINT=https://minio.bmserver.org
ENV S3_REGION=ap-northeast-2
ENV S3_ACCESS_KEY \
    S3_SECRET_KEY

COPY . /app

WORKDIR app

RUN chmod +x gradlew

RUN sh gradlew build --warning-mode all


ENTRYPOINT sh -c 'java -jar $(find build/libs -name "*.jar" | grep -v "plain" | head -n 1)'
