package com.rigg.ads.service;

import com.rigg.ads.dto.CampaignDTO;
import com.rigg.ads.entity.Campaign;
import com.rigg.ads.entity.Client;
import com.rigg.ads.repository.CampaignRepository;
import com.rigg.ads.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final ClientRepository clientRepository;
    private final GoogleSheetService googleSheetService;

    public CampaignService(CampaignRepository campaignRepository, ClientRepository clientRepository, GoogleSheetService googleSheetService) {
        this.campaignRepository = campaignRepository;
        this.clientRepository = clientRepository;
        this.googleSheetService = googleSheetService;
    }

    // Create campaign
    public CampaignDTO createCampaign(Long clientId, Campaign campaign) {
        Optional<Client> clientOpt = clientRepository.findById(clientId);
        if (!clientOpt.isPresent()) {
            throw new RuntimeException("Client not found");
        }
        campaign.setClient(clientOpt.get());
        campaign.setActiveStatus(1);
        Campaign saved = campaignRepository.save(campaign);
        return mapToDTO(saved);
    }

    // Fetch sheet data as JSON
    public List<List<Object>> getCampaignSheetData(Long id) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));

        try {
            return googleSheetService.readSheet(campaign.getSpreadsheetId());
        } catch (Exception e) {
            throw new RuntimeException("Failed to read sheet", e);
        }
    }

    // Get all campaigns
    public List<CampaignDTO> getAllCampaigns() {
        return campaignRepository.findByActiveStatusOrderByIdDesc(1).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Get campaigns by client
    public List<CampaignDTO> getCampaignsByClient(Long clientId) {
        return campaignRepository.findByClientId(clientId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Get single campaign
    public CampaignDTO getCampaignById(Long id) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));
        return mapToDTO(campaign);
    }

    // Update campaign
    public CampaignDTO updateCampaign(Long id, Campaign updatedCampaign) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));

        campaign.setCampaignName(updatedCampaign.getCampaignName());
        campaign.setGoal(updatedCampaign.getGoal());
        campaign.setDescription(updatedCampaign.getDescription());
        campaign.setAmount(updatedCampaign.getAmount());
        campaign.setStartDate(updatedCampaign.getStartDate());
        campaign.setEndDate(updatedCampaign.getEndDate());

        if (updatedCampaign.getClient() != null && updatedCampaign.getClient().getId() != null) {
            Long newClientId = updatedCampaign.getClient().getId();
            Client newClient = clientRepository.findById(newClientId)
                    .orElseThrow(() -> new RuntimeException("New client not found"));
            campaign.setClient(newClient);
        }

        // Update sheet info if provided
        if (updatedCampaign.getSpreadsheetId() != null && !updatedCampaign.getSpreadsheetId().isEmpty()) {
            campaign.setSpreadsheetId(updatedCampaign.getSpreadsheetId());
        }
        if (updatedCampaign.getSpreadsheetUrl() != null && !updatedCampaign.getSpreadsheetUrl().isEmpty()) {
            campaign.setSpreadsheetUrl(updatedCampaign.getSpreadsheetUrl());
        }

        Campaign saved = campaignRepository.save(campaign);
        return mapToDTO(saved);
    }

    // Delete campaign
    public void deleteCampaign(Long id) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));
        campaign.setActiveStatus(0);
        campaignRepository.save(campaign);

    }

    // Mapper method
    private CampaignDTO mapToDTO(Campaign c) {
        return new CampaignDTO(
                c.getId(),
                c.getCampaignName(),
                c.getGoal(),
                c.getDescription(),
                c.getAmount(),
                c.getStartDate(),
                c.getEndDate(),
                c.getClient() != null ? c.getClient().getId() : null,
                c.getClient() != null
                        ? c.getClient().getTitle() + " " + c.getClient().getFirstName() + " " + c.getClient().getLastName()
                        : null,
                c.getSpreadsheetId(),
                c.getSpreadsheetUrl(),
                c.getActiveStatus()
        );
    }
}

