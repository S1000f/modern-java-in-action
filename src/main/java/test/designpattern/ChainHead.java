package test.designpattern;

public class ChainHead extends AbstractChain<String> {

  @Override
  public String handleInternally(String input) {
    return "Head Chain Processing : " + input;
  }
}
