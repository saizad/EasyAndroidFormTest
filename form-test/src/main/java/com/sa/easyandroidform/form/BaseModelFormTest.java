package com.sa.easyandroidform.form;

import androidx.annotation.NonNull;

import com.sa.easyandroidform.ErrorField;
import com.sa.easyandroidform.ObservablesCall;
import com.sa.easyandroidform.StringUtils;
import com.sa.easyandroidform.Utils;
import com.sa.easyandroidform.fields.BaseField;
import com.sa.easyandroidform.fields.MandatoryStringField;
import com.sa.easyandroidform.fields.StringField;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Any;

import java.util.List;

import io.reactivex.observers.TestObserver;

import static com.sa.easyandroidform.Utils.randomListItem;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


abstract public class BaseModelFormTest<F extends FormModel<?>> {

    private final static String MANDATORY_STRING_FIELD_NAME = "mandatory-base-form-test-string";
    private final static String STRING_FIELD_NAME = "base-form-test-string";

    protected @NonNull F form;

    @BeforeEach
    void setUp() {
        form = initForm();
    }

    abstract public @NonNull F initForm();

    public abstract BaseField<?> changeFormFieldToAnyValue();

    public abstract void setValidValue(BaseField<?> field);

    private StringField addField() {
        return addField(STRING_FIELD_NAME);
    }

    private StringField addField(@NonNull String fieldName) {
        return new StringField(fieldName, Any.ANY.toString());
    }

    private MandatoryStringField mandatoryAddField() {
        return mandatoryAddField(MANDATORY_STRING_FIELD_NAME);
    }

    private MandatoryStringField mandatoryAddField(@NonNull String fieldName) {
        return new MandatoryStringField(fieldName, StringUtils.random(10));
    }

    protected List<BaseField<?>> mandatoryFieldList() {
        return Utils.filter(form.fields, BaseField::isMandatory);
    }

    protected List<BaseField<?>> nonMandatoryFieldList() {
        return Utils.filter(form.fields, type -> !type.isMandatory());
    }

    protected List<BaseField<?>> setFieldList() {
        return Utils.filter(form.fields, BaseField::isSet);
    }

    protected List<BaseField<?>> notSetFieldList() {
        return Utils.filter(form.fields, type -> !type.isSet());
    }

    @Test
    void buildForm__fail() {
        final List<BaseField<?>> mandatoryFields = mandatoryFieldList();
        if (!mandatoryFields.isEmpty()) {
            final BaseField<?> baseField = randomListItem(mandatoryFields);
            baseField.setField(null);
            final Object build = form.build();
            assertNull(build);
        }
    }

    @Test
    public void buildForm__success() {
        for (BaseField<?> field : form.fields) {
            setValidValue(field);
        }
        Object build = form.build();
        Assertions.assertNotNull(build);
    }

    @Test
    public void requiredBuild__Fail() {
        final List<BaseField<?>> mandatoryList = mandatoryFieldList();
        if (!mandatoryList.isEmpty()) {
            final BaseField<?> baseField = randomListItem(mandatoryList);
            baseField.setField(null);
            assertThrows(IllegalStateException.class, () -> form.requiredBuild());
        }
    }

    @Test
    public void requiredBuild_Success() {
        for (BaseField<?> field : form.fields) {
            setValidValue(field);
        }
        Assertions.assertDoesNotThrow(() -> form.requiredBuild());
    }

    @Test
    void requiredField__throws() {
        assertThrows(UnsupportedOperationException.class, form::requiredField);
    }

    @Test
    void getField__throws() {
        assertThrows(UnsupportedOperationException.class, form::getField);
    }

    @Test
    void setIsMandatory__throws() {
        assertThrows(UnsupportedOperationException.class, () -> form.setIsMandatory(false));
    }

    @Test
    void isFormModified__true() {
        final List<BaseField<?>> list = setFieldList();
        if (!list.isEmpty()) {
            changeFormFieldToAnyValue();
            assertTrue(form.isModified());
        }
    }

    @Test
    void isFormModified__false() {
        assertFalse(form.isModified());
    }

    @Test
    void hasValueChanged__true() {
        changeFormFieldToAnyValue();
        assertTrue(form.hasValueChanged());
    }

    @Test
    void hasValueChanged__false() {
        assertFalse(form.hasValueChanged());
    }

    @Test
    void isValid__false() {
        final List<BaseField<?>> mandatoryList = mandatoryFieldList();
        if (!mandatoryList.isEmpty()) {
            final BaseField<?> baseField = randomListItem(mandatoryList);
            baseField.setField(null);
            assertFalse(form.isValid());
        }
    }

    @Test
    void isValid__true() {
        for (BaseField<?> field : form.fields) {
            setValidValue(field);
        }
        assertTrue(form.isValid());
    }

    @Test
    void isAllMandatoryFieldsProvided__false() {
        final List<BaseField<?>> mandatoryList = mandatoryFieldList();
        for (BaseField<?> baseField : mandatoryList) {
            baseField.setField(null);
        }
        if (!mandatoryList.isEmpty()) {
            assertFalse(form.isAllMandatoryFieldsProvided());
        }
    }

    @Test
    void isAllMandatoryFieldsProvided__true() {
        for (BaseField<?> field : mandatoryFieldList()) {
            setValidValue(field);
        }
        assertTrue(form.isAllMandatoryFieldsProvided());
    }

