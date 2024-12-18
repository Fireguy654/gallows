package backend.academy.gallows.service.action;

import backend.academy.gallows.model.data.WordSource;
import java.util.Arrays;
import java.util.List;

public class MenuAction implements ChoosingAction<MenuAction.Option> {
    MenuAction() {
    }

    @Override
    public String getPicture() {
        return PICTURE;
    }

    @Override
    public String getName() {
        return "option";
    }

    @Override
    public List<Option> getChoices(WordSource words) {
        return Arrays.asList(Option.values());
    }

    @Override
    public StateAction getDecision(Option choice) {
        return switch (choice) {
            case START -> ActionFactory.getHangmanAction();
            case DIFFICULTY -> ActionFactory.getDifficultyAction();
            case CATEGORY -> ActionFactory.getCategoryAction();
            case QUIT -> ActionFactory.getQuitAction();
        };
    }

    private static final String PICTURE = """
         _ __ ___   ___ _ __  _   _\s
        | '_ ` _ \\ / _ \\ '_ \\| | | |
        | | | | | |  __/ | | | |_| |
        |_| |_| |_|\\___|_| |_|\\__,_|
        """;

    public enum Option {
        START, DIFFICULTY, CATEGORY, QUIT
    }
}
