package ru.sample.collections;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Nechaev Mikhail
 * Since 16/11/2018.
 */
class CollectionsAlgorithms {

    public static void main(String[] args) {
        int intResult;

        List<Integer> src = IntStream.range(0, 20).boxed().collect(Collectors.toList());
        System.out.println(src); //[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]
        intResult = Collections.max(src);
        System.out.println(intResult); //19
        intResult = Collections.min(src);
        System.out.println(intResult); //0
        Collections.reverse(src); //O(n)
        System.out.println(src); //[19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0]

        Collections.rotate(src.subList(/* index inc */ 1, /* index exc */ 5), /* dist */ 2);
        System.out.println(src); //[19, [16, 15, 18, 17,] 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0]
        //[1,2,3,4] -> 1 -> [4,1,2,3]

        Collections.sort(src); //TimSort — (n log(n))
        System.out.println(src); //[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]

        Collections.swap(src, /* index */ 2, /* index */ 19);
        System.out.println(src); //[0, 1, 19, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 2]

        boolean booleanResult;

        booleanResult = Collections.replaceAll(src, /* oldValue */ 19, /* newValue */ -10);
        System.out.println(src); //[0, 1, -10, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 2]
        System.out.println(booleanResult); //true | true если была хотя бы одна замена

        intResult = Collections.binarySearch(src.subList(12, 17), /* value */ 14); //O(log(n))
        System.out.println(intResult); //2
        intResult = Collections.binarySearch(src.subList(12, 17), 0);
        //В общем случае, если элемента нет, то: -(место вставки) - 1
        System.out.println(intResult); //-1 == -(0) - 1

        Collections.fill(src, -1);
        System.out.println(src); //[-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1]

        List<Integer> dup = Arrays.asList(1, 1, 2, 2, 3, 1, 1, 2, 2, 3);
        System.out.println(dup); //[1, 1, 2, 2, 3, 1, 1, 2, 2, 3]

        intResult = Collections.indexOfSubList(dup, Arrays.asList(1, 1, 2));
        System.out.println(intResult); //0
        intResult = Collections.lastIndexOfSubList(dup, Arrays.asList(1, 1, 2));
        System.out.println(intResult); //5

        //Количество элементов
        intResult = Collections.frequency(dup, 1);
        System.out.println(intResult); //4

        // true если коллекции не имеют общих элементов
        booleanResult = Collections.disjoint(Set.of("A"), Set.of("B"));
        System.out.println(booleanResult); //true
        booleanResult = Collections.disjoint(Set.of("A", "B"), Set.of("B", "C"));
        System.out.println(booleanResult); //false

        Collections.copy(src /* dest */, dup /* src */); // при вызове должно быть srcSize <= dest.size();
        System.out.println(src); //[1, 1, 2, 2, 3, 1, 1, 2, 2, 3, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1]

        Collections.shuffle(src); //O(n)
        //Или можно передать свой экземпляр класса Random: shuffle(List<?> list, Random rnd)
        System.out.println(src); //например: [1, -1, -1, -1, 2, -1, -1, -1, -1, -1, 2, 1, -1, 3, -1, 1, 3, 1, 2, 2]
    }
}
