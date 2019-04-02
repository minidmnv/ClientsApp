package pl.mnicinski.ClientsApp.domain.validation;

import javax.validation.constraints.NotNull;
import java.util.Objects;

import static java.lang.String.format;

public class CommonStringValidator {

    public static Validation<String> notNull() {
        return SimpleValidation.of(Objects::nonNull, "must not be null");
    }

    public static Validation<String> longerThen(@NotNull int minLength) {
        return SimpleValidation.of(value -> value.length() >= minLength, format("must be of %s chars length, or longer", minLength));
    }


}
