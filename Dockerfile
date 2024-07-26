# build
FROM maven AS build
WORKDIR /usr/src/app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Init
FROM eclipse-temurin:21-jdk
EXPOSE 8888
COPY --from=build /usr/src/app/target/*.jar ./
ENTRYPOINT ["java","-jar","newhopeserver-1.jar"]