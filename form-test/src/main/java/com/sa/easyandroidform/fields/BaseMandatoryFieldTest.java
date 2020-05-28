package com.sa.easyandroidform.fields;

import org.junit.jupiter.api.Test;

import io.reactivex.exceptions.CompositeException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class BaseMandatoryFieldTest<F> extends BaseFieldTest<F> {

    public BaseMandatoryFieldTest(MandatoryField<F> mandatoryField, MandatoryField<F> nonEmptyMandatoryField) {
        super(mandatoryField, mandatoryField, nonEmptyMandatoryField, nonEmptyMandatoryField);
    }

    @Override
    final void isMandatory_false() {

    }

    @Override
    final void invalidObservable_not_called() {

    }

    @Override
    final void errorState_true() {

    }

    @Test
    void mandatory_field_null_validate(){
        mandatoryField.setField(null);
        assertThrows(CompositeException.class, mandatoryField::validate);
    }

    @Override
    void non_mandatory_field_null_validate() {
        nonMandatoryOgField.setField(null);
        assertThrows(CompositeException.class, nonMandatoryOgField::validate);
    }
}