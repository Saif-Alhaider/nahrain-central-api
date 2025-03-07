package io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.validation.email;

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
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@nahrainuniv(\\.edu(\\.iq)?)?$";
    private static final String DOMAIN = "@nahrainuniv.edu.iq";
    private static final String EDU_PART = ".edu";
    private static final String IQ_PART = ".iq";

    @Override
    public boolean isValid(String email) {
        return email.matches(EMAIL_PATTERN);
    }

    @Override
    public String getCompletedEmail(String email) {
        if (!email.contains("@nahrainuniv")) {
            email += DOMAIN;
        } else if (!email.contains(EDU_PART)) {
            email += EDU_PART + IQ_PART;
        } else if (!email.endsWith(IQ_PART)) {
            email += IQ_PART;
        }
        return email;
    }
}

