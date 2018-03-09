
import java.util.Set;


public enum Day {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
    THURSDAY, FRIDAY, SATURDAY
}


@interface SomeAnnotation {
   String author();
   String date();

}


public class Person implements SomeAnnotation{


	public static void main(String[] args) {

		String string1 = "Hello";

		String string2 = "World";



		System.out.println(string1);

		Person person1 = new Person();

		//person1.method1();

		Vehicle x = person1.method1();



	}


	public Vehicle method1() {
		Person person3 = new Person();
		System.out.println("method1");
		return new Vehicle();

	}


}

public class Vehicle {


	public void method2() {
		Person person2 = new Person();
	}

}

public interface MyInterface{

}







//
