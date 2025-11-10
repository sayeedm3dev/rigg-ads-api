package com.rigg.ads.controller;

import com.rigg.ads.entity.Client;
import com.rigg.ads.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // Create Client with image
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Client> createClient(
            @RequestParam String title,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String countryCode,
            @RequestParam String phone,
            @RequestParam String businessName,
            @RequestParam String businessType,
            @RequestParam String website,
            @RequestParam String address,
            @RequestParam String city,
            @RequestParam String country,
            @RequestParam(required = false) MultipartFile image
    ) throws IOException {
        Client client = new Client();
        client.setTitle(title);
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setEmail(email);
        client.setCountryCode(countryCode);
        client.setPhone(phone);
        client.setBusinessName(businessName);
        client.setBusinessType(businessType);
        client.setWebsite(website);
        client.setAddress(address);
        client.setCity(city);
        client.setCountry(country);

        if (image != null && !image.isEmpty()) {
            // Convert image to Base64 string
            String base64Image = Base64.getEncoder().encodeToString(image.getBytes());
            client.setImage(base64Image);
        }

        return ResponseEntity.ok(clientService.addClient(client));
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    // Read by ID
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    // Update Client
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<Client> updateClient(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String countryCode,
            @RequestParam String phone,
            @RequestParam String businessName,
            @RequestParam String businessType,
            @RequestParam String website,
            @RequestParam String address,
            @RequestParam String city,
            @RequestParam String country,
            @RequestParam(required = false) MultipartFile image
    ) throws IOException {
        Client updatedClient = new Client();
        updatedClient.setTitle(title);
        updatedClient.setFirstName(firstName);
        updatedClient.setLastName(lastName);
        updatedClient.setEmail(email);
        updatedClient.setCountryCode(countryCode);
        updatedClient.setPhone(phone);
        updatedClient.setBusinessName(businessName);
        updatedClient.setBusinessType(businessType);
        updatedClient.setWebsite(website);
        updatedClient.setAddress(address);
        updatedClient.setCity(city);
        updatedClient.setCountry(country);

        if (image != null && !image.isEmpty()) {
            String base64Image = Base64.getEncoder().encodeToString(image.getBytes());
            updatedClient.setImage(base64Image);
        }

        return ResponseEntity.ok(clientService.updateClient(id, updatedClient));
    }

    // Delete Client
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
