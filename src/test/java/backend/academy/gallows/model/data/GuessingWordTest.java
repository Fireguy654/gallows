package backend.academy.gallows.model.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;

class GuessingWordTest {
    @ParameterizedTest
    @DisplayName("The letter which the word contains should be unhidden after guessing it.")
    @ValueSource(strings = {"word", "hyphen-word", "n_umber"})
    void guessRightLetter(String word) {
        GuessingWord guessingWord = new GuessingWord(word);
        char checking = word.charAt(0);

        boolean ans = guessingWord.guessLetter(checking);

        assertThat(ans).isTrue();
        assertShowingLetter(word, guessingWord, checking);
    }

    @ParameterizedTest
    @DisplayName("The case of the guessing letter shouldn't change the behaviour.")
    @ValueSource(strings = {"word", "hyphen-word", "n_umber"})
    void guessCaseInsensetivityLetter(String word) {
        GuessingWord guessingWordLower = new GuessingWord(word);
        char checkingLower = word.charAt(0);
        GuessingWord guessingWordUpper = new GuessingWord(word.toUpperCase());
        char checkingUpper = Character.toUpperCase(word.charAt(0));

        boolean ansLower = guessingWordLower.guessLetter(Character.toUpperCase(checkingLower));
        boolean ansUpper = guessingWordUpper.guessLetter(Character.toLowerCase(checkingUpper));

        assertThat(ansLower).isTrue();
        assertThat(ansUpper).isTrue();
        assertShowingLetter(word, guessingWordLower, checkingLower);
        assertShowingLetter(word, guessingWordUpper, checkingUpper);
    }

    @ParameterizedTest
    @DisplayName("The guessing of non-letter chars shouldn't change the word.")
    @ValueSource(strings = {"word", "hyphen-word", "n_umber"})
    void guessNotLetter(String word) {
        GuessingWord guessingWord = new GuessingWord(word);

        boolean ans = guessingWord.guessLetter('-');

        assertThat(ans).isFalse();
        assertShowingLetter(word, guessingWord, '-');
    }

    @Test
    @DisplayName("The letter which the word doesn't contain shouldn't change the word after guessing it.")
    void guessWrongLetter() {
        GuessingWord guessingWord = new GuessingWord("word");
        String hiddenWord = guessingWord.getWord(true);

        boolean ans = guessingWord.guessLetter('h');

        assertThat(ans).isFalse();
        assertThat(hiddenWord).isEqualTo(guessingWord.getWord(true));
    }

    @Test
    @DisplayName("The getter of the word should correctly return its hidden and unhidden view.")
    void getWord() {
        GuessingWord guessingWord = new GuessingWord("word");

        String hiddenWord = guessingWord.getWord(true);
        String unhiddenWord = guessingWord.getWord(false);

        assertThat(hiddenWord).isEqualTo("____");
        assertThat(unhiddenWord).isEqualTo("word");
    }

    @Test
    @DisplayName("Finishing of the word should mean the absence of the unguessed letters.")
    void isFinished() {
        GuessingWord word = new GuessingWord("word");
        word.guessLetter('w');
        word.guessLetter('h');
        word.guessLetter('d');
        word.guessLetter('o');
        GuessingWord otherWord = new GuessingWord("wo_rd");
        for (char i: otherWord.getWord(false).toCharArray()) {
            otherWord.guessLetter(i);
        }
        GuessingWord hyphen = new GuessingWord("-");

        assertThat(word.isFinished()).isFalse();
        assertThat(otherWord.isFinished()).isTrue();
        assertThat(hyphen.isFinished()).isTrue();
    }

    @Test
    @DisplayName("The hidden version of the reseted word should be the same as just created word.")
    void reset() {
        GuessingWord changedWord = new GuessingWord("word");
        changedWord.guessLetter('w');
        changedWord.guessLetter('h');
        GuessingWord word = new GuessingWord("word");

        changedWord.reset();

        assertThat(word.getWord(true)).isEqualTo(changedWord.getWord(true));
    }

    private static void assertShowingLetter(String word, GuessingWord guessedWord, char guessedLetter) {
        String hiddenWord = guessedWord.getWord(true);
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guessedLetter) {
                assertThat(hiddenWord.charAt(i)).isEqualTo(guessedLetter);
            }
        }
    }
}
