# Explore With Me Plus

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0.0-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-13-blue)
![Docker](https://img.shields.io/badge/Docker-20.10.17-blue)
![Hibernate](https://img.shields.io/badge/Hibernate-5.6.9-blue)

## Описание

Explore With Me Plus — это расширенное приложение для планирования мероприятий, позволяющее пользователям создавать, просматривать и участвовать в различных событиях. Приложение построено с использованием Java, Spring Boot и PostgreSQL, что делает его мощным инструментом для организации мероприятий.

## Функционал

- Создание событий: Пользователи могут создавать события, указывая дату, время, место и описание.
- Просмотр событий: В приложении доступен список всех событий с возможностью фильтрации по различным параметрам.
- Участие в событиях: Пользователи могут регистрироваться на участие в выбранных событиях.
- Модерация: Администраторы могут управлять созданными событиями, одобрять или отклонять их.
- Статистика: Поддержка аналитики и статистики по мероприятиям и участникам.

## Технологии

- Java 17: Основной язык разработки.
- Spring Boot 3.0.0: Фреймворк для создания микросервисов и REST API.
- PostgreSQL 13: Реляционная база данных для хранения данных приложения.
- Hibernate: ORM (Object-Relational Mapping) для взаимодействия с базой данных.
- Docker: Контейнеризация приложения для легкого развёртывания.

## Требования

- Java 17
- Maven 3.8.1+
- PostgreSQL 13+
- Docker (опционально)

## Установка и запуск

1. Клонируйте репозиторий и перейдите в каталог проекта:
  
       git clone https://github.com/Islandec235/java-explore-with-me-plus.git
   
   Затем:
   
       cd java-explore-with-me-plus
   
3. Соберите и запустите контейнеры:
  
       docker-compose up --build
   
4. Приложение будет доступно по адресу:
  
   http://localhost:8080
   
## API Документация

API документация доступна через Swagger UI. Чтобы получить доступ к API, перейдите по адресу (для stats сервиса 9090):
http://localhost:8080/swagger-ui.html

## Авторы
- [Islandec235](https://github.com/Islandec235)
- [nulome](https://github.com/nulome)
