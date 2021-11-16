package search.querymatcher;

import java.util.List;

import search.analyzer.Analyzer;
import search.analyzer.StandardAnalyzer;

public class QueryMatcherFactory<T> {
  private final StandardAnalyzer queryAnalyzer;
  private final Analyzer<T> dataAnalyzer;

  public QueryMatcherFactory(StandardAnalyzer queryAnalyzer, Analyzer<T> dataAnalyzer) {
    this.queryAnalyzer = queryAnalyzer;
    this.dataAnalyzer = dataAnalyzer;
  }

  public QueryMatcher<T> create(List<T> database, MatchingOperator operator) {
    switch (operator) {
      case ALL:
        return new AllQueryMatcher<>(database, queryAnalyzer, dataAnalyzer);
      case ANY:
        return new AnyQueryMatcher<>(database, queryAnalyzer, dataAnalyzer);
      case NONE:
        return new NoneQueryMatcher<>(database, queryAnalyzer, dataAnalyzer);
      default:
        return null;
    }
  }
}
