package backend.academy.gallows.service.action;

import backend.academy.gallows.model.data.LetterResult;
import backend.academy.gallows.model.data.Settings;
import backend.academy.gallows.model.data.WordSource;
import backend.academy.gallows.model.mechanics.HangmanDefaultSlides;
import backend.academy.gallows.model.mechanics.HangmanGuessingWord;
import backend.academy.gallows.service.UserConnector;
import backend.academy.gallows.util.CollectionsUseful;
import java.io.IOException;
import java.util.function.Predicate;

public class HangmanAction implements StateAction {
    HangmanAction() {
    }

    @Override
    public StateAction action(UserConnector connector, WordSource words, Settings settings) throws IOException {
        String chosenCategory = settings.category == null
            ? CollectionsUseful.getRandomElement(words.getCategories()) : settings.category;
        connector.post(String.format("The chosen category is '%s'", chosenCategory));
        HangmanGuessingWord controller =
            new HangmanGuessingWord(new HangmanDefaultSlides(), settings.difficulty, words.getWords(chosenCategory));
        while (!controller.isFinished()) {
            connector.post(controller.getField(true));
            connector.post("Choose your next letter to guess or type 'hint' to get a hint");
            String ans = connector.getAns(
                String::toUpperCase,
                "Incorrect input. Type the letter or 'hint'",
                IS_ANSWER_CORRECT);
            if (HINT_WORD.equalsIgnoreCase(ans)) {
                controller.hintFlag(true);
            } else {
                LetterResult res = controller.tryLetter(ans.charAt(0));
                connector.post(switch (res) {
                    case REPEAT -> "You already tested this letter";
                    case FAILURE -> "The word doesn't contain this letter";
                    case SUCCESS -> "The word contains this letter";
                });
            }
        }
        connector.post(controller.getField(false));
        connector.post(controller.isWin() ? "You won!" : "You lost!");
        connector.post("Type anything to go back to the menu");
        connector.ignoreInput();
        return ActionFactory.getMenuAction();
    }

    private static final String HINT_WORD = "hint";
    private static final Predicate<String> IS_ANSWER_CORRECT =
        (ans) -> ans.matches("[a-zA-Z]") || HINT_WORD.equalsIgnoreCase(ans);
}
