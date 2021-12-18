package test;

import java.util.function.Supplier;
import test.stream.ExampleStream;

public class Main {
  public static void main(String[] args) {

    ExampleStream.exampleCollectionMethods();

    final HeadProcessing headProcessing = new HeadProcessing();
    final UppercaseProcessing uppercaseProcessing = new UppercaseProcessing();
    headProcessing.setNextProcessor(uppercaseProcessing);
    final String handle = headProcessing.handle("Hi there!");
    System.out.println(handle);

    final HeadChain headChain = new HeadChain();
    final QuestionMarkChain questionMarkChain = new QuestionMarkChain();
    headChain.setNextChain(questionMarkChain);
    final String result = headChain.handle("is this working");
    System.out.println(result);

    final HeadChain2 headChain2 = new HeadChain2();
    headChain2.setNextChain(QuestionMarkChain2::new);
    final String result2 = headChain2.handle("delegate pattern");
    System.out.println(result2);

  }

  public static class HeadChain implements Chain<String> {

    private Chain<String> nextChain;

    public void setNextChain(Chain<String> nextChain) {
      this.nextChain = nextChain;
    }

    @Override
    public Chain<String> getNextChain() {
      return nextChain;
    }

    @Override
    public String handleInternally(String input) {
      return "this is head chain work : " + input;
    }
  }

  public static class QuestionMarkChain implements Chain<String> {

    private Chain<String> nextChain;

    public void setNextChain(Chain<String> nextChain) {
      this.nextChain = nextChain;
    }

    @Override
    public Chain<String> getNextChain() {
      return nextChain;
    }

    @Override
    public String handleInternally(String input) {
      return input + "???";
    }
  }

  public static class HeadChain2 extends AbstractChain<String> {

    @Override
    public String handleInternally(String input) {
      return "this is head processing : " + input;
    }
  }

  public static class QuestionMarkChain2 extends AbstractChain<String> {

    @Override
    public String handleInternally(String input) {
      return input + "??";
    }
  }

  public abstract static class AbstractChain<T> implements Chain<T> {

    protected Chain<T> nextChain;

    public void setNextChain(Supplier<? extends Chain<T>> supplier) {
      this.nextChain = supplier.get();
    }

    @Override
    public Chain<T> getNextChain() {
      return nextChain;
    }
  }

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

  public static class HeadProcessing extends Processor<String> {

    @Override
    protected String handleImplement(String input) {
      return "this is head processing : " + input;
    }
  }

  public static class UppercaseProcessing extends Processor<String> {

    @Override
    protected String handleImplement(String input) {
      return input.toUpperCase();
    }
  }

  public abstract static class Processor<T> {

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
}

