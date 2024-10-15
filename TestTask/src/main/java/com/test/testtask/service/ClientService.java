package com.test.testtask.service;

import com.test.testtask.DTO.ClientCreationDTO;
import com.test.testtask.DTO.ClientDTO;
import com.test.testtask.Entity.Client;
import com.test.testtask.repository.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepo clientRepo;

    @Autowired
    public ClientService(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    public Client createClient(ClientCreationDTO clientCreationDTO) {
        Client client = Client.builder()
                .name(clientCreationDTO.getName())
                .contacts(clientCreationDTO.getContacts())
                .build();
        return clientRepo.save(client);
    }

    public Client addContact(Long clientId, String contact) {
        Client client = getClientById(clientId);
        client.getContacts().add(contact);
        return clientRepo.save(client);
    }

    public Client getClientById(Long clientId) {
        return clientRepo.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    public List<ClientDTO> getAllClients() {
        return clientRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ClientDTO convertToDTO(Client client) {
        return ClientDTO.builder()
                .name(client.getName())
                .contacts(client.getContacts().stream().collect(Collectors.toSet()))
                .build();
    }

    public List<String> getContactsByType(Long clientId, String type) {
        Client client = getClientById(clientId);
        return client.getContacts().stream()
                .filter(contact -> contact.startsWith(type))
                .collect(Collectors.toList());
    }
    public List<String> getAllContacts(Long clientId) {
        Client client = getClientById(clientId);
        return new ArrayList<>(client.getContacts());
    }
}