import org.scalatest.{FunSuite, Matchers}

/**
  * Created by inieto on 27/04/15.
  */
class _02_Classes extends FunSuite with Matchers {

   test("Classes in Scala are static templates that can be instantiated into many objects at runtime") {
     class Point(xc: Int, yc: Int) {
       var x: Int = xc
       var y: Int = yc
       def move(dx: Int, dy: Int) {
         x = x + dx
         y = y + dy
       }
       override def toString(): String = "(" + x + ", " + y + ")";
     }

     //Classes are instantiated with the new primitive, as the following example will show
     val pt = new Point(1, 2)
     pt.toString() should be ("(1, 2)")
     pt.move(10, 10)
     pt.toString() should be ("(11, 12)")
   }

   test("You can define class with var or val parameters. val parameters in class definition define getter") {
     class ClassWithValParameter(val name: String)
     val aClass = new ClassWithValParameter("Gandalf")
     aClass.name should be("Gandalf")
   }

   test("var parameters in class definition define getter and setter") {
     class ClassWithVarParameter(var description: String)

     val aClass = new ClassWithVarParameter("Flying character")
     aClass.description should be("Flying character")

     aClass.description = "Flying white character"
     aClass.description should be("Flying white character")
   }
 }
