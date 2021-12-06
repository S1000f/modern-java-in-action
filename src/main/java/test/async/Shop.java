package test.async;

import static java.util.stream.Collectors.toList;

import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import test.async.Discount.Code;

@EqualsAndHashCode
@ToString
@Getter
@Builder
@AllArgsConstructor
public final class Shop {

  private String shopName;

  private Shop() {}

  public static Shop of(String shopName) {
    return Shop.builder()
        .shopName(shopName)
        .build();
  }

  public double getPrice(String product) {
    return calculatePrice(product);
  }

  public Future<Double> getPriceAsync(String product) {
    CompletableFuture<Double> future = new CompletableFuture<>();
    new Thread(() -> {
      try {
        double price = calculatePrice(product);
        future.complete(price);
      } catch (Exception ex) {
        future.completeExceptionally(ex);
      }
    }).start();

    return future;
  }

  // 위의 메소드와 작동 및 예외처리까지 완전히 동일하다
  public Future<Double> getPriceAsync2(String product) {
    return CompletableFuture.supplyAsync(() -> calculatePrice(product));
  }

  public String getPriceWithDiscount(String product) {
    final double price = calculatePrice(product);

    SecureRandom random = new SecureRandom();
    Discount.Code code = Discount.Code.values()[random.nextInt(Code.values().length)];

    return String.format("%s:%.2f:%s", shopName, price, code);
  }

  private double calculatePrice(String product) {
    ExampleCompletableFuture.delay();
    SecureRandom random = new SecureRandom();

    return random.nextDouble() * product.charAt(0) + product.charAt(1);
  }

}
