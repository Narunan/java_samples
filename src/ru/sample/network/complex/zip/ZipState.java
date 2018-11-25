package ru.sample.network.complex.zip;

import ru.sample.network.complex.utils.wrappers.fields.Field;
import ru.sample.network.complex.utils.wrappers.fields.IntegerField;

class ZipState {

    private ZipState() {
        /* empty */
    }

    public static final Field<Integer> TIMEOUT = new IntegerField("timeout", 0);
    public static final Field<Integer> COUNT = new IntegerField("count", 5);
}
