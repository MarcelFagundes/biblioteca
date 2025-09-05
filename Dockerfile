    # Use uma imagem Java base como ponto de partida
    FROM openjdk:21-jdk-slim

    # Defina o diretório de trabalho dentro do contêiner
    WORKDIR /app

    # Copie o arquivo JAR gerado para o diretório de trabalho
    COPY target/*.jar app.jar

    # Expõe a porta em que a aplicação Spring Boot será executada
    EXPOSE 8080

    # Comando para executar a aplicação quando o contêiner iniciar
    ENTRYPOINT ["java", "-jar", "app.jar"]

    # Iniciar Docker
    # docker exec -it postgres_biblioteca psql -U usr_biblioteca -d biblioteca