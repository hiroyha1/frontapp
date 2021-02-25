FROM mcr.microsoft.com/java/jre-headless:8u282-zulu-alpine

WORKDIR /app
COPY ./build/libs/frontapp-*.jar ./app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-Xmx128M", "-XX:+PrintFlagsFinal", "-jar", "app.jar"]