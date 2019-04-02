package pl.mnicinski.ClientsApp.domain.client;

import pl.mnicinski.ClientsApp.domain.client.address.Address;

import java.util.List;
import java.util.UUID;

public interface ClientService {

    Client add(Client client);

    Client newClientAddress(UUID clientId, Address address);

    List<Client> getAll();
}
