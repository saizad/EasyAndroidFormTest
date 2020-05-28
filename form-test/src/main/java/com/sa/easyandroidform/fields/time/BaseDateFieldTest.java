package com.sa.easyandroidform.fields.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

abstract public class BaseDateFieldTest extends BaseDateTimeFieldTest {

    public BaseDateFieldTest(DateField field, DateField mandatoryField, DateField mandatoryOgField, DateField nonMandatoryOgField) {
        super(field, mandatoryField, mandatoryOgField, nonMandatoryOgField);
    }

    @Test
    protected final void dateTime_withTimeAtStartOfDay() {
        final DateField dateField = (DateField) this.mandatoryOgField;
        Assertions.assertEquals(0, dateField.requiredDateTime().getMillisOfDay());
    }


}
