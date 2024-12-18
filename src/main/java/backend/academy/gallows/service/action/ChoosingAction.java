package backend.academy.gallows.service.action;

import backend.academy.gallows.model.data.Settings;
import backend.academy.gallows.model.data.WordSource;
import backend.academy.gallows.service.UserConnector;
import java.io.IOException;
import java.util.List;

public interface ChoosingAction<T> extends StateAction {
    @Override
    default StateAction action(UserConnector connector, WordSource words, Settings settings) throws IOException {
        connector.post(getPicture());
        T ans = connector.getChosen(getName(), getChoices(words));
        saveChoice(ans, settings);
        return getDecision(ans);
    }

    String getPicture();

    String getName();

    List<T> getChoices(WordSource words);

    default void saveChoice(T choice, Settings settings) {}

    StateAction getDecision(T ans);
}
