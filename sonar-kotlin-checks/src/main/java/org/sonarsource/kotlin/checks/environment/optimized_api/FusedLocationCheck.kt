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
package org.sonarsource.kotlin.checks.environment.optimized_api

import org.jetbrains.kotlin.psi.KtImportList
import org.sonar.check.Rule
import org.sonarsource.kotlin.api.checks.AbstractCheck
import org.sonarsource.kotlin.api.frontend.KotlinFileContext

private const val WRONG_IMPORT = "android.location"
private const val GOOD_IMPORT = "com.google.android.gms.location"

private const val ERROR_MESSAGE = "Use com.google.android.gms.location instead of android.location to maximize battery life."

@Rule(key = "SEC517")
class FusedLocationCheck : AbstractCheck() {

    override fun visitImportList(importList: KtImportList, data: KotlinFileContext?) {
        val goodImports = importList.imports.filter { it.importPath.toString().startsWith(GOOD_IMPORT) }
        val wrongImports = importList.imports.filter { it.importPath.toString().startsWith(WRONG_IMPORT) }

        if (wrongImports.isNotEmpty()) {
            if (goodImports.isEmpty()) {
                wrongImports.forEach { import ->
                    import.importedReference?.let { data?.reportIssue(it, ERROR_MESSAGE) }
                }
            }
        }
    }
}