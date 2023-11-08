package by.clevertec.service;

import by.clevertec.dateformat.LocalDateDeserializer;
import by.clevertec.dateformat.LocalDateSerializer;
import by.clevertec.dateformat.OffsetDateTimeDeserializer;
import by.clevertec.dateformat.OffsetDateTimeSerializer;
import by.clevertec.entity.Auction;
import by.clevertec.entity.Car;
import by.clevertec.entity.Person;
import by.clevertec.entity.Transaction;
import by.clevertec.serializable.JsonSerializable;
import by.clevertec.serializable.JsonSerializableImpl;
import by.clevertec.utils.AuctionTestData;
import by.clevertec.utils.CarTestData;
import by.clevertec.utils.PersonTestData;
import by.clevertec.utils.TransactionTestData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class FromJsonServiceTest {
    private final ToJsonService toJsonService = new ToJsonService();
    private final FromJsonService fromJsonService = new FromJsonService();

    Gson gson;
    JsonSerializable jsonSerializable;

    @BeforeEach
    void setUp() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeSerializer())
                .registerTypeAdapter(OffsetDateTimeDeserializer.class, new OffsetDateTimeDeserializer())
                .create();
        jsonSerializable = new JsonSerializableImpl(fromJsonService, toJsonService);
    }

    @Test
    void shouldReturnObjectAuction() {
        // given
        Auction auction = AuctionTestData.builder().build().buildAuction();
        String jsonLine = toJsonService.toJson(auction);
        Auction expected = gson.fromJson(jsonLine, Auction.class);

        // when
        Auction actual = (Auction) fromJsonService.toObject(jsonLine, Auction.class);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldReturnObjectCar() {
        // given
        Car car = CarTestData.builder().build().buildCar();
        String jsonLine = toJsonService.toJson(car);
        Car expected = gson.fromJson(jsonLine, Car.class);

        // when
        Car actual = (Car) fromJsonService.toObject(jsonLine, Car.class);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldReturnObjectPerson() {
        // given
        Person person = PersonTestData.builder().build().buildPerson();
        String jsonLine = toJsonService.toJson(person);
        Person expected = gson.fromJson(jsonLine, Person.class);

        // when
        Person actual = (Person) fromJsonService.toObject(jsonLine, Person.class);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldReturnObjectTransaction() {
        // given
        Transaction transaction = TransactionTestData.builder().build().buildTransaction();
        String jsonLine = toJsonService.toJson(transaction);
        Transaction expected = gson.fromJson(jsonLine, Transaction.class);

        // when
        Transaction actual = (Transaction) fromJsonService.toObject(jsonLine, Transaction.class);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}