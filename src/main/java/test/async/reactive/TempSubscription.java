package test.async.reactive;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
@Builder
@AllArgsConstructor
public class TempSubscription implements Subscription {

  private final Subscriber<? super TempInfo> subscriber;
  private final String town;

  @Override
  public void request(long n) {
    for (long i = 0L; i < n; i++) {
      try {
        subscriber.onNext(TempInfo.fetch(town));
      } catch (Exception e) {
        subscriber.onError(e);
        break;
      }
    }
  }

  @Override
  public void cancel() {
    subscriber.onComplete();
  }
}
