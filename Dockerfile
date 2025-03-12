FROM azul/zulu-openjdk:23

WORKDIR /app

COPY init-db2.sh /init-db2.sh
COPY target/hexagonal-architecture-app-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar", "init-db2.sh"]
