package test;

import java.lang.reflect.Field;

public class AnnotationProcessing {

  public static void process(MyClass myClass) throws IllegalAccessException, NoSuchFieldException {
    Class<? extends MyClass> clazz = myClass.getClass();

    Field[] declaredFields = clazz.getDeclaredFields();

    for (Field field : declaredFields) {
      if (field.isAnnotationPresent(Custom.class)) {
        System.out.println(field.getName());
        System.out.println(field.getType());

        field.setAccessible(true);

        Object obj = field.get(myClass);
        takeString(obj);
      }
    }
  }

  public static void takeString(Object string) {
    System.out.println(string);
  }

}
