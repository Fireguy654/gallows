package backend.academy.gallows.service.action;

import backend.academy.gallows.model.data.Difficulty;
import backend.academy.gallows.model.data.Settings;
import backend.academy.gallows.model.data.WordSource;
import backend.academy.gallows.service.TextIOConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChoosingActionTest {
    @Mock
    private WordSource wordSource;
    private StringWriter output;
    private Settings settings;

    @BeforeEach
    void setUp() {
        output = new StringWriter();
        settings = new Settings(Difficulty.MEDIUM);
    }

    @Test
    @DisplayName("Category action test.")
    void categoryActionTest() {
        assertThatCode(() -> {
            CategoryAction categoryAction = ActionFactory.getCategoryAction();
            when(wordSource.getCategories()).thenReturn(List.of("category"));
            TextIOConnector conn = getStringConnector("1", output);

            StateAction res = categoryAction.action(conn, wordSource, settings);

            assertThat(res).isInstanceOf(MenuAction.class);
            assertThat(output.toString()).contains(categoryAction.getPicture()).contains(categoryAction.getName())
                .contains(wordSource.getCategories());
            assertThat(settings.category).isEqualTo("category");
        }).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Difficulty action test")
    void difficultyActionTest() {
        assertThatCode(() -> {
            DifficultyAction difficultyAction = ActionFactory.getDifficultyAction();
            TextIOConnector conn = getStringConnector("3", output);

            StateAction res = difficultyAction.action(conn, wordSource, settings);

            assertThat(res).isInstanceOf(MenuAction.class);
            assertThat(output.toString()).contains(difficultyAction.getPicture()).contains(difficultyAction.getName())
                .contains(Arrays.stream(Difficulty.values()).map(Object::toString).toList());
            assertThat(settings.difficulty).isEqualTo(Difficulty.HARD);
        }).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Menu action test")
    void menuActionTest() {
        assertThatCode(() -> {
            MenuAction menuAction = ActionFactory.getMenuAction();
            TextIOConnector conn = getStringConnector("1", output);

            StateAction res = menuAction.action(conn, wordSource, settings);

            assertThat(res).isInstanceOf(HangmanAction.class);
            assertThat(output.toString()).contains(menuAction.getPicture()).contains(menuAction.getName())
                .contains(Arrays.stream(MenuAction.Option.values()).map(Object::toString).toList());
        }).doesNotThrowAnyException();
    }

    private static TextIOConnector getStringConnector(String input, StringWriter output) {
        StringReader reader = new StringReader(input);
        return new TextIOConnector(new BufferedReader(reader), new BufferedWriter(output));
    }
}
