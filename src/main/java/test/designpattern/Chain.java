package test.designpattern;

public interface Chain<T> {

  Chain<T> getNextChain();

  T handleInternally(T input);

  default T handle(T input) {
    final T t = handleInternally(input);

    if (getNextChain() != null) {
      return getNextChain().handleInternally(t);
    }

    return t;
  }
}
