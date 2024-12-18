package backend.academy.gallows.model.data;

public interface GuessingWordSource {
    boolean guessLetter(char letter);

    String getWord(boolean hidden);

    boolean isFinished();

    void reset();
}
