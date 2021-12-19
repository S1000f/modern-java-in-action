package test.dsl.methodchaining;

import test.dsl.Order;
import test.dsl.Stock;
import test.dsl.Trade;
import test.dsl.Trade.Type;

public class MethodChainingOrderBuilder {

  public final Order order = new Order();

  private MethodChainingOrderBuilder(String customer) {
    order.setCustomer(customer);
  }

  public static MethodChainingOrderBuilder forCustomer(String customer) {
    return new MethodChainingOrderBuilder(customer);
  }

  public TradeBuilder buy(int quantity) {
    return new TradeBuilder(this, Type.BUY, quantity);
  }

  public TradeBuilder sell(int quantity) {
    return new TradeBuilder(this, Type.SELL, quantity);
  }

  public MethodChainingOrderBuilder addTrade(Trade trade) {
    order.addTrade(trade);
    return this;
  }

  public Order end() {
    return order;
  }

  public static class TradeBuilder {

    private final MethodChainingOrderBuilder builder;
    public final Trade trade = new Trade();

    private TradeBuilder(MethodChainingOrderBuilder builder, Trade.Type type, int quantity) {
      this.builder = builder;
      this.trade.setType(type);
      this.trade.setQuantity(quantity);
    }

    public StockBuilder stock(String symbol) {
      return new StockBuilder(builder, trade, symbol);
    }
  }

  public static class StockBuilder {

    private final MethodChainingOrderBuilder orderBuilder;
    private final Trade trade;
    private final Stock stock = new Stock();

    private StockBuilder(MethodChainingOrderBuilder orderBuilder, Trade trade, String symbol) {
      this.orderBuilder = orderBuilder;
      this.trade = trade;
      this.stock.setSymbol(symbol);
    }

    public TradeBuilderWithStock on(String market) {
      stock.setMarket(market);
      trade.setStock(stock);
      return new TradeBuilderWithStock(orderBuilder, trade);
    }
  }

  public static class TradeBuilderWithStock {

    private final MethodChainingOrderBuilder builder;
    private final Trade trade;

    private TradeBuilderWithStock(MethodChainingOrderBuilder builder, Trade trade) {
      this.builder = builder;
      this.trade = trade;
    }

    public MethodChainingOrderBuilder at(double price) {
      trade.setPrice(price);
      return builder.addTrade(this.trade);
    }
  }

}
