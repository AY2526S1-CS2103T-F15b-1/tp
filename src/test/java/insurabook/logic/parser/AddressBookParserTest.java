package insurabook.logic.parser;

import static insurabook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static insurabook.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static insurabook.testutil.Assert.assertThrows;
import static insurabook.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

//import insurabook.logic.commands.AddClientCommand;
import insurabook.logic.commands.ClearCommand;
import insurabook.logic.commands.DeleteClientCommand;
//import insurabook.logic.commands.EditCommand;
//import insurabook.logic.commands.EditCommand.EditClientDescriptor;
import insurabook.logic.commands.ExitCommand;
import insurabook.logic.commands.FindClientCommand;
import insurabook.logic.commands.HelpCommand;
import insurabook.logic.commands.ListCommand;
import insurabook.logic.commands.ViewCommand;
import insurabook.logic.parser.exceptions.ParseException;
//import insurabook.model.client.Client;
import insurabook.model.client.NameContainsKeywordsPredicate;
//import insurabook.testutil.ClientBuilder;
//import insurabook.testutil.ClientUtil;
//import insurabook.testutil.EditClientDescriptorBuilder;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    //@Test
    //public void parseCommand_add() throws Exception {
    //    Client client = new ClientBuilder().build();
    //    AddClientCommand command = (AddClientCommand) parser.parseCommand(ClientUtil.getAddClientCommand(client));
    //    assertEquals(new AddClientCommand(client), command);
    //}

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteClientCommand command = (DeleteClientCommand) parser.parseCommand(
                DeleteClientCommand.COMMAND_WORD + " " + INDEX_FIRST_CLIENT.getOneBased());
        assertEquals(new DeleteClientCommand(INDEX_FIRST_CLIENT), command);
    }

    //@Test
    //public void parseCommand_edit() throws Exception {
    //    Client client = new ClientBuilder().build();
    //    EditClientDescriptor descriptor = new EditClientDescriptorBuilder(client).build();
    //    EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
    //            + INDEX_FIRST_CLIENT.getOneBased() + " " + ClientUtil.getEditPersonDescriptorDetails(descriptor));
    //    assertEquals(new EditCommand(INDEX_FIRST_CLIENT, descriptor), command);
    //}

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindClientCommand command = (FindClientCommand) parser.parseCommand(
                FindClientCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindClientCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_view() throws Exception {
        assertTrue(parser.parseCommand(ViewCommand.COMMAND_WORD + " -c") instanceof ViewCommand);
        assertTrue(parser.parseCommand(ViewCommand.COMMAND_WORD + " -p") instanceof ViewCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
