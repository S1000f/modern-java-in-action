package test.dsl;

import java.util.function.Consumer;
import java.util.stream.Stream;
import test.dsl.Trade.Type;

public class MixedBuilder {

  public static Order forCustomer(String customer, TradeBuilder... builders) {
    final Order order = new Order();
    order.setCustomer(customer);
    Stream.of(builders).forEach(builder -> order.addTrade(builder.trade));

    return order;
  }

  public static TradeBuilder buying(Consumer<TradeBuilder> consumer) {
    return trade(consumer, Type.BUY);
  }

  public static TradeBuilder selling(Consumer<TradeBuilder> consumer) {
    return trade(consumer, Type.SELL);
  }

  private static TradeBuilder trade(Consumer<TradeBuilder> consumer, Trade.Type type) {
    TradeBuilder builder = new TradeBuilder();
    builder.trade.setType(type);
    consumer.accept(builder);

    return builder;
  }

  public static class TradeBuilder {
    private final Trade trade = new Trade();

    public TradeBuilder quantity(int quantity) {
      trade.setQuantity(quantity);
      return this;
    }

    public TradeBuilder at(double price) {
      trade.setPrice(price);
      return this;
    }

    public StockBuilder stock(String symbol) {
      return new StockBuilder(this, trade, symbol);
    }
  }

  public static class StockBuilder {
    private final TradeBuilder builder;
    private final Trade trade;
    private final Stock stock = new Stock();

    private StockBuilder(TradeBuilder builder, Trade trade, String symbol) {
      this.builder = builder;
      this.trade = trade;
      this.stock.setSymbol(symbol);
    }

    public TradeBuilder on(String market) {
      this.stock.setMarket(market);
      this.trade.setStock(stock);

      return builder;
    }
  }

}
