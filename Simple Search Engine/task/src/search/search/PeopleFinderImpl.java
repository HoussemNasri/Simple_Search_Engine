package search.search;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import search.Person;
import search.analyzer.PeopleAnalyzer;
import search.analyzer.StandardAnalyzer;
import search.querymatcher.MatchingOperator;
import search.querymatcher.QueryMatcherFactory;
import search.querymatcher.Document;

/**
 * This is the default implementation of the {@code PeopleFinder} interface, backed by the Inverted
 * Index data structure.
 */
public class PeopleFinderImpl implements PeopleFinder {
  private List<Person> peopleDatabase;
  private MatchingOperator operator;
  private final QueryMatcherFactory<Person> queryMatcherFactory;
  private final PeopleAnalyzer peopleAnalyzer = new PeopleAnalyzer();

  public PeopleFinderImpl(List<Person> peopleDatabase, MatchingOperator operator) {
    setDatabase(peopleDatabase);
    setOperator(operator);
    this.queryMatcherFactory = new QueryMatcherFactory<>(new StandardAnalyzer());
  }

  @Override
  public Set<Person> find(String query) {
    List<Document> peopleRawData =
        peopleDatabase.stream()
            .map(peopleAnalyzer::analyze)
            .map(Document::new)
            .collect(Collectors.toList());
    return queryMatcherFactory.create(peopleRawData, operator).match(query).stream()
        .map(peopleDatabase::get)
        .collect(Collectors.toSet());
  }

  @Override
  public void setDatabase(List<Person> peopleDatabase) {
    Objects.requireNonNull(peopleDatabase);
    this.peopleDatabase = peopleDatabase;
  }

  @Override
  public void setOperator(MatchingOperator operator) {
    Objects.requireNonNull(operator);
    this.operator = operator;
  }
}
