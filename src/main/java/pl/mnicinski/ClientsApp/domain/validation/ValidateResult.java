package pl.mnicinski.ClientsApp.domain.validation;

public class ValidateResult {

    private final boolean isValid;
    private final String message;
    private static final ValidateResult OK = new ValidateResult(true, null);

    private ValidateResult(boolean isValid, String message) {
        this.isValid = isValid;
        this.message = message;
    }

    public static ValidateResult ok() {
        return OK;
    }

    public static ValidateResult fail(String message) {
        return new ValidateResult(false, message);
    }

    public boolean isValid() {
        return isValid;
    }

    public void throwIfInvalid(String parameter) {
        if(!isValid) {
            throw new IllegalArgumentException(parameter + " " + message);
        }
    }
}
