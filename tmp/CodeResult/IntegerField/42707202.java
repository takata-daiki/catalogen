package segal.gui;

public class IntegerField extends ValueField<Integer> {

    public IntegerField(String labelText, Integer startValue) {
        super(labelText, startValue);
    }

    @Override
    protected Integer parseString(String s) {
        return Integer.parseInt(s);
    }
}
