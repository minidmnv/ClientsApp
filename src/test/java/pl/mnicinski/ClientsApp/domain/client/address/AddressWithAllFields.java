package pl.mnicinski.ClientsApp.domain.client.address;

import java.util.UUID;

public class AddressWithAllFields {

    public static final UUID ADDRESS_UUID = UUID.fromString("2c64bbfe-e127-495e-9670-96c0516667fd");
    public static final String FLAT_NUMBER = "213";
    public static final String HOUSE_NUMBER = "6116";
    public static final String STREET = "Krakowska";

    public static final String JSON =
            "    {\n" +
            "      \"flatNumber\": \"" + FLAT_NUMBER + "\",\n" +
            "      \"houseNumber\": \"" + HOUSE_NUMBER + "\",\n" +
            "      \"street\": \"" + STREET + "\",\n" +
            "      \"uuid\": \"" + ADDRESS_UUID + "\"\n" +
            "    }\n";

}
