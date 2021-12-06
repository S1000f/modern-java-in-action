package test.async;

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
public class Quote {

  private final String shopName;
  private final double price;
  private final Code discountCode;

  public static Quote parse(String s) {
    final String[] split = s.split(":");

    return Quote.builder()
        .shopName(split[0])
        .price(Double.parseDouble(split[1]))
        .discountCode(Code.valueOf(split[2]))
        .build();
  }

}
