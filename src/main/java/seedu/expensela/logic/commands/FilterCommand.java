package seedu.expensela.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import seedu.expensela.commons.core.Messages;
import seedu.expensela.model.Filter;
import seedu.expensela.model.Model;
import seedu.expensela.model.transaction.CategoryEqualsKeywordPredicate;
import seedu.expensela.model.transaction.DateEqualsKeywordPredicate;
import seedu.expensela.model.transaction.Transaction;

/**
 * Finds and lists all transactions in expenseLa whose category is of the argument keyword.
 * Keyword matching is case insensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all transactions whose category/date is of "
            + "the specified keyword (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " GROCERIES";

    private final Predicate<Transaction> predicate;

    public FilterCommand(CategoryEqualsKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    public FilterCommand(DateEqualsKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
//        model.updateFilteredTransactionList(predicate);
        if (predicate.getClass() == CategoryEqualsKeywordPredicate.class) {
            model.updateFilteredTransactionList(new DateEqualsKeywordPredicate(Arrays.asList(model.getFilter().getDateMonth())), predicate);
            model.setFilter(new Filter(predicate.toString(), model.getFilter().getDateMonth()));
        } else {
            model.updateFilteredTransactionList(new CategoryEqualsKeywordPredicate(Arrays.asList(model.getFilter().getFilterCategoryName())), predicate);
            model.setFilter(new Filter(model .getFilter().getFilterCategoryName(), predicate.toString()));
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_TRANSACTION_LISTED_OVERVIEW, model.getFilteredTransactionList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && predicate.equals(((FilterCommand) other).predicate)); // state check
    }
}
