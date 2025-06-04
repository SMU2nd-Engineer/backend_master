FROM eclipse-temurin:17-alpine AS build
RUN apk add --no-cache bash

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN ./mvnw dependency:go-offline -B

COPY . .

RUN chmod +x ./mvnw

RUN ./mvnw package -DskipTests

# 런타임 스테이지
FROM eclipse-temurin:17-alpine AS runtime

WORKDIR /app

RUN addgroup -g 1000 worker \
    && adduser -u 1000 -G worker -s /bin/sh -D -h /home/worker worker \
    && mkdir -p /home/worker && chown -R worker:worker /home/worker

COPY --from=build --chown=worker:worker /app/target/*.jar ./main.jar

# 외부 볼륨 마운트 경로
VOLUME ["/app/data"]

USER worker:worker

ENV SPRING_PROFILES_ACTIVE=docker

ENV TZ=Asia/Seoul

EXPOSE 8100

ENTRYPOINT ["java","-Duser.timezone=Asia/Seoul", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "main.jar"]
