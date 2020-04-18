import org.jetbrains.kotlin.mainKts.MainKtsScript
import java.io.File
import kotlin.script.experimental.api.*
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate
import kotlin.script.experimental.jvmhost.createJvmEvaluationConfigurationFromTemplate

private fun evalFileTest(scriptFile: File): ResultWithDiagnostics<EvaluationResult> {
    val compilationConfiguration = createJvmCompilationConfigurationFromTemplate<MainKtsScript> {
        jvm {
            dependenciesFromCurrentContext(wholeClasspath = true)
        }
    }
    //work around for wrong number arguments
    //add empty array for constructor args
    val evaluationConfiguration = createJvmEvaluationConfigurationFromTemplate<MainKtsScript>() {
        refineConfigurationBeforeEvaluate {
            val res =
                it.evaluationConfiguration.with {
                    constructorArgs(emptyArray<String>())
                }
            res.asSuccess()
        }
    }
    return BasicJvmScriptingHost().eval(
        scriptFile.toScriptSource(),
        compilationConfiguration,
        evaluationConfiguration
    )
}


fun main() {
    val evalFileTest = evalFileTest(File("src/main/kotlin/test.kts"))
    println(evalFileTest is ResultWithDiagnostics.Success)
}
