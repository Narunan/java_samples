package ru.sample.network.complex.utils.wrappers.fields;

public class IntegerField extends Field<Integer> {

    public IntegerField(String name, Integer defaultValue) {
        super(name, defaultValue);
    }

    @Override
    protected Integer get(String value) {
        return Integer.valueOf(value);
    }
}
