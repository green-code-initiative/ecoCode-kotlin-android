package org.sonarsource.kotlin.checks.environment.batch

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.sonarsource.kotlin.checks.CheckTest
import org.sonarsource.kotlin.testapi.KotlinVerifier

class JobCoalesceRuleTest: CheckTest(JobCoalesceRule()) {
}