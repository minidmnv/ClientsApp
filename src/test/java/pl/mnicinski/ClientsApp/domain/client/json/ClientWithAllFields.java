package pl.mnicinski.ClientsApp.domain.client.json;

import java.util.UUID;

public class ClientWithAllFields {

    public static final UUID CLIENT_UUID = UUID.fromString("2c64bbfe-e127-495e-9670-96c0516667fd");
    public static final String LOGIN = "mnicinski";
    public static final String FLAT_NUMBER = "213";
    public static final String HOUSE_NUMBER = "6116";
    public static final String STREET = "Krakowska";
    public static final String E_MAIL = "nicinski.michal@gmail.com";

    public static final String JSON = "{\n" +
            "  \"addresses\": [\n" +
            "    {\n" +
            "      \"flatNumber\": \"" + FLAT_NUMBER + "\",\n" +
            "      \"houseNumber\": \"" + HOUSE_NUMBER + "\",\n" +
            "      \"street\": \"" + STREET + "\",\n" +
            "      \"uuid\": null" +
            "    }\n" +
            "  ],\n" +
            "  \"login\": \"" + LOGIN + "\",\n" +
            "  \"mailAddress\": \"" + E_MAIL + "\",\n" +
            "  \"uuid\": \"" + CLIENT_UUID + "\"" +
            "}";

}
