FROM azul/zulu-openjdk:17 AS builder

WORKDIR /app

COPY target/hexagonal-architecture-app-1.0-SNAPSHOT.jar app.jar

FROM azul/zulu-openjdk-alpine:17

WORKDIR /app

COPY --from=builder /app .

ENTRYPOINT ["java", "-jar", "app.jar"]