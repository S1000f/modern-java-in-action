package test.designpattern;

public class UpperProcessing extends Processor<String> {

  @Override
  protected String handleImplement(String input) {
    return input.toUpperCase();
  }
}
