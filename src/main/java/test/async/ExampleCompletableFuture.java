package test.async;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class ExampleCompletableFuture {

  public static void delay() {
    try {
      Thread.sleep(1000L);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public static void basicExample() {
    Shop shop = Shop.of("BestShop");

    long start = System.nanoTime();

    final Future<Double> futurePrice = shop.getPriceAsync("my favorite product");

    long invocationTime = ((System.nanoTime() - start) / 1_000_000);

    System.out.println("invocation returned after " + invocationTime + " milli");

    // do something else
    final Shop worstShopEver = Shop.of("worst shop ever");

    try {
      final Double price = futurePrice.get();
      System.out.printf("the price is %.2f%n", price);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
    System.out.println("price returned after " + retrievalTime + " milli");
  }

  public void withStreamExample() {
    final List<Shop> shops = Arrays.asList(Shop.of("BestShopEver"), Shop.of("LetsBuy"), Shop.of("MyFavoriteShop"),
        Shop.of("BuyItAll"));
  }

  // 동기화 - 병렬 스트림
  public static List<String> findPrices(List<Shop> shops, String product) {
    return shops.parallelStream()
        .map(shop -> String.format("%s price is %.2f", shop.getShopName(), shop.getPrice(product)))
        .collect(toList());
  }

  // 비동기 사용
  public static List<String> findPrices2(List<Shop> shops, String product) {
    final List<CompletableFuture<String>> collect = shops.stream()
        .map(shop -> CompletableFuture.supplyAsync(() -> shop.getShopName() + " price is " + shop.getPrice(product)))
        .collect(toList());

    // 스트림 연산은 게으른 연산이므로, 하나의 파이프라인이 아니라 비동기의 결과를 취합하는 파이프라인을 별도로 분리한다
    // join 메소드는 예외를 발생시키지 않는다 - try/catch 불필요
    return collect.stream()
        .map(CompletableFuture::join)
        .collect(toList());
  }

  public static List<String> findPrices3(List<Shop> shops, String product) {
    Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), r -> {
      Thread thread = new Thread(r);
      thread.setDaemon(true);
      return thread;
    });

    final List<CompletableFuture<String>> collect = shops.stream()
        .map(shop -> CompletableFuture.supplyAsync(() -> shop.getShopName() + " price is " + shop.getPrice(product),
            executor))
        .collect(toList());

    return collect.stream()
        .map(CompletableFuture::join)
        .collect(toList());
  }

}
