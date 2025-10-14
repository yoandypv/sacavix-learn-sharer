#!/bin/bash

set -e  # Salir si algún comando falla

echo "🚀 Iniciando proceso de construcción de sacavix-learn-sharer..."

# Variables
IMAGE_NAME="sacavix-learn-sharer"
IMAGE_TAG="latest"
FULL_IMAGE_NAME="${IMAGE_NAME}:${IMAGE_TAG}"

# Función para mostrar ayuda
show_help() {
    echo "Uso: $0 [opciones]"
    echo ""
    echo "Opciones:"
    echo "  -t, --tag TAG     Especificar tag personalizado (default: latest)"
    echo "  -h, --help        Mostrar esta ayuda"
    echo ""
    echo "Ejemplos:"
    echo "  $0                # Construye sacavix-learn-backend:latest"
    echo "  $0 -t v1.0.0      # Construye sacavix-learn-backend:v1.0.0"
}

# Procesar argumentos
while [[ $# -gt 0 ]]; do
    case $1 in
        -t|--tag)
            IMAGE_TAG="$2"
            FULL_IMAGE_NAME="${IMAGE_NAME}:${IMAGE_TAG}"
            shift 2
            ;;
        -h|--help)
            show_help
            exit 0
            ;;
        *)
            echo "❌ Opción desconocida: $1"
            show_help
            exit 1
            ;;
    esac
done

echo "📦 Construyendo imagen: ${FULL_IMAGE_NAME}"

# Paso 1: Compilar la aplicación con Maven
echo "🔨 Paso 1: Compilando aplicación con Maven..."
if ! mvn clean package -DskipTests; then
    echo "❌ Error: Falló la compilación con Maven"
    exit 1
fi

echo "✅ Aplicación compilada exitosamente"

# Paso 2: Verificar que el JAR fue creado
JAR_FILE="target/sharerer-0.0.1-SNAPSHOT.jar"
if [ ! -f "$JAR_FILE" ]; then
    echo "❌ Error: No se encontró el archivo JAR en $JAR_FILE"
    exit 1
fi

echo "✅ JAR encontrado: $JAR_FILE"

# Paso 3: Verificar archivos de OpenTelemetry
echo "🔍 Verificando archivos de OpenTelemetry..."
if [ ! -f "otel/opentelemetry-javaagent.jar" ]; then
    echo "❌ Error: No se encontró otel/opentelemetry-javaagent.jar"
    exit 1
fi

if [ ! -f "otel/otel.properties" ]; then
    echo "❌ Error: No se encontró otel/otel.properties"
    exit 1
fi

echo "✅ Archivos de OpenTelemetry verificados"

# Paso 4: Construir la imagen Docker
echo "🐳 Paso 2: Construyendo imagen Docker..."
if ! docker build -t "$FULL_IMAGE_NAME" .; then
    echo "❌ Error: Falló la construcción de la imagen Docker"
    exit 1
fi

echo "✅ Imagen Docker construida exitosamente: ${FULL_IMAGE_NAME}"

# Paso 5: Verificar que la imagen fue creada
echo "🔍 Verificando imagen creada..."
if docker images | grep -q "$IMAGE_NAME"; then
    echo "✅ Imagen verificada:"
    docker images | grep "$IMAGE_NAME"
else
    echo "❌ Error: La imagen no fue encontrada después de la construcción"
    exit 1
fi

# Paso 6: Mostrar información adicional
echo ""
echo "🎉 ¡Construcción completada exitosamente!"
echo ""
echo "📋 Información de la imagen:"
echo "   Nombre: ${FULL_IMAGE_NAME}"
echo "   Tamaño: $(docker images --format "table {{.Size}}" ${FULL_IMAGE_NAME} | tail -n 1)"
echo ""
echo "🚀 Para ejecutar el contenedor:"
echo "   docker run --name sacavix-learn-sharer -p 8100:8100 -d ${FULL_IMAGE_NAME}"
echo ""
echo "📝 Para usar en docker-compose.yml:"
echo "   services:"
echo "     backend:"
echo "       image: ${FULL_IMAGE_NAME}"
echo "       container_name: sacavix-learn-sharer"
echo "       ports:"
echo "         - \"8100:8100\""
echo ""
echo "🧹 Para limpiar imágenes antiguas:"
echo "   docker image prune -f"