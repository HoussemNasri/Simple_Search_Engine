package search;

public class Person {
  private final String firstname;
  private final String lastname;
  private final String email;

  public Person(String firstname, String lastname, String email) {
    this.firstname = firstname.strip();
    this.lastname = lastname.strip();
    this.email = email != null ? email.strip() : null;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String toString() {
    if (email == null) {
      return String.join(" ", firstname, lastname);
    } else {
      return String.join(" ", firstname, lastname, email);
    }
  }
}
