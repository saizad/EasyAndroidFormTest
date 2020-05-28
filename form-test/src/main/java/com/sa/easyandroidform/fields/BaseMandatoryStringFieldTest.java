package com.sa.easyandroidform.fields;

public abstract class BaseMandatoryStringFieldTest extends BaseMandatoryFieldTest<String> {

    public BaseMandatoryStringFieldTest(MandatoryStringField mandatoryField, MandatoryStringField nonEmptyMandatoryField) {
        super(mandatoryField, nonEmptyMandatoryField);
    }

    @Override
    protected String getInvalidFieldValue() {
        return "";
    }

}