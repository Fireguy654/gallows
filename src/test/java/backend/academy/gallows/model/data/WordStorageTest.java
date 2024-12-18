package backend.academy.gallows.model.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

class WordStorageTest {
    @Test
    @DisplayName("The amount of categories shouldn't be zero and each category shouldn't be empty.")
    void checkNotEmpty() {
        WordStorage wordStorage = new WordStorage();

        List<String> categories = wordStorage.getCategories();
        List<List<WordWithHint>> words = categories.stream().map(wordStorage::getWords).toList();

        assertThat(categories).isNotEmpty();
        assertThat(words).allSatisfy(wordGroup -> assertThat(wordGroup).isNotEmpty());
    }
}
