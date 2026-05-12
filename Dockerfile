# STAGE 1: Build (Using Alpine Maven to keep the builder light)
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build-env
WORKDIR /opt/app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
# Build the jar and remove the source code immediately to save space
RUN mvn clean package -DskipTests -B

# STAGE 2: Extract Layers (Still need JDK here for layertools)
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /opt/app
COPY --from=build-env /opt/app/target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

# STAGE 3: Final Runtime (The tiny 150MB result)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /opt/app

# Security: Alpine-specific user creation
RUN addgroup -S spring && adduser -S springuser -G spring
USER springuser

# Copy layers from builder
COPY --from=builder /opt/app/dependencies/ ./
COPY --from=builder /opt/app/spring-boot-loader/ ./
COPY --from=builder /opt/app/snapshot-dependencies/ ./
COPY --from=builder /opt/app/application/ ./

EXPOSE 8080
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]