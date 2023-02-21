package lotto.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class LottoNumberTest {
    @ValueSource(ints = [0, 46, -10000, 10000, -3, 48])
    @ParameterizedTest
    fun `로또 숫자는 1 이상 45 이하이다`(value: Int) {
        assertThrows<IllegalArgumentException> { LottoNumber.from(value) }
    }

    @ValueSource(ints = [1, 25, 45])
    @ParameterizedTest
    fun `로또 숫자의 동일성 비교`(value: Int) {
        assertThat(LottoNumber.from(value)).isSameAs(LottoNumber.from(value))
    }
}
