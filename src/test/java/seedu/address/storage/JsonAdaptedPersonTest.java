package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.DressCode;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Size;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_SIZE = "+22";
    private static final String INVALID_DRESSCODE = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_DESCRIPTION = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_SIZE = BENSON.getSize().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_DRESCODE = BENSON.getDressCode().toString();
    private static final List<JsonAdaptedDescription> VALID_DESCRIPTIONS = BENSON.getDescriptions().stream()
            .map(JsonAdaptedDescription::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_SIZE, VALID_EMAIL, VALID_DRESCODE, VALID_DESCRIPTIONS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_SIZE, VALID_EMAIL, VALID_DRESCODE,
                VALID_DESCRIPTIONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidSize_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_SIZE, VALID_EMAIL, VALID_DRESCODE, VALID_DESCRIPTIONS);
        String expectedMessage = Size.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullSize_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_DRESCODE,
                VALID_DESCRIPTIONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Size.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_SIZE, INVALID_EMAIL, VALID_DRESCODE, VALID_DESCRIPTIONS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_SIZE, null, VALID_DRESCODE,
                VALID_DESCRIPTIONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidDressCode_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_SIZE, VALID_EMAIL, INVALID_DRESSCODE, VALID_DESCRIPTIONS);
        String expectedMessage = DressCode.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDressCode_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_SIZE, VALID_EMAIL, null,
                VALID_DESCRIPTIONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DressCode.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidDescriptions_throwsIllegalValueException() {
        List<JsonAdaptedDescription> invalidDescriptions = new ArrayList<>(VALID_DESCRIPTIONS);
        invalidDescriptions.add(new JsonAdaptedDescription(INVALID_DESCRIPTION));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_SIZE, VALID_EMAIL, VALID_DRESCODE,
                        invalidDescriptions);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}
