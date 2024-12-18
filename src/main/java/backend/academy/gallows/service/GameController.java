package backend.academy.gallows.service;

import backend.academy.gallows.model.data.Difficulty;
import backend.academy.gallows.model.data.Settings;
import backend.academy.gallows.model.data.WordSource;
import backend.academy.gallows.service.action.ActionFactory;
import backend.academy.gallows.service.action.StateAction;
import java.io.IOException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GameController {
    private final WordSource source;
    private final UserConnector connector;

    public void start() {
        Settings settings = new Settings(Difficulty.MEDIUM);
        StateAction state = ActionFactory.getMenuAction();
        try {
            while (!state.isFinished()) {
                state = state.action(connector, source, settings);
            }
        } catch (IOException e) {
            System.err.println("Can't use input/output: " + e.getMessage());
        }
    }
}
