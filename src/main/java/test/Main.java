package test;

import static java.util.stream.Collectors.toList;
import static test.Dish.Type.FISH;
import static test.Dish.Type.MEAT;
import static test.Dish.Type.OTHER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import test.Dish.Type;

public class Main {
  public static void main(String[] args) {
    List<Dish> menu = new ArrayList<>(Arrays.asList(
        Dish.of("pork", false, 800, MEAT),
        Dish.of("beef", false, 700, MEAT),
        Dish.of("chicken", false, 400, MEAT),
        Dish.of("french fries", false, 530, OTHER),
        Dish.of("rice", true, 350, OTHER),
        Dish.of("season fruit", true, 120, OTHER),
        Dish.of("pizza", true, 550, OTHER),
        Dish.of("prawns", false, 300, FISH),
        Dish.of("salmon", false, 450, FISH)
    ));

    List<String> threeHighCalroricDishNames = menu.stream()
        .filter(dish -> dish.getCalories() > 300)
        .map(Dish::getName)
        .limit(3)
        .collect(toList());

    System.out.println(threeHighCalroricDishNames);


  }

}

