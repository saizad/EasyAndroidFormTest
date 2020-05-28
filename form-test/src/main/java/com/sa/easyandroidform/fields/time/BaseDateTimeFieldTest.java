package com.sa.easyandroidform.fields.time;

import com.sa.easyandroidform.fields.BaseFieldTest;

import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

abstract public class BaseDateTimeFieldTest extends BaseFieldTest<String> {

    protected static final String FIELD_NAME = "random";
    protected static final String NOW = DateTime.now().toString();
    protected static final String NOW_PLUS_10_MINUTES = DateTime.now().plusMinutes(10).toString();


    public BaseDateTimeFieldTest(DateTimeField field, DateTimeField mandatoryField,
                                 DateTimeField mandatoryOgField, DateTimeField nonMandatoryOgField) {
        super(field, mandatoryField, mandatoryOgField, nonMandatoryOgField);
    }

    @Test
    protected void datetime_null(){
        field.setField(getInvalidFieldValue());
        Assertions.assertNull(((DateTimeField) field).dateTime());
    }

    @Test
    protected void datetime_not_null(){
        field.setField(getNewValidFieldValue());
        Assertions.assertNotNull(((DateTimeField) field).dateTime());
    }

    @Test
    protected void required_datetime_throws(){
        field.setField(getInvalidFieldValue());
        Assertions.assertThrows(NullPointerException.class, ((DateTimeField) field)::requiredDateTime);
    }

    @Test
    protected void required_datetime_does_not_throws(){
        field.setField(getNewValidFieldValue());
        Assertions.assertDoesNotThrow(((DateTimeField) field)::requiredDateTime);
    }

    @Test
    protected void resolveToDateTime_throws(){
        final DateTimeField field = (DateTimeField) this.field;
        Assertions.assertThrows(Exception.class, () -> field.resolveToDateTime("ADFASDFAS"));
    }

    @Test
    protected void resolveToDateTime_does_not_throws(){
        final DateTimeField field = (DateTimeField) this.field;
        Assertions.assertDoesNotThrow(() -> field.resolveToDateTime(getNewValidFieldValue()));
    }

    @NotNull
    @Override
    protected String fieldName() {
        return FIELD_NAME;
    }

    @NotNull
    @Override
    protected String getNewValidFieldValue() {
        return NOW_PLUS_10_MINUTES;
    }

}
