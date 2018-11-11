package ru.mail.polis.stream;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Nechaev Mikhail
 * Since 17/04/2017.
 */
class Stream6 {

    static class Student {
        private final String name;
        private final int grade;
        private Student(String name, int grade) {
            this.name = name;
            this.grade = grade;
        }
        static Student of(String name, int grade) {
            return new Student(name, grade);
        }

        @SuppressWarnings("unused")
        public String getName() {
            return name;
        }
        int getGrade() {
            return grade;
        }

        @Override
        public String toString() {
            return name + ":" + grade;
        }
    }
    private void run() {
        Map<Integer, List<Student>> map =
                Stream
                .of(Student.of("A", 1),
                    Student.of("B", 2),
                    Student.of("C", 1),
                    Student.of("D", 2))
                .collect(Collectors
                        .groupingBy(Student::getGrade)
                );
        //{1=[A:1, C:1], 2=[B:2, D:2]}
        System.out.println(map);
    }

    public static void main(String[] args) {
        new Stream6().run();
    }
}
