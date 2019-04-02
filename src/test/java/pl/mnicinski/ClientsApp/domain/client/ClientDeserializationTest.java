package pl.mnicinski.ClientsApp.domain.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import pl.mnicinski.ClientsApp.domain.client.json.ProperNewClient;

import java.io.IOException;
import java.util.Collections;

import static org.junit.Assert.*;

public class ClientDeserializationTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldDeserializeNullToEmptySet() throws IOException {
        String json = "{\n" +
                "  \"addresses\": null,\n" +
                "  \"login\": \"string\",\n" +
                "  \"mailAddress\": \"str@ing\",\n" +
                "  \"uuid\": null\n" +
                "}";
        Client deserializedClient = objectMapper.readValue(json, Client.class);

        assertEquals(Collections.emptySet(), deserializedClient.getAddresses());
    }

    @Test
    public void shouldDeserializeLogin() throws IOException {

        Client deserializedClient = objectMapper.readValue(ProperNewClient.JSON, Client.class);

        assertEquals(ProperNewClient.LOGIN, deserializedClient.getLogin());
    }

    @Test
    public void shouldDeserializeAddresses() throws IOException {
        Client deserializedClient = objectMapper.readValue(ProperNewClient.JSON, Client.class);

        assertEquals(1, deserializedClient.getAddresses().size());
    }

    @Test
    public void shouldDeserializeMailAddress() throws IOException {
        Client deserializedClient = objectMapper.readValue(ProperNewClient.JSON, Client.class);

        assertEquals(ProperNewClient.E_MAIL, deserializedClient.getMailAddress());
    }

    @Test
    public void shouldDeserializeUUID() throws IOException {
        Client deserializedClient = objectMapper.readValue(ProperNewClient.JSON, Client.class);

        assertEquals(null, deserializedClient.getUuid());
    }

}