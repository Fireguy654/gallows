package backend.academy.gallows.service.action;

import backend.academy.gallows.model.data.Difficulty;
import backend.academy.gallows.model.data.Settings;
import backend.academy.gallows.model.data.WordSource;
import java.util.Arrays;
import java.util.List;

public class DifficultyAction implements ChoosingAction<Difficulty> {
    DifficultyAction() {
    }

    @Override
    public String getPicture() {
        return PICTURE;
    }

    @Override
    public String getName() {
        return "difficulty";
    }

    @Override
    public List<Difficulty> getChoices(WordSource words) {
        return Arrays.asList(Difficulty.values());
    }

    @Override
    public void saveChoice(Difficulty choice, Settings settings) {
        settings.difficulty = choice;
    }

    @Override
    public StateAction getDecision(Difficulty ans) {
        return ActionFactory.getMenuAction();
    }

    private static final String PICTURE = """
        ——————————DIFFICULTY——————————
        """;
}
