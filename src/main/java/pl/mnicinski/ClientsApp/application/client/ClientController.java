package pl.mnicinski.ClientsApp.application.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.mnicinski.ClientsApp.api.client.ClientAPI;
import pl.mnicinski.ClientsApp.domain.client.Client;
import pl.mnicinski.ClientsApp.domain.client.ClientService;
import pl.mnicinski.ClientsApp.domain.client.address.Address;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/clients")
public class ClientController implements ClientAPI {

    @Autowired ClientService clientService;

    @Override
    @GetMapping
    public @ResponseBody List<Client> getAllClients() {
        return clientService.getAll();
    }

    @Override
    @PostMapping
    public @ResponseBody Client add(@RequestBody Client client) {
        return clientService.add(client);
    }

    @Override
    @PostMapping("/{clientId}")
    public @ResponseBody Client addClientAddress(@PathVariable UUID clientId, @RequestBody Address address) {
        return clientService.newClientAddress(clientId, address);
    }
}
