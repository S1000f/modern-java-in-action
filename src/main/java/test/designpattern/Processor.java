package test.designpattern;

public abstract class Processor<T> {

  protected Processor<T> nextProcessor;

  public void setNextProcessor(Processor<T> nextProcessor) {
    this.nextProcessor = nextProcessor;
  }

  public T handle(T input) {
    final T t = handleImplement(input);
    if (nextProcessor != null) {
      return nextProcessor.handle(t);
    }

    return t;
  }

  abstract protected T handleImplement(T input);

}
