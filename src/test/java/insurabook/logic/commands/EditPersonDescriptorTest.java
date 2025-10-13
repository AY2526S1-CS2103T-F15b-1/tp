package insurabook.logic.commands;

import static insurabook.logic.commands.CommandTestUtil.DESC_AMY;
import static insurabook.logic.commands.CommandTestUtil.DESC_BOB;
import static insurabook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import insurabook.logic.commands.EditCommand.EditClientDescriptor;
import insurabook.model.client.Portfolio;
import insurabook.testutil.EditClientDescriptorBuilder;

public class EditPersonDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditClientDescriptor descriptorWithSameValues = new EditClientDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditClientDescriptor editedAmy = new EditClientDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditClientDescriptorBuilder(DESC_AMY).withClientId("999").build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditClientDescriptorBuilder(DESC_AMY).withPortfolio(new Portfolio()).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditClientDescriptor editPersonDescriptor = new EditClientDescriptor();
        String expected = EditClientDescriptor.class.getCanonicalName() + "{name="
                + editPersonDescriptor.getName().orElse(null) + ", clientId="
                + editPersonDescriptor.getClientId().orElse(null) + ", portfolio="
                + editPersonDescriptor.getPortfolio().orElse(null) + "}";
        assertEquals(expected, editPersonDescriptor.toString());
    }
}
