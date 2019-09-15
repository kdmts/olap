# OLAP Application

*Запуск приложения*

1. git clone https://github.com/kdmts/olap.git
2. Добавить файл с данными в директорию resources/data (/data/uid_page_timestamp.sorted.csv). При изменении пути или имени файла поменять проперти в additional.properties. Сам файл на GitHub не залит, можно найти на Я.Диске.
3. mvn spring-boot:run
4. Перейти на localhost:8081 (изменить порт можно в application.properties)

*Примечания*

1. /pages и /similarity реализованы и как RestController, и как Controller
2. При поднятии приложения дата файл копируется в target, это занимает время (1-2 минуты) работает метод /pages, его результат кэшируется. Метод выполняется ~ 2.5 - 3 минуты. Т.е. с копированием и предобработкой приложение запускатеся около пяти минут.
3. Время выполнения /similarity ~6 минут

![example](https://i.imgur.com/0qNSOnN.png "Title")

