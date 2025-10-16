package insurabook.storage;

import static insurabook.storage.JsonAdaptedClient.MISSING_FIELD_MESSAGE_FORMAT;
import static insurabook.testutil.Assert.assertThrows;
import static insurabook.testutil.TypicalPersons.BENSON;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import insurabook.commons.exceptions.IllegalValueException;
import insurabook.model.client.ClientId;
import insurabook.model.client.Name;

public class JsonAdaptedClientTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_CLIENT_ID = "-adfasd";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_CLIENT_ID = BENSON.getClientId().toString();

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedClient person = new JsonAdaptedClient(BENSON);
        assertEquals(BENSON, person.toModelTypeWithoutPolicies());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedClient person =
                new JsonAdaptedClient(INVALID_NAME, VALID_CLIENT_ID, null);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelTypeWithoutPolicies);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedClient person = new JsonAdaptedClient(null, VALID_CLIENT_ID, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelTypeWithoutPolicies);
    }

    @Test
    public void toModelType_invalidClientId_throwsIllegalValueException() {
        JsonAdaptedClient person =
                new JsonAdaptedClient(VALID_NAME, INVALID_CLIENT_ID, null);
        String expectedMessage = ClientId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelTypeWithoutPolicies);
    }

    @Test
    public void toModelType_nullClientId_throwsIllegalValueException() {
        JsonAdaptedClient person = new JsonAdaptedClient(VALID_NAME, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ClientId.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelTypeWithoutPolicies);
    }

}
