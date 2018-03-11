public interface MyInterface{

}

public enum Day implements MyInterface{
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
    THURSDAY, FRIDAY, SATURDAY
}


@interface SomeAnnotation {
   String author();
   String date();

}

@interface SomeAnnotation2 {
    String author2();
    String date2();

}
