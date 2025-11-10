package com.rigg.ads.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleSheetService {

    private Sheets sheetsService;

    @PostConstruct
    public void init() throws Exception {

        GoogleCredentials credentials = GoogleCredentials
                .fromStream(new FileInputStream("src/main/resources/credentials.json"))
                .createScoped(
                        Arrays.asList(
                                "https://www.googleapis.com/auth/spreadsheets"
                        )
                );

        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

        sheetsService = new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                requestInitializer
        ).setApplicationName("Campaign App").build();
    }

    // Read rows
    public List<List<Object>> readSheet(String spreadsheetId) throws Exception {
        ValueRange response = sheetsService.spreadsheets().values()
                .get(spreadsheetId, "Sheet1!A2:H10000")
                .execute();
        return response.getValues();
    }

    // Append a new row
    public void appendRow(String spreadsheetId, List<Object> row) throws Exception {
        ValueRange body = new ValueRange().setValues(Collections.singletonList(row));
        sheetsService.spreadsheets().values()
                .append(spreadsheetId, "Sheet1!A:H", body)
                .setValueInputOption("RAW")
                .execute();
    }
}

