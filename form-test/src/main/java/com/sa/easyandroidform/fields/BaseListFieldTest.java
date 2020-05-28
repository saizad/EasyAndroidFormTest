package com.sa.easyandroidform.fields;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public abstract class BaseListFieldTest<F> extends BaseFieldTest<List<F>> {

    private List<F> more;
    private List<F> less;
    private List<F> equal;

   public BaseListFieldTest(ListField<F> field, ListField<F> mandatoryField, ListField<F> mandatoryOgField, ListField<F> nonMandatoryOgField) {
        super(field, mandatoryField, mandatoryOgField, nonMandatoryOgField);
        more = getListSizeMoreThanOGField();
        less = getListSizeLessThanOGField();
        equal = getListSizeEqualToOGField();
    }

    @NotNull
    @Override
    final protected List<F> getNewValidFieldValue() {
        return getListSizeEqualToOGField();
    }

    @Test
    final void is_more_provided(){
        assertTrue(more.size() > nonMandatoryOgField.getField().size());
    }

    @Test
    final void is_more_not_provided(){
        assertFalse(more.size() <= nonMandatoryOgField.getField().size());
    }

    @Test
    final void is_less_provided(){
        assertTrue(less.size() < nonMandatoryOgField.getField().size());
    }

    @Test
    final void is_less_not_provided(){
        assertFalse(less.size() >= nonMandatoryOgField.getField().size());
    }

    @Test
    final void is_equal_provided(){
        assertEquals(equal.size(), nonMandatoryOgField.getField().size());
    }

    @Test
    final void is_equal_not_provided(){
        final int size = nonMandatoryOgField.getField().size();
        assertFalse(equal.size() < size || equal.size() > size);
    }

    @Test
    final void mandatory_empty_true() {
        mandatoryField.setField(getNewValidFieldValue());
        assertTrue(mandatoryField.isValid());
    }

    @Test
    final void mandatory_empty_false() {
        mandatoryField.setField(Collections.emptyList());
        assertFalse(mandatoryField.isValid());
    }

    @Test
    final void list_hasValueChanged_less() {
        field.setField(less);
        assertTrue(field.hasValueChanged());
    }

    @Test
    final void list_hasValueChanged_more() {
        field.setField(more);
        assertTrue(field.hasValueChanged());
    }

    @Test
    final void list_hasValueChanged_equal() {
        field.setField(equal);
        assertTrue(field.hasValueChanged());
    }

    @Override
    protected final List<F> getInvalidFieldValue() {
        return null;
    }

    protected abstract @NonNull
    List<F> getListSizeMoreThanOGField();

    protected abstract @NonNull
    List<F> getListSizeLessThanOGField();

    protected abstract @NonNull
    List<F> getListSizeEqualToOGField();
}