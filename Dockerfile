# --- 빌드 스테이지 ---
FROM --platform=linux/amd64 gradle:8.4.0-jdk17 AS builder
WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradle ./gradle
RUN gradle dependencies --no-daemon

COPY . .
RUN ./gradlew bootJar -x test

# --- 실행 스테이지 ---
FROM --platform=linux/amd64 openjdk:17-slim
ARG JAR_FILE=build/libs/*.jar

COPY --from=builder /app/${JAR_FILE} /chatme-app.jar
ENTRYPOINT ["java", "-jar", "/chatme-app.jar"]
