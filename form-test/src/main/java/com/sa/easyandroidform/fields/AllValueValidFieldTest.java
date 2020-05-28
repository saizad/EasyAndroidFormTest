package com.sa.easyandroidform.fields;

public abstract class AllValueValidFieldTest<F> extends BaseFieldTest<F> {

    public AllValueValidFieldTest(BaseField<F> field, BaseField<F> mandatoryField, BaseField<F> mandatoryOgField, BaseField<F> nonMandatoryOgField) {
        super(field, mandatoryField, mandatoryOgField, nonMandatoryOgField);
    }

    @Override
    protected final F getInvalidFieldValue() {
        return null;
    }
}