package org.valeryvash.util;

public class ServiceChecker {

    public static void throwIfNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("null observed in Service layer");
        }
    }

    public static void throwIfNotPositive(long id) {
        if (!(id > 0)){
            throw new IllegalArgumentException("Id shall have a positive value");
        }
    }

    public static void throwIfNotZero(long id) {
        if (!(id == 0)){
            throw new IllegalArgumentException("Id shall be zero");
        }
    }
}
