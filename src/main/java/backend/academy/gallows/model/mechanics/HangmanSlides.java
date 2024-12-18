package backend.academy.gallows.model.mechanics;

public interface HangmanSlides {
    String getSlide();

    void change(int len);

    void reset();

    int getMaxSlideInd();
}
