package search.querymatcher;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import search.analyzer.Analyzer;
import search.analyzer.StandardAnalyzer;

public class NoneQueryMatcher<T> extends QueryMatcher<T> {

  public NoneQueryMatcher(
      List<T> database, StandardAnalyzer queryAnalyzer, Analyzer<T> dataAnalyzer) {
    super(database, queryAnalyzer, dataAnalyzer);
  }

  @Override
  public Set<Integer> match(String query) {
    Set<Integer> matched = new HashSet<>(getAllIndexes());
    matched.removeAll(
        new AnyQueryMatcher<>(getDatabase(), getQueryAnalyzer(), getDataAnalyzer()).match(query));
    return matched;
  }

  private Set<Integer> getAllIndexes() {
    return IntStream.range(0, getDatabase().size()).boxed().collect(Collectors.toSet());
  }
}
