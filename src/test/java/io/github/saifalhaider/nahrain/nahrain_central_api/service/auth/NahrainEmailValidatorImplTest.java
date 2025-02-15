package io.github.saifalhaider.nahrain.nahrain_central_api.service.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NahrainEmailValidatorImplTest {

    private EmailValidator emailValidator;

    @BeforeEach
    public void setUp() {
        emailValidator = new NahrainEmailValidatorImpl();
    }

    @Test
    void should_return_true_when_passing_correct_nahrian_email() {
        boolean isValid = emailValidator.isValid("test@nahrainuniv.edu.iq");
        assertTrue(isValid);
    }

    @Test
    void should_return_true_when_not_adding_iq() {
        boolean isValid = emailValidator.isValid("test@nahrainuniv.edu");
        assertTrue(isValid);
    }

    @Test
    void should_return_true_when_not_adding_iq_and_edu() {
        boolean isValid = emailValidator.isValid("test@nahrainuniv");
        assertTrue(isValid);
    }

    @Test
    void should_return_false_when_not_using_nahrain_domain() {
        boolean isValid = emailValidator.isValid("test@gmail.com");
        assertFalse(isValid);
    }

    @Test
    void should_return_false_when_not_adding_any_domain() {
        boolean isValid = emailValidator.isValid("test@");
        assertFalse(isValid);
    }
}