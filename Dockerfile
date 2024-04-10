FROM eclipse-temurin:21
RUN mkdir /opt/app
COPY target/*.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","/app.jar"]