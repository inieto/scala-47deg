import org.scalatest.{FunSuite, Matchers}

/**
  * Created by inieto on 27/04/15.
  */
class _17_Traits extends FunSuite with Matchers {

  /* Similar to interfaces in Java, traits are used to define object types by specifying the signature of the supported methods.
     Unlike Java, Scala allows traits to be partially implemented; i.e. it is possible to define default implementations for some methods.
     In contrast to classes, traits may not have constructor parameters. Here is an example:

          trait Similarity {
            def isSimilar(x: Any): Boolean
            def isNotSimilar(x: Any): Boolean = !isSimilar(x)
          }

     This trait consists of two methods `isSimilar` and `isNotSimilar`. While 'isSimilar' does not provide a concrete method implementation
     (it is abstract in the terminology of Java), method 'isNotSimilar' defines a concrete implementation.
     Consequently, classes that integrate this trait only have to provide a concrete implementation for 'isSimilar'.
     The behavior for 'isNotSimilar' gets inherited directly from the trait. */

   test("Traits are typically integrated into a class (or other traits) with a mixin class composition") {
     trait Similarity {
       def isSimilar(x: Any): Boolean
       def isNotSimilar(x: Any): Boolean = !isSimilar(x)
     }
     class Point(xc: Int, yc: Int) extends Similarity {
       var x: Int = xc
       var y: Int = yc
       def isSimilar(obj: Any) =
         obj.isInstanceOf[Point] &&
           obj.asInstanceOf[Point].x == x
     }
       val p1 = new Point(2, 3)
       val p2 = new Point(2, 4)
       val p3 = new Point(3, 3)
       assert(!p1.isNotSimilar(p2))
       assert(p1.isNotSimilar(p3))
       assert(p1.isNotSimilar(2))
   }


   test("A class uses the extends keyword to mixin a trait if it is the only relationship the class inherits") {
     case class Event(name: String)

     trait EventListener {
       def listen(event: Event): String
     }
     class MyListener extends EventListener {
       def listen(event: Event): String = {
         event match {
           case Event("Moose Stampede") => "An unfortunate moose stampede occurred"
           case _ => "Nothing of importance occurred"
         }
       }
     }
     val evt = Event("Moose Stampede")
     val myListener = new MyListener
     myListener.listen(evt) should be ("An unfortunate moose stampede occurred")
   }


   test("A class can only extend from one class or trait, any subsequent extension should use the keyword with") {
     case class Event(name: String)

     trait EventListener {
       def listen(event: Event): String
     }
     class OurListener

     class MyListener extends OurListener with EventListener {
       def listen(event: Event) : String = {
         event match {
           case Event("Woodchuck Stampede") => "An unfortunate woodchuck stampede occurred"
           case _ => "Nothing of importance occurred"
         }
       }
     }
     val evt = Event("Woodchuck Stampede")
     val myListener = new MyListener
     myListener.listen(evt) should be ("An unfortunate woodchuck stampede occurred")
   }


   test("Traits are polymorphic. Any type can be referred to by another type if related by extension") {
     case class Event(name: String)

     trait EventListener {
       def listen(event: Event): String
     }
     class MyListener extends EventListener {
       def listen(event: Event) : String = {
         event match {
           case Event("Moose Stampede") => "An unfortunate moose stampede occurred"
           case _ => "Nothing of importance occurred"
         }
       }
     }
     val myListener = new MyListener

     myListener.isInstanceOf[MyListener] should be(true)
     myListener.isInstanceOf[EventListener] should be(true)
     myListener.isInstanceOf[Any] should be(true)
     myListener.isInstanceOf[AnyRef] should be(true)
   }


   test("Traits can have concrete implementations that can be mixed into concrete classes with it's own state") {
     trait Logging {
       var logCache = List[String]()

       def log(value: String) = {
         logCache = logCache :+ value
         println(value)
       }
     }
     class Welder extends Logging {
       def weld() {
         log("welding pipe")
       }
     }
     class Baker extends Logging {
       def bake() {
         log("baking cake")
       }
     }
     val welder = new Welder
     welder.weld()

     val baker = new Baker
     baker.bake()

     welder.logCache.size should be(1)
     baker.logCache.size should be(1)
   }


