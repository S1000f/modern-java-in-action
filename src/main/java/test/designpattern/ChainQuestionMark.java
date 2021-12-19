package test.designpattern;

public class ChainQuestionMark extends AbstractChain<String> {

  @Override
  public String handleInternally(String input) {
    return input + "???";
  }
}
