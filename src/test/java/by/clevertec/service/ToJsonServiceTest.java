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
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ToJsonServiceTest {
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
    void shouldReturnJsonLineForCar() {
        // given
        Car car = CarTestData.builder().build().buildCar();
        String expected = gson.toJson(car);

        // when
        String actual = toJsonService.toJson(car);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldReturnJsonLineForAuction(){
        // given
        Auction auction = AuctionTestData.builder().build().buildAuction();
        String expected = gson.toJson(auction);

        // when
        String actual = toJsonService.toJson(auction);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldReturnJsonLineForPerson(){
        // given
        Person person = PersonTestData.builder().build().buildPerson();
        String expected = gson.toJson(person);

        // when
        String actual = toJsonService.toJson(person);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldReturnJsonLineTransaction(){
        // given
        Transaction transaction = TransactionTestData.builder().build().buildTransaction();
        String expected = toJsonService.toJson(transaction);

        // when
        String actual = toJsonService.toJson(transaction);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}