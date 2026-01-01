#!/bin/bash
# filepath: docker-run.sh

# Script para construir y ejecutar el contenedor Docker del Backend SACAViX Learn

IMAGE_NAME="sacavix-learn-sharer"
CONTAINER_NAME="sacavix-learn-sharer-container"
PORT="8080"
PROFILE="prod"  # Perfil de Spring Boot por defecto

# Función para mostrar ayuda
show_help() {
    echo "Uso: ./docker-run.sh [OPCIÓN] [PERFIL]"
    echo ""
    echo "Opciones:"
    echo "  build     Construir la imagen Docker"
    echo "  run       Ejecutar el contenedor"
    echo "  stop      Detener el contenedor"
    echo "  restart   Reiniciar el contenedor"
    echo "  logs      Mostrar logs del contenedor"
    echo "  clean     Limpiar imagen y contenedor"
    echo "  status    Mostrar estado del contenedor"
    echo "  shell     Acceder al shell del contenedor"
    echo "  help      Mostrar esta ayuda"
    echo ""
    echo "Perfiles disponibles:"
    echo "  dev       Desarrollo (por defecto)"
    echo "  prod      Producción"
    echo "  test      Testing"
    echo ""
    echo "Ejemplos:"
    echo "  ./docker-run.sh build"
    echo "  ./docker-run.sh run dev"
    echo "  ./docker-run.sh run prod"
    echo "  ./docker-run.sh build && ./docker-run.sh run"
}

# Función para construir la imagen
build_image() {
    echo "🔨 Construyendo imagen Docker para Spring Boot..."
    echo "📦 Compilando aplicación con Maven/Gradle..."

    # Detectar si es Maven o Gradle
    if [ -f "pom.xml" ]; then
        echo "📋 Proyecto Maven detectado"
        ./mvnw clean package -DskipTests
        BUILD_TOOL="maven"
    elif [ -f "build.gradle" ] || [ -f "build.gradle.kts" ]; then
        echo "📋 Proyecto Gradle detectado"
        ./gradlew clean build -x test
        BUILD_TOOL="gradle"
    else
        echo "❌ No se detectó Maven ni Gradle"
        exit 1
    fi

    if [ $? -ne 0 ]; then
        echo "❌ Error al compilar la aplicación"
        exit 1
    fi

    echo "🐳 Construyendo imagen Docker..."
    docker build -t $IMAGE_NAME .

    if [ $? -eq 0 ]; then
        echo "✅ Imagen construida exitosamente: $IMAGE_NAME"
        echo "📊 Información de la imagen:"
        docker images $IMAGE_NAME
    else
        echo "❌ Error al construir la imagen Docker"
        exit 1
    fi
}

run_container() {
    echo "🚀 Ejecutando contenedor en modo host..."

    docker stop $CONTAINER_NAME 2>/dev/null
    docker rm $CONTAINER_NAME 2>/dev/null

    docker run -d \
        --name $CONTAINER_NAME \
        --restart=always \
        -v /learn/data/pdfs:/learn/data/pdfs \
        -v /learn/data/images:/learn/data/images \
        --network=host \
        $IMAGE_NAME

    if [ $? -eq 0 ]; then
        # Nota: El puerto $PORT ya NO es relevante, la app escucha DIRECTO en el puerto 8080 del host.
        echo "✅ Contenedor ejecutándose en MODO HOST (acceso directo a la red del anfitrión)."
        echo "🌐 La aplicación está disponible en http://localhost:8080"
        echo "📋 Nombre del contenedor: $CONTAINER_NAME"
    else
        echo "❌ Error al ejecutar el contenedor"
        exit 1
    fi
}

# Función para verificar el estado de salud
check_health() {
    echo "🔍 Verificando estado de salud..."
    for i in {1..10}; do
        if curl -s http://localhost:$PORT/actuator/health > /dev/null 2>&1; then
            echo "✅ Backend está funcionando correctamente"
            return 0
        else
            echo "⏳ Esperando... ($i/10)"
            sleep 3
        fi
    done
    echo "⚠️  El backend puede estar iniciando aún. Revisa los logs con: ./docker-run.sh logs"
}

# Función para detener el contenedor
stop_container() {
    echo "🛑 Deteniendo contenedor Spring Boot..."
    docker stop $CONTAINER_NAME
    docker rm $CONTAINER_NAME
    echo "✅ Contenedor detenido y eliminado"
}

# Función para reiniciar el contenedor
restart_container() {
    local spring_profile=${1:-$PROFILE}
    echo "🔄 Reiniciando contenedor Spring Boot..."
    stop_container
    run_container $spring_profile
}

# Función para mostrar logs
show_logs() {
    echo "📋 Mostrando logs del contenedor Spring Boot..."
    echo "   (Usa Ctrl+C para salir)"
    docker logs -f $CONTAINER_NAME
}

# Función para mostrar estado
show_status() {
    echo "📊 Estado del contenedor:"
    docker ps -a | grep $CONTAINER_NAME
    echo ""
    echo "🔍 Verificando conectividad..."
    if curl -s http://localhost:$PORT/actuator/health > /dev/null 2>&1; then
        echo "✅ Backend responde correctamente"
        curl -s http://localhost:$PORT/actuator/health | python3 -m json.tool 2>/dev/null || echo "Health check OK"
    else
        echo "❌ Backend no está respondiendo"
    fi
}

# Función para acceder al shell del contenedor
access_shell() {
    echo "🖥️  Accediendo al shell del contenedor..."
    docker exec -it $CONTAINER_NAME /bin/bash
}

# Función para limpiar
clean_up() {
    echo "🧹 Limpiando imagen y contenedor Spring Boot..."
    docker stop $CONTAINER_NAME 2>/dev/null
    docker rm $CONTAINER_NAME 2>/dev/null
    docker rmi $IMAGE_NAME 2>/dev/null

    # Limpiar imágenes huérfanas
    echo "🗑️  Limpiando imágenes huérfanas..."
    docker image prune -f

    echo "✅ Limpieza completada"
}

# Procesar argumentos
case "$1" in
    "build")
        build_image
        ;;
    "run")
        run_container $2
        ;;
    "stop")
        stop_container
        ;;
    "restart")
        restart_container $2
        ;;
    "logs")
        show_logs
        ;;
    "status")
        show_status
        ;;
    "shell")
        access_shell
        ;;
    "clean")
        clean_up
        ;;
    "help"|"--help"|"-h")
        show_help
        ;;
    "")
        echo "❓ No se especificó ninguna acción"
        show_help
        ;;
    *)
        echo "❌ Opción no válida: $1"
        show_help
        exit 1
        ;;
esac