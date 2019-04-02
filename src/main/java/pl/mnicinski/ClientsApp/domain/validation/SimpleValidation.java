package pl.mnicinski.ClientsApp.domain.validation;

import java.util.function.Predicate;

public class SimpleValidation<T> implements Validation<T> {

    private final Predicate<T> testPredicate;
    private final String failMessage;

    private SimpleValidation(Predicate<T> testPredicate, String failMessage) {
        this.testPredicate = testPredicate;
        this.failMessage = failMessage;
    }

    public static <T> SimpleValidation<T> of(Predicate<T> test, String failMessage) {
        return new SimpleValidation<>(test, failMessage);
    }

    @Override
    public ValidateResult validate(T parameter) {
        return testPredicate.test(parameter) ? ValidateResult.ok() : ValidateResult.fail(failMessage);
    }

}
