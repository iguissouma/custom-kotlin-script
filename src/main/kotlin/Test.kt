
fun main() {
 KotlinJsr223CustomKtsScriptEngineFactory().scriptEngine.eval(
     """
         @file:Import("common.custom.kts")
         println("hello" + greeting)
         
     """
 )
}
