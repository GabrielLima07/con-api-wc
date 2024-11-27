FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine

COPY --from=build /app/target/wayclient-backend-0.0.1-SNAPSHOT.jar app.jar

ARG DB_URL
ARG DB_USERNAME
ARG DB_PASSWORD
ARG RECAPTCHA_SECRET_KEY

ENV SPRING_DATASOURCE_URL=${DB_URL}
ENV SPRING_DATASOURCE_USERNAME=${DB_USERNAME}
ENV SPRING_DATASOURCE_PASSWORD=${DB_PASSWORD}
ENV SPRING_RECAPTCHA_SECRET_KEY=${RECAPTCHA_SECRET_KEY}

RUN addgroup -S app && adduser -S app -G app

USER app


ENTRYPOINT ["java", "-jar", "/app.jar"]