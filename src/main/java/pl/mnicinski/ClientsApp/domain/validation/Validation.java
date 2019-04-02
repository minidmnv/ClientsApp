package pl.mnicinski.ClientsApp.domain.validation;

@FunctionalInterface
public interface Validation<T> {

    ValidateResult validate(T parameter);

    default Validation<T> and(Validation<T> other) {
        return (value) -> {
            ValidateResult result = validate(value);
            return !result.isValid() ? result : other.validate(value);
        };
    }

}
