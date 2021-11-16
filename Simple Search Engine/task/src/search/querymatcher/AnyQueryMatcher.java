package search.querymatcher;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import search.analyzer.StandardAnalyzer;

public class AnyQueryMatcher extends QueryMatcher {
  public AnyQueryMatcher(
          List<Document> database, StandardAnalyzer queryAnalyzer) {
    super(database, queryAnalyzer);
  }

  @Override
  public Set<Integer> match(String query) {
    if (query == null || query.isBlank()) {
      return Collections.emptySet();
    }
    List<String> queryTokens = tokenizeQuery(query);

    Set<Integer> matched = new HashSet<>();
    queryTokens.stream().map(this::getIndexes).forEach(matched::addAll);

    return matched;
  }
}
