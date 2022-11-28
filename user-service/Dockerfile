FROM openjdk:18-jdk-alpine
EXPOSE 8083
COPY build/libs/user-service-1.0-SNAPSHOT.jar .
RUN apk add --no-cache bash
COPY wait-for.sh /wait-for.sh
RUN chmod +x /wait-for.sh
ENTRYPOINT ["/wait-for.sh", "order-service:8082", "--timeout=130", "--", "java", "-jar", "user-service-1.0-SNAPSHOT.jar"]