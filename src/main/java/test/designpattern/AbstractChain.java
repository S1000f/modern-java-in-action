package test.designpattern;

import java.util.function.Supplier;

public abstract class AbstractChain<T> implements Chain<T> {

  private Chain<T> nextChain;

  public void setNextChain(Supplier<? extends Chain<T>> supplier) {
    this.nextChain = supplier.get();
  }

  @Override
  public Chain<T> getNextChain() {
    return nextChain;
  }
}