   test("Traits are instantiated before a classes instantiation") {
     var sb = List[String]()

     trait T1 {
       sb = sb :+ "In T1: x=%s".format(x)
       val x = 1
       sb = sb :+ "In T1: x=%s".format(x)
     }
     class C1 extends T1 {
       sb = sb :+ "In C1: y=%s".format(y)
       val y = 2
       sb = sb :+ "In C1: y=%s".format(y)
     }
     sb = sb :+ "Creating C1"
     new C1
     sb = sb :+ "Created C1"

     sb.mkString(";") should be("Creating C1;In T1: x=0;In T1: x=1;In C1: y=0;In C1: y=2;Created C1")
   }


   test("Traits are instantiated before a classes instantiation from left to right") {
     var sb = List[String]()

     trait T1 {
       sb = sb :+ "In T1: x=%s".format(x)
       val x = 1
       sb = sb :+ "In T1: x=%s".format(x)
     }
     trait T2 {
       sb = sb :+ "In T2: z=%s".format(z)
       val z = 1
       sb = sb :+ "In T2: z=%s".format(z)
     }
     class C1 extends T1 with T2 {
       sb = sb :+ "In C1: y=%s".format(y)
       val y = 2
       sb = sb :+ "In C1: y=%s".format(y)
     }
     sb = sb :+ "Creating C1"
     new C1
     sb = sb :+ "Created C1"

     sb.mkString(";") should be("Creating C1;In T1: x=0;In T1: x=1;In T2: z=0;In T2: z=1;In C1: y=0;In C1: y=2;Created C1")
   }


   test("Instantiations are tracked and will not allow a duplicate instantiation. \" + Note T1 extends T2, and C1 also extends T2, but T2 is only instantiated once") {
     var sb = List[String]()

     trait T1 extends T2 {
       sb = sb :+ "In T1: x=%s".format(x)
       val x = 1
       sb = sb :+ "In T1: x=%s".format(x)
     }
     trait T2 {
       sb = sb :+ "In T2: z=%s".format(z)
       val z = 1
       sb = sb :+ "In T2: z=%s".format(z)
     }
     class C1 extends T1 with T2 {
       sb = sb :+ "In C1: y=%s".format(y)
       val y = 2
       sb = sb :+ "In C1: y=%s".format(y)
     }
     sb = sb :+ "Creating C1"
     new C1
     sb = sb :+ "Created C1"

     sb.mkString(";") should be("Creating C1;In T2: z=0;In T2: z=1;In T1: x=0;In T1: x=1;In C1: y=0;In C1: y=2;Created C1")
   }


   test("The diamond of death is avoided since instantiations are tracked and will not allow multiple instantiations") {
     var sb = List[String]()

     trait T1 {
       sb = sb :+ "In T1: x=%s".format(x)
       val x = 1
       sb = sb :+ "In T1: x=%s".format(x)
     }
     trait T2 extends T1 {
       sb = sb :+ "In T2: z=%s".format(z)
       val z = 2
       sb = sb :+ "In T2: z=%s".format(z)
     }
     trait T3 extends T1 {
       sb = sb :+ "In T3: w=%s".format(w)
       val w = 3
       sb = sb :+ "In T3: w=%s".format(w)
     }
     class C1 extends T2 with T3 {
       sb = sb :+ "In C1: y=%s".format(y)
       val y = 4
       sb = sb :+ "In C1: y=%s".format(y)
     }
     sb = sb :+ "Creating C1"
     new C1
     sb = sb :+ "Created C1"

     sb.mkString(";") should be("Creating C1;In T1: x=0;In T1: x=1;In T2: z=0;In T2: z=2;In T3: w=0;In T3: w=3;In C1: y=0;In C1: y=4;Created C1")
   }

 }
