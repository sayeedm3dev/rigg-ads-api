package com.rigg.ads.service;

import com.rigg.ads.repository.CampaignRepository;
import com.rigg.ads.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HomeService {
    private final ClientRepository clientRepository;
    private final CampaignRepository campaignRepository;

    public HomeService(ClientRepository clientRepository, CampaignRepository campaignRepository) {
        this.clientRepository = clientRepository;
        this.campaignRepository = campaignRepository;
    }

    public Map<String, Long> getClientStats() {
        long activeClients = clientRepository.countByActiveStatus(1);

        Map<String, Long> stats = new HashMap<>();
        stats.put("activeClients", activeClients);
        return stats;
    }

    public Map<String, Long> getCampaignStats() {
        long activeCampaigns = campaignRepository.countByActiveStatus(1);

        Map<String, Long> stats = new HashMap<>();
        stats.put("activeCampaigns", activeCampaigns);
        return stats;
    }
}
