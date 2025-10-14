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
COPY otel/opentelemetry-javaagent.jar otel/opentelemetry-javaagent.jar
COPY otel/otel.properties otel/otel.properties

# Variables por defecto (se pueden sobrescribir en tiempo de ejecución)
ENV JAVA_OPTS=""
ENV DB_PWD="root"
ENV EMAIL_PWD="U68DHVWbCpFgntkZ"
ENV JWT_SECRET="rG9Z!mZ#v4x7LkB@ePwqQ8uNnV5tZyR2$FbWgHsKxLpTqEzJu"
ENV SECURE_ADMIN_TOKEN="12qwaszx"

# Puerto de la app
EXPOSE 8100

# Comando de arranque con el agente OpenTelemetry
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -javaagent:otel/opentelemetry-javaagent.jar \ 
-Dotel.javaagent.configuration-file=otel/otel.properties \
-jar app.jar"]
