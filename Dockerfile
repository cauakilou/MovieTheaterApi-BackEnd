# Estágio 1: Build da Aplicação com Maven
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia a estrutura do projeto
COPY pom.xml .
COPY src ./src

# **A CORREÇÃO DEFINITIVA:** Apaga os ficheiros .properties potencialmente corrompidos
# e recria-os do zero com conteúdo limpo e codificação UTF-8 garantida.

# Limpa e recria o application.properties principal
RUN rm -f /app/src/main/resources/application.properties
RUN echo "spring.application.name=MovieTheatherAPI" > /app/src/main/resources/application.properties && \
    echo "server.port=8080" >> /app/src/main/resources/application.properties && \
    echo "spring.jpa.hibernate.ddl-auto=update" >> /app/src/main/resources/application.properties && \
    echo "spring.jpa.show-sql=true" >> /app/src/main/resources/application.properties && \
    echo "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect" >> /app/src/main/resources/application.properties

# Limpa e recria o application.properties de teste
RUN rm -f /app/src/test/resources/application.properties
RUN echo "spring.jpa.show-sql=true" > /app/src/test/resources/application.properties

# Continua com o processo de build
RUN mvn dependency:go-offline
RUN mvn package -DskipTests

# Estágio 2: Execução da Aplicação
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/MovieTheatherAPI-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]