package search.analyzer;

import java.util.List;
import java.util.Set;

/**
 * Analysis makes full-text search possible through tokenization: breaking a text down into smaller
 * chunks, called tokens. In most cases, these tokens are individual words.
 *
 * @param <T> the type of object to analyze
 */
public interface Analyzer<T> {
  List<String> analyze(T text);
}
