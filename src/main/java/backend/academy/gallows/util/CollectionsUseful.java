package backend.academy.gallows.util;

import java.security.SecureRandom;
import java.util.Collection;

public final class CollectionsUseful {
    private CollectionsUseful() {}

    public static <T> T getRandomElement(Collection<T> collection) {
        return collection.stream().skip(SECURE_RANDOM.nextInt(collection.size())).findFirst().orElse(null);
    }

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
}
