package me.friwi.tello4j.util;

public class TelloArgumentVerifier {
    public static void checkRange(int x, int min, int max, String description) {
        if (x < min || x > max) {
            throw new IllegalArgumentException(description.replace("%x", String.valueOf(x))
                    .replace("%min", String.valueOf(min))
                    .replace("%max", String.valueOf(max)));
        }
    }

    public static void checkRangeAllowNegative(int x, int min, int max, String description) {
        checkRange(x < 0 ? -x : x, min, max, description);
    }

    public static void checkRangeAllowNegativeOne(int[] xValues, int min, int max, String description) {
        for (int x : xValues) {
            if (x < 0) x = -x;
            if (x >= min && x <= max) {
                return; //Found one
            }
        }
        //Found none
        throw new IllegalArgumentException(description
                .replace("%min", String.valueOf(min))
                .replace("%max", String.valueOf(max)));
    }

    public static void checkRangeAllowNegativeAll(int[] xValues, int min, int max, String description) {
        for (int x : xValues) {
            if (x < 0) x = -x;
            if (x < min || x > max) {
                throw new IllegalArgumentException(description
                        .replace("%min", String.valueOf(min))
                        .replace("%max", String.valueOf(max)));
            }
        }
    }
}
