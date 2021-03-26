package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRESSCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SIZE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.garment.AttributesContainsKeywordsPredicate;
import seedu.address.model.garment.ColourContainsKeywordsPredicate;
import seedu.address.model.garment.ContainsKeywordsPredicate;
import seedu.address.model.garment.DescriptionContainsKeywordsPredicate;
import seedu.address.model.garment.DressCodeContainsKeywordsPredicate;
import seedu.address.model.garment.NameContainsKeywordsPredicate;
import seedu.address.model.garment.SizeContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SIZE, PREFIX_COLOUR, PREFIX_DRESSCODE,
                        PREFIX_DESCRIPTION, PREFIX_TYPE);

        //ContainsKeywordsPredicate containsKeywordsPredicate;
        //String[] keywords = {""};//just init 1st see if works when check the keywords in last if block
        //List<ContainsKeywordsPredicate> predicateList = new ArrayList<>();
        //boolean isValidInput = false;
        /*
        //potential issue if finding by more than 1 attribute, looks at 1st thing in here, if multiple of the same
        // attribute will look at last one, eg find n/x n/y will find n/y, not both
        //another issue when finding a description with >1 word causes bug
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            isValidInput = true;
            keywords = argMultimap.getValue(PREFIX_NAME).get().split("\\s+");
            //containsKeywordsPredicate =
            //        new NameContainsKeywordsPredicate(Arrays.asList(keywords));
            predicateList.add(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        }
        if (argMultimap.getValue(PREFIX_DRESSCODE).isPresent()) {
            isValidInput = true;
            keywords = argMultimap.getValue(PREFIX_DRESSCODE).get().split("\\s+");
            //containsKeywordsPredicate =
            //        new DressCodeContainsKeywordsPredicate(Arrays.asList(keywords));
            predicateList.add(new DressCodeContainsKeywordsPredicate(Arrays.asList(keywords)));

        }
        if (argMultimap.getValue(PREFIX_COLOUR).isPresent()) {
            isValidInput = true;
            keywords = argMultimap.getValue(PREFIX_COLOUR).get().split("\\s+");
            //containsKeywordsPredicate =
            //        new ColourContainsKeywordsPredicate(Arrays.asList(keywords));
            predicateList.add(new ColourContainsKeywordsPredicate(Arrays.asList(keywords)));

        }
        if (argMultimap.getValue(PREFIX_SIZE).isPresent()) {
            isValidInput = true;
            keywords = argMultimap.getValue(PREFIX_SIZE).get().split("\\s+");
            //containsKeywordsPredicate =
            //        new SizeContainsKeywordsPredicate(Arrays.asList(keywords));
            predicateList.add(new SizeContainsKeywordsPredicate(Arrays.asList(keywords)));

        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            isValidInput = true;
            keywords = argMultimap.getValue(PREFIX_DESCRIPTION).get().split("\\s+");
            //containsKeywordsPredicate =
            //        new DescriptionContainsKeywordsPredicate(Arrays.asList(keywords));
            predicateList.add(new DescriptionContainsKeywordsPredicate(Arrays.asList(keywords)));
        }
        */

        /*if (!isValidSyntax(argMultimap)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        if (keywords[0].equals("")) { //Empty arguments
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }*/
        if (!(isValidInput(argMultimap))) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        AttributesContainsKeywordsPredicate predicates = new AttributesContainsKeywordsPredicate(argMultimap);

        //can instead give a list of predicates
        return new FindCommand(predicates);
    }

    /**
     * Returns true if there are any valid prefixed input.
     */
    /*public boolean isValidSyntax(ArgumentMultimap argMultimap) {
        return !(argMultimap.getValue(PREFIX_DESCRIPTION).isPresent() || argMultimap.getValue(PREFIX_COLOUR).isPresent()
                || argMultimap.getValue(PREFIX_SIZE).isPresent() || argMultimap.getValue(PREFIX_DRESSCODE).isPresent()
                || argMultimap.getValue(PREFIX_NAME).isPresent());
    }*/

    //maybe can separate to each prefix, but that sorta forces each prefic to be present
    public boolean isValidInput(ArgumentMultimap argMultimap) {
        String[] keywords = {""};
        boolean isValidSyntax = false;
        boolean isNotEmpty = true;
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            isValidSyntax = true;
            keywords = argMultimap.getValue(PREFIX_NAME).get().split("\\s+");
            isNotEmpty = isNotEmpty && isNotEmpty(keywords);
        }
        if (argMultimap.getValue(PREFIX_DRESSCODE).isPresent()) {
            isValidSyntax = true;
            keywords = argMultimap.getValue(PREFIX_DRESSCODE).get().split("\\s+");
            isNotEmpty = isNotEmpty && isNotEmpty(keywords);
        }
        if (argMultimap.getValue(PREFIX_COLOUR).isPresent()) {
            isValidSyntax = true;
            keywords = argMultimap.getValue(PREFIX_COLOUR).get().split("\\s+");
            isNotEmpty = isNotEmpty && isNotEmpty(keywords);
        }
        if (argMultimap.getValue(PREFIX_SIZE).isPresent()) {
            isValidSyntax = true;
            keywords = argMultimap.getValue(PREFIX_SIZE).get().split("\\s+");
            isNotEmpty = isNotEmpty && isNotEmpty(keywords);
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            isValidSyntax = true;
            keywords = argMultimap.getValue(PREFIX_DESCRIPTION).get().split("\\s+");
            isNotEmpty = isNotEmpty && isNotEmpty(keywords);
        }
        if (argMultimap.getValue(PREFIX_TYPE).isPresent()) {
            isValidSyntax = true;
            keywords = argMultimap.getValue(PREFIX_TYPE).get().split("\\s+");
            isNotEmpty = isNotEmpty && isNotEmpty(keywords);
        }
        return isValidSyntax && isNotEmpty;
    }

    public boolean isNotEmpty(String[] input) {
        if (!(input[0].equals(""))) {
            return true;
        } else {
            return false;
        }
    }
}
