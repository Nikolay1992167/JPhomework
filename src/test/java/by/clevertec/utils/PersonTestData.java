package by.clevertec.utils;

import by.clevertec.enams.Status;
import by.clevertec.entity.Person;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

import static by.clevertec.utils.Constants.*;

@Data
@Builder(setterPrefix = "with")
public class PersonTestData {

    @Builder.Default
    private UUID id = PERSON_ID;
    @Builder.Default
    private String firstname = PERSON_FIRST_NAME;
    @Builder.Default
    private String lastname = PERSON_LAST_NAME;
    @Builder.Default
    private BigDecimal amount = PERSON_AMOUNT;
    @Builder.Default
    private boolean isExistToday = PERSON_IS_EXIST_TODAY;
    @Builder.Default
    private Status status = PERSON_STATUS;

    public Person buildPerson(){
        return new Person(id, firstname, lastname, amount, isExistToday, status);
    }
}
