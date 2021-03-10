package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.description.Description;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Garment {

    // Identity fields
    private final Name name;
    private final Size size;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Description> descriptions = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Garment(Name name, Size size, Email email, Address address, Set<Description> descriptions) {
        requireAllNonNull(name, size, email, address, descriptions);
        this.name = name;
        this.size = size;
        this.email = email;
        this.address = address;
        this.descriptions.addAll(descriptions);
    }

    public Name getName() {
        return name;
    }

    public Size getSize() {
        return size;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable description set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Description> getDescriptions() {
        return Collections.unmodifiableSet(descriptions);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameGarment(Garment otherGarment) {
        if (otherGarment == this) {
            return true;
        }

        return otherGarment != null
                && otherGarment.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Garment)) {
            return false;
        }

        Garment otherGarment = (Garment) other;
        return otherGarment.getName().equals(getName())
                && otherGarment.getSize().equals(getSize())
                && otherGarment.getEmail().equals(getEmail())
                && otherGarment.getAddress().equals(getAddress())
                && otherGarment.getDescriptions().equals(getDescriptions());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, size, email, address, descriptions);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Size: ")
                .append(getSize())
                .append("; Email: ")
                .append(getEmail())
                .append("; Address: ")
                .append(getAddress());

        Set<Description> descriptions = getDescriptions();
        if (!descriptions.isEmpty()) {
            builder.append("; Descriptions: ");
            descriptions.forEach(builder::append);
        }
        return builder.toString();
    }

}
