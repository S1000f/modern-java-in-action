package test.dsl;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

  private String customer;
  private List<Trade> trades = new ArrayList<>();

  public void addTrade(Trade trade) {
    trades.add(trade);
  }

  public double getValue() {
    return trades.stream()
        .mapToDouble(Trade::getValue)
        .sum();
  }

}
