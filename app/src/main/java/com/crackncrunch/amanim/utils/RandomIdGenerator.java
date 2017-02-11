package com.crackncrunch.amanim.utils;

import java.util.Random;

public class RandomIdGenerator {

    public static String generateId() {
        //return UUID.randomUUID().toString();
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < 24; ++i) {
            int randomNumber = (int) (new Random().nextFloat() * 9);
            randomString.append(randomNumber);
        }
        return randomString.toString();
    }

    /*
    public static String generateId() {
        StringBuilder chars = new StringBuilder();

        // add letters a to z
        for (int i = 97; i < 123; ++i) {
            chars.append((char) i);
        }

        // add numbers 0 to 9
        for (int i = 0; i < 10; ++i) {
            chars.append(i);
        }

        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < 24; ++i) {
            int randomNumber = (int) (new Random().nextFloat() * (chars.toString
                    ().length() - 1));
            randomString.append(chars.charAt(randomNumber));
        }
        return randomString.toString();
    }
*/
    public static int generateRemoteId() {
        return (int) (new Random().nextFloat() * 1000);
    }
}
