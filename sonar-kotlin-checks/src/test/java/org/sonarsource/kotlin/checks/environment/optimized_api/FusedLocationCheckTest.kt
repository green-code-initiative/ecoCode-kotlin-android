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
package org.sonarsource.kotlin.checks.environment.optimized_api

import org.junit.jupiter.api.Test
import org.sonarsource.kotlin.checks.CheckTest
import org.sonarsource.kotlin.testapi.KotlinVerifier

class FusedLocationCheckTest : CheckTest(FusedLocationCheck()) {

    @Test
    fun `with import android_location`() {
        KotlinVerifier(check) {
            this.fileName = "OnlyAndroidLocationFusedLocationCheck.kt"
            this.classpath = emptyList()
            this.deps = emptyList()
        }.verify()
    }

    @Test
    fun `with import android_location and com_google_android_gms_location`() {
        KotlinVerifier(check) {
            this.fileName = "BothAndroidAndGoogleLocationFusedLocationCheck.kt"
            this.classpath = emptyList()
            this.deps = emptyList()
        }.verifyNoIssue()
    }

}

