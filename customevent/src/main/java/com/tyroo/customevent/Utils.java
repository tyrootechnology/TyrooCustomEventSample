package com.tyroo.customevent;

/**
 * Created by sukhpalsingh on 11/04/18.
 */

public final class Utils {

    public static boolean checkClassExist(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
