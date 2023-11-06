package by.clevertec.entity;

import by.clevertec.enams.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private UUID id;
    private String firstname;
    private String lastname;
    private BigDecimal amount;
    private boolean isExistToday;
    private Status status;
}
