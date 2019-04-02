package pl.mnicinski.ClientsApp.domain.validation;

import org.junit.Test;

import static org.junit.Assert.*;
import static pl.mnicinski.ClientsApp.domain.validation.CommonObjectValidator.isNull;

public class CommonObjectValidatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void isNullShouldFailAtNotNullObject() {
        ValidateResult validateResult = isNull().validate(Integer.valueOf(9));

        assertEquals(false, validateResult.isValid());

        validateResult.throwIfInvalid("test");
    }

    @Test
    public void notNullShouldPassAtNullObject() {
        ValidateResult validateResult = isNull().validate(null);

        assertEquals(true, validateResult.isValid());

        validateResult.throwIfInvalid("test");
    }
}