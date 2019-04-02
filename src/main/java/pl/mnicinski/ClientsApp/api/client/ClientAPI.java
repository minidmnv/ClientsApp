package pl.mnicinski.ClientsApp.api.client;

import pl.mnicinski.ClientsApp.domain.client.Client;
import pl.mnicinski.ClientsApp.domain.client.address.Address;

import java.util.List;
import java.util.UUID;

public interface ClientAPI {

    /**
     * Returns all clients with their addresses.
     *
     * @return List of {@link Client}
     */
    List<Client> getAllClients();

    /**
     * Method used to add new client record.
     *
     * @param client
     * @return {@link Client} that represent new client.
     */
    Client add(Client client);

    /**
     *
     * Method used to add new address to existing client;
     *
     * @param clientId the {@link UUID} connected with existing client which we want to add address to
     * @param address the reference address which we want to add
     * @return {@link Client} that represents modified client entity with new address.
     */
    Client addClientAddress(UUID clientId, Address address);

}
