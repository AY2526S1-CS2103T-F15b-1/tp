package insurabook.storage;

import static insurabook.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static insurabook.testutil.Assert.assertThrows;
import static insurabook.testutil.TypicalClients.BENSON;
//import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import insurabook.commons.exceptions.IllegalValueException;
import insurabook.model.client.ClientId;
import insurabook.model.client.Name;

public class JsonAdaptedClientTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_CLIENT_ID = "HELLO123";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_CLIENT_ID = BENSON.getId().toString();

    //@Test
    //public void toModelType_validClientDetails_returnsPerson() throws Exception {
    //    JsonAdaptedClient client = new JsonAdaptedClient(BENSON);
    //    assertEquals(BENSON, client.toModelType());
    //}

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedClient client =
                new JsonAdaptedClient(INVALID_NAME, VALID_CLIENT_ID);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedClient client = new JsonAdaptedClient(null, VALID_CLIENT_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_invalidClientId_throwsIllegalValueException() {
        JsonAdaptedClient client =
                new JsonAdaptedClient(VALID_NAME, INVALID_CLIENT_ID);
        String expectedMessage = ClientId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_nullClientId_throwsIllegalValueException() {
        JsonAdaptedClient client = new JsonAdaptedClient(VALID_NAME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ClientId.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

}
