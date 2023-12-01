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

import org.junit.jupiter.api.Test
import org.sonarsource.kotlin.checks.CheckTest
import org.sonarsource.kotlin.testapi.KotlinVerifier

class BleCheckTest : CheckTest(BluetoothBleCheck()) {
    @Test
    fun `with both Bc and Ble`() {
        KotlinVerifier(check) {
            this.fileName = "BothBcBleCheck.kt"
            this.classpath = emptyList()
            this.deps = emptyList()
        }.verify()
    }

    @Test
    fun `with only Bc`() {
        KotlinVerifier(check) {
            this.fileName = "OnlyBcCheck.kt"
            this.classpath = emptyList()
            this.deps = emptyList()
        }.verify()
    }

    @Test
    fun `with only Ble`() {
        KotlinVerifier(check) {
            this.fileName = "OnlyBleCheck.kt"
            this.classpath = emptyList()
            this.deps = emptyList()
        }.verify()
    }

}

