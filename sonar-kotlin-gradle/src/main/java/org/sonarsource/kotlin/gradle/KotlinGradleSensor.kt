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
package org.sonarsource.kotlin.gradle

import org.gradle.tooling.GradleConnector
import org.gradle.tooling.model.kotlin.dsl.KotlinDslScriptsModel
import org.jetbrains.kotlin.resolve.BindingContext
import org.slf4j.LoggerFactory
import org.sonar.api.batch.fs.FileSystem
import org.sonar.api.batch.fs.InputFile
import org.sonar.api.batch.sensor.SensorContext
import org.sonar.api.batch.sensor.SensorDescriptor
import org.sonar.api.batch.rule.CheckFactory
import org.sonarsource.analyzer.commons.ProgressReport
import org.sonarsource.kotlin.api.sensors.AbstractKotlinSensor
import org.sonarsource.kotlin.api.sensors.AbstractKotlinSensorExecuteContext
import org.sonarsource.kotlin.visiting.KtChecksVisitor

private val LOG = LoggerFactory.getLogger(KotlinGradleSensor::class.java)

class KotlinGradleSensor(
    checkFactory: CheckFactory,
): AbstractKotlinSensor(
    checkFactory, KOTLIN_GRADLE_CHECKS
) {
    override fun describe(descriptor: SensorDescriptor) {
        descriptor
            .onlyOnLanguage("kotlin")
            .name("Gradle Sensor")
    }

    override fun getExecuteContext(
        sensorContext: SensorContext,
        filesToAnalyze: Iterable<InputFile>,
        progressReport: ProgressReport,
        filenames: List<String>
    ) = object: AbstractKotlinSensorExecuteContext(
        sensorContext, filesToAnalyze, progressReport, listOf(KtChecksVisitor(checks)), filenames, LOG
    ) {
        override val bindingContext: BindingContext = BindingContext.EMPTY
    }

    override fun getFilesToAnalyse(sensorContext: SensorContext): Iterable<InputFile> {
        val fileSystem: FileSystem = sensorContext.fileSystem()

        val projectConnection = GradleConnector.newConnector()
            .forProjectDirectory(fileSystem.baseDir())
            .connect()

        projectConnection.newBuild()
            .forTasks("prepareKotlinBuildScriptModel")
            .run()

        val models = projectConnection.getModel(KotlinDslScriptsModel::class.java).scriptModels
        return models.keys.mapNotNull { file ->
            val predicate = fileSystem.predicates().hasAbsolutePath(file.absolutePath)
            fileSystem.inputFile(predicate)
        }
    }
}
