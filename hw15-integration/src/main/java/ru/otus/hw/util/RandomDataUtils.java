package ru.otus.hw.util;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Random;

@UtilityClass
public class RandomDataUtils {

    private static final Random RANDOM = new Random();

    private static final List<String> COLORS = List.of("красный", "зеленый", "синий", "белый");

    public static int getRandomSize() {
        return RANDOM.nextInt(4) + 1;
    }

    public static double getRandomWight(int size) {
        return RANDOM.nextDouble() * 5 + size;
    }

    public static String getRandomColor(double weight) {
        if (weight < 5.0) {
            return COLORS.get(RANDOM.nextInt(COLORS.size() - 2));
        } else {
            return COLORS.get(RANDOM.nextInt(COLORS.size() - 2) + 2);
        }
    }

}
