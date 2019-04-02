package pl.mnicinski.ClientsApp.domain.client.address;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class AddressTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldFailValidationOnNewAddressWithNotNullUUID() throws IOException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("uuid for new values must be null");

        Address deserializedAddress = objectMapper.readValue(AddressWithAllFields.JSON, Address.class);

        assertEquals(false, deserializedAddress.validateNew());
    }

    @Test
    public void shouldFailValidationOnNewWrongAddressWithNullUUID() throws IOException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("street must not be null");

        Address deserializedAddress = objectMapper.readValue(NewAddress.JSON, Address.class);
        deserializedAddress = new Address.Builder(deserializedAddress)
                .withStreet(null)
                .build();
        assertEquals(false, deserializedAddress.validateNew());
    }

    @Test
    public void shouldPassValidationOnNewProperAddressWithNullUUID() throws IOException {
        Address deserializedAddress = objectMapper.readValue(NewAddress.JSON, Address.class);

        assertEquals(true, deserializedAddress.validateNew());
    }

    @Test
    public void shouldPassValidationOnExistingAddressWithNotNullUUID() throws IOException {
        Address deserializedAddress = objectMapper.readValue(AddressWithAllFields.JSON, Address.class);

        assertEquals(true, deserializedAddress.validate());
    }

    @Test
    public void shouldFailValidationOnNullStreet() throws IOException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("street must not be null");

        Address deserializedAddress = objectMapper.readValue(AddressWithAllFields.JSON, Address.class);
        new Address.Builder(deserializedAddress)
                .withStreet(null)
                .build()
                .validate();
    }

    @Test
    public void shouldFailValidationOnTooShortStreet() throws IOException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("street must be of 2 chars length, or longer");

        Address deserializedAddress = objectMapper.readValue(AddressWithAllFields.JSON, Address.class);
        new Address.Builder(deserializedAddress)
                .withStreet("s")
                .build()
                .validate();
    }

    @Test
    public void shouldFailValidationOnNullHouse() throws IOException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("houseNumber must not be null");

        Address deserializedAddress = objectMapper.readValue(AddressWithAllFields.JSON, Address.class);
        new Address.Builder(deserializedAddress)
                .withHouseNumber(null)
                .build()
                .validate();
    }

    @Test
    public void shouldFailValidationOnNullFlat() throws IOException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("flatNumber must not be null");

        Address deserializedAddress = objectMapper.readValue(AddressWithAllFields.JSON, Address.class);
        new Address.Builder(deserializedAddress)
                .withFlatNumber(null)
                .build()
                .validate();
    }

    @Test
    public void shouldReturnProperValuesWhenBuildFromAnotherAddress() throws IOException {
        Address deserializedAddress = objectMapper.readValue(AddressWithAllFields.JSON, Address.class);
        deserializedAddress = new Address.Builder(deserializedAddress).build();

        assertEquals(AddressWithAllFields.ADDRESS_UUID, deserializedAddress.getUuid());
        assertEquals(AddressWithAllFields.FLAT_NUMBER, deserializedAddress.getFlatNumber());
        assertEquals(AddressWithAllFields.HOUSE_NUMBER, deserializedAddress.getHouseNumber());
        assertEquals(AddressWithAllFields.STREET, deserializedAddress.getStreet());
    }

    @Test
    public void shouldEqualOnAddressFromBuilderCopy() throws IOException {
        Address firstDeserializedAddress = objectMapper.readValue(AddressWithAllFields.JSON, Address.class);
        Address secondDeserializedAddress = new Address.Builder(firstDeserializedAddress).build();

        assertEquals(true, firstDeserializedAddress.equals(secondDeserializedAddress));
    }

    @Test
    public void shouldEqualOnAddressFromBuilder() throws IOException {
        Address firstDeserializedAddress = objectMapper.readValue(NewAddress.JSON, Address.class);
        Address secondDeserializedAddress = new Address.Builder()
                .withHouseNumber(NewAddress.HOUSE_NUMBER)
                .withFlatNumber(NewAddress.FLAT_NUMBER)
                .withStreet(NewAddress.STREET).build();

        assertEquals(true, firstDeserializedAddress.equals(secondDeserializedAddress));
    }

    @Test
    public void shouldntEqualOnAddressFromBuilderWithDifferentFields() throws IOException {
        Address firstDeserializedAddress = objectMapper.readValue(AddressWithAllFields.JSON, Address.class);
        Address secondDeserializedAddress =
                new Address.Builder(firstDeserializedAddress).withStreet("new street").build();

        assertEquals(false, firstDeserializedAddress.equals(secondDeserializedAddress));
    }
}