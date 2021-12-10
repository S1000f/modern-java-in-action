package test.async.reactive;

import java.util.concurrent.Flow.Publisher;

public class ExampleReactive {

  public static void example() {
    getTemperatures("Daegu").subscribe(new TempSubscriber());
  }

  public static Publisher<TempInfo> getTemperatures(String town) {
    return subscriber -> subscriber.onSubscribe(new TempSubscription(subscriber, town));
  }

  public static Publisher<TempInfo> getCelsiusTemperatures(String town) {
    return subscriber -> {
      TempProcessor processor = new TempProcessor();
      processor.subscribe(subscriber);
      processor.onSubscribe(new TempSubscription(processor, town));
    };
  }

}
