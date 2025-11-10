package com.rigg.ads.controller;

import com.rigg.ads.dto.CampaignDTO;
import com.rigg.ads.entity.Campaign;
import com.rigg.ads.service.CampaignService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @PostMapping("/client/{clientId}")
    public CampaignDTO createCampaign(@PathVariable Long clientId, @RequestBody Campaign campaign) {
        return campaignService.createCampaign(clientId, campaign);
    }

    @GetMapping
    public List<CampaignDTO> getAllCampaigns() {
        return campaignService.getAllCampaigns();
    }

    @GetMapping("/client/{clientId}")
    public List<CampaignDTO> getCampaignsByClient(@PathVariable Long clientId) {
        return campaignService.getCampaignsByClient(clientId);
    }

    @GetMapping("/{id}")
    public CampaignDTO getCampaignById(@PathVariable Long id) {
        return campaignService.getCampaignById(id);
    }

    @PutMapping("/{id}")
    public CampaignDTO updateCampaign(@PathVariable Long id, @RequestBody Campaign campaign) {
        return campaignService.updateCampaign(id, campaign);
    }

    @DeleteMapping("/{id}")
    public void deleteCampaign(@PathVariable Long id) {
        campaignService.deleteCampaign(id);
    }

    @GetMapping("/{id}/sheet")
    public List<List<Object>> getSheetData(@PathVariable Long id) {
        return campaignService.getCampaignSheetData(id);
    }

}

