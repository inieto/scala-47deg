import java.util.Date
import org.scalatest.{FunSuite, Matchers}

/**
  * Created by inieto on 27/04/15.
  */
class _05_Tuples extends FunSuite with Matchers {

   test("Tuples can be created easily") {
     val t = (1, "hello", Console)  //tuple holding an integer, a string, and the console
     val t3 = new Tuple3(1, "hello", Console)
     val tuple = ("apple", "dog")
     val fruit = tuple._1
     val animal = tuple._2

     fruit should be("apple")
     animal should be("dog")
   }

   test("Tuples may be of mixed type") {
     val tuple5 = ("a", 1, 2.2, new Date(), BigDecimal(5))

     tuple5._2 should be(1)
     tuple5._5 should be(BigDecimal(5))
   }

   test("You can assign multiple variables at once using tuples") {
     val student = ("Sean Rogers", 21, 3.5)
     val (name, age, gpa) = student

     name should be("Sean Rogers")
     age should be(21)
     gpa should be(3.5)
   }

   test("Tuples items can be swapped on a Tuple 2") {
     val tuple = ("apple", 3).swap
     tuple._1 should be(3)
     tuple._2 should be("apple")
   }
 }
