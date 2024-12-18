package backend.academy.gallows.model.data;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;

public class WordStorage implements WordSource {
    private static final Map<String, Map<String, String>> WORDS = ImmutableMap.of(
            "Sport", ImmutableMap.of(
                    "Volleyball", "In this sport the highest recorded score in one set was 54-52",
                    "Basketball", "At first players weren't allowed move with the ball in this sport.",
                    "Hockey", "In this sport players can lose up to 5 to 8 pounds in a single game.",
                    "Football", "It's the most popular sport all around the world.",
                    "Badminton", "The fastest hit in this sport is 426 kilometers per hour."),
            "Halloween", ImmutableMap.of(
                    "Spider", "Its legs are controlled by its blood flows.",
                    "Vampire", "He has good manners and loves red drinks.",
                    "Werewolf", "He can transfer his infection to another person with a bite.",
                    "Witch", "She knows how to fly and make different potions.",
                    "Frankenstein", "He consists of different parts of body."),
            "Countries", ImmutableMap.of(
                    "Russia", "It's the biggest country in the world.",
                    "Germany", "People of this country are the most sluggish ones.",
                    "China", "It's a country with the fastest economy growth.",
                    "France", "The language of this country is named 'The language of love'.",
                    "Australia", "People usually confuse this country with Austria.")
    );

    @Override
    public List<String> getCategories() {
        return WORDS.keySet().stream().toList();
    }

    @Override
    public List<WordWithHint> getWords(String category) {
        Map<String, String> words = WORDS.get(category);
        return words == null ? List.of() : words.entrySet().stream()
                .map((entry) -> new WordWithHint(entry.getKey(), entry.getValue())).toList();
    }
}
