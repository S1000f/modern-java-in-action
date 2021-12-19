package test.dsl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trade {

  public enum Type {
    BUY,
    SELL
  }

  private Type type;
  private Stock stock;
  private int quantity;
  private double price;

  public double getValue() {
    return quantity * price;
  }

}
