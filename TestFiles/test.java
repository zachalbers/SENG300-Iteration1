



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


class Person implements SomeAnnotation{


	public static void main(String[] args) {

		String string1 = "Hello";

		String string2 = "World";

        int x = 5;



		Person person1 = new Person(string1);

        Person person2 = new Person();





	}


// 	public Vehicle method1() {
// 		Person person3 = new Person();
// 		System.out.println("method1");
// 		return new Vehicle();
//
// 	}
//
//
// }
//
// class Vehicle {
//
//
// 	// public void method2() {
// 	// 	Person person2 = new Person();
// 	//}
//
// }








//
