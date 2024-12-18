package backend.academy.gallows.model.mechanics;

import backend.academy.gallows.model.data.Difficulty;
import backend.academy.gallows.model.data.GuessingWord;
import backend.academy.gallows.model.data.GuessingWordSource;
import backend.academy.gallows.model.data.LetterResult;
import backend.academy.gallows.model.data.WordWithHint;
import backend.academy.gallows.util.CollectionsUseful;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unimi.dsi.fastutil.chars.CharOpenHashSet;
import it.unimi.dsi.fastutil.chars.CharSet;
import java.util.Collection;
import lombok.Setter;

public class HangmanGuessingWord {
    private final HangmanSlides hangmanSlides;
    private final GuessingWordSource wordSource;
    private final String hint;
    private final CharSet usedChars = new CharOpenHashSet();
    private final int mistakes;
    private int mistakesDone = 0;
    @Setter
    private boolean hintFlag = false;

    @SuppressFBWarnings
    public HangmanGuessingWord(HangmanSlides slides, Difficulty difficulty, Collection<WordWithHint> words) {
        this.hangmanSlides = slides;
        slides.reset();
        WordWithHint chosen = CollectionsUseful.getRandomElement(words);
        hint = chosen.hint();
        this.wordSource = new GuessingWord(chosen.word());
        this.mistakes = Integer.max(MINIMAL_ATTEMPTS, switch (difficulty) {
            case EASY -> this.hangmanSlides.getMaxSlideInd();
            case MEDIUM -> this.hangmanSlides.getMaxSlideInd() - DIFFICULTY_DELTA;
            case HARD -> this.hangmanSlides.getMaxSlideInd() - 2 * DIFFICULTY_DELTA;
        });
    }

    public LetterResult tryLetter(char ch) {
        if (usedChars.contains(ch)) {
            return LetterResult.REPEAT;
        }
        usedChars.add(ch);

        if (wordSource.guessLetter(ch)) {
            return LetterResult.SUCCESS;
        } else {
            hangmanSlides.change(hangmanSlides.getMaxSlideInd() / mistakes
                    + (mistakesDone < hangmanSlides.getMaxSlideInd() % mistakes ? 1 : 0));
            ++mistakesDone;
            return LetterResult.FAILURE;
        }
    }

    public boolean isFinished() {
        return isLose() || isWin();
    }

    public boolean isWin() {
        return wordSource.isFinished();
    }

    public boolean isLose() {
        return mistakesDone == mistakes;
    }

    public String getField(boolean hidden) {
        return hangmanSlides.getSlide() + wordSource.getWord(hidden) + System.lineSeparator()
                + String.format("The number of attempts: %d", mistakes - mistakesDone)
                + (hintFlag ? System.lineSeparator() + hint : "");
    }

    private static final int DIFFICULTY_DELTA = 2;
    private static final int MINIMAL_ATTEMPTS = 1;
}
