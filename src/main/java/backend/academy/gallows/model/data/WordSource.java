package backend.academy.gallows.model.data;

import java.util.List;

public interface WordSource {
    List<String> getCategories();

    List<WordWithHint> getWords(String category);
}
