package insurabook.logic.commands;

import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_NAME;
import static insurabook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static insurabook.logic.parser.CliSyntax.PREFIX_PHONE;
import static insurabook.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import insurabook.commons.util.CollectionUtil;
import insurabook.commons.util.ToStringBuilder;
import insurabook.logic.Messages;
import insurabook.logic.commands.exceptions.CommandException;
import insurabook.model.Model;
import insurabook.model.claims.InsuraDate;
import insurabook.model.client.Address;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.client.Email;
import insurabook.model.client.Name;
import insurabook.model.client.Phone;
import insurabook.model.tag.Tag;

/**
 * Edits the details of an existing person in the insurabook.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the client identified "
            + "by the client's ID number. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: " + PREFIX_CLIENT_ID + " CLIENT_ID "
            + "[" + PREFIX_CLIENT_NAME + " NAME] "
            + "[" + PREFIX_PHONE + " PHONE] "
            + "[" + PREFIX_EMAIL + " EMAIL]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_CLIENT_ID + " 1 "
            + PREFIX_CLIENT_NAME + " John "
            + PREFIX_PHONE + " 91234567";

    public static final String MESSAGE_EDIT_CLIENT_SUCCESS = "Edited client: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_NO_MATCHING_CLIENT = "No client found matching ID %1$s";
    public static final String MESSAGE_DUPLICATE_CLIENT = "This client already exists in the InsuraBook.";

    private final ClientId idToEdit;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param idToEdit of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(ClientId idToEdit, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(idToEdit);
        requireNonNull(editPersonDescriptor);

        this.idToEdit = idToEdit;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Client clientToEdit = findClient(model.getFilteredClientList());
        Client editedClient = createEditedPerson(clientToEdit, editPersonDescriptor);

        if (!clientToEdit.isSameClient(editedClient) && model.hasClient(editedClient)) {
            throw new CommandException(MESSAGE_DUPLICATE_CLIENT);
        }

        model.setClient(clientToEdit, editedClient);
        model.updateFilteredClientList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitInsuraBook();
        return new CommandResult(String.format(MESSAGE_EDIT_CLIENT_SUCCESS, Messages.format(editedClient)));
    }

    /**
     * Finds Client to edit by idToEdit.
     */
    private Client findClient(List<Client> clients) throws CommandException {
        Optional<Client> clientToEdit = clients.stream()
                .filter(x -> x.getClientId().equals(idToEdit))
                .findFirst();
        return clientToEdit.orElseThrow(() ->
                new CommandException(String.format(MESSAGE_NO_MATCHING_CLIENT, idToEdit)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Client createEditedPerson(Client clientToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert clientToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(clientToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(clientToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(clientToEdit.getEmail());

        // keep the same client ID and portfolio
        InsuraDate birthday = clientToEdit.getBirthday();
        ClientId clientId = clientToEdit.getClientId();

        Client editedClient = new Client(updatedName, updatedPhone, updatedEmail, birthday, clientId);
        editedClient.setPortfolio(clientToEdit.getPortfolio());

        return editedClient;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return idToEdit.equals(otherEditCommand.idToEdit)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("idToEdit", idToEdit)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)
                    .toString();
        }
    }
}
