package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SIZE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GARMENTS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.description.Description;
import seedu.address.model.person.Address;
import seedu.address.model.person.Colour;
import seedu.address.model.person.Name;
import seedu.address.model.person.Garment;
import seedu.address.model.person.Size;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_SIZE + "SIZE] "
            + "[" + PREFIX_COLOUR + "COLOUR] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SIZE + "36 "
            + PREFIX_COLOUR + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditGarmentDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditGarmentDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditGarmentDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Garment> lastShownList = model.getFilteredGarmentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Garment garmentToEdit = lastShownList.get(index.getZeroBased());
        Garment editedGarment = createEditedGarment(garmentToEdit, editPersonDescriptor);

        if (!garmentToEdit.isSameGarment(editedGarment) && model.hasGarment(editedGarment)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setGarment(garmentToEdit, editedGarment);
        model.updateFilteredGarmentList(PREDICATE_SHOW_ALL_GARMENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedGarment));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code garmentToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Garment createEditedGarment(Garment garmentToEdit, EditGarmentDescriptor editPersonDescriptor) {
        assert garmentToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(garmentToEdit.getName());
        Size updatedSize = editPersonDescriptor.getSize().orElse(garmentToEdit.getSize());
        Colour updatedColour = editPersonDescriptor.getColour().orElse(garmentToEdit.getColour());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(garmentToEdit.getAddress());
        Set<Description> updatedDescriptions = editPersonDescriptor.getDescriptions()
                .orElse(garmentToEdit.getDescriptions());

        return new Garment(updatedName, updatedSize, updatedColour, updatedAddress, updatedDescriptions);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with.
     * Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditGarmentDescriptor {
        private Name name;
        private Size size;
        private Colour colour;
        private Address address;
        private Set<Description> descriptions;

        public EditGarmentDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code descriptions} is used internally.
         */
        public EditGarmentDescriptor(EditGarmentDescriptor toCopy) {
            setName(toCopy.name);
            setSize(toCopy.size);
            setColour(toCopy.colour);
            setAddress(toCopy.address);
            setDescriptions(toCopy.descriptions);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, size, colour, address, descriptions);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setSize(Size size) {
            this.size = size;
        }

        public Optional<Size> getSize() {
            return Optional.ofNullable(size);
        }

        public void setColour(Colour colour) {
            this.colour = colour;
        }

        public Optional<Colour> getColour() {
            return Optional.ofNullable(colour);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code descriptions} to this object's {@code descriptions}.
         * A defensive copy of {@code descriptions} is used internally.
         */
        public void setDescriptions(Set<Description> descriptions) {
            this.descriptions = (descriptions != null) ? new HashSet<>(descriptions) : null;
        }

        /**
         * Returns an unmodifiable description set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code descriptions} is null.
         */
        public Optional<Set<Description>> getDescriptions() {
            return (descriptions != null) ? Optional.of(Collections.unmodifiableSet(descriptions)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditGarmentDescriptor)) {
                return false;
            }

            // state check
            EditGarmentDescriptor e = (EditGarmentDescriptor) other;

            return getName().equals(e.getName())
                    && getSize().equals(e.getSize())
                    && getColour().equals(e.getColour())
                    && getAddress().equals(e.getAddress())
                    && getDescriptions().equals(e.getDescriptions());
        }
    }
}
