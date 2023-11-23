/*
 * SonarSource Kotlin
 * Copyright (C) 2018-2023 SonarSource SA
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
package org.sonarsource.kotlin.plugin

import org.sonar.api.Plugin
import org.sonarsource.kotlin.gradle.KotlinGradleSensor

class KotlinPlugin : Plugin {

    companion object {
        // Global constants
        const val REPOSITORY_NAME = "SonarAnalyzer"
        const val PROFILE_NAME = "ecoCode (Android)"
        const val SKIP_UNCHANGED_FILES_OVERRIDE = "sonar.kotlin.skipUnchanged"
        const val GRADLE_PROJECT_ROOT_PROPERTY = "sonar.kotlin.gradleProjectRoot"
    }

    private fun doScanGradleFiles(context: Plugin.Context): Boolean = context.bootConfiguration[GRADLE_PROJECT_ROOT_PROPERTY].isPresent

    override fun define(context: Plugin.Context) {

        context.addExtensions(
            KotlinSensor::class.java,
            KotlinRulesDefinition::class.java,
            KotlinProfileDefinition::class.java,
        )

        if (doScanGradleFiles(context)) {
            context.addExtension(KotlinGradleSensor::class.java)
        }
    }
}