    @Test
    void add_field__is_valid() {
        for (BaseField<?> field : form.fields) {
            setValidValue(field);
        }
        final MandatoryStringField mandatoryField = mandatoryAddField(MANDATORY_STRING_FIELD_NAME);
        form.add(mandatoryField);
        assertTrue(form.isValid());
    }

    @Test
    void add_field__is_invalid() {
        final MandatoryStringField field = mandatoryAddField();
        form.add(field);
        field.setField(null);
        assertFalse(form.isValid());
    }

    @Test
    void findField_Failed() {
        form.add(mandatoryAddField());
        assertNull(form.findField(StringUtils.random(10)));
    }

    @Test
    void findField_Success() {
        form.add(mandatoryAddField());
        assertNotNull(form.findField(MANDATORY_STRING_FIELD_NAME));
    }

    @Test
    void requiredFindField_Failed() {
        form.add(mandatoryAddField());
        assertThrows(IllegalStateException.class, () -> form.requiredFindField(StringUtils.random(10)));
    }

    @Test
    void requiredFindField_Success() {
        form.add(mandatoryAddField());
        assertDoesNotThrow(() -> form.requiredFindField(MANDATORY_STRING_FIELD_NAME));
    }

    @Test
    void form_emptyFields() {
        assertFalse(form.fields.isEmpty());
    }

    @Test
    void isSet_False() {
        for (BaseField<?> field : form.fields) {
            field.clear();
        }
        assertFalse(form.isSet());
    }

    @Test
    void errorCount_mandatory() {
        final List<BaseField<?>> mandatoryFields = mandatoryFieldList();
        for (BaseField<?> field : mandatoryFields) {
            field.setField(null);
        }
        final List<ErrorField> errors = form.errors();
        assertEquals(errors.size(), mandatoryFields.size());
    }

    @Test
    void errorCount_non_mandatory() {
        for (BaseField<?> baseField : mandatoryFieldList()) {
            setValidValue(baseField);
        }

        for (BaseField<?> field : nonMandatoryFieldList()) {
            field.setField(null);
        }

        final List<ErrorField> errors = form.errors();
        assertEquals(errors.size(), 0, Utils.listToString(errors, errorField ->
                Utils.compositeExceptionMessage(errorField.getException())));
    }

    @Test
    void addFieldCountMatch() {
        int prevSize = form.fields.size();
        form.add(addField());
        int newSize = form.fields.size();
        assertEquals(prevSize, newSize - 1);
    }

    @Test
    void clearForm__success() {
        form.clear();
        assertFalse(form.isSet());
    }

    @Test
    void isMandatory__false() {
        final FormModelHelper formModelHelper = new FormModelHelper(addField(StringUtils.random(3)), addField(), addField(StringUtils.random(3)));
        assertFalse(formModelHelper.isMandatory());
    }

    @Test
    void isMandatory__true() {
        final FormModelHelper formModelHelper = new FormModelHelper(mandatoryAddField(), mandatoryAddField(StringUtils.random(3)), addField());
        assertTrue(formModelHelper.isMandatory());
    }

    @Test
    void duplicate_add_fieldId__throws() {
        assertThrows(Exception.class, () -> form.add(mandatoryAddField(), mandatoryAddField()));
    }

    @Test
    void duplicate_add_fieldId__does_not_throws() {
        assertDoesNotThrow(() -> form.add(mandatoryAddField(StringUtils.random(3))));
    }

    @Test
    void duplicate_fieldId__throws() {
        assertThrows(Exception.class, () -> new FormModelHelper(mandatoryAddField(), addField(), addField()));
    }

    @Test
    void duplicate_fieldId__does_not_throws() {
        assertDoesNotThrow(() -> {
            new FormModelHelper(mandatoryAddField(), addField());
        });
    }

    @Test
    void formEdited__1_field() {
        final TestObserver<BaseField<?>> test = form.formEdited().skip(form.size()).test();
        changeFormFieldToAnyValue();
        test.assertValueCount(1);
    }

    @Test
    void formEdited__1_same_field() {
        final TestObserver<BaseField<?>> test = form.formEdited().skip(form.size()).test();
        final BaseField<?> baseField = changeFormFieldToAnyValue();
        test.assertValue(baseField);
    }

    @Test
    void observable__1_field() {
        final TestObserver<Object> test = form.observable().skip(form.size()).test();
        changeFormFieldToAnyValue();
        test.assertValueCount(1);
    }

    @Test
    void modifiedObservable__no_value() {
        final TestObserver<Boolean> test = form.modifiedObservable().skip(form.size()).test();
        test.assertNoValues();
    }

    @Test
    void modifiedObservable__true() {
        final TestObserver<Boolean> test = form.modifiedObservable().skip(form.size()).test();
        changeFormFieldToAnyValue();
        test.assertValue(true);
    }

    @Test
    void modifiedObservable__false() {
        final List<BaseField<?>> notSetFieldList = notSetFieldList();
        TestObserver<Boolean> test = form.modifiedObservable().skip(form.size()).test();
        Boolean[] values = new Boolean[notSetFieldList.size()];
        for (int i = 0; i < notSetFieldList.size(); i++) {
            notSetFieldList.get(i).setField(null);
            values[i] = false;
        }
        test.assertValues(values);
    }

    @Test
    void setObservable_called() {
        final List<BaseField<?>> setFields = setFieldList();
        for (BaseField<?> setField : setFields) {
            int countSize = 1;
            if (setField instanceof FormModel) {
                final FormModel<?> formModel = (FormModel<?>) setField;
                countSize = formModel.size();
            }
            setValidValue(setField);
            ObservablesCall.setObservable(setField, countSize);
        }
    }
}
