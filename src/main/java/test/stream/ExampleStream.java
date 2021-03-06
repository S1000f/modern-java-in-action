package test.stream;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.Map.entry;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.filtering;
import static java.util.stream.Collectors.flatMapping;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
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
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import test.Dish;
import test.Dish.Type;
import test.Trader;
import test.Trader.Transaction;

public class ExampleStream {

  private static final List<Dish> menu = new ArrayList<>(Arrays.asList(
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

  private static final Map<String, List<String>> dishTags = new HashMap<>();

  static {
    dishTags.put("pork", Arrays.asList("greasy", "salty"));
    dishTags.put("beef", Arrays.asList("salty", "roasted"));
    dishTags.put("chicken", Arrays.asList("fried", "crisp"));
    dishTags.put("french fries", Arrays.asList("greasy", "fried"));
    dishTags.put("rice", Arrays.asList("light", "natural"));
    dishTags.put("season fruit", Arrays.asList("fresh", "natural"));
    dishTags.put("pizza", Arrays.asList("tasty", "salty"));
    dishTags.put("prawns", Arrays.asList("tasty", "roasted"));
    dishTags.put("salmon", Arrays.asList("delicious", "fresh"));
  }

  private static final List<Transaction> transactions;

  static {
    Trader raoul = Trader.of("Raoul", "Cambridge");
    Trader mario = Trader.of("Mario", "Milan");
    Trader alan = Trader.of("Alan", "Cambridge");
    Trader brian = Trader.of("Brian", "Cambridge");

    transactions = new ArrayList<>(Arrays.asList(
        Transaction.of(brian, 2011, 300),
        Transaction.of(raoul, 2012, 1000),
        Transaction.of(raoul, 2011, 400),
        Transaction.of(mario, 2012, 710),
        Transaction.of(mario, 2012, 700),
        Transaction.of(alan, 2012, 950)
    ));
  }

  // Java 9
  private static final Map<String, Integer> ageOfFriends = Map.of(
      "Raphael", 30, "Olivia", 25, "Thibaut", 26);

  // Java 9
  private static final Map<String, String> favoriteMovies = Map.ofEntries(
      entry("Raphael", "Star Wars"),
      entry("Cristina", "Matrix"),
      entry("Olivia", "James Bond")
  );

  enum CaloricLevel {
    DIET,
    NORMAL,
    FAT
  }

  public static boolean isPrime(int candidate) {
    int candidateRoot = (int) Math.sqrt(candidate);

    return IntStream.rangeClosed(2, candidateRoot)
        .noneMatch(i -> candidate % i == 0);
  }

  public static void exampleCollectionMethods() {
    List<Transaction> transactionList = new ArrayList<>(transactions);
    List<Transaction> transactionList1 = new ArrayList<>(transactions);

    try {
      // for-each ????????? ??????????????? Iterator ????????? ???????????? ???????????? ????????????
      // ????????? ????????? remove ???????????? Collection ????????? ???????????????.
      // ????????? remove ??? ?????????, ?????? ???????????? ????????? ?????? Iterator ????????? ???????????? ???????????? ????????? ????????? ????????????.
      for (Transaction transaction : transactionList) {
        if (transaction.getValue() > 700) {
          transactionList.remove(transaction);
        }
      }
    } catch (ConcurrentModificationException e) {
      e.printStackTrace();
    }

    // ??????1. ?????? ????????? ???????????? ????????? ???????????? ?????????
    final List<Transaction> collect = transactionList.stream()
        .filter(transaction -> transaction.getValue() <= 700)
        .collect(toList());
    System.out.println(collect);

    // ??????2. removeIf ??? ????????????
    transactionList.removeIf(tx -> tx.getValue() > 700);
    System.out.println(transactionList);

    // ???????????? ????????? ????????? ????????? ??????????????? for-each ??? ???????????? ?????? ?????? ???????????? ???????????? ????????????
    transactionList1.replaceAll(tx -> Transaction.of(tx.getTrader(), tx.getYear(), tx.getValue() + 1000));
    System.out.println(transactionList1);

    // ??? ?????? ????????? ??????????????? ?????????????????? ????????? ??? ??????
    favoriteMovies.entrySet()
        .stream()
        .sorted(Entry.comparingByKey())
        .forEachOrdered(System.out::println);

    final Map<String, String> friendMovieMap = new HashMap<>(favoriteMovies);
    friendMovieMap.computeIfAbsent("Nick", x -> x + "'s favorite is Die Hard");
    System.out.println(friendMovieMap);

    // computeIfPresent ??? ????????? ????????? ???????????? null ??? ????????? ?????? ?????? ?????? ????????????.
    // ????????? ???????????? remove ???????????? ??????????????? ???????????????, ????????? ?????? ???????????? ???????????? ??????.
    friendMovieMap.computeIfPresent("Nick", (k, v) -> null);
    System.out.println(friendMovieMap);

    Map<String, String> otherFriends = new HashMap<>();
    otherFriends.put("Nick", "Interstellar");
    otherFriends.put("Olivia", "Die Hard");

    // merge ??? ??? ?????? ????????? ?????? ????????? ?????? ???????????? ????????????. v2 ??? ?????? v ??? ????????????
    Map<String, String> everyone = new HashMap<>(favoriteMovies);
    otherFriends.forEach((k, v) -> everyone.merge(k, v, (v1, v2) -> v1 + " & " + v2));
    System.out.println(everyone);

    final ConcurrentHashMap<String, Long> concurrentHashMap = new ConcurrentHashMap<>();
    long threshold = 1;

    final Long aLong = concurrentHashMap.reduceValues(threshold, Long::max);
    final KeySetView<String, Long> view = concurrentHashMap.keySet();

  }

  public static void examplePartitoning() {
    final Map<Boolean, List<Dish>> vegeOrNot = menu.stream()
        .collect(partitioningBy(Dish::isVegetarian));

    final List<Dish> vegeMenu = vegeOrNot.get(true);

    final List<Dish> vegeMenu2 = menu.stream()
        .filter(Dish::isVegetarian)
        .collect(toList());

    System.out.println(vegeMenu);
    System.out.println(vegeMenu2);

    // partitioning ?????? Collectors ??? ?????? ??????????????? ????????? ?????? ??? ??? ??????.
    final Map<Boolean, Dish> mostCaloricPartitionedByVerge = menu.stream()
        .collect(partitioningBy(Dish::isVegetarian,
            collectingAndThen(maxBy(comparingInt(Dish::getCalories)), Optional::get)));

    System.out.println(mostCaloricPartitionedByVerge);

    int n = 40;

    final Map<Boolean, List<Integer>> collect = IntStream.rangeClosed(2, n)
        .boxed()
        .collect(partitioningBy(ExampleStream::isPrime));

    System.out.println(collect);

  }

  public static void exampleGrouping() {
    final Map<Type, List<Dish>> dishesByType = menu.stream()
        .collect(groupingBy(Dish::getType));

    System.out.println(dishesByType);

    // grouping ????????? ????????? ??? ??????, ????????? ????????? ????????? ????????? ??? ??? ??????.
    final Map<Type, List<Dish>> collect = menu.stream()
        .filter(dish -> dish.getCalories() > 500)
        .collect(groupingBy(Dish::getType));

    // grouping ???????????? ????????? ????????? filtering ??? ????????? ?????? ??? ??????????????? ?????? ????????? ?????? ??????.
    final Map<Type, List<Dish>> collect1 = menu.stream()
        .collect(groupingBy(Dish::getType, filtering(dish -> dish.getCalories() > 500, toList())));

    System.out.println(collect);
    System.out.println(collect1);

    // Collector ????????? ???????????? ???????????? ????????? ?????? (groupingBy, filtering, mapping 3????????? ?????????)
    final Map<Type, List<String>> collect2 = menu.stream()
        .collect(
            groupingBy(Dish::getType, filtering(dish -> dish.getCalories() > 500, mapping(Dish::getName, toList()))));

    System.out.println(collect2);

    // flatMapping ?????? ?????? ??????????????? ?????? ?????????????????? ?????? ??? ??????.
    final Map<Type, Set<String>> collect3 = menu.stream()
        .collect(
            groupingBy(Dish::getType, flatMapping(dish -> dishTags.get(dish.getName()).stream(), toSet())));

    // maxBy ??? ???????????? Optional ??? ???????????? ?????? collectingAndThen ??? ????????????.
    // collectingAndThen ??? thenApply ??? ????????? ??????
    final Map<Type, Dish> collect4 = menu.stream()
        .collect(groupingBy(Dish::getType, collectingAndThen(maxBy(comparingInt(Dish::getCalories)), Optional::get)));

    final Map<Type, Integer> collect5 = menu.stream()
        .collect(groupingBy(Dish::getType,
            mapping(Dish::getCalories,
                collectingAndThen(reducing(Integer::sum), Optional::get))));
  }

  public static void examples() {

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
