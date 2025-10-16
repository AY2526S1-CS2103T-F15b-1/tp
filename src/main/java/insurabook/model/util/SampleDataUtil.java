package insurabook.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import insurabook.model.AddressBook;
import insurabook.model.ReadOnlyAddressBook;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.client.Name;
import insurabook.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Client[] getSamplePersons() {
        return new Client[] {
            new Client(new Name("Alex Yeoh"), new ClientId("1")),
            new Client(new Name("Bernice Yu"), new ClientId("2")),
            new Client(new Name("Charlotte Oliveiro"), new ClientId("3")),
            new Client(new Name("David Li"), new ClientId("4")),
            new Client(new Name("Irfan Ibrahim"), new ClientId("5")),
            new Client(new Name("Roy Balakrishnan"), new ClientId("6"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Client sampleClient : getSamplePersons()) {
            sampleAb.addPerson(sampleClient);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
