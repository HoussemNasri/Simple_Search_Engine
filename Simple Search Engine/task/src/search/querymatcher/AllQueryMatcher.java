package search.querymatcher;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import search.analyzer.Analyzer;
import search.analyzer.StandardAnalyzer;

public class AllQueryMatcher<T> extends QueryMatcher<T> {
  public AllQueryMatcher(
      List<T> database, StandardAnalyzer queryAnalyzer, Analyzer<T> dataAnalyzer) {
    super(database, queryAnalyzer, dataAnalyzer);
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
