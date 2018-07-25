package com.sofka.db.Utils;

import com.github.javafaker.Faker;
import org.json.JSONObject;

public class FakeData {

    private static Faker faker = new Faker();

    public static String countryGenerator() {
        String countryName = faker.address().country();
        return countryName;
    }

    public static String [] generateArrayOfCountries(int numberOfCountries) {
        String [] countriesArray = new String[numberOfCountries];
        for (int i = 0; i < numberOfCountries; i++) {
            countriesArray[i] = countryGenerator();
        }
        return  countriesArray;
    }

    public static String cityGenerator(){
        String cityName = faker.address().cityName();
        return cityName;
    }
}
