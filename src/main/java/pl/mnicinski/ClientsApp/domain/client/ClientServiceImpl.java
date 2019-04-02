package pl.mnicinski.ClientsApp.domain.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mnicinski.ClientsApp.domain.client.address.Address;

import java.util.List;
import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired ClientRepository clientRepository;

    @Override
    public Client add(Client client) {
        if(client.validateNew()) {
            return clientRepository.save(client);
        }

        throw new RuntimeException("Shouldn't be here");
    }

    @Override
    public Client newClientAddress(UUID clientId, Address address) {
        if(address.validateNew()) {
            return clientRepository.save(
                    clientRepository
                            .getOne(clientId)
                            .appendNewAddress(address)
            );
        }

        throw new RuntimeException("Shouldn't be here");
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }
}
