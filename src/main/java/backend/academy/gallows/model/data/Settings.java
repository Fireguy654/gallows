package backend.academy.gallows.model.data;

public class Settings {
    public String category = null;
    public Difficulty difficulty;

    public Settings(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
