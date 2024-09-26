
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/coupon-management-system.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]