import java.util.Date

import org.scalatest.{FunSuite, Matchers}

/**
  * Created by inieto on 27/04/15.
  */
class _06_HighOrderFunctions extends FunSuite with Matchers {

  //Meet lambda
  // Scala provides a relatively lightweight syntax for defining anonymous functions. Anonymous functions in source
  // code are called function literals and at run time and are instantiated into objects called function values.

   test("Scala supports first-class functions, which means you can express functions in function literal syntax, " +
     "(x: Int) => x + 1, and that functions can be represented by objects, which are called function values. ") {
     def lambda = { x: Int => x + 1 }
     def lambda2 = (x:Int) => x + 1
     val lambda3 = (x:Int) => x + 1

     val lambda4 = new Function1[Int, Int] {
       def apply(v1: Int): Int = v1 + 1
     }

     def lambda5(x:Int) = x + 1

     val result = lambda(3)
     val `result1AndHalf` = lambda.apply(3)

     val result2 = lambda2(3)
     val result3 = lambda3(3)
     val result4 = lambda4(3)
     val result5 = lambda5(3)

     result should be(4)
     result1AndHalf should be(4)
     result2 should be(4)
     result3 should be(4)
     result4 should be(4)
     result5 should be(4)
   }

   test("An anonymous function can also take on a different look by taking out the brackets") {
     def lambda = (x: Int) => x + 1
     def result = lambda(5)
     result should be(6)
   }

  //Meet closure
  //A closure is a function, whose return value depends on the value of one or more variables declared outside this function

   test("Here the only variable used in the function body, i * 10, is i, which is defined as a parameter to the function") {
     val multiplier = (i:Int) => i * 10   //this is a lambda

     var incrementer = 1

     def closure = {
       x: Int => x + incrementer  //incrementer has to be present in the scope of the declaration
     }

     val result1 = closure(10)
     result1 should be(11)

     incrementer = 2

     val result2 = closure(10)
     result2 should be(12)
   }

   test("We can take that closure and throw into a method and it will still hold the environment") {
     def summation(x: Int, y: Int => Int) = y(x)

     var incrementer = 3
     def closure = (x: Int) => x + incrementer

     val result = summation(10, closure)
     result should be(13)

     incrementer = 4
     val result2 = summation(10, closure)
     result2 should be(14)
   }

   test("Function returning another function") {
     def addWithoutSyntaxSugar(x: Int) = {
       new Function1[Int, Int]() {
         def apply(y: Int): Int = x + y
       }
     }
     addWithoutSyntaxSugar(1).isInstanceOf[Function1[Int,Int]] should be(true)
     addWithoutSyntaxSugar(2)(3) should be(5)

     def fiveAdder = addWithoutSyntaxSugar(5)
     fiveAdder(5) should be(10)

     def addWithSyntaxSugar(x: Int) = (y:Int) => x + y

     addWithSyntaxSugar(1).isInstanceOf[Function1[Int, Int]] should be(true)
   }

   test("Function taking another function as parameter. Helps in composing functions." +
     " Hint: a map method applies the function to each element of a list") {
     def makeUpper(xs: List[String]) = xs map {_.toUpperCase}

     def makeWhatEverYouLike(xs: List[String], sideEffect: String => String) = {
       xs map sideEffect
     }

     makeUpper(List("abc", "xyz", "123")) should be(List("ABC", "XYZ", "123"))

     makeWhatEverYouLike(List("ABC", "XYZ", "123"), {
       x => x.toLowerCase
     }) should be(List("abc", "xyz", "123"))

     //using it inline
     List("Scala", "Erlang", "Clojure") map {_.length} should be(List(5, 6, 7))
   }

   test("Currying is a technique to transform function with multiple parameters to function with one parameter") {
     def multiply(x: Int, y: Int) = x * y

     (multiply _).isInstanceOf[Function2[_, _, _]] should be(true)

     val multiplyCurried = (multiply _).curried

     multiply(4, 5) should be(20)
     multiplyCurried(3)(2) should be(6)
   }

   test("Currying allows you to create specialized version of generalized function") {
     def customFilter(f: Int => Boolean)(xs: List[Int]) = {
       xs filter f
     }

     def onlyEven(x: Int) = x % 2 == 0
     val xs = List(12, 11, 5, 20, 3, 13, 2)

     customFilter(onlyEven)(xs) should be(List(12, 20, 2))

     val onlyEvenFilter = customFilter(onlyEven) _
     onlyEvenFilter(xs) should be(List(12, 20, 2))
   }
 }
