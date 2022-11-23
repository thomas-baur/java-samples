FROM openjdk:11.0.1-jre-slim-stretch
EXPOSE 8080
ARG JAR=tracing-otel-quarkus-1.0.0-SNAPSHOT.jar
COPY target/$JAR /app.jar
COPY opentelemetry-javaagent.jar /opentelemetry-javaagent.jar
ENTRYPOINT ["java","-jar","/app.jar"]