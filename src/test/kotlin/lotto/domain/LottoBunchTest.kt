package lotto.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class LottoBunchTest {
    @Test
    fun `로또 다발은 101개의 로또를 가질 수 없다`() {
        assertThrows<IllegalArgumentException> {
            LottoBunch(
                List(101) {
                    Lotto(listOf(1, 2, 3, 4, 5, 6))
                },
            )
        }
    }
}
