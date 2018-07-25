package com.sofka.db.Utils;



import java.io.BufferedReader;
import java.io.InputStreamReader;

public  final class UserInput {
    private static InputStreamReader in = new InputStreamReader(System.in);
    private static BufferedReader buffer = new BufferedReader(in);



    public static String readString(String message) throws Exception {

        try {
            return  readInput(message);
        }catch (Exception e) {
            throw new Exception("Enter a valid input");
        }
    }

    public static Double readDouble(String message) throws Exception {
        String input;
        double number;
        try {
            input = readInput(message);
            number = Double.parseDouble(input);
            return number;
        }catch (Exception e) {
            throw new Exception("Enter a valid number");
        }
    }

    public static int readInt(String message) throws Exception {
        String input;
        int number;
        try {
            input = readInput(message);
            number = Integer.parseInt(input);
            return number;
        }catch (Exception e) {
            throw new Exception("Enter a valid number");
        }
    }

    /**
     *
     * @param message
     * @return input
     * @throws Exception
     */
    public static String readInput(String message) throws Exception {
        String input;
        try {
            System.out.println(message);
            input = buffer.readLine();
            return input;
        }catch (Exception e) {
            throw new Exception();
        }
    }

    public static char readChar(String message) throws Exception {
        char input;
        try {
            input = readInput(message).charAt(0);
            return input;
        }catch (Exception e) {
            throw new Exception("Enter a valid input");
        }
    }
}