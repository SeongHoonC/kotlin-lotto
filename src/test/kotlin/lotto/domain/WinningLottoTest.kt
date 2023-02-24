package lotto.domain

import lotto.constant.Rank
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class WinningLottoTest {

    fun WinningLotto(mainLottoNumbers: List<Int>, bonusLottoNumber: Int): WinningLotto {
        return WinningLotto(mainLottoNumbers.map { LottoNumber.from(it) }.toSet(), LottoNumber.from(bonusLottoNumber))
    }

    @Test
    fun `메인 번호는 6개이다`() {
        assertThrows<IllegalArgumentException> {
            WinningLotto(
                listOf(3, 45, 34),
                36,
            )
        }
    }

    @Test
    fun `메인 번호는 서로 중복되지 않는다`() {
        assertThrows<IllegalArgumentException> {
            WinningLotto(
                listOf(1, 2, 3, 4, 5, 5),
                6,
            )
        }
    }

    @Test
    fun `메인과 보너스 번호는 서로 중복되지 않는다`() {
        assertThrows<IllegalArgumentException> {
            WinningLotto(
                listOf(1, 2, 3, 4, 5, 6),
                6,
            )
        }
    }

    @Test
    fun `메인 번호가 5개 맞고 보너스 번호가 맞으면 2등이다`() {
        val winningLotto = WinningLotto(
            listOf(1, 2, 3, 4, 5, 6),
            7,
        )

        val lotto = Lotto(
            listOf(1, 2, 3, 4, 5, 7),
        )

        assertThat(winningLotto.getRank(lotto)).isEqualTo(Rank.SECOND)
    }
}
