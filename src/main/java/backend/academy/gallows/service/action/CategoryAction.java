package backend.academy.gallows.service.action;

import backend.academy.gallows.model.data.Settings;
import backend.academy.gallows.model.data.WordSource;
import java.util.List;

public class CategoryAction implements ChoosingAction<String> {
    CategoryAction() {
    }

    @Override
    public String getPicture() {
        return PICTURE;
    }

    @Override
    public String getName() {
        return "categories";
    }

    @Override
    public List<String> getChoices(WordSource words) {
        return words.getCategories();
    }

    @Override
    public void saveChoice(String choice, Settings settings) {
        settings.category = choice;
    }

    @Override
    public StateAction getDecision(String choice) {
        return ActionFactory.getMenuAction();
    }

    private static final String PICTURE = """
        ——————————CATEGORY——————————
        """;
}
