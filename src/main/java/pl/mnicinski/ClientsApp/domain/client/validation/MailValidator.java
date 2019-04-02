package pl.mnicinski.ClientsApp.domain.client.validation;

import pl.mnicinski.ClientsApp.domain.validation.CommonStringValidator;
import pl.mnicinski.ClientsApp.domain.validation.SimpleValidation;
import pl.mnicinski.ClientsApp.domain.validation.Validation;

public class MailValidator extends CommonStringValidator {

    public static Validation<String> hasAtSign() {
        return SimpleValidation.of(value -> value.contains("@"), "should contain @(at) sign");
    }

}
