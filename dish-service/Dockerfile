FROM openjdk:18-jdk-alpine
EXPOSE 8081
COPY build/libs/dish-service-1.0-SNAPSHOT.jar .
RUN apk add --no-cache bash
COPY wait-for.sh /wait-for.sh
RUN chmod +x /wait-for.sh
ENTRYPOINT ["/wait-for.sh", "gateway:8989", "--timeout=90", "--", "java", "-jar", "dish-service-1.0-SNAPSHOT.jar"]