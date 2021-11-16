package search.analyzer;

import java.util.Arrays;
import java.util.List;

import search.Person;

public class PeopleAnalyzer implements Analyzer<Person> {
  @Override
  public List<String> analyze(Person person) {
    return Arrays.asList(person.toString().split("\\s"));
  }
}
