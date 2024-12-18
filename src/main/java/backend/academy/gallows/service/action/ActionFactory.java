package backend.academy.gallows.service.action;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings
public final class ActionFactory {
    private ActionFactory() {}

    public static MenuAction getMenuAction() {
        return MENU_ACTION;
    }

    public static CategoryAction getCategoryAction() {
        return CATEGORY_ACTION;
    }

    public static DifficultyAction getDifficultyAction() {
        return DIFFICULTY_ACTION;
    }

    public static HangmanAction getHangmanAction() {
        return HANGMAN_ACTION;
    }

    public static QuitAction getQuitAction() {
        return QUIT_ACTION;
    }

    private static final MenuAction MENU_ACTION = new MenuAction();
    private static final CategoryAction CATEGORY_ACTION = new CategoryAction();
    private static final DifficultyAction DIFFICULTY_ACTION = new DifficultyAction();
    private static final HangmanAction HANGMAN_ACTION = new HangmanAction();
    private static final QuitAction QUIT_ACTION = new QuitAction();
}
