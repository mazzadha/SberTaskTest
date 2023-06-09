# SberTestTask

Этот проект представляет собой пример автоматизированного тестирования приложения. Он содержит тесты для проверки различных аспектов функциональности приложения с использованием Java, JUnit и Gson.

## Использование

1. Убедитесь, что на вашем компьютере установлены Java и Maven.
2. Клонируйте или скачайте репозиторий с проектом.
3. Откройте проект в выбранной вами среде разработки.
4. Для установки зависимостей проекта выполните следующую команду в корневой папке проекта:
mvn clean install
5. Чтобы запустить все тесты, выполните следующую команду:
mvn test
6. Чтобы запустить только позитивные тесты, выполните следующую команду:
mvn test -Dgroups="positive"
7. Чтобы запустить только негативные тесты, выполните следующую команду:
mvn test -Dgroups="negative"

## Дополнительная информация

- Файлы `result.json` и `test_parameters.json` в папке `src/test/resources` содержат данные, используемые в тестах.
- Позитивные тесты проверяют правильное выполнение функциональности приложения, а негативные тесты проверяют обработку некорректных данных или ситуаций.
- Класс `JsonTests` содержит тестовые методы, разделенные на позитивные и негативные сценарии.
- Каждый тест проверяет определенный аспект функциональности приложения с использованием проверок `assertTrue` и `assertFalse`.
- В случае неудачного выполнения теста, будет выведено сообщение с описанием причичины неуспеха.
