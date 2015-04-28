import org.scalatest.{FunSuite, Matchers}

/**
  * Created by inieto on 27/04/15.
  */
class _03_Options extends FunSuite with Matchers {

   test("Option[A] is a container for an optional value of type A." +
     " If the value of type A is present, the Option[A] is an instance of Some[A], containing the present value of type A." +
     " If the value is absent, the Option[A] is the object None.") {

     val someValue: Option[String] = Some("I am wrapped in something")
     someValue.get should be("I am wrapped in something")

     val nullValue: Option[String] = None
     nullValue should be(None)
   }

   def maybeItWillReturnSomething(flag: Boolean): Option[String] = {
     if (flag) Some("Found value") else None
   }

   test("Having 'maybeItWillReturnSomething' represent null with None because null is a bad idea") {
     val value1 = maybeItWillReturnSomething(true)
     val value2 = maybeItWillReturnSomething(false)

     value1.get should be("Found value")
     intercept[java.util.NoSuchElementException] {
       value2.get
     }
   }

   test("Provide a default value for None") {
     val value1 = maybeItWillReturnSomething(true)
     val value2 = maybeItWillReturnSomething(false)

     value1 getOrElse "No value" should be("Found value")
     value2 getOrElse "No value" should be("No value")
     value2 getOrElse {
       "default function"
     } should be("default function")
   }

   test("Checking whether option has value") {
     val value1 = maybeItWillReturnSomething(true)
     val value2 = maybeItWillReturnSomething(false)

     value1.isEmpty should be(false)
     value2.isEmpty should be(true)
   }

   test("Option can also be used with pattern matching") {
     val someValue: Option[Double] = Some(20.0)
     val value = someValue match {
       case Some(v) => v
       case None => 0.0
     }
     value should be(20.0)
     val noValue: Option[Double] = None
     val value1 = noValue match {
       case Some(v) => v
       case None => 0.0
     }
     value1 should be(0.0)
   }
 }
