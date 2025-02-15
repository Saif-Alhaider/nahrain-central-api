package io.github.saifalhaider.nahrain.nahrain_central_api.service.auth;

/**
 * Implementation of the EmailValidator interface to validate email addresses
 * specifically for the Nahrain University domain.
 * <p>
 * This class validates email addresses ensuring they conform to the Nahrain
 * University email structure. Valid emails can belong to domains such as
 * "nahrainuniv.edu" or "nahrainuniv.edu.iq".
 * <p>
 * The validation is performed using a regular expression pattern.
 */
public class NahrainEmailValidatorImpl implements EmailValidator {
    private String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@nahrainuniv(\\.edu(\\.iq)?)?$";

    @Override
    public boolean isValid(String email) {
        return email.matches(EMAIL_PATTERN);
    }
}
