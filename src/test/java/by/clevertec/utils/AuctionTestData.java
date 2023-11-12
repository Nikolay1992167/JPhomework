package by.clevertec.utils;

import by.clevertec.entity.Auction;
import by.clevertec.entity.Car;
import by.clevertec.entity.Person;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.*;

import static by.clevertec.utils.Constants.*;

@Data
@Builder(setterPrefix = "with")
public class AuctionTestData {
    @Builder.Default
    private UUID id = AUCTION_ID;
    @Builder.Default
    private String name = AUCTION_NAME;
    @Builder.Default
    private LocalDate dateEvent = AUCTION_DATE_EVEN;
    @Builder.Default
    private List<Car> cars = FIRST_LIST_OF_CAR;
    @Builder.Default
    private Set<Person> people = AUCTION_PEOPLE;
    @Builder.Default
    private Map<String, Integer> mapListOfCar = MAP_LIST_OF_CAR;

    public Auction buildAuction(){
        return new Auction(id, name, dateEvent, cars, people, mapListOfCar);
    }
}
