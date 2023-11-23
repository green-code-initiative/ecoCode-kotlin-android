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
package org.sonarsource.kotlin.checks

import org.jetbrains.kotlin.psi.KtImportDirective
import org.jetbrains.kotlin.psi.KtImportList
import org.sonar.check.Rule
import org.sonarsource.kotlin.api.checks.AbstractCheck
import org.sonarsource.kotlin.api.frontend.KotlinFileContext

private const val IMPORT_STR_BC = "android.bluetooth"
private const val IMPORT_STR_BLE = "android.bluetooth.le"

private const val ERROR_MESSAGE = "You are using Bluetooth. Did you take a look at the Bluetooth Low Energy API?"
private const val GOOD_PRACTICE_MESSAGE = "Using android.bluetooth.le.* is a good practice."

@Rule(key = "S1981908")
class BluetoothBleCheck : AbstractCheck() {

    override fun visitImportList(importList: KtImportList, data: KotlinFileContext?) {
        val bleImports: ArrayList<KtImportDirective> = ArrayList()
        val bcImports: ArrayList<KtImportDirective> = ArrayList()
        var hasBluetoothImports = false

        importList.imports.forEach {
            if(it.importPath.toString().startsWith(IMPORT_STR_BC)) {
                hasBluetoothImports = true
                bcImports.add(it)
            }
            if(it.importPath.toString().startsWith(IMPORT_STR_BLE)) {
                hasBluetoothImports = true
                bleImports.add(it)
            }
        }

        if(hasBluetoothImports){
            if(bleImports.isNotEmpty()){
                bleImports.forEach {
                    it.importedReference?.let { data?.reportIssue(it, GOOD_PRACTICE_MESSAGE) }
                }
            }else{
                bcImports.forEach{
                    it.importedReference?.let { data?.reportIssue(it, ERROR_MESSAGE)}
                }
            }
        }
    }
}