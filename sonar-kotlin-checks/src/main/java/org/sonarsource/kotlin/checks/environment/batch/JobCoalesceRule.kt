package org.sonarsource.kotlin.checks.environment.batch

import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.resolve.calls.context.TemporaryTraceAndCache
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
import org.jetbrains.kotlin.resolve.calls.util.getResolvedCall
import org.sonar.check.Rule
import org.sonarsource.kotlin.api.checks.AbstractCheck
import org.sonarsource.kotlin.api.checks.FunMatcher
import org.sonarsource.kotlin.api.checks.matches
import org.sonarsource.kotlin.api.frontend.KotlinFileContext


private const val ERROR_MSG = "Avoid using AlarmManager or a SyncAdapter for an alarm. Instead use the JobScheduler because the alarm triggers are mutualized."

private val REPLACEMENT_FUNCTIONS = mapOf(
        FunMatcher(qualifier = "android.app.AlarmManager", name = "set") to ERROR_MSG,
        FunMatcher(qualifier = "android.app.AlarmManager", name = "setAlarmClock") to ERROR_MSG,
        FunMatcher(qualifier = "android.app.AlarmManager", name = "setAndAllowWhileIdle") to ERROR_MSG,
        FunMatcher(qualifier = "android.app.AlarmManager", name = "setExact") to ERROR_MSG,
        FunMatcher(qualifier = "android.app.AlarmManager", name = "setExactAndAllowWhileIdle") to ERROR_MSG,
        FunMatcher(qualifier = "android.app.AlarmManager", name = "setInexactRepeating") to ERROR_MSG,
        FunMatcher(qualifier = "android.app.AlarmManager", name = "setRepeating") to ERROR_MSG,
        FunMatcher(qualifier = "android.app.AlarmManager", name = "setWindow") to ERROR_MSG,
)

@Rule(key = "EC501")
class JobCoalesceRule : AbstractCheck() {
    val functionsToVisit = REPLACEMENT_FUNCTIONS.keys
    override fun visitCallExpression(callExpression: KtCallExpression, kotlinFileContext: KotlinFileContext) {
        val funMatcher = FunMatcher {
            qualifier = "android.app.AlarmManager"
            withNames("set", "setAlarmClock")
        }

        val resolvedCall = callExpression.getResolvedCall(kotlinFileContext.bindingContext) ?: return
            functionsToVisit.firstOrNull { resolvedCall matches it }
        //        ?.let { visitFunctionCall(callExpression, resolvedCall, it, kotlinFileContext) }
        println("QCA")
    }//goeffray.adde@sonarsource.com

    fun visitFunctionCall(callExpression: KtCallExpression, resolvedCall: ResolvedCall<*>, kotlinFileContext: KotlinFileContext) {
        println("test")

        val calleeExpression = callExpression.calleeExpression ?: return
        REPLACEMENT_FUNCTIONS
                .filter { it.key.matches(resolvedCall) }
                .forEach { kotlinFileContext.reportIssue(calleeExpression, it.value) };

    }

}