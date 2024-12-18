package backend.academy.gallows.service.action;

import backend.academy.gallows.model.data.Settings;
import backend.academy.gallows.model.data.WordSource;
import backend.academy.gallows.service.UserConnector;
import java.io.IOException;

public class QuitAction implements StateAction {
    QuitAction() {
    }

    @Override
    public StateAction action(UserConnector connector, WordSource words, Settings settings) throws IOException {
        return this;
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
