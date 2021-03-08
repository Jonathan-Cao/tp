package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SizeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Size(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Size(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Size.isValidSize(null));

        // invalid phone numbers
        assertFalse(Size.isValidSize("")); // empty string
        assertFalse(Size.isValidSize(" ")); // spaces only
        assertFalse(Size.isValidSize("phone")); // non-numeric
        assertFalse(Size.isValidSize("9011p041")); // alphabets within digits
        assertFalse(Size.isValidSize("9 1")); // spaces within digits

        // valid phone numbers
        assertTrue(Size.isValidSize("23")); // exactly 3 numbers
        assertTrue(Size.isValidSize("11"));
    }
}