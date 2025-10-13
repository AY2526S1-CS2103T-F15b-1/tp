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
    public static Client[] getSampleClients() {
        return new Client[] {
            new Client(new Name("Alex Yeoh"), new ClientId("1")),
            new Client(new Name("John Doe"), new ClientId("2")),
            new Client(new Name("May Bee"), new ClientId("3")),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Client sampleClient : getSampleClients()) {
            sampleAb.addClient(sampleClient);
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
