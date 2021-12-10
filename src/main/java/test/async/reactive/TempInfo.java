package test.async.reactive;

import java.security.SecureRandom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
@AllArgsConstructor
public class TempInfo {

  public static final SecureRandom random = new SecureRandom();

  private final String town;
  private final int temp;

  public static TempInfo fetch(String town) {
    // 10% 의 확률로 온도 가져오기 작업이 실패함을 시뮬레이션
    if (random.nextInt(10) == 0) {
      throw new RuntimeException("error");
    }

    return new TempInfo(town, random.nextInt(100));
  }

}
