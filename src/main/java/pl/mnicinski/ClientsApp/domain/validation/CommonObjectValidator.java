package pl.mnicinski.ClientsApp.domain.validation;

import java.util.Objects;

public class CommonObjectValidator {

    public static Validation<Object> isNull() {
        return SimpleValidation.of(Objects::isNull, "must be null");
    }

}
