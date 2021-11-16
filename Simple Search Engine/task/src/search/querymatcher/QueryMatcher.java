package search.querymatcher;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import search.analyzer.StandardAnalyzer;

public abstract class QueryMatcher {
  private final Map<String, Set<Integer>> invertedIndex = new HashMap<>();

  private final StandardAnalyzer queryAnalyzer;
  private final List<Document> database;

  public QueryMatcher(List<Document> documentList, StandardAnalyzer queryAnalyzer) {
    this.database = documentList;
    this.queryAnalyzer = queryAnalyzer;
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

  protected List<Document> getDocumentList() {
    return database;
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
      Document document = database.get(i);
      document.getWords().forEach(word -> putToken(word, finalI));
    }
  }
}
