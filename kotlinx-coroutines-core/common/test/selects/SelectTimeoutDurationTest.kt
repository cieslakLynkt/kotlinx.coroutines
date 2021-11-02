/*
 * Copyright 2016-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
 */

package kotlinx.coroutines.selects

import kotlinx.coroutines.*
import kotlin.test.*
import kotlin.time.*

@ExperimentalTime
class SelectTimeoutDurationTest : TestBase() {
    @Test
    fun testBasic() = runTest {
        expect(1)
        val result = select<String> {
            onTimeout(Duration.milliseconds(1000)) {
                expectUnreached()
                "FAIL"
            }
            onTimeout(Duration.milliseconds(100)) {
                expect(2)
                "OK"
            }
            onTimeout(Duration.milliseconds(500)) {
                expectUnreached()
                "FAIL"
            }
        }
        assertEquals("OK", result)
        finish(3)
    }

    @Test
    fun testZeroTimeout() = runTest {
        expect(1)
        val result = select<String> {
            onTimeout(Duration.seconds(1)) {
                expectUnreached()
                "FAIL"
            }
            onTimeout(Duration.ZERO) {
                expect(2)
                "OK"
            }
        }
        assertEquals("OK", result)
        finish(3)
    }

    @Test
    fun testNegativeTimeout() = runTest {
        expect(1)
        val result = select<String> {
            onTimeout(Duration.seconds(1)) {
                expectUnreached()
                "FAIL"
            }
            onTimeout(-Duration.milliseconds(10)) {
                expect(2)
                "OK"
            }
        }
        assertEquals("OK", result)
        finish(3)
    }
}
