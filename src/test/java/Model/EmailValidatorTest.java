package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {

    @Test
    void isValidEmail() {
        String validEmail = "user@mail.com";
        assertTrue(EmailValidator.isValidEmail(validEmail));

        String inValidEmail1 = "user.com";
        assertFalse(EmailValidator.isValidEmail(inValidEmail1));

        String inValidEmail2 = "user@";
        assertFalse(EmailValidator.isValidEmail(inValidEmail2));
    }
}