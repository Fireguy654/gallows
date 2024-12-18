package backend.academy.gallows.model.mechanics;

import backend.academy.gallows.model.data.Difficulty;
import backend.academy.gallows.model.data.LetterResult;
import backend.academy.gallows.model.data.WordWithHint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class HangmanGuessingWordTest {
    @Mock
    private HangmanDefaultSlides mockHangman;

    @Test
    void successLetter() {
        HangmanGuessingWord game = create("word");

        LetterResult res = game.tryLetter('r');

        assertThat(res).isEqualTo(LetterResult.SUCCESS);
        assertThat(game.getField(true)).contains("__r_");
    }

    @Test
    void repeatLetter() {
        HangmanGuessingWord game = create("word");
        game.tryLetter('r');
        game.tryLetter('n');
        String field = game.getField(true);

        LetterResult res1 = game.tryLetter('r');
        LetterResult res2 = game.tryLetter('n');

        assertThat(res1).isEqualTo(LetterResult.REPEAT);
        assertThat(res2).isEqualTo(LetterResult.REPEAT);
        assertThat(game.getField(true)).isEqualTo(field);
    }

    @Test
    void failureLetter() {
        HangmanGuessingWord game = createTwoTry("word");

        LetterResult res = game.tryLetter('s');

        assertThat(res).isEqualTo(LetterResult.FAILURE);
        assertThat(game.getField(true)).contains("attempts: 1");
    }

    @Test
    void complexTryTest() {
        HangmanGuessingWord game = createTwoTry("word");

        game.tryLetter('r');
        game.tryLetter('w');
        game.tryLetter('n');
        game.tryLetter('w');

        assertThat(game.getField(true)).contains("attempts: 1").contains("w_r_");
    }

    @Test
    void difficultyTest() {
        HangmanGuessingWord gameEasy = create("word", Difficulty.EASY);
        HangmanGuessingWord gameHard = create("word", Difficulty.HARD);

        gameHard.tryLetter('m');
        gameHard.tryLetter('n');
        gameHard.tryLetter('s');
        gameHard.tryLetter('p');
        gameEasy.tryLetter('m');
        gameEasy.tryLetter('n');
        gameEasy.tryLetter('s');
        gameEasy.tryLetter('p');

        assertThat(gameEasy.isFinished()).isFalse();
        assertThat(gameHard.isFinished()).isTrue();
    }

    @Test
    @DisplayName("The correctness of finish.")
    void isFinished() {
        HangmanGuessingWord winGame = createOneTry("w");
        HangmanGuessingWord loseGame = createOneTry("w");
        HangmanGuessingWord inprogressGame = createOneTry("word");

        winGame.tryLetter('w');
        loseGame.tryLetter('o');
        inprogressGame.tryLetter('o');

        assertThat(winGame.isFinished()).isTrue();
        assertThat(loseGame.isFinished()).isTrue();
        assertThat(inprogressGame.isWin()).isFalse();
    }

    @Test
    @DisplayName("The correctness of win.")
    void isWin() {
        HangmanGuessingWord winGame = createOneTry("w");
        HangmanGuessingWord loseGame = createOneTry("w");
        HangmanGuessingWord inprogressGame = createOneTry("word");

        winGame.tryLetter('w');
        loseGame.tryLetter('o');
        inprogressGame.tryLetter('o');

        assertThat(winGame.isWin()).isTrue();
        assertThat(loseGame.isWin()).isFalse();
        assertThat(inprogressGame.isWin()).isFalse();
    }

    @Test
    @DisplayName("The correctness of lose.")
    void isLose() {
        HangmanGuessingWord winGame = createOneTry("w");
        HangmanGuessingWord loseGame = createOneTry("w");
        HangmanGuessingWord inprogressGame = createOneTry("word");

        winGame.tryLetter('w');
        loseGame.tryLetter('o');
        inprogressGame.tryLetter('o');

        assertThat(winGame.isLose()).isFalse();
        assertThat(loseGame.isLose()).isTrue();
        assertThat(inprogressGame.isWin()).isFalse();
    }

    @Test
    @DisplayName("Hint is printed only when hint flag is set.")
    void printedHint() {
        HangmanGuessingWord game = createOneTry("word", "!hint!");
        String noHintRes = game.getField(false);

        game.hintFlag(true);

        assertThat(noHintRes).doesNotContain("!hint!");
        assertThat(game.getField(false)).contains("!hint!");
    }

    private HangmanGuessingWord createOneTry(String word) {
        return createOneTry(word, "hint");
    }

    private HangmanGuessingWord createOneTry(String word, String hint) {
        when(mockHangman.getMaxSlideInd()).thenReturn(1);
        return new HangmanGuessingWord(mockHangman, Difficulty.EASY, List.of(new WordWithHint(word, hint)));
    }

    private HangmanGuessingWord createTwoTry(String word) {
        when(mockHangman.getMaxSlideInd()).thenReturn(2);
        return new HangmanGuessingWord(mockHangman, Difficulty.EASY, List.of(new WordWithHint(word, "hint")));
    }

    private HangmanGuessingWord create(String word) {
        return create(word, Difficulty.EASY);
    }

    private HangmanGuessingWord create(String word, Difficulty difficulty) {
        return new HangmanGuessingWord(new HangmanDefaultSlides(), difficulty, List.of(new WordWithHint(word, "hint")));
    }
}
