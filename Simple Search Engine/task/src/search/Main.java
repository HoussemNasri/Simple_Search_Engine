package search;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import search.search.PeopleFinderImpl;
import search.querymatcher.MatchingOperator;

public class Main {
  private static final Scanner scanner = new Scanner(System.in);

  private boolean isRunning = false;

  private List<Person> allPeople;

  private void printMenu() {
    System.out.println(
        "=== Menu ===\n" + "1. Find a person\n" + "2. Print all people\n" + "0. Exit");
  }

  private String input() {
    System.out.print("> ");
    return scanner.nextLine();
  }

  private List<Person> readPeopleFromConsole() {
    PeopleReader peopleReader = new PeopleReader(System.in);
    List<Person> people = new ArrayList<>();
    System.out.println("Enter the number of people:");
    int personCount = Integer.parseInt(input());
    System.out.println("Enter all people:");
    for (int i = 0; i < personCount; i++) {
      System.out.print("> ");
      if (peopleReader.hasMorePeople()) {
        people.add(peopleReader.nextPerson());
      }
    }
    System.out.println();
    return people;
  }

  private List<Person> readPeopleFromFile(File file) {
    try (FileInputStream fileInputStream = new FileInputStream(file)) {
      PeopleReader reader = new PeopleReader(fileInputStream);
      List<Person> people = new ArrayList<>();
      while (reader.hasMorePeople()) {
        people.add(reader.nextPerson());
      }
      return people;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Collections.emptyList();
  }

  private Set<Person> findPerson(String query, MatchingOperator operator) {
    return new PeopleFinderImpl(allPeople, operator).find(query);
  }

  private MatchingOperator readMatchingOperator() {
    System.out.println("Select a matching strategy: ALL, ANY, NONE");
    String matchingOperatorInput = input();
    System.out.println();
    try {
      if (matchingOperatorInput == null || matchingOperatorInput.isBlank()) {
        throw new IllegalArgumentException();
      }
      return MatchingOperator.valueOf(matchingOperatorInput.toUpperCase());
    } catch (IllegalArgumentException e) {
      return MatchingOperator.ANY;
    }
  }

  private String readQuery() {
    System.out.println("Enter a name or email to search all suitable people. ");
    return input();
  }

  private void onFindPersonMenuSelected() {
    MatchingOperator matchingOperator = readMatchingOperator();
    String query = readQuery();
    System.out.println();
    Set<Person> foundPeople = findPerson(query, matchingOperator);
    int foundPeopleCount = foundPeople.size();
    if (foundPeopleCount > 0) {
      System.out.printf("%d persons found:%n", foundPeopleCount);
      foundPeople.forEach(System.out::println);
    } else {
      System.out.println("No matching people found.");
    }
    System.out.println();
  }

  private void onPrintAllPeopleMenuSelected() {
    System.out.println("=== List of people ===");
    allPeople.forEach(System.out::println);
    System.out.println();
  }

  private void exit() {
    System.out.println("Bye!");
    System.exit(0);
  }

  private void processArguments(String[] args) {
    if (args.length > 0) {
      if (args.length == 2) {
        if (args[1].endsWith(".txt")) {
          allPeople = readPeopleFromFile(new File(args[1]));
        } else {
          exit();
        }

      } else {
        exit();
      }
    } else {
      allPeople = readPeopleFromConsole();
    }
  }

  private void run(String[] args) {
    processArguments(args);
    while (true) {
      int selectedMenuOption;
      do {
        printMenu();
        selectedMenuOption = Integer.parseInt(input());
        if (selectedMenuOption > 2 || selectedMenuOption < 0) {
          System.out.println("Incorrect option! Try again.");
        }
        System.out.println();
      } while (selectedMenuOption > 2 || selectedMenuOption < 0);

      switch (selectedMenuOption) {
        case 1:
          onFindPersonMenuSelected();
          break;
        case 2:
          onPrintAllPeopleMenuSelected();
          break;
        case 0:
          exit();
          break;
      }
    }
  }

  public static void main(String[] args) {
    new Main().run(args);
  }
}
