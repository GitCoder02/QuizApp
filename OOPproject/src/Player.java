public class Player {
  private String name;
  private String registrationNumber;
  private int score;

  public Player(String name, String registrationNumber) {
      this.name = name;
      this.registrationNumber = registrationNumber;
      this.score = 0;
  }

  public String getName() {
      return name;
  }

  public String getRegistrationNumber() {
      return registrationNumber;
  }

  public int getScore() {
      return score;
  }

  public void increaseScore() {
      score++;
  }
}
