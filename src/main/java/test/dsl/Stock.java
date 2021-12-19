package test.dsl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

  private String symbol;
  private String market;

}
