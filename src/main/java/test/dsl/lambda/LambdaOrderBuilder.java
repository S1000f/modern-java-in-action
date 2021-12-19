package test.dsl.lambda;

import java.util.function.Consumer;
import test.dsl.Order;
import test.dsl.Stock;
import test.dsl.Trade;
import test.dsl.Trade.Type;

public class LambdaOrderBuilder {

  private final Order order = new Order();

  public static Order order(Consumer<LambdaOrderBuilder> consumer) {
    LambdaOrderBuilder builder = new LambdaOrderBuilder();
    consumer.accept(builder);

    return builder.order;
  }

  public void forCustomer(String customer) {
    this.order.setCustomer(customer);
  }

  public void buy(Consumer<TradeBuilder> consumer) {
    trade(consumer, Type.BUY);
  }

  public void sell(Consumer<TradeBuilder> consumer) {
    trade(consumer, Type.SELL);
  }

  private void trade(Consumer<TradeBuilder> consumer, Trade.Type type) {
    TradeBuilder builder = new TradeBuilder();
    builder.trade.setType(type);
    consumer.accept(builder);
    order.addTrade(builder.trade);
  }

  public static class TradeBuilder {

    private final Trade trade = new Trade();

    public void quantity(int quantity) {
      this.trade.setQuantity(quantity);
    }

    public void price(double price) {
      this.trade.setPrice(price);
    }

    public void stock(Consumer<StockBuilder> consumer) {
      StockBuilder builder = new StockBuilder();
      consumer.accept(builder);
      this.trade.setStock(builder.stock);
    }
  }

  public static class StockBuilder {

    private final Stock stock = new Stock();

    public void symbol(String symbol) {
      this.stock.setSymbol(symbol);
    }

    public void market(String market) {
      this.stock.setMarket(market);
    }
  }

}
