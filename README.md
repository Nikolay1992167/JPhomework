# JSON application

В приложении реализована возможность сериализации и десериализации объектов Java в формат JSON и обратно.
Интерфейс JsonSerializable определяет два метода для выполнения этих операций. Это позволяет преобразовывать
объекты Java в строковое представление JSON и восстанавливать объекты из этого представления. Приложение имеет базовый
алгоритм заложенный в объектах Auction, Car, Person, Transaction, что полностью обеспечивает потребность условий
задания.

### Технологии применённые в проекте

* Java 17
* Gradle 8.1.1
* Gson 2.10.1
* Mockito-junit-jupiter 5.6.0
* Junit-bom 5.9.2
* Junit-jupiter
* Assertj-core:3.24.2

### Unit тесты

Модульные тесты на 100% покрывают слой сервисов ToJsonService, FromJsonService.
Но при выполнении тестов возникла проблема представления объекта при преобразовании с помощью библиотека gson,
представление объекта выполнена через хэшкод(например для Auction результат Auction@47e4d9d0 и Auction@1948ea69), всё
что смог найти это проблема с переопределением hashCode(), equals() и toString(), но это проблему не решило.
То же и при выполнении теста void shouldReturnObjectTransaction().

### Функциональность

В общем приложение может:

1. Сериализация объектов:
   для класса Auction

````json
  {
  "id": "76a4a999-92d7-452f-9a7b-34607ecb688e",
  "name": "Лучшие авто сезона!",
  "dateEvent": "2023-11-06",
  "cars": [
    {
      "id": "003387b9-4390-49bc-a116-9f5da24fe8ef",
      "brand": "Hyndai",
      "model": "i30",
      "dateProduction": "2023-02-14",
      "type": "HATCHBACK",
      "price": 19000
    },
    {
      "id": "61803ed3-74d5-4b1a-a3e2-f861b83967c1",
      "brand": "Ford",
      "model": "Focus",
      "datProduction": "2023-01-10",
      "type": "HATCHBACK",
      "price": 18500
    }
  ],
  "people": [
    {
      "id": "1790b89a-25f3-4764-9496-4ae5ac7db0bb",
      "firstname": "Василий",
      "lastname": "Долларовый",
      "amount": 50000,
      "isExistToday": true,
      "status": "BUYER"
    },
    {
      "id": "221f4ccb-2ab8-41da-a71e-6d67b9e59a84",
      "firstname": "Николай",
      "lastname": "Денежный",
      "amount": 100000,
      "isExistToday": true,
      "status": "BUYER"
    }
  ],
  "mapListOfCar": {
    "Audi": 70000,
    "VW": 45000,
    "Ford": 30000
  }
}
````

для класса Car

````json
  {
  "id": "003387b9-4390-49bc-a116-9f5da24fe8ef",
  "brand": "Toyota",
  "model": "Camry",
  "dateProduction": "2023-10-15",
  "type": "SEDAN",
  "price": 23000
}
````

для класса Person

````json
  {
  "id": "39f9dd57-25f7-4d21-b598-0d50dd4067f8",
  "firstname": "Евгений",
  "lastname": "Лопушков",
  "amount": 85000,
  "isExistToday": true,
  "status": "BUYER"
}
````

для класса Transaction

````json
{
  "id": "a81665e4-f798-4af5-a131-3b9cffcd33b8",
  "createDate": "2023-11-07T00:35:30.000000004+03:00",
  "person": {
    "id": "221f4ccb-2ab8-41da-a71e-6d67b9e59a84",
    "firstname": "Николай",
    "lastname": "Денежный",
    "amount": 100000,
    "isExistToday": true,
    "status": "BUYER"
  },
  "cars": [
    {
      "id": "948730ef-223f-4569-9814-a641c97e196f",
      "brand": "Audi",
      "model": "A4",
      "dateProduction": "2023-02-18",
      "type": "WAGON",
      "price": 45000
    },
    {
      "id": "d4a2ec62-b5c1-4043-b890-59846386f185",
      "brand": "VW",
      "model": "Passat",
      "dateProduction": "2023-02-01",
      "type": "WAGON",
      "price": 44400
    }
  ],
  "mapOfCars": {
    "Germany": [
      {
        "id": "948730ef-223f-4569-9814-a641c97e196f",
        "brand": "Audi",
        "model": "A4",
        "dateProduction": "2023-02-18",
        "type": "WAGON",
        "price": 45000
      },
      {
        "id": "d4a2ec62-b5c1-4043-b890-59846386f185",
        "brand": "VW",
        "model": "Passat",
        "dateProduction": "2023-02-01",
        "type": "WAGON",
        "price": 44400
      }
    ]
  }
}
````

2. Десериализация объектов:
для класса Auction
(id=76a4a999-92d7-452f-9a7b-34607ecb688e,
name=Лучшие авто сезона!,
dateEvent=2023-11-06,
cars=[Car(id=003387b9-4390-49bc-a116-9f5da24fe8ef, brand=Hyndai, model=i30, dateProduction=2023-02-14, type=HATCHBACK, price=19000.0), Car(id=61803ed3-74d5-4b1a-a3e2-f861b83967c1, brand=Ford, model=Focus, dateProduction=2023-01-10, type=HATCHBACK, price=18500.0)],
people=[Person(id=221f4ccb-2ab8-41da-a71e-6d67b9e59a84, firstname=Николай, lastname=Денежный, amount=100000, isExistToday=true, status=BUYER), Person(id=1790b89a-25f3-4764-9496-4ae5ac7db0bb, firstname=Василий, lastname=Долларовый, amount=50000, isExistToday=true, status=BUYER)],
mapListOfCar={VW=45000, Ford=30000, Audi=70000})

для класса Car
(id=003387b9-4390-49bc-a116-9f5da24fe8ef, 
brand=Toyota, 
model=Camry, 
dateProduction=2023-10-15, 
type=SEDAN, price=23000.0)

для класса Person
(id=39f9dd57-25f7-4d21-b598-0d50dd4067f8, 
firstname=Евгений, 
lastname=Лопушков, 
amount=85000, 
isExistToday=true, 
status=BUYER)

для класса Transaction
(id=a81665e4-f798-4af5-a131-3b9cffcd33b8, 
createDate=2023-11-07T00:35:30.000000004+03:00, 
person=Person(id=221f4ccb-2ab8-41da-a71e-6d67b9e59a84, firstname=Николай, lastname=Денежный, amount=100000, isExistToday=true, status=BUYER), 
cars=[Car(id=948730ef-223f-4569-9814-a641c97e196f, brand=Audi, model=A4, dateProduction=2023-02-18, type=WAGON, price=45000.0), Car(id=d4a2ec62-b5c1-4043-b890-59846386f185, brand=VW, model=Passat, dateProduction=2023-02-01, type=WAGON, price=44400.0)], 
mapOfCars={Germany=[{"id":"948730ef-223f-4569-9814-a641c97e196f","brand":"Audi","model":"A4","dateProduction":"2023-02-18","type":"WAGON","price":45000.0},{"id":"d4a2ec62-b5c1-4043-b890-59846386f185","brand":"VW","model":"Passat","dateProduction":"2023-02-01","type":"WAGON","price":44400.0}]})