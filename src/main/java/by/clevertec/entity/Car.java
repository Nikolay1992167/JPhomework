package by.clevertec.entity;

import by.clevertec.enams.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class Car {

    private UUID id;
    private String brand;
    private String model;
    private LocalDate dateProduction;
    private Type type;
    private double price;
}
