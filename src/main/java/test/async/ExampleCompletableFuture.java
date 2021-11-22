package test.async;

import java.util.concurrent.Future;

public final class ExampleCompletableFuture {

  public static void delay() {
    try {
      Thread.sleep(1000L);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public static void examples() {
    Shop shop = Shop.of("BestShop");

    long start = System.nanoTime();

    final Future<Double> futurePrice = shop.getPriceAsync("my favorite product");

    long invocationTime = ((System.nanoTime() - start) / 1_000_000);

    System.out.println("invocation returned after " + invocationTime + " milli");

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
}
