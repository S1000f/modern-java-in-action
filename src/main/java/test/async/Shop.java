package test.async;

import java.security.SecureRandom;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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
      double price = calculatePrice(product);
      future.complete(price);
    }).start();

    return future;
  }

  private double calculatePrice(String product) {
    ExampleCompletableFuture.delay();
    SecureRandom random = new SecureRandom();

    return random.nextDouble() * product.charAt(0) + product.charAt(1);
  }

}
