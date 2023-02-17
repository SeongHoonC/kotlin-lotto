package lotto.domain

import lotto.domain.factory.LottoFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BankTest {

    @Test
    fun `로또 번호와 당첨번호가 6개 같다(6을 반환)`() {
        assertThat(
            Bank.countMatchedMainLottoNumber(
                Lotto(
                    listOf(
                        LottoNumber(1),
                        LottoNumber(2),
                        LottoNumber(3),
                        LottoNumber(4),
                        LottoNumber(5),
                        LottoNumber(6),
                    ),
                ),
                WinningLotto(
                    listOf(
                        LottoNumber(1),
                        LottoNumber(2),
                        LottoNumber(3),
                        LottoNumber(4),
                        LottoNumber(5),
                        LottoNumber(6),
                    ),
                    LottoNumber(7),
                ),
            ),
        ).isEqualTo(6)
    }

    @Test
    fun `보너스 번호가 당첨되었다`() {
        assertThat(
            Bank.checkMatchedBonusLottoNumber(
                Lotto(
                    listOf(
                        LottoNumber(1),
                        LottoNumber(2),
                        LottoNumber(3),
                        LottoNumber(4),
                        LottoNumber(7),
                        LottoNumber(6),
                    ),
                ),
                WinningLotto(
                    listOf(
                        LottoNumber(1),
                        LottoNumber(2),
                        LottoNumber(3),
                        LottoNumber(4),
                        LottoNumber(5),
                        LottoNumber(6),
                    ),
                    LottoNumber(7),
                ),
            ),
        ).isTrue
    }

    @Test
    fun `로또 3개 중 2등과 3등이 하나씩 당첨되었다면 당첨 금액의 합계는 삼천백오십만원 이다`() {
        Bank.sumTotalPrizeMoney(
            LottoBunch(
                TestLottoFactory(
                    listOf(
                        Lotto(
                            listOf(
                                LottoNumber(1),
                                LottoNumber(2),
                                LottoNumber(3),
                                LottoNumber(4),
                                LottoNumber(5),
                                LottoNumber(7),
                            ),
                        ),
                        Lotto(
                            listOf(
                                LottoNumber(1),
                                LottoNumber(2),
                                LottoNumber(3),
                                LottoNumber(4),
                                LottoNumber(5),
                                LottoNumber(8),
                            ),
                        ),
                        Lotto(
                            listOf(
                                LottoNumber(11),
                                LottoNumber(12),
                                LottoNumber(13),
                                LottoNumber(14),
                                LottoNumber(17),
                                LottoNumber(16),
                            ),
                        ),
                    ),
                ),
                3
            ),
            WinningLotto(
                listOf(
                    LottoNumber(1),
                    LottoNumber(2),
                    LottoNumber(3),
                    LottoNumber(4),
                    LottoNumber(5),
                    LottoNumber(6),
                ),
                LottoNumber(7),
            ),
        )
    }

    @Test
    fun `14000원으로 5000원을 번다면 수익률은 35%이다`() {
        assertThat(Bank.getYieldRate(PurchaseMoney(14000), 5000)).isEqualTo(0.35714285714285715)
    }

    class TestLottoFactory(_lottes: List<Lotto>) : LottoFactory {
        private val lottoes = _lottes.toMutableList()
        override fun createLotto(): Lotto {
            return lottoes.removeFirst()
        }
    }
}
