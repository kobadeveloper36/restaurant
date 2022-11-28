FROM openjdk:18-jdk-alpine
EXPOSE 8082
COPY build/libs/order-service-1.0-SNAPSHOT.jar .
RUN apk add --no-cache bash
COPY wait-for.sh /wait-for.sh
RUN chmod +x /wait-for.sh
ENTRYPOINT ["/wait-for.sh", "dish-service:8081", "--timeout=110", "--", "java", "-jar", "order-service-1.0-SNAPSHOT.jar"]