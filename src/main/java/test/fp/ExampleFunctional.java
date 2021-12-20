package test.fp;

public class ExampleFunctional {

  public static void examples() {
    final LazyList<Integer> numbers = from(2);

    final Integer head = numbers.head();
    final Integer head1 = numbers.tail().head();
    final Integer head2 = numbers.tail().tail().head();

    System.out.println(head);
    System.out.println(head1);
    System.out.println(head2);
  }

  public static LazyList<Integer> from(int n) {
    return new LazyList<>(n, () -> from(n + 1));
  }

}
