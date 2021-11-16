package search.search;

import java.util.List;
import java.util.Set;

import search.Person;
import search.querymatcher.MatchingOperator;

public interface PeopleFinder {
  Set<Person> find(String query);

  void setDatabase(List<Person> peopleDatabase);

  void setOperator(MatchingOperator operator);
}
