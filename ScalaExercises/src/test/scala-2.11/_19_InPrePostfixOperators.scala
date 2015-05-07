import org.scalatest.{FunSuite, Matchers}

/**
  * Created by inieto on 27/04/15.
  */
class _19_InPrePostfixOperators extends FunSuite with Matchers {

   test("Any method which takes a <single parameter> can be used as an infix operator: 'a.m(b)' can be written 'a m b'") {
     val g: Int = 3
     (g + 4) should be(7) // + is an infix operator
     g.+(4) should be(7) // same result but not using the infix operator
   }


   test("Infix Operators do NOT work if an object has a method that takes two parameters") {
     val g: String = "Check out the big brains on Brad!"

     g indexOf 'o' should be(6) //indexOf(Char) can be used as an infix operator
     // g indexOf 'o', 4 should be (6) //indexOf(Char, Int) cannot be used an infix operator
     g.indexOf('o', 7) should be(25) //indexOf(Char, Int) must use standard java/scala calls
   }

  /* Any method which does not require a parameter can be used as a postfix operator: 'a.m' can be written 'a m'.
    For instance "a.##(b)" can be written "a ## b" and "a.!" can be written "a!"

    Postfix operators have lower precedence than infix operators, so:
      "foo bar baz" means "foo.bar(baz)"
      "foo bar baz bam" means "(foo.bar(baz)).bam"
      "foo bar baz bam bim" means "(foo.bar(baz)).bam(bim)"
  */
   test("Postfix operator takes no params") {
     val g: Int = 31
     (g toHexString) should be("1f") //toHexString takes no params therefore can be called as a postfix operator.
   }

   test("Prefix operators work if an object has a method name that starts with 'unary_'") {
     val g: Int = 31
     (-g) should be(-31)
   }

   test("Here we create our own prefix operator for our own class." +
        "The only identifiers that can be used as prefix operators are '+ - ! ~'") {
     class Stereo {
       def unary_+ = "on"
       def unary_- = "off"
     }
     val stereo = new Stereo
     (+stereo) should be("on")
     (-stereo) should be("off")
   }

}
