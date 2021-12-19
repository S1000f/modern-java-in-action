package test.dsl.nested;

import java.util.stream.Stream;
import test.dsl.Order;
import test.dsl.Stock;
import test.dsl.Trade;
import test.dsl.Trade.Type;

public class NestedOrderBuilder {

  public static Order order(String customer, Trade... trades) {
    Order order = new Order();
    order.setCustomer(customer);
    Stream.of(trades).forEach(order::addTrade);

    return order;
  }

  public static Trade buy(int quantity, Stock stock, double price) {
    return buildTrade(quantity, stock, price, Type.BUY);
  }

  public static Trade sell(int quantity, Stock stock, double price) {
    return buildTrade(quantity, stock, price, Type.SELL);
  }

  public static double at(double price) {
    return price;
  }

  public static Stock stock(String symbol, String market) {
    final Stock stock = new Stock();
    stock.setMarket(market);
    stock.setSymbol(symbol);

    return stock;
  }

  public static String on(String market) {
    return market;
  }

  private static Trade buildTrade(int quantity, Stock stock, double price, Trade.Type type) {
    Trade trade = new Trade();
    trade.setQuantity(quantity);
    trade.setType(type);
    trade.setStock(stock);
    trade.setPrice(price);

    return trade;
  }

}
