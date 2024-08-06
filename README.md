# Тестовое задание
## Требования: 

- написать приложение, которое по REST принимает запрос вида
POST api/v1/wallet<br>
{<br>
valletId: UUID,<br>
operationType: DEPOSIT or WITHDRAW,<br>
amount: 1000<br>
}<br>
после выполнять логику по изменению счета в базе данных<br>
- предусмотреть возможность получить баланс кошелька
GET api/v1/wallets/{WALLET_UUID}<br>
стек:<br>
java 8-17<br>
Spring 3<br>
Postgresql<br>
- должны быть написаны миграции для базы данных с помощью liquibase
- обратить особое внимание проблемам при работе в конкурентной среде (1000 RPS по
одному кошельку). Ни один запрос не должен быть не обработан (50Х error)
- предусмотреть соблюдение формата ответа для заведомо неверных запросов, когда
кошелька не существует, не валидный json, или недостаточно средств
- приложение должно запускаться в докер контейнере, база данных тоже, вся система
должна подниматься с помощью docker-compose
- предусмотреть возможность настраивать различные параметры как на стороне
приложения так и базы данных без пересборки контейнеров
- эндпоинты должны быть покрыты тестами

### Образец тела POST запроса (walletId заменить на сгенерированный при старте приложения):
{
    "walletId": "80244a3f-6870-46b0-87b4-26715d849f7e",
    "operationType": "DEPOSIT",
    "amount": 1200
}
