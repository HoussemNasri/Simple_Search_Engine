package search.querymatcher;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import search.analyzer.StandardAnalyzer;

public class NoneQueryMatcher extends QueryMatcher {

  public NoneQueryMatcher(List<Document> database, StandardAnalyzer queryAnalyzer) {
    super(database, queryAnalyzer);
  }

  @Override
  public Set<Integer> match(String query) {
    Set<Integer> matched = new HashSet<>(getAllIndexes());
    matched.removeAll(new AnyQueryMatcher(getDocumentList(), getQueryAnalyzer()).match(query));
    return matched;
  }

  private Set<Integer> getAllIndexes() {
    return IntStream.range(0, getDocumentList().size()).boxed().collect(Collectors.toSet());
  }
}
