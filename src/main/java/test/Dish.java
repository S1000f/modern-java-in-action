package test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString(of = {"name", "type"})
@Getter
@Builder
@AllArgsConstructor
public final class Dish {

  private String name;
  private boolean vegetarian;
  private int calories;
  private Type type;

  private Dish() {}

  public static Dish of(String name, boolean vegetarian, int calories, Type type) {
    return Dish.builder()
        .name(name)
        .vegetarian(vegetarian)
        .calories(calories)
        .type(type)
        .build();
  }

  public enum Type {
    MEAT,
    FISH,
    OTHER
  }

}
