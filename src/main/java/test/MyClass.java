package test;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public final class MyClass {

  private final String name;
  private final int age;
  @Custom
  private final String custom;

  private MyClass(String name, int age, String custom) {
    this.name = name;
    this.age = age;
    this.custom = custom;
  }

  public static MyClass of(String name, int age, String custom) {
    return new MyClass(name, age, custom);
  }

}
