package test.dsl;

import java.util.function.DoubleUnaryOperator;

public class TaxCalculator {

  private DoubleUnaryOperator taxFunction = d -> d;

  public static TaxCalculator of(DoubleUnaryOperator f) {
    return new TaxCalculator().with(f);
  }

  public TaxCalculator with(DoubleUnaryOperator f) {
    taxFunction = taxFunction.andThen(f);
    return this;
  }

  public double calculate(Order order) {
    return taxFunction.applyAsDouble(order.getValue());
  }

  public static double regional(double value) {
    return value * 1.1D;
  }

  public static double general(double value) {
    return value * 1.3D;
  }

  public static double surcharge(double value) {
    return value * 1.05D;
  }

}
