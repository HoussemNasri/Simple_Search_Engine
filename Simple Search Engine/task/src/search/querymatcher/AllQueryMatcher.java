package search.querymatcher;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import search.analyzer.StandardAnalyzer;

public class AllQueryMatcher extends QueryMatcher {
  public AllQueryMatcher(
          List<Document> database, StandardAnalyzer queryAnalyzer) {
    super(database, queryAnalyzer);
  }

  @Override
  public Set<Integer> match(String query) {
    if (query == null || query.isBlank()) {
      return Collections.emptySet();
    }
    List<String> queryTokens = tokenizeQuery(query);

    Set<Integer> matched = new HashSet<>(getIndexes(queryTokens.get(0)));
    queryTokens.stream().skip(1).map(this::getIndexes).forEach(matched::retainAll);

    return matched;
  }
}
