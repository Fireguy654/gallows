package backend.academy.gallows.model.data;

public class GuessingWord implements GuessingWordSource {
    private final String word;
    private final char[] guess;

    public GuessingWord(String word) {
        this.word = word;
        this.guess = word.toCharArray();
        reset();
    }

    @Override
    public boolean guessLetter(char letter) {
        if (!Character.isLetter(letter)) {
            return false;
        }
        boolean guessed = false;
        for (int i = 0; i < word.length(); i++) {
            if (Character.toUpperCase(this.word.charAt(i)) == Character.toUpperCase(letter)) {
                guess[i] = word.charAt(i);
                guessed = true;
            }
        }
        return guessed;
    }

    @Override
    public String getWord(boolean hidden) {
        return hidden ? new String(guess) : word;
    }

    @Override
    public boolean isFinished() {
        for (int i = 0; i < guess.length; i++) {
            if (Character.isLetter(word.charAt(i)) && guess[i] == EMPTY) {
                return false;
            }
        }
        return true;
    }

    @Override
    public final void reset() {
        for (int i = 0; i < word.length(); ++i) {
            guess[i] = Character.isLetter(word.charAt(i)) ? EMPTY : word.charAt(i);
        }
    }

    private static final char EMPTY = '_';
}
