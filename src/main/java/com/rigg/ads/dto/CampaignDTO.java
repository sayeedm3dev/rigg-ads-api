package com.rigg.ads.dto;

import java.time.LocalDate;

public class CampaignDTO {
    private Long id;
    private String campaignName;
    private String goal;
    private String description;
    private Double amount;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long clientId;
    private String clientName;

    private String spreadsheetId;    // Sheet ID
    private String spreadsheetUrl;   // Sheet link

    private Integer activeStatus;

    public CampaignDTO() {
    }

    public CampaignDTO(Long id, String campaignName, String goal, String description, Double amount, LocalDate startDate, LocalDate endDate, Long clientId, String clientName, String spreadsheetId, String spreadsheetUrl, Integer activeStatus) {
        this.id = id;
        this.campaignName = campaignName;
        this.goal = goal;
        this.description = description;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.clientId = clientId;
        this.clientName = clientName;
        this.spreadsheetId = spreadsheetId;
        this.spreadsheetUrl = spreadsheetUrl;
        this.activeStatus = activeStatus;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getSpreadsheetUrl() {
        return spreadsheetUrl;
    }

    public void setSpreadsheetUrl(String spreadsheetUrl) {
        this.spreadsheetUrl = spreadsheetUrl;
    }

    public String getSpreadsheetId() {
        return spreadsheetId;
    }

    public void setSpreadsheetId(String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
    }

    public Integer getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Integer activeStatus) {
        this.activeStatus = activeStatus;
    }
}
