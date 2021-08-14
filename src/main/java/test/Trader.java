package test;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public final class Trader {

  private final String name;
  private final String city;

  private Trader(String name, String city) {
    this.name = name;
    this.city = city;
  }

  public static Trader of(String name, String city) {
    return new Trader(name, city);
  }

  @EqualsAndHashCode
  @ToString
  @Getter
  public static final class Transaction {

    private final Trader trader;
    private final int year;
    private final int value;

    private Transaction(Trader trader, int year, int value) {
      this.trader = trader;
      this.year = year;
      this.value = value;
    }

    public static Transaction of(Trader trader, int year, int value) {
      return new Transaction(trader, year, value);
    }
  }

}
