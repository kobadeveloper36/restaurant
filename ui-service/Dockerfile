FROM openjdk:18-jdk-alpine
EXPOSE 8080
COPY build/libs/ui-service-1.0-SNAPSHOT.jar .
RUN apk add --no-cache bash
COPY wait-for.sh /wait-for.sh
RUN chmod +x /wait-for.sh
ENTRYPOINT ["/wait-for.sh", "user-service:8083", "--timeout=150", "--", "java", "-jar", "ui-service-1.0-SNAPSHOT.jar"]