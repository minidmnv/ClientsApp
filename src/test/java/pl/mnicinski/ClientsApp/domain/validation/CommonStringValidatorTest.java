package pl.mnicinski.ClientsApp.domain.validation;

import org.junit.Test;

import static org.junit.Assert.*;
import static pl.mnicinski.ClientsApp.domain.validation.CommonStringValidator.longerThen;
import static pl.mnicinski.ClientsApp.domain.validation.CommonStringValidator.notNull;

public class CommonStringValidatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void notNullShouldFailAtNullString() {
        ValidateResult validateResult = notNull().validate(null);

        assertEquals(false, validateResult.isValid());

        validateResult.throwIfInvalid("test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void longerThenShouldFailAtFewerChars() {
        assertEquals(false, longerThen(2).validate("s").isValid());
        assertEquals(false, longerThen(5).validate("sora").isValid());
        assertEquals(false, longerThen(1).validate("").isValid());
        assertEquals(false, longerThen(10).validate("").isValid());

        longerThen(15).validate("doiuwqndoiuqnw").throwIfInvalid("test");
    }

    @Test
    public void longerThenShouldPassForEnoughChars() {
        assertEquals(true, longerThen(2).validate("sa").isValid());
        assertEquals(true, longerThen(5).validate("soradsadasdaa").isValid());
        assertEquals(true, longerThen(1).validate("s").isValid());
        assertEquals(true, longerThen(10).validate("dafqwfqqqf").isValid());

        longerThen(15).validate("doiuwqndoiuqnwsa").throwIfInvalid("test");
    }
}