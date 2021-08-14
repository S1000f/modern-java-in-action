package test;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static test.Dish.Type.FISH;
import static test.Dish.Type.MEAT;
import static test.Dish.Type.OTHER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import test.Trader.Transaction;

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

    List<String> collect = new ArrayList<>(Arrays.asList("Hello", "World")).stream()
        .flatMap(word -> Arrays.stream(word.split("")))
        .distinct()
        .collect(toList());

    System.out.println(collect);

    List<Integer> num1 = new ArrayList<>(Arrays.asList(1, 2, 3));
    List<Integer> num2 = new ArrayList<>(Arrays.asList(3, 4));

    List<ArrayList<Integer>> collect1 = num1.stream()
        .flatMap(n1 -> num2.stream()
            .filter(n2 -> (n1 + n2) % 3 == 0)
            .map(n2 -> new ArrayList<>(Arrays.asList(n1, n2))))
        .collect(toList());

    System.out.println(collect1);

    Integer reduce = menu.stream()
        .map(dish -> 1)
        .reduce(0, Integer::sum);

    System.out.println(reduce);

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

    List<Transaction> q1 = transactions.stream()
        .filter(tr -> tr.getYear() == 2011)
        .sorted(comparing(Transaction::getValue))
        .collect(toList());

    System.out.println(q1);

    List<String> q2 = transactions.stream()
        .map(Transaction::getTrader)
        .map(Trader::getCity)
        .distinct()
        .collect(toList());

    System.out.println(q2);

    List<Trader> q3 = transactions.stream()
        .map(Transaction::getTrader)
        .filter(trader -> trader.getCity().equals("Cambridge"))
        .sorted(comparing(Trader::getName))
        .distinct()
        .collect(toList());

    System.out.println(q3);

    List<String> q4 = transactions.stream()
        .map(tr -> tr.getTrader().getName())
        .distinct()
        .sorted(String::compareTo)
        .collect(toList());

    System.out.println(q4);

    boolean q5 = transactions.stream()
        .anyMatch(tr -> tr.getTrader().getCity().equals("Milan"));

    System.out.println(q5);

    transactions.stream()
        .filter(tr -> tr.getTrader().getCity().equals("Cambridge"))
        .forEach(tr -> System.out.println(tr.getValue()));

    Optional<Integer> q7 = transactions.stream()
        .map(Transaction::getValue)
        .reduce(Integer::max);

    Optional<Transaction> min = transactions.stream()
        .min(comparing(Transaction::getValue));

  }

}

