FROM amazoncorretto:21-alpine

# Etiqueta para el nombre del contenedor
LABEL name="sacavix-learn-sharer"

# Crea un directorio de trabajo
WORKDIR /app

# Instala herramientas necesarias para descomprimir y wget (para descargar el agente si hace falta)
RUN apk add --no-cache bash curl unzip

# Copia tu aplicación Spring Boot (ajusta el nombre del .jar)
COPY target/sharerer-0.0.1-SNAPSHOT.jar app.jar

# Copia el agente OpenTelemetry (si lo tenés en el proyecto)
#COPY otel/opentelemetry-javaagent.jar otel/opentelemetry-javaagent.jar
#COPY otel/otel.properties otel/otel.properties

COPY newrelic/newrelic.jar /app/newrelic.jar
COPY newrelic/newrelic.yml /app/newrelic.yml
ENV NEW_RELIC_APP_NAME="SACAViX Learn Sharer Live"
ENV NEW_RELIC_LICENSE_KEY="031f927750186f47e74176f7d0a0a40dFFFFNRAL"


# Variables por defecto (se pueden sobrescribir en tiempo de ejecución)
ENV JAVA_OPTS=""
ENV DB_PWD="te!UpRRU5EZ8W2UqqxXe@n"

# Puerto de la app
EXPOSE 8100

# Comando de arranque con el agente OpenTelemetry
#ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -javaagent:otel/opentelemetry-javaagent.jar \
#-Dotel.javaagent.configuration-file=otel/otel.properties \
#-jar app.jar"]

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -javaagent:/app/newrelic.jar \
-Dspring.profiles.active=prod \
-jar app.jar"]