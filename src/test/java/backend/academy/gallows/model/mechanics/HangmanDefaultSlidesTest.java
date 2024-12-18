package backend.academy.gallows.model.mechanics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class HangmanDefaultSlidesTest {
    @Test
    @DisplayName("Trying to change the max slide hangman state with positive number should do nothing.")
    void changeMax() {
        HangmanDefaultSlides hangmanSlide = new HangmanDefaultSlides();
        hangmanSlide.change(hangmanSlide.getMaxSlideInd());
        String expectedPicture = hangmanSlide.getSlide();

        hangmanSlide.change(1);

        assertThat(hangmanSlide.getSlide()).isEqualTo(expectedPicture);
    }

    @Test
    @DisplayName("The reseted hangman state should be the same as just created.")
    void reset() {
        HangmanDefaultSlides hangmanSlide = new HangmanDefaultSlides();
        hangmanSlide.change(2);
        HangmanDefaultSlides justCreatedHangmanSlide = new HangmanDefaultSlides();

        hangmanSlide.reset();

        assertThat(hangmanSlide.getSlide()).isEqualTo(justCreatedHangmanSlide.getSlide());
    }
}
