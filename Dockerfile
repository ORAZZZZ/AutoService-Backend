# Используем официальный образ OpenJDK 17 для сборки
FROM openjdk:17-jdk-slim as build

# Устанавливаем Maven
RUN apt-get update && apt-get install -y maven

# Устанавливаем рабочую директорию в контейнере
WORKDIR /app

# Копируем pom.xml и качаем все зависимости
COPY pom.xml .
RUN mvn dependency:go-offline

# Копируем исходный код приложения
COPY src ./src

# Собираем приложение с помощью Maven
RUN mvn clean package -DskipTests

# Используем официальный образ OpenJDK 17 для финального контейнера
FROM openjdk:17-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем jar файл, созданный на предыдущем этапе
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar /app/app.jar

# Открываем порт, на котором будет работать приложение
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
