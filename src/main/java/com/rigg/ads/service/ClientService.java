package com.rigg.ads.service;

import com.rigg.ads.components.UserUtil;
import com.rigg.ads.entity.Client;
import com.rigg.ads.entity.Role;
import com.rigg.ads.entity.User;
import com.rigg.ads.repository.ClientRepository;
import com.rigg.ads.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder, UserRepository userRepository, EmailService emailService) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    // Create
    public Client addClient(Client client) {
        // Save client
        client.setActiveStatus(1);
        Client savedClient = clientRepository.save(client);

        // Generate username and password
        String username = UserUtil.generateUsername(client.getFirstName());
        String rawPassword = UserUtil.generateRandomPassword(8);

        // Create User entity
        User user = new User();
        user.setUsername(username);
        user.setEmail(client.getEmail());
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        user.setClient(savedClient);
        userRepository.save(user);

        // Send email
        String emailText = String.format(
                "Hello %s,\n\nYour account has been created.\nUsername: %s\nPassword: %s\n\nPlease change your password after first login.",
                client.getFirstName(), username, rawPassword
        );
        emailService.sendSimpleEmail(client.getEmail(), "Your Account Details", emailText);

        return savedClient;

    }

    // Read all
    public List<Client> getAllClients() {
//        return clientRepository.findAll();
        return clientRepository.findByActiveStatusOrderByIdDesc(1);
    }

    // Read by id
    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    // Update
    public Client updateClient(Long id, Client updatedClient) {
        Client client = getClientById(id);
        client.setTitle(updatedClient.getTitle());
        client.setFirstName(updatedClient.getFirstName());
        client.setLastName(updatedClient.getLastName());
        client.setEmail(updatedClient.getEmail());
        client.setCountryCode(updatedClient.getCountryCode());
        client.setPhone(updatedClient.getPhone());
        client.setBusinessName(updatedClient.getBusinessName());
        client.setBusinessType(updatedClient.getBusinessType());
        client.setWebsite(updatedClient.getWebsite());
        client.setAddress(updatedClient.getAddress());
        client.setCity(updatedClient.getCity());
        client.setCountry(updatedClient.getCountry());
        client.setImage(updatedClient.getImage());
        return clientRepository.save(client);
    }

    // Delete
    public void deleteClient(Long id) {
        Client client = getClientById(id);
        client.setActiveStatus(0);
        clientRepository.save(client);
    }
}

