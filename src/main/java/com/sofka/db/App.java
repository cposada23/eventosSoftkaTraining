package com.sofka.db;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.sofka.db.Utils.FakeData;
import com.sofka.db.Utils.UserInput;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final String host = "35.167.117.237";
    private static final String port = "3306";
    private static final String bd = "BDCamilo";
    private static final String user = "sofkauniversity";
    private static final String password = "Sofka2018";

    static Connection connection;
    static PreparedStatement preparedStatement;
    public static void main( String[] args ) {
        char option;
        boolean go = true;

        makeConnection();

        while (go) {
            printMenu();
            try {
                option = UserInput.readChar("Enter the option: ");
                switch (option ) {
                    case '1':
                        generateFakeData();
                        break;
                    case '2':
                        showCountries();
                        break;
                    case '3':
                        go = false;
                        break;
                    default:
                        log("Enter a valid input!");
                        break;
                }

            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    public static void generateFakeData(){
        int numberOfCities = 5;
        int numberOfCountries = 1;
        int numberOfAddresses = 50;
        int numberOfClients = numberOfAddresses;

        String [] countries    = FakeData.generateArrayOfCountries(numberOfCountries);
        showStringArray(countries);
        addCountries(countries);
        String[][] cities = new String[numberOfCountries][numberOfCities];

    }

    private static void addCountries (String[] countries ) {
        log("[add] -- [Countries] -- [" + countries.length + "]");
        for (int i = 0; i < countries.length; i++) {
            log("[adding country] -- [" + countries[i] + "]");
            addCountri(countries[i]);

        }
    }

    private static void showCountries () {
        log("------ COUNTRY LIST -------");
        try {
            String queryStatement = "SELECT * FROM pais";
            preparedStatement = connection.prepareStatement(queryStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.printf("%s %S %n", resultSet.getString("pais"), resultSet.getString("last_update"));
            }
            log("------ END OF COUNTRY LIST -------");
        }catch (SQLException e) {
            e.printStackTrace();
            log("[ERROR]  -- [LISTING COUNTRIES]");
        }
    }

    public static void addCountri(String countriName) {
        String insertCountryStatement = "INSERT INTO pais (pais, last_update) VALUES(?,?)";
        String now = getNow();
        try {
            preparedStatement = connection.prepareStatement(insertCountryStatement);
            preparedStatement.setString(1, countriName);
            preparedStatement.setString(2, now);
            preparedStatement.execute();
            log("[SUCCESS] -- [COUNTRY ADDED TO DATABASE] -- [" + countriName + "]");

        }catch (SQLException e) {
            log("[ERROR] -- [ADDING COUNTRY TO DATABASE] -- [" +  countriName + "]");
            e.printStackTrace();
        }
    }

    private static String getNow() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static void showStringArray(String [] array) {
        System.out.println("Showing array");
        for (int i = 0; i < array.length; i++) {
            System.out.printf("%d --- %s %n", i, array[i]);
        }
    }


    public static void makeConnection() {
        String url = "jdbc:mysql://" + host + "/" + bd;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            log("Congrats - Seems your MySQL JDBC Driver Registered!");
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                log("Connection Successful! Enjoy. Now it's time to push data");
            } else {
                log("Failed to make connection!");
            }
        }catch (ClassNotFoundException e) {
            log("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
            e.printStackTrace();
        }catch (SQLException e) {
            log("MySql connection failed");
            e.printStackTrace();
        }
    }

    public static void log(String string) {
        System.out.println(string);
    }

    public static void printMenu() {
        System.out.println("SOFKA EVENTS");
        System.out.println("What do you want to do?");
        System.out.println("1. Generate fake data");
        System.out.println("2. List countries");
        System.out.println("3. EXIT");
    }
}
