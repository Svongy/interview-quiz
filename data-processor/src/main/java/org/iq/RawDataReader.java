package org.iq;

import lombok.extern.java.Log;
import org.iq.utils.ConfigReader;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/*
Class is responsible to getting Questions data from Google Sheets and save it locally to resources folder.
In order to specify your own sheet ID refer to a 'resources/application.properties' and change the following:
** GOOGLE_SHEET_ID - with your sheet ID
** EXPORT_FORMAT - supported csv (TODO: json)
** OUT_FILE_PATH - with your desired out file location
 */
@Log
public class RawDataReader {
    private static final String G_SHEET_URL = ConfigReader.readProperty("GOOGLE_SHEET_URL");
    private static final StringBuilder GET_DATA_RESPONSE = new StringBuilder();
    private static final File RESULT_FILE_PATH = new File(ConfigReader.readProperty("OUT_FILE_PATH"));
    private static final boolean DATA_FETCH_FLAG = Boolean.parseBoolean(ConfigReader.readProperty("DATA_FETCH_FLAG"));

    public static void getQuestionsDataSet() {
        if (DATA_FETCH_FLAG | !RESULT_FILE_PATH.exists()) {
            try {
                fetchData();
                saveData();
            } catch (IOException e) {
                log.severe("Exception occurred while retrieving or saving questions data with a message " +
                        e.getMessage());
            }
        } else {
            log.info("File with data is present, skipping fetch...");
        }
    }

    private static void fetchData() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(G_SHEET_URL).openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            GET_DATA_RESPONSE.setLength(0);
            while ((line = reader.readLine()) != null) {
                GET_DATA_RESPONSE.append(line).append("\n");
            }
            reader.close();
        } else {
            log.severe("Failed to fetch data. Response code: " + responseCode);
        }
    }

    private static void saveData() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESULT_FILE_PATH, StandardCharsets.UTF_8))) {
            writer.append(GET_DATA_RESPONSE);
        }
    }

}
