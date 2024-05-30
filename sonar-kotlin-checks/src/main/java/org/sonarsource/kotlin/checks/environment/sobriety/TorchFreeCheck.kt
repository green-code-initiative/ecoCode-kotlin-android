/*
 * SonarSource Kotlin
 * Copyright (C) 2018-2024 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonarsource.kotlin.checks.environment.sobriety

import org.jetbrains.kotlin.psi.KtParameter
import org.sonar.check.Rule
import org.sonarsource.kotlin.api.checks.CallAbstractCheck
import org.sonarsource.kotlin.api.checks.FunMatcher
import org.sonarsource.kotlin.api.frontend.KotlinFileContext

private const val ERROR_MESSAGE =
    "Flashlight is one of the most energy-intensive component. Don't programmatically turn it on."

@Rule(key = "EC530")
class TorchFreeCheck : CallAbstractCheck() {
    private val REPL = FunMatcher(qualifier = "android.hardware.camera2.CameraManager", name = "setTorchMode")

    override val functionsToVisit = setOf(REPL)

    override fun visitParameter(parameter: KtParameter, ctx: KotlinFileContext) {
        if (parameter.name == "enabled") {
            //TODO: Check parameter is true
            parameter.let { ctx.reportIssue(it, ERROR_MESSAGE) }
        }
    }
}