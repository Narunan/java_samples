package ru.sample.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Nechaev Mikhail
 * Since 16/11/2018.
 */
public class EnumSetMapExample {

    enum Color {
        RED,
        GREEN,
        BLUE,
        CYAN,
    }

    public static void main(String[] args) {
        Set<Color> noneOf = EnumSet.noneOf(Color.class);
        Set<Color> allColors = EnumSet.allOf(Color.class);
        Set<Color> green = EnumSet.of(Color.GREEN);
        Collection<Color> colorCollection = Arrays.asList(Color.RED, Color.BLUE);
        EnumSet<Color> redBlue = EnumSet.copyOf(colorCollection);

        //или EnumSet.complementOf((EnumSet<Color>) redBlue), где Set<Color> redBlue = EnumSet.copyOf(colorCollection);
        Set<Color> greenCyan = EnumSet.complementOf(redBlue); //complementOf(EnumSet<E> s)

        //У элементов enum есть неявный int ordinal, обозначающий позицию элемента в порядке объявления
        Set<Color> greenBlueCyan = EnumSet.range(Color.GREEN, Color.CYAN);

        System.out.println(noneOf); //[]
        System.out.println(allColors); //[RED, GREEN, BLUE, CYAN]
        System.out.println(green); //[GREEN]
        System.out.println(redBlue); //[RED, BLUE]
        System.out.println(greenCyan); //[GREEN, CYAN]
        System.out.println(greenBlueCyan); //[GREEN, BLUE, CYAN]

        Map<Color, Integer> enumMap = new EnumMap<>(Color.class);
        enumMap.put(Color.RED, 10);
        enumMap.put(Color.GREEN, 20);
        System.out.println(enumMap); //{RED=10, GREEN=20}
    }
}
