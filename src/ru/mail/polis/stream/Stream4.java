package ru.mail.polis.stream;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by Nechaev Mikhail
 * Since 17/04/2017.
 */
class Stream4 {

    class StringConsumer implements Consumer<String> {
        final Map<String, String> map;
        StringConsumer() {
            map = new HashMap<>();
        }
        @Override
        public void accept(String s) {
            String[] flat = s.split(":");
            if (flat.length == 2) {
                map.put(flat[0], flat[1]);
            }
        }
        void combine(StringConsumer other) {
            map.putAll(other.get());
        }
        Map<String, String> get() {
            return map;
        }
    }
    private void run() {
        Map<String, String> map = Stream
                .of("A:1", "B:2", "C:3", "D:4")
                .collect(StringConsumer::new,
                        StringConsumer::accept,
                        StringConsumer::combine
                ).get();
        //{A=1, B=2, C=3, D=4}
        System.out.println(map);
    }

    public static void main(String[] args) {
        new Stream4().run();
    }
}
