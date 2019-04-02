package pl.mnicinski.ClientsApp.domain.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pl.mnicinski.ClientsApp.domain.client.address.Address;
import pl.mnicinski.ClientsApp.domain.client.json.ClientWithAllFields;
import pl.mnicinski.ClientsApp.domain.client.json.ProperNewClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ClientTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldFailValidationOnNewClientWithNotNullUUID() throws IOException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("uuid for new values must be null");

        Client deserializedClient = objectMapper.readValue(ClientWithAllFields.JSON, Client.class);

        assertEquals(false, deserializedClient.validateNew());
    }

    @Test
    public void shouldFailValidationOnNewWrongClientWithNullUUID() throws IOException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("login must not be null");

        Client deserializedClient = objectMapper.readValue(ProperNewClient.JSON, Client.class);
        deserializedClient = new Client.Builder(deserializedClient)
                .withLogin(null)
                .build();
        assertEquals(false, deserializedClient.validateNew());
    }

    @Test
    public void shouldPassValidationOnNewProperClientWithNullUUID() throws IOException {
        Client deserializedClient = objectMapper.readValue(ProperNewClient.JSON, Client.class);

        assertEquals(true, deserializedClient.validateNew());
    }

    @Test
    public void shouldPassValidationOnExistingClientWithNotNullUUID() throws IOException {
        Client deserializedClient = objectMapper.readValue(ClientWithAllFields.JSON, Client.class);

        assertEquals(true, deserializedClient.validate());
    }

    @Test
    public void shouldFailValidationOnNullLogin() throws IOException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("login must not be null");

        Client deserializedClient = objectMapper.readValue(ClientWithAllFields.JSON, Client.class);
        new Client.Builder(deserializedClient)
                .withLogin(null)
                .build()
                .validate();
    }

    @Test
    public void shouldFailValidationOnTooShortLogin() throws IOException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("login must be of 3 chars length, or longer");

        Client deserializedClient = objectMapper.readValue(ClientWithAllFields.JSON, Client.class);
        new Client.Builder(deserializedClient)
                .withLogin("te")
                .build()
                .validate();
    }

    @Test
    public void shouldFailValidationOnNullMailAddress() throws IOException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("mailAddress must not be null");

        Client deserializedClient = objectMapper.readValue(ClientWithAllFields.JSON, Client.class);
        new Client.Builder(deserializedClient)
                .withMailAddress(null)
                .build()
                .validate();
    }

    @Test
    public void shouldFailValidationOnMailWithoutAtSign() throws IOException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("mailAddress should contain @(at) sign");

        Client deserializedClient = objectMapper.readValue(ClientWithAllFields.JSON, Client.class);
        new Client.Builder(deserializedClient)
                .withMailAddress("totally_valid_mail_address")
                .build()
                .validate();
    }

    @Test
    public void shouldReturnProperValuesWhenBuildFromAnotherClient() throws IOException {
        Client deserializedClient = objectMapper.readValue(ClientWithAllFields.JSON, Client.class);
        deserializedClient = new Client.Builder(deserializedClient).build();

        Address address = deserializedClient.getAddresses().iterator().next();
        assertEquals(ClientWithAllFields.CLIENT_UUID, deserializedClient.getUuid());
        assertEquals(ClientWithAllFields.E_MAIL, deserializedClient.getMailAddress());
        assertEquals(ClientWithAllFields.LOGIN, deserializedClient.getLogin());

        assertEquals(ClientWithAllFields.FLAT_NUMBER, address.getFlatNumber());
        assertEquals(ClientWithAllFields.HOUSE_NUMBER, address.getHouseNumber());
        assertEquals(ClientWithAllFields.STREET, address.getStreet());
    }

    @Test
    public void shouldEqualOnClientFromBuilderCopy() throws IOException {
        Client firstDeserializedClient = objectMapper.readValue(ClientWithAllFields.JSON, Client.class);
        Client secondDeserializedClient = new Client.Builder(firstDeserializedClient).build();

        assertEquals(true, firstDeserializedClient.equals(secondDeserializedClient));
    }

    @Test
    public void shouldEqualOnClientFromBuilder() throws IOException {
        Client firstDeserializedClient = objectMapper.readValue(ProperNewClient.JSON, Client.class);
        Client secondDeserializedClient = new Client.Builder()
                .withLogin(ProperNewClient.LOGIN)
                .withMailAddress(ProperNewClient.E_MAIL)
                .withAddress(firstDeserializedClient.getAddresses().iterator().next()).build();

        assertEquals(true, firstDeserializedClient.equals(secondDeserializedClient));
    }

    @Test
    public void shouldEqualOnClientFromBuilderWithAppendedAddress() throws IOException {
        Client firstDeserializedClient = objectMapper.readValue(ProperNewClient.JSON, Client.class);
        Client secondDeserializedClient = new Client.Builder()
                .withLogin(ProperNewClient.LOGIN)
                .withMailAddress(ProperNewClient.E_MAIL)
                .build().appendNewAddress(firstDeserializedClient.getAddresses().iterator().next());

        assertEquals(true, firstDeserializedClient.equals(secondDeserializedClient));
    }

    @Test
    public void shouldEqualOnClientFromBuilderWithAddresses() throws IOException {
        Client firstDeserializedClient = objectMapper.readValue(ProperNewClient.JSON, Client.class);
        Client secondDeserializedClient = new Client.Builder()
                .withLogin(ProperNewClient.LOGIN)
                .withMailAddress(ProperNewClient.E_MAIL)
                .withAddresses(firstDeserializedClient.getAddresses()).build();

        assertEquals(true, firstDeserializedClient.equals(secondDeserializedClient));
    }

    @Test
    public void shouldntEqualOnClientFromBuilderWithDifferentFields() throws IOException {
        Client firstDeserializedClient = objectMapper.readValue(ClientWithAllFields.JSON, Client.class);
        Client secondDeserializedClient =
                new Client.Builder(firstDeserializedClient).withLogin("new login").build();

        assertEquals(false, firstDeserializedClient.equals(secondDeserializedClient));
    }

    @Test
    public void shouldntEqualOnClientFromBuilderWithDifferentAddressList() throws IOException {
        Client firstDeserializedClient = objectMapper.readValue(ClientWithAllFields.JSON, Client.class);
        Address newAddress =
                new Address.Builder(firstDeserializedClient.getAddresses().iterator().next())
                        .withHouseNumber("21322").build();
        Client secondDeserializedClient =
                new Client.Builder(firstDeserializedClient)
                        .withAddress(newAddress)
                        .build();

        assertEquals(false, firstDeserializedClient.equals(secondDeserializedClient));
    }

    @Test
    public void shouldntAddSecondSameClientToSet() throws IOException {
        Client firstDeserializedClient = objectMapper.readValue(ClientWithAllFields.JSON, Client.class);
        Client secondDeserializedClient = new Client.Builder(firstDeserializedClient).build();


        Set<Client> clientsSet = new HashSet<>(Arrays.asList(firstDeserializedClient, secondDeserializedClient));

        assertEquals(1, clientsSet.size());
    }

}