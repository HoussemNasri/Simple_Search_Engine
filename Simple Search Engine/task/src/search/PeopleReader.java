package search;

import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

/**
 * Reads data from a given InputStream line by line then parse that data to a {@see Person} object
 */
public class PeopleReader {
  private static final String EMAIL_REGEX =
      "[a-zA-Z][a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}";
  private static final String PERSON_DATA_PATTERN =
      String.format("[a-zA-Z]+ [a-zA-Z]+( %s)?", EMAIL_REGEX);
  private final Scanner scanner;

  public PeopleReader(InputStream inputStream) {
    Objects.requireNonNull(inputStream);
    scanner = new Scanner(inputStream).useDelimiter("\\R");
  }

  public boolean hasMorePeople() {
    return scanner.hasNext(PERSON_DATA_PATTERN);
  }

  public Person nextPerson() {
    if (hasMorePeople()) {
      return parsePerson(scanner.next());
    }
    return null;
  }

  private Person parsePerson(String personRawData) {
    String[] tokens = personRawData.trim().split("\\s");
    if (tokens.length == 3) {
      return new Person(tokens[0], tokens[1], tokens[2]);
    } else if (tokens.length == 2) {
      return new Person(tokens[0], tokens[1], null);
    }
    return null;
  }
}
