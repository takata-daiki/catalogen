package org.pirkaengine.form.field;

import java.lang.annotation.Annotation;

import org.pirkaengine.form.FormMessage;
import org.pirkaengine.form.annotation.RangeInteger;
import org.pirkaengine.form.exception.ConvertException;
import org.pirkaengine.form.validator.RangeIntegerValidator;
import org.pirkaengine.form.validator.Validator;
import org.pirkaengine.form.validator.ValidatorBase;

/**
 * ????????.
 * @author shuji.w6e
 * @since 0.1.0
 */
public class IntegerField extends BaseField<Integer> {

    /**
     * ??????null????????????.
     */
    public IntegerField() {
        this(null);
    }

    /**
     * ??????????????????????.
     * @param defaultValue ??????
     */
    public IntegerField(Integer defaultValue) {
        setValue(defaultValue);
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.field.BaseField#getFieldType()
     */
    @Override
    public Class<Integer> getFieldType() {
        return Integer.class;
    }

    @Override
    protected Integer convert(String text) {
        if (text == null || text.isEmpty()) return null;
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            throw new ConvertException(e);
        }
    }

    @Override
    protected Validator<Integer> getValidator(FormMessage message, Annotation anno) {
        Class<? extends Annotation> type = anno.annotationType();
        ValidatorBase<Integer> validator = null;
        if (type == RangeInteger.class) {
            validator = RangeIntegerValidator.create(message, RangeInteger.class.cast(anno));
        }
        if (validator != null) {
            return validator;
        }
        return super.getValidator(message, anno);
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.field.BaseField#toString(java.lang.Object)
     */
    @Override
    protected String toString(Integer value) {
        if (value == null) return "";
        return value.toString();
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.field.BaseField#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof IntegerField)) return false;
        return super.equals(obj);
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.field.BaseField#hashCode()
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
