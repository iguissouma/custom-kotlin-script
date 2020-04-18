import java.io.File
import kotlin.script.experimental.api.EvaluationResult
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate
import kotlin.script.experimental.jvmhost.createJvmEvaluationConfigurationFromTemplate

fun evalFile(scriptFile: File): ResultWithDiagnostics<EvaluationResult> {

    val compilationConfiguration = createJvmCompilationConfigurationFromTemplate<CustomKtsScript>()
    val evaluationConfiguration = createJvmEvaluationConfigurationFromTemplate<CustomKtsScript>()

    return BasicJvmScriptingHost().eval(scriptFile.toScriptSource(), compilationConfiguration, evaluationConfiguration)
}



fun main() {
 KotlinJsr223CustomKtsScriptEngineFactory().scriptEngine.eval(
     """
         @file:Import("common.kts")
         println("hello" + greeting)
         
     """
 )
}
