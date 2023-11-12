package by.clevertec.utils;

import by.clevertec.entity.Car;
import by.clevertec.entity.Person;
import by.clevertec.entity.Transaction;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

import static by.clevertec.utils.Constants.*;

@Data
@Builder(setterPrefix = "with")
public class TransactionTestData {

    @Builder.Default
    private UUID id = TRANSACTION_ID;
    @Builder.Default
    private OffsetDateTime createDate = TRANSACTION_CREATE_DATE;
    @Builder.Default
    private Person person = TRANSACTION_PERSON;
    @Builder.Default
    private Car[] cars = TRANSACTION_CARS;
    @Builder.Default
    private Map<String, Car[]> mapOfCars = MAP_OF_CARS;

    public Transaction buildTransaction(){
        return new Transaction(id, createDate, person, cars, mapOfCars);
    }
}
