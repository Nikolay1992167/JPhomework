package by.clevertec.entity;

import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class Auction {

    private UUID id;
    private String name;
    private LocalDate dateEvent;
    private List<Car> cars;
    private Set<Person> people;
    private Map<String, Integer> mapListOfCar;
}
