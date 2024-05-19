package com.jansouza.springboot.lab.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLStreamHandler;
import java.net.URLConnection;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;

public class SimulatedApiCall {

    private static final Logger logger = LoggerFactory.getLogger(SimulatedApiCall.class);

    public static void main(String[] args) {
        // Testa a simulação da chamada de API
        try {
            HttpURLConnection connection = simulateApiCall("https://localhost:8080/test");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println("Simulated API Response: " + response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HttpURLConnection simulateApiCall(String apiUrl) throws IOException {
        URL url = new URL(null, apiUrl, new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(URL url) throws IOException {
                return new SimulatedHttpURLConnection(url);
            }
        });
        return (HttpURLConnection) url.openConnection();
    }

    // Classe para simular HttpURLConnection
    static class SimulatedHttpURLConnection extends HttpURLConnection {

        private boolean connected = false;

        protected SimulatedHttpURLConnection(URL url) {
            super(url);
        }

        @Override
        public void connect() throws IOException {
            if (!connected) {
                connected = true;
            }
        }

        @Override
        public void disconnect() {
            connected = false;
        }

        @Override
        public boolean usingProxy() {
            return false;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            String simulatedResponse = simulateApiResponse();
            return new ByteArrayInputStream(simulatedResponse.getBytes());
        }

        // Simula a resposta da API
        private String simulateApiResponse() {
            addRandomSleep();
            Random random = new Random();
            JsonObject responseJson = new JsonObject();
            boolean isSuccess = random.nextBoolean();
            responseJson.addProperty("status", isSuccess ? "success" : "error");
            JsonObject dataJson = new JsonObject();
            if (isSuccess) {
                dataJson.addProperty("message", getRandomSuccessMessage(random));
                dataJson.addProperty("id", random.nextInt(1000));
                dataJson.addProperty("timestamp", System.currentTimeMillis());
            } else {
                dataJson.addProperty("errorCode", random.nextInt(100));
                dataJson.addProperty("errorMessage", getRandomErrorMessage(random));
            }
            responseJson.add("data", dataJson);
            return responseJson.toString();
        }

        // Mensagem de sucesso aleatória
        private String getRandomSuccessMessage(Random random) {
            String[] messages = {
                "Operation completed successfully.",
                "Data retrieved successfully.",
                "Your request was processed correctly.",
                "Success! Your action was completed.",
                "Everything went smoothly!"
            };
            return messages[random.nextInt(messages.length)];
        }

        // Mensagem de erro aleatória
        private String getRandomErrorMessage(Random random) {
            String[] messages = {
                "An unknown error occurred.",
                "Failed to process the request.",
                "The requested resource was not found.",
                "Validation error occurred.",
                "Permission denied."
            };
            return messages[random.nextInt(messages.length)];
        }

        // Adiciona um atraso aleatório
        private void addRandomSleep() {
            Random random = new Random();
            int sleepTime = 500 + random.nextInt(1500); // Entre 500ms e 2000ms
            try {
                logger.debug("addRandomSleep - " + sleepTime);
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
