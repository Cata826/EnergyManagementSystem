package org.example;//package org.example;
//
//import com.opencsv.exceptions.CsvValidationException;
//import com.rabbitmq.client.*;
//import com.opencsv.CSVReader;
//import org.json.JSONObject;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.concurrent.TimeoutException;
//
//public class Simulator {
//    private static final String QUEUE_NAME = "sensor_data_queue";
////    private static final String CSV_FILE_PATH = "/app/sensor.csv";
////    private static final String CONFIG_FILE_PATH = "/app/configuration.txt";
//
//
//    private static final String CSV_FILE_PATH = "D:/Documente/Downloads/sensor.csv";
//    private static final String CONFIG_FILE_PATH = "D:/Documente/Downloads/configuration.txt";
//    public static void main(String[] args) {
//
//        try {
//            String deviceId = readDeviceIdFromConfig();
//            if (deviceId == null) {
//                System.err.println("Failed to read device ID from configuration file.");
//                return;
//            }
//
//
//            CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH));
//            String[] nextLine;
//
//            ConnectionFactory factory = new ConnectionFactory();
//            factory.setHost("localhost");
//            factory.setUsername("guest");
//            factory.setPassword("guest");
//
//            try (Connection connection = factory.newConnection();
//                 Channel channel = connection.createChannel()) {
//
//                channel.queueDeclare(QUEUE_NAME, true, false, false, null);
//
//                while (true) {
//                    try {
//                        if (!((nextLine = reader.readNext()) != null)) break;
//                    } catch (CsvValidationException e) {
//                        throw new RuntimeException(e);
//                    }
//                    String measurementValue = nextLine[0];
//
//                    JSONObject jsonMessage = new JSONObject();
//                    jsonMessage.put("timestamp", System.currentTimeMillis());
//                    jsonMessage.put("device_id", deviceId);
//                    jsonMessage.put("measurement_value", measurementValue);
//
//                    String message = jsonMessage.toString();
//                    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
//                    System.out.println("Sent: " + message);
//
//                    Thread.sleep(6000);
//                }
//            } catch (IOException | TimeoutException | InterruptedException e) {
//                e.printStackTrace();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static String readDeviceIdFromConfig() {
//        try (BufferedReader br = new BufferedReader(new FileReader(CONFIG_FILE_PATH))) {
//            return br.readLine();
//        } catch (IOException e) {
//            System.err.println("Error reading device ID from configuration file: " + e.getMessage());
//            return null;
//        }
//    }
//}

//package org.example;
//
//import com.opencsv.CSVReader;
//import com.opencsv.exceptions.CsvValidationException;
//import org.json.JSONObject;
//import com.rabbitmq.client.*;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeoutException;

