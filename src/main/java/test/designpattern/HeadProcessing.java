package test.designpattern;

public class HeadProcessing extends Processor<String> {

  @Override
  protected String handleImplement(String input) {
    return "head processing : " + input;
  }
}
