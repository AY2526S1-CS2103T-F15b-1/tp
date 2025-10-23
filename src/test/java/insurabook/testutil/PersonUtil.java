package insurabook.testutil;

import static insurabook.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_NAME;

import insurabook.logic.commands.AddClientCommand;
import insurabook.logic.commands.EditCommand.EditPersonDescriptor;
import insurabook.model.client.Client;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddClientCommand(Client client) {
        return AddClientCommand.COMMAND_WORD + " " + getPersonDetails(client);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Client client) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_CLIENT_NAME + client.getName().fullName + " ");
        sb.append(PREFIX_BIRTHDAY + client.getBirthday().toString() + " ");
        sb.append(PREFIX_CLIENT_ID + client.getClientId().clientId + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_CLIENT_NAME).append(name.fullName).append(" "));
        return sb.toString();
    }
}
