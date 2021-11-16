package search.querymatcher;

import java.util.Collections;
import java.util.List;
/**
 * A {@code Document} is a collection of words, it's what the search engine returns to match a
 * certain query
 */
public class Document {
  private final List<String> words;

  public Document(List<String> words) {
    this.words = words;
  }

  public List<String> getWords() {
    return Collections.unmodifiableList(words);
  }
}
