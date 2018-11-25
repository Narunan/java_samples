package ru.sample.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Nechaev Mikhail
 * Since 16/11/2018.
 */
class CheckedCollections {

    @SuppressWarnings({"unchecked", "UnnecessaryLocalVariable", "unused"})
    public static void main(String[] args) {
        List<Integer> genericListOfInteger = new ArrayList<>();
        genericListOfInteger.add(1);
        //genericListOfInteger.add("abc"); //нельзя. ошибка компиляции
        List uncheckedListWithoutGenericType = genericListOfInteger;
        uncheckedListWithoutGenericType.add("abc"); //так можно
        try {
            Integer secondElement = genericListOfInteger.get(1);
        } catch (ClassCastException expected) {
            //Но сломается при получении элемента
        }
        List<Integer> checkedGenericListOfInteger
                = Collections.checkedList(genericListOfInteger, Integer.class);
        checkedGenericListOfInteger.add(3);
        //checkedGenericListOfInteger.add("abc") //нельзя. ошибка компиляции
        List checkedListWithoutGenericType = checkedGenericListOfInteger;
        try {
            checkedListWithoutGenericType.add("abc");
        } catch (ClassCastException expected) {
            //А теперь сломается в момент добавления
        }
    }
}
