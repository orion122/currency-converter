# Конвертер валют
Конвертирует выбранную сумму из одной выбранной валюты в другую.

## Запуск
- сборка приложения: `./mvnw package`
- запуск контейнера с PostgreSQL: `docker-compose up -d`
- запуск приложения: `java -jar ./target/currency-converter-1.jar`

## Конфигурирование
URL и подключение к PoatgreSQL конфигурируется в файле `application.properties`

## GraphQL
http://localhost:8080/graphiql

- список курсов валют:
    - пример запроса:
    ```graphql
    {
      currencies {
        name
        nominal
        updateDate
        rate
      }
    }
    ```
    - пример ответа:
    ```json
    {
      "data": {
        "currencies": [
          {
            "name": "Австралийский доллар",
            "nominal": 1,
            "updateDate": "2020-11-19",
            "rate": 55.5784
          },
          {
            "name": "Азербайджанский манат",
            "nominal": 1,
            "updateDate": "2020-11-19",
            "rate": 44.6891
          }
        ]
      }
    }
    ```

- курс валюты:
    - пример запроса:
    ```graphql
    {
      currency(id: "R01010") {
        id
        name
        numCode
        charCode
        nominal
        updateDate
        rate
      }
    }
    ```
    - пример ответа:
    ```json
    {
      "data": {
        "currency": {
          "id": "R01010",
          "name": "Австралийский доллар",
          "numCode": "36",
          "charCode": "AUD",
          "nominal": 1,
          "updateDate": "2020-11-19",
          "rate": 55.5784
        }
      }
    }
    ```

- конвертация:
    - пример запроса:
    ```graphql
    {
      convert(fromId: "R01585F", toId: "R01710A", sum: 15)
    }
    ```
    - пример ответа:
    ```json
    {
      "data": {
        "convert": 12.789
      }
    }
    ```
- статистика конвертаций:
    - пример запроса:
    ```graphql
    {
      conversionStatistics {
        fromCurrency {
          name
        }
        toCurrency {
          name
        }
        averageRate
        sum
      }
    }
    ```
    - пример ответа:
    ```json
    {
      "data": {
        "conversionStatistics": [
          {
            "fromCurrency": {
              "name": "Румынский лей"
            },
            "toCurrency": {
              "name": "Армянских драмов"
            },
            "averageRate": 121.13,
            "sum": 110
          },
          {
            "fromCurrency": {
              "name": "Румынский лей"
            },
            "toCurrency": {
              "name": "Новый туркменский манат"
            },
            "averageRate": 0.85,
            "sum": 30
          }
        ]
      }
    }
    ```
