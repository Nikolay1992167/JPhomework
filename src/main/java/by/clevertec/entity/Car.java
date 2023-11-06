package by.clevertec.entity;

import by.clevertec.enams.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    private UUID id;
    private String brand;
    private String model;
    private LocalDate dateProduction;
    private Type type;
    private double price;
}
