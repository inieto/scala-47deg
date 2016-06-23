import org.scalatest.{FunSuite, Matchers}

/**
  * Created by inieto on 27/04/15.
  */
class _04_Objects extends FunSuite with Matchers {

  object Greeting {
    def english = "Hi"
    def espanol = "Hola"
    def deutsch = "Hallo"
    def magyar = "Szia"
  }

  test("Here is proof an object is a singleton, and not a static method in a class") {
    Greeting.english should be("Hi")
    Greeting.espanol should be("Hola")
    Greeting.deutsch should be("Hallo")
    Greeting.magyar should be("Szia")
  }

  test("Having 'maybeItWillReturnSomething' represent null with None because null is a bad idea") {
    val x = Greeting
    val y = x
    x eq y should be(true) //Reminder, eq checks for reference

    val z = Greeting
    x eq z should be(true)
  }

  test("An object that has the same name as class is called a companion object, it is used to contain factories for the class that it complements") {
    class Movie(val name: String, val year: Short)

    object Movie {
      def academyAwardBestMoviesForYear(x: Short) = {
        //These are match statement, more powerful than Java switch statements!
        x match {
          case 1930 => Some(new Movie("All Quiet On the Western Front", 1930))
          case 1931 => Some(new Movie("Cimarron", 1931))
          case 1932 => Some(new Movie("Grand Hotel", 1932))
          case _ => None
        }
      }
    }

    Movie.academyAwardBestMoviesForYear(1932).get.name should be("Grand Hotel")
  }

  test("A companion object stores shared variables and values for every instantiated class to share") {
    class SecretAgent(val name: String) {
      def shoot(n: Int) {
        SecretAgent.decrementBullets(n) //Idea: "Wrong forward reference" - compiles anyway
      }
    }
    object SecretAgent {
      //This is encapsulated!
      var bullets: Int = 3000

      private def decrementBullets(count: Int) {
        if (bullets - count <= 0) bullets = 0
        else bullets = bullets - count
      }
    }
    val bond = new SecretAgent("James Bond")
    val felix = new SecretAgent("Felix Leitner")
    val jason = new SecretAgent("Jason Bourne")
    val _99 = new SecretAgent("99")
    val max = new SecretAgent("Max Smart")

    bond.shoot(800)
    felix.shoot(200)
    jason.shoot(150)
    _99.shoot(150)
    max.shoot(200)

    SecretAgent.bullets should be(1500)
  }
   test("A companion object can also see private values and variables of the instantiated objects") {
     class Person (val name:String,  private val superheroName:String)  //The superhero name is private!

     object Person {
       def showMeInnerSecret(x:Person) = x.superheroName
     }

     val clark = new Person("Clark Kent", "Superman")
     val peter = new Person("Peter Parker", "Spiderman")
     val bruce = new Person("Bruce Wayne", "Batman")
     val diana = new Person("Diana Prince", "Wonder Woman")

     Person.showMeInnerSecret(clark) should be ("Superman")
     Person.showMeInnerSecret(peter) should be ("Spiderman")
     Person.showMeInnerSecret(bruce) should be ("Batman")
     Person.showMeInnerSecret(diana) should be ("Wonder Woman")
   }

   test("~Mine~ Initialization order") {
     class SomeClass (message: String) {
       println("Class initialization")

       def this(number: Int) {
         this(number.toString)
         println("instance initialization")
       }

       def sayHi() {
         println(s"Hi $message")
       }
     }
     object SomeObject {
       println("Object initialization")
       val bar = "Bar"
       def sayHo() {
         println(s"Ho $bar")
       }
     }
     val a = new SomeClass("Foo")
     a.sayHi()
     SomeObject.sayHo()
     val b = new SomeClass(12)
     /* prints:
      Class initialization
      Hi Foo
      Object initialization
      Ho Bar
      Class initialization
      instance initialization
     */
   }
 }
