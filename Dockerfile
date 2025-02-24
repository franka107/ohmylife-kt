# Etapa 1: Construcción
FROM gradle:8.5-jdk17 AS build
WORKDIR /app

# Copia solo los archivos de configuración primero (para aprovechar la cache de dependencias)
COPY gradle gradle
COPY gradlew .
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY apps/mscore/build.gradle.kts apps/mscore/

# Descarga dependencias sin compilar el código
RUN ./gradlew :apps:mscore:dependencies --no-daemon

# Ahora copia el código fuente (cambia solo cuando hay modificaciones)
COPY . .

# Compila y empaqueta la aplicación
RUN ./gradlew :apps:mscore:installDist --no-daemon

# Etapa 2: Ejecución
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copia solo el binario compilado desde la etapa anterior
COPY --from=build /app/apps/mscore/build/install/mscore /app

# Expone el puerto en el que corre Ktor
EXPOSE 8080

# Comando de ejecución
CMD ["./bin/mscore"]