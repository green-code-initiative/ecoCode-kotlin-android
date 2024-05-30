package org.sonarsource.kotlin.checks.environment.idleness

import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
import org.sonar.check.Rule
import org.sonarsource.kotlin.api.checks.CallAbstractCheck
import org.sonarsource.kotlin.api.checks.FunMatcher
import org.sonarsource.kotlin.api.frontend.KotlinFileContext

private const val ERROR_MESSAGE =
    "You are keeping the screen turned on, this will drain the battery very fast. Only use this feature if strictly necessary"

@Rule(key = "EC505")
class KeepScreenOnCheck : CallAbstractCheck() {
    override val functionsToVisit = listOf(
        FunMatcher(qualifier = "android.view.Window") {
            withNames(
                "addFlags",
            )
        },
    )

    override fun visitFunctionCall(
        callExpression: KtCallExpression,
        resolvedCall: ResolvedCall<*>,
        kotlinFileContext: KotlinFileContext
    ) {
        kotlinFileContext.reportIssue(callExpression.calleeExpression!!, ERROR_MESSAGE)
    }
}

/*
TODO old test, will be removed

{
@Rule(key = "EC505")
class KeepScreenOnCheck : CallAbstractCheck()
   override val functionsToVisit = setOf(
       FunMatcher(name = "addFlags") { withArguments("kotlin.String") },
   )

   override fun visitFunctionCall(
       callExpression: KtCallExpression,
       resolvedCall: ResolvedCall<*>,
       kotlinFileContext: KotlinFileContext
   ) {
       val caller = getCaller(callExpression)
       *//*
       * kotlinFileContext.inputFileContext.reportIssue(ruleKey, kotlinFileContext.textRange(caller), Message( ERROR_MESSAGE), emptyList(), null)
      *//*
*//*
       callExpression.calleeExpression?.let { kotlinFileContext.reportIssue(it, ERROR_MESSAGE) }
*//*
       callExpression.let { kotlinFileContext.reportIssue(it, ERROR_MESSAGE) }
*//*        if (caller.text == "window") {

       }*//*
   }

   private fun getCaller(callExpression: KtCallExpression): PsiElement =
       callExpression.parent.firstChild

}
*/
