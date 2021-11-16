package search.querymatcher;

import java.util.List;

import search.analyzer.StandardAnalyzer;

public class QueryMatcherFactory<T> {
  private final StandardAnalyzer queryAnalyzer;

  public QueryMatcherFactory(StandardAnalyzer queryAnalyzer) {
    this.queryAnalyzer = queryAnalyzer;
  }

  public QueryMatcher create(List<Document> database, MatchingOperator operator) {
    switch (operator) {
      case ALL:
        return new AllQueryMatcher(database, queryAnalyzer);
      case ANY:
        return new AnyQueryMatcher(database, queryAnalyzer);
      case NONE:
        return new NoneQueryMatcher(database, queryAnalyzer);
      default:
        return null;
    }
  }
}
