package pl.mnicinski.ClientsApp.domain.validation;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ValidationTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Validation<String> firstValidationLambda =
            SimpleValidation.of(p -> p != null, "notNull");
    private Validation<String> secondValidationLambda =
            SimpleValidation.of(p -> p.length() > 1, "length");

    @Test
    public void shouldFailOnFirstValidation() {

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("test notNull");

        firstValidationLambda
                .and(secondValidationLambda)
                .validate(null)
                .throwIfInvalid("test");
    }

    @Test
    public void shouldFailOnSecondValidation() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("test length");

        firstValidationLambda
                .and(secondValidationLambda)
                .validate("a")
                .throwIfInvalid("test");
    }

}
