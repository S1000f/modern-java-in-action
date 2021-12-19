package test.designpattern;

public class ExamplesDesignPatterns {

  public static void examples() {
    final HeadProcessing headProcessing = new HeadProcessing();
    final UpperProcessing upperProcessing = new UpperProcessing();

    headProcessing.setNextProcessor(upperProcessing);
    final String handle1 = headProcessing.handle("input data");
    System.out.println(handle1);

    final ChainHead chainHead = new ChainHead();

    chainHead.setNextChain(ChainQuestionMark::new);
    final String handle2 = chainHead.handle("input");
    System.out.println(handle2);

  }

}
