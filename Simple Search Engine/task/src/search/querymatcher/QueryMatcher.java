package search.querymatcher;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import search.analyzer.Analyzer;
import search.analyzer.StandardAnalyzer;

public abstract class QueryMatcher<T> {
  private final Map<String, Set<Integer>> invertedIndex = new HashMap<>();

  private final StandardAnalyzer queryAnalyzer;
  private final Analyzer<T> dataAnalyzer;
  private final List<T> database;

  public QueryMatcher(List<T> database, StandardAnalyzer queryAnalyzer, Analyzer<T> dataAnalyzer) {
    this.database = database;
    this.queryAnalyzer = queryAnalyzer;
    this.dataAnalyzer = dataAnalyzer;
    startIndexing();
  }

  public abstract Set<Integer> match(String query);

  /** Split {@code query} into list of tokens using {@code queryAnalyzer} */
  public List<String> tokenizeQuery(String query) {
    return queryAnalyzer.analyze(query);
  }

  /** Returns the list of indexes where {@code word} has occurred */
  public Set<Integer> getIndexes(String word) {
    return invertedIndex.getOrDefault(normalize(word), Collections.emptySet());
  }

  protected List<T> getDatabase() {
    return database;
  }

  protected Analyzer<T> getDataAnalyzer() {
    return dataAnalyzer;
  }

  protected StandardAnalyzer getQueryAnalyzer() {
    return queryAnalyzer;
  }

  /** Need to normalise words in the indexed text as well as query terms to the same form */
  protected final String normalize(String token) {
    return token.toUpperCase();
  }

  private void putToken(String token, int index) {
    if (token == null || index < 0 || index >= database.size()) {
      return;
    }
    String term = normalize(token);
    if (invertedIndex.containsKey(term)) {
      invertedIndex.get(term).add(index);
    } else {
      invertedIndex.put(term, new HashSet<>(List.of(index)));
    }
  }

  private void startIndexing() {
    for (int i = 0; i < database.size(); i++) {
      final int finalI = i;
      T person = database.get(i);
      dataAnalyzer.analyze(person).forEach(token -> putToken(token, finalI));
    }
  }
}
