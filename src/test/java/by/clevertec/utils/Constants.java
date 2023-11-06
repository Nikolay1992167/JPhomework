package by.clevertec.utils;

import by.clevertec.enams.Status;
import by.clevertec.enams.Type;
import by.clevertec.entity.Car;
import by.clevertec.entity.Person;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Constants {

    public static final UUID CAR_ID = UUID.fromString("003387b9-4390-49bc-a116-9f5da24fe8ef");
    public static final String CAR_BRAND = "Toyota";
    public static final String CAR_MODEL = "Camry";
    public static final LocalDate CAR_DATE_PRODUCTION = LocalDate.of(2023, 10, 15);
    public static final Type CAR_TYPE = Type.SEDAN;
    public static final double CAR_PRICE = 23000.0;
    public static final UUID AUCTION_ID = UUID.fromString("76a4a999-92d7-452f-9a7b-34607ecb688e");
    public static final String AUCTION_NAME = "Лучшие авто сезона!";
    public static final LocalDate AUCTION_DATE_EVEN = LocalDate.of(2023, 11, 6);
    public static final List<Car> FIRST_LIST_OF_CAR = List.of(
            new Car(
                    UUID.fromString("299bc76e-f4c4-4fed-9aa3-d4a9ff583be2"),
                    "Hyndai",
                    "i30",
                    LocalDate.of(2023, 2, 14),
                    Type.HATCHBACK,
                    19000.0
            ),
            new Car(
                    UUID.fromString("61803ed3-74d5-4b1a-a3e2-f861b83967c1"),
                    "Ford",
                    "Focus",
                    LocalDate.of(2023, 1, 10),
                    Type.HATCHBACK,
                    18500.0
            )
    );
    public static final List<Car> SECOND_LIST_OF_CAR = List.of(
            new Car(
                    UUID.fromString("948730ef-223f-4569-9814-a641c97e196f"),
                    "Audi",
                    "A4",
                    LocalDate.of(2023, 2, 18),
                    Type.WAGON,
                    45000.0
            ),
            new Car(
                    UUID.fromString("d4a2ec62-b5c1-4043-b890-59846386f185"),
                    "VW",
                    "Passat",
                    LocalDate.of(2023, 2, 1),
                    Type.WAGON,
                    44400.0
            )
    );
    public static final double[] AUCTION_PRICES = new double[]{10000.0, 20000.0, 30000.0};
    public static final Set<Person> AUCTION_PEOPLE = Set.of(
            new Person(
                    UUID.fromString("221f4ccb-2ab8-41da-a71e-6d67b9e59a84"),
                    "Николай",
                    "Денежный",
                    BigDecimal.valueOf(100000),
                    true,
                    Status.BUYER
            ),
            new Person(
                    UUID.fromString("1790b89a-25f3-4764-9496-4ae5ac7db0bb"),
                    "Василий",
                    "Долларовый",
                    BigDecimal.valueOf(50000),
                    true,
                    Status.BUYER
            )
    );
    public static final Map<Integer, List<Car>> MAP_LIST_OF_CAR = Map.of(
            1, FIRST_LIST_OF_CAR,
            2, SECOND_LIST_OF_CAR
    );
    public static final List<Map<Integer, Car>> LIST_MAP_OF_CAR = List.of(
            Map.of(
                    1, FIRST_LIST_OF_CAR.get(0),
                    2, FIRST_LIST_OF_CAR.get(1)
            ),
            Map.of(
                    1, SECOND_LIST_OF_CAR.get(0),
                    2, SECOND_LIST_OF_CAR.get(1)
            )
    );
    public static final Map<Integer, Map<String, Car>> MAP_FOR_BROSHURE = Map.of(
            5, Map.of(
                    "Family", FIRST_LIST_OF_CAR.get(0),
                    "Young", FIRST_LIST_OF_CAR.get(1)
            ),
            9, Map.of(
                    "Business", SECOND_LIST_OF_CAR.get(0),
                    "Rich", SECOND_LIST_OF_CAR.get(1)
            ));
    public static final UUID PERSON_ID = UUID.fromString("39f9dd57-25f7-4d21-b598-0d50dd4067f8");
    public static final String PERSON_FIRST_NAME = "Евгений";
    public static final String PERSON_LAST_NAME = "Лопушков";
    public static final BigDecimal PERSON_AMOUNT = BigDecimal.valueOf(85000);
    public static final boolean PERSON_IS_EXIST_TODAY = true;
    public static final Status PERSON_STATUS = Status.BUYER;
    public static final UUID TRANSACTION_ID = UUID.fromString("a81665e4-f798-4af5-a131-3b9cffcd33b8");
    public static final OffsetDateTime TRANSACTION_CREATE_DATE = OffsetDateTime.of(2023, 11, 07, 0, 35, 30, 4, ZoneOffset.of("+03:00"));
    public static final Person TRANSACTION_PERSON = AUCTION_PEOPLE.stream()
            .findFirst().get();
    public static final Car[] TRANSACTION_CARS = new Car[]{
            SECOND_LIST_OF_CAR.get(0),
            SECOND_LIST_OF_CAR.get(1)
    };
    public static final Map<String, Car[]> MAP_OF_CARS = Map.of(
            "Germany", TRANSACTION_CARS
    );
}
