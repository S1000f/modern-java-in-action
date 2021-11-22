package test.stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.toList;
import static test.Dish.Type.FISH;
import static test.Dish.Type.MEAT;
import static test.Dish.Type.OTHER;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import test.Dish;
import test.Trader;
import test.Trader.Transaction;

public class ExampleStream {

  public static void examples() {
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

    final Comparator<Dish> dishComparator = Comparator.comparingInt(Dish::getCalories);

    final Optional<Dish> collect2 = menu.stream()
        .collect(maxBy(dishComparator));

    final IntSummaryStatistics collect3 = menu.stream()
        .collect(summarizingInt(Dish::getCalories));

    final String joining = menu.stream()
        .map(Dish::getName)
        .collect(joining(", "));

    System.out.println(joining);

    final Integer caloriesSum = menu.stream()
        .collect(reducing(0, Dish::getCalories, (a, b) -> a + b));

    final Integer caloriesSum2 = menu.stream()
        .map(Dish::getCalories)
        .reduce(0, Integer::sum);

    final Optional<Dish> mostCalorie = menu.stream()
        .reduce((a, b) -> a.getCalories() > b.getCalories() ? a : b);

    List<String> collect = new ArrayList<>(Arrays.asList("Hello", "World")).stream()
        .flatMap(word -> Arrays.stream(word.split("")))
        .distinct()
        .collect(toList());

    List<Integer> num1 = new ArrayList<>(Arrays.asList(1, 2, 3));
    List<Integer> num2 = new ArrayList<>(Arrays.asList(3, 4));

    List<ArrayList<Integer>> collect1 = num1.stream()
        .flatMap(n1 -> num2.stream()
            .filter(n2 -> (n1 + n2) % 3 == 0)
            .map(n2 -> new ArrayList<>(Arrays.asList(n1, n2))))
        .collect(toList());

    Integer reduce = menu.stream()
        .map(dish -> 1)
        .reduce(0, Integer::sum);

    Trader raoul = Trader.of("Raoul", "Cambridge");
    Trader mario = Trader.of("Mario", "Milan");
    Trader alan = Trader.of("Alan", "Cambridge");
    Trader brian = Trader.of("Brian", "Cambridge");

    List<Transaction> transactions = new ArrayList<>(Arrays.asList(
        Transaction.of(brian, 2011, 300),
        Transaction.of(raoul, 2012, 1000),
        Transaction.of(raoul, 2011, 400),
        Transaction.of(mario, 2012, 710),
        Transaction.of(mario, 2012, 700),
        Transaction.of(alan, 2012, 950)
    ));

    Optional<Transaction> min = transactions.stream()
        .min(comparing(Transaction::getValue));

    int calorieSum = menu.stream()
        .mapToInt(Dish::getCalories)
        .sum();

    long count = IntStream.rangeClosed(1, 100)
        .filter(n -> n % 2 == 0)
        .count();

    Stream.of("Modern", "Java", "In", "Action")
        .map(String::toUpperCase)
        .forEach(System.out::println);

    Stream<Object> empty = Stream.empty();

    long uniqueWords = 0;
    try(Stream<String> lines = Files.lines(Paths.get("/data.txt"), StandardCharsets.UTF_8)) {
      uniqueWords = lines.flatMap(line -> Arrays.stream(line.split("\\^")))
          .distinct()
          .count();
    } catch (
        IOException e) {
      //
    }

    Stream.iterate(0, n -> n + 2)
        .limit(10)
        .forEach(System.out::println);
  }

}
