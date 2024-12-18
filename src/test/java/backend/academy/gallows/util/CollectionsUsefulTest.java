package backend.academy.gallows.util;

import org.instancio.Instancio;
import org.junit.jupiter.api.RepeatedTest;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class CollectionsUsefulTest {
    @RepeatedTest(10)
    void getRandomElement() {
        List<String> info = Instancio.ofList(String.class).size(10).create();

        String res = CollectionsUseful.getRandomElement(info);

        assertThat(res).isIn(info);
    }
}