//public class Simulator {
//    private static final String QUEUE_NAME = "sensor_data_queue";
//    private static final String CSV_FILE_PATH = "D:/Documente/Downloads/sensor.csv";
//    private static final String CONFIG_FILE_PATH_1 = "D:/Documente/Downloads/configuration.txt";
//    private static final String CONFIG_FILE_PATH_2 = "D:/Documente/Downloads/configuration2.txt";
//
//    public static void main(String[] args) {
//        ExecutorService executor = Executors.newFixedThreadPool(2);
//
//        executor.execute(new DeviceSimulator(CONFIG_FILE_PATH_1));
//
//
//        executor.execute(new DeviceSimulator(CONFIG_FILE_PATH_2));
//
//        executor.shutdown();
//    }
//
//    static class DeviceSimulator implements Runnable {
//        private final String configFilePath;
//
//        public DeviceSimulator(String configFilePath) {
//            this.configFilePath = configFilePath;
//        }
//
//        @Override
//        public void run() {
//            try {
//                String deviceId = readDeviceIdFromConfig(configFilePath);
//                if (deviceId == null) {
//                    System.err.println("Failed to read device ID from configuration file: " + configFilePath);
//                    return;
//                }
//
//                CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH));
//                String[] nextLine;
//
//                ConnectionFactory factory = new ConnectionFactory();
//                factory.setHost("localhost");
//                factory.setUsername("guest");
//                factory.setPassword("guest");
//
//                try (Connection connection = factory.newConnection();
//                     Channel channel = connection.createChannel()) {
//
//                    channel.queueDeclare(QUEUE_NAME, true, false, false, null);
//
//                    while (true) {
//                        try {
//                            if (!((nextLine = reader.readNext()) != null)) break;
//                        } catch (CsvValidationException e) {
//                            throw new RuntimeException(e);
//                        }
//
//                        String measurementValue = nextLine[0];
//
//                        JSONObject jsonMessage = new JSONObject();
//                        jsonMessage.put("timestamp", System.currentTimeMillis());
//                        jsonMessage.put("device_id", deviceId);
//                        jsonMessage.put("measurement_value", measurementValue);
//
//                        String message = jsonMessage.toString();
//                        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
//                        System.out.println("Sent from " + configFilePath + ": " + message);
//
//                        Thread.sleep(60000); // Wait 60 seconds
//                    }
//                } catch (IOException | TimeoutException | InterruptedException e) {
//                    e.printStackTrace();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        private static String readDeviceIdFromConfig(String configFilePath) {
//            try (BufferedReader br = new BufferedReader(new FileReader(configFilePath))) {
//                return br.readLine();
//            } catch (IOException e) {
//                System.err.println("Error reading device ID from configuration file: " + e.getMessage());
//                return null;
//            }
//        }
//    }
//}

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.json.JSONObject;
import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
//package org.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.json.JSONObject;
import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Simulator {
    private static final String QUEUE_NAME = "sensor_data_queue";
    private static final String CSV_FILE_PATH = "D:/Documente/Downloads/sensor.csv";
    private static final String CONFIG_FILE_PATH_1 = "D:/Documente/Downloads/configuration.txt";
    private static final String CONFIG_FILE_PATH_2 = "D:/Documente/Downloads/configuration2.txt";

    public static void main(String[] args) {
        try {
            String deviceId1 = readDeviceIdFromConfig(CONFIG_FILE_PATH_1);
            String deviceId2 = readDeviceIdFromConfig(CONFIG_FILE_PATH_2);

            if (deviceId1 == null || deviceId2 == null) {
                System.err.println("Failed to read device IDs from configuration files.");
                return;
            }

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            factory.setUsername("guest");
            factory.setPassword("guest");

            try (Connection connection = factory.newConnection();
                 Channel channel = connection.createChannel()) {

                channel.queueDeclare(QUEUE_NAME, true, false, false, null);

                while (true) { // Continuous sending loop
                    try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH))) {
                        String[] nextLine;

                        while ((nextLine = reader.readNext()) != null) {
                            String measurementValue = nextLine[0];

                            // Create and send the first message
                            JSONObject jsonMessage1 = new JSONObject();
                            jsonMessage1.put("timestamp", System.currentTimeMillis());
                            jsonMessage1.put("device_id", deviceId1);
                            jsonMessage1.put("measurement_value", measurementValue);

                            String message1 = jsonMessage1.toString();
                            channel.basicPublish("", QUEUE_NAME, null, message1.getBytes());
                            System.out.println("Sent for Device 1: " + message1);

                            // Create and send the second message
                            JSONObject jsonMessage2 = new JSONObject();
                            jsonMessage2.put("timestamp", System.currentTimeMillis());
                            jsonMessage2.put("device_id", deviceId2);
                            jsonMessage2.put("measurement_value", measurementValue);

                            String message2 = jsonMessage2.toString();
                            channel.basicPublish("", QUEUE_NAME, null, message2.getBytes());
                            System.out.println("Sent for Device 2: " + message2);

                            // Wait 20 seconds before processing the next line
                            Thread.sleep(20000);
                        }
                    }
                }
            }
        } catch (IOException | TimeoutException | CsvValidationException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String readDeviceIdFromConfig(String configFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(configFilePath))) {
            return br.readLine();
        } catch (IOException e) {
            System.err.println("Error reading device ID from configuration file: " + e.getMessage());
            return null;
        }
    }
}

