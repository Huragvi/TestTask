package com.test.testtask.controller;

import com.test.testtask.DTO.ClientCreationDTO;
import com.test.testtask.DTO.ClientDTO;
import com.test.testtask.Entity.Client;
import com.test.testtask.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientCreationDTO clientCreationDTO) {
        Client client = clientService.createClient(clientCreationDTO);
        return ResponseEntity.ok(clientService.convertToDTO(client));
    }

    @PostMapping("/{clientId}/contacts")
    public ResponseEntity<ClientDTO> addContact(@PathVariable Long clientId, @RequestParam String contact) {
        Client client = clientService.addContact(clientId, contact);
        return ResponseEntity.ok(clientService.convertToDTO(client));
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable Long clientId) {
        Client client = clientService.getClientById(clientId);
        return ResponseEntity.ok(clientService.convertToDTO(client));
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }
    @GetMapping("/{clientId}/contacts/{type}")
    public ResponseEntity<List<String>> getContactsByType(@PathVariable Long clientId, @PathVariable String type) {
        List<String> contacts = clientService.getContactsByType(clientId, type);
        return ResponseEntity.ok(contacts);
    }
    @GetMapping("/{clientId}/contacts")
    public ResponseEntity<List<String>> getAllContacts(@PathVariable Long clientId) {
        List<String> contacts = clientService.getAllContacts(clientId);
        return ResponseEntity.ok(contacts);
    }
}