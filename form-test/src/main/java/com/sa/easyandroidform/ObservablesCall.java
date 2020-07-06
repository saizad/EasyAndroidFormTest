package com.sa.easyandroidform;

import com.sa.easyandroidform.fields.BaseField;
import com.sa.easyandroidform.fields.Field;

import org.mockito.Mockito;

import io.reactivex.observers.TestObserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ObservablesCall {
    private static final String FIELD_NAME = "random";


    public void fieldid_same(Field<?> field) {
        assertEquals(FIELD_NAME, field.getFieldId());
    }


    public void isMandatory_false(Field<?> field) {
        assertFalse(field.isMandatory());
    }


    public <M> void isMandatory_true(Field<M> field) {
        final Field<M> spyField = Mockito.spy(field);
        assertTrue(spyField.isMandatory());
    }


    public void ogfield_same() {
        final String any = "any";
        final Field<Object> spyField = Mockito.spy(new Field<>(FIELD_NAME, any, true));
        assertEquals(any, spyField.getOgField());
    }

    public static void isFieldValid(BaseField<?> field) {
        assertFalse(field.isValid());
    }

    public static void errorState(BaseField<?> field, boolean state) {
        final TestObserver<?> testObserver = field.errorStateObservable()
                .map(__ -> __.first)
                .test()
                .withTag(field.getFieldId())
                .assertValue(state);
        testObserver.dispose();
    }

    public static void isModifiedObservable(BaseField<?> field, boolean state) {
        final TestObserver<?> testObserver = field.modifiedObservable()
                .test()
                .withTag(field.getFieldId())
                .assertValue(state);
        testObserver.dispose();
    }

    public static void isValueModifiedObservable(BaseField<?> field, boolean state) {
        final TestObserver<?> testObserver = field.isValueModifiedObservable()
                .test()
                .withTag(field.getFieldId())
                .assertValue(state);
        testObserver.dispose();
    }

    public static void validObservable(BaseField<?> field, boolean state) {
        final TestObserver<?> testObserver = field.validObservable()
                .test()
                .withTag(field.getFieldId())
                .assertValue(state);
        testObserver.dispose();
    }

    public static void networkError(BaseField<?> field, int callCount) {
        final TestObserver<?> testObserver = field.networkError()
                .test()
                .withTag(field.getFieldId())
                .assertValueCount(callCount);
        testObserver.dispose();
    }

    public static void fieldUnsetObservable(BaseField<?> field, int callCount) {
        final TestObserver<?> testObserver = field.fieldUnsetObservable()
                .test()
                .withTag(field.getFieldId())
                .assertValueCount(callCount);
        testObserver.dispose();
    }

    public static void notEmptyValidObservable(BaseField<?> field, int callCount) {
        final TestObserver<?> testObserver =
                field
                        .notEmptyValidObservable()
                        .test()
                        .withTag(field.getFieldId())
                        .assertValueCount(callCount);
        testObserver.dispose();
    }

    public static void nonEmptyInvalidObservable(BaseField<?> field, int callCount) {
        final TestObserver<?> testObserver =
                field
                        .nonEmptyInvalidObservable()
                        .test()
                        .withTag(field.getFieldId())
                        .assertValueCount(callCount);
        testObserver.dispose();
    }

    public static void invalidObservable(BaseField<?> field, int callCount) {
        final TestObserver<?> testObserver =
                field
                        .invalidObservable()
                        .test()
                        .withTag(field.getFieldId())
                        .assertValueCount(callCount);
        testObserver.dispose();
    }

    public static void setObservable(BaseField<?> field, int callCount) {
        final TestObserver<?> testObserver =
                field.setObservable()
                        .test()
                        .withTag(field.getFieldId())
                        .assertValueCount(callCount);
        testObserver.dispose();
    }

    public static void observable(BaseField<?> field, int callCount) {
        final TestObserver<Object> objectTestObserver =
                field.observable()
                        .test()
                        .withTag(field.getFieldId())
                        .assertValueCount(callCount);
        objectTestObserver.dispose();
    }
}
