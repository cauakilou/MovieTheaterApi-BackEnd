# Usa uma imagem base com o JDK 21
FROM eclipse-temurin:21-jdk-jammy

# Define o diretório de trabalho no contêiner
WORKDIR /app

# Copia o arquivo JAR gerado pelo Maven para o contêiner
# O caminho de origem é relativo ao contexto de build (a raiz do projeto)
COPY target/MovieTheatherAPI-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta que sua aplicação Spring Boot usa
EXPOSE 8080

# Define o comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]