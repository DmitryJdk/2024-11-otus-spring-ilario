### Домашнее задание

#### user/user
#### user1/user

docker compose up --build -d
docker compose down
docker image rm hw17-docker-app

Обернуть приложение в docker-контейнер

Цель: деплоить приложение в современном DevOps-стеке

Результат: обёртка приложения в Docker

#### Описание/Пошаговая инструкция выполнения домашнего задания:

Внимание! Задание выполняется на основе любого сделанного Web-приложения

1. Обернуть приложение в docker-контейнер. Dockerfile принято располагать в корне репозитория. В image должна попадать JAR-приложения. Сборка в контейнере рекомендуется, но не обязательна.

2. БД в собственный контейнер оборачивать не нужно (если только Вы не используете кастомные плагины)

3. Настроить связь между контейнерами, с помощью docker-compose

4. Опционально: сделать это в локальном кубе.

5. Приложение желательно реализовать с помощью всех Best Practices Docker (логгирование в stdout и т.д.)


