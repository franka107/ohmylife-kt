# Etapa 1: Construcción
FROM gradle:8.5-jdk17 AS build
WORKDIR /app

# Copia los archivos del proyecto
COPY . .

# Compila y empaqueta la aplicación
RUN ./gradlew :apps:mscore:installDist --no-daemon

# Etapa 2: Ejecución
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copia el resultado de la compilación desde la etapa anterior
COPY --from=build /app/apps/mscore/build/install/mscore /app

# Expone el puerto en el que corre Ktor (ajústalo si es diferente)
EXPOSE 8080

# Comando de ejecución
CMD ["./bin/mscore"]