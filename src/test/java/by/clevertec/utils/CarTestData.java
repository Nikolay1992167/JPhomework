package by.clevertec.utils;

import by.clevertec.enams.Type;
import by.clevertec.entity.Car;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

import static by.clevertec.utils.Constants.*;

@Data
@Builder(setterPrefix = "with")
public class CarTestData {

    @Builder.Default
    private UUID id = CAR_ID;
    @Builder.Default
    private String brand = CAR_BRAND;
    @Builder.Default
    private String model = CAR_MODEL;
    @Builder.Default
    private LocalDate dateProduction = CAR_DATE_PRODUCTION;
    @Builder.Default
    private Type type = CAR_TYPE;
    @Builder.Default
    private double price = CAR_PRICE;

    public Car buildCar() {
        return new Car(id, brand, model, dateProduction, type, price);
    }
}
