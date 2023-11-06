package by.clevertec.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private UUID id;
    private OffsetDateTime createDate;
    private Person person;
    private Car[] car;
    private Map<UUID, Car[]> listOfPrices;
}
