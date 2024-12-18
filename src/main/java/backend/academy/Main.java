package backend.academy;

import backend.academy.gallows.model.data.WordStorage;
import backend.academy.gallows.service.GameController;
import backend.academy.gallows.service.TextIOConnector;
import backend.academy.gallows.service.UserConnector;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        try (UserConnector con = new TextIOConnector(
            new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8)),
            new BufferedWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8))
        )) {
            GameController controller = new GameController(new WordStorage(), con);
            controller.start();
        } catch (IOException e) {
            System.err.println("Couldn't close the resources: " + e.getMessage());
        }
    }
}
