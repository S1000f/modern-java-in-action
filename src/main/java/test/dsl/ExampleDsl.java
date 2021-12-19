package test.dsl;

import static test.dsl.MixedBuilder.buying;
import static test.dsl.MixedBuilder.selling;
import static test.dsl.nested.NestedOrderBuilder.at;
import static test.dsl.nested.NestedOrderBuilder.buy;
import static test.dsl.nested.NestedOrderBuilder.on;
import static test.dsl.nested.NestedOrderBuilder.sell;
import static test.dsl.nested.NestedOrderBuilder.stock;

import test.dsl.lambda.LambdaOrderBuilder;
import test.dsl.methodchaining.MethodChainingOrderBuilder;
import test.dsl.nested.NestedOrderBuilder;

public class ExampleDsl {

  public static void examples() {
    final Order order = MethodChainingOrderBuilder.forCustomer("Mr. Whale")
        .buy(80)
        .stock("Apple")
        .on("NASDAQ")
        .at(14005.44)
        .sell(12)
        .stock("Tesla")
        .on("NASDAQ")
        .at(904.5)
        .end();

    final Order order1 = NestedOrderBuilder.order("Milly",
        buy(80, stock("Apple", on("NYSE")), at(1344.33)),
        sell(12, stock("Tesla", on("NASDAQ")), at(59243.442)));

    final Order order2 = LambdaOrderBuilder.order(orders -> {
      orders.forCustomer("Big Hand");
      orders.buy(t -> {
        t.quantity(80);
        t.price(134.43);
        t.stock(stock -> {
          stock.symbol("IBM");
          stock.market("NYSE");
        });
      });
      orders.sell(t -> {
        t.quantity(34);
        t.price(9005.4);
        t.stock(stock -> {
          stock.symbol("TESLA");
          stock.market("NASDAQ");
        });
      });
    });

    final Order order3 = MixedBuilder.forCustomer("LastCustomer",
        buying(t -> t.quantity(49)
            .stock("APPLE")
            .on("NYSE")
            .at(24525.24)),
        selling(t -> t.quantity(134)
            .at(2555.44)
            .stock("TESLA")
            .on("NASDAQ")));

    final double tax = TaxCalculator.of(TaxCalculator::general)
        .with(TaxCalculator::regional)
        .with(TaxCalculator::surcharge)
        .calculate(order);
  }

}
