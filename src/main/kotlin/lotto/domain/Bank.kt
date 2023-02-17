package lotto.domain

import lotto.constant.BonusResult
import lotto.constant.Rank

object Bank {
    fun countMatchedMainLottoNumber(lotto: Lotto, winningLotto: WinningLotto): Int =
        lotto.lottoNumbers.count { lottoNumber -> lottoNumber.value in winningLotto.mainLottoNumbers.map { it.value } }

    fun checkMatchedBonusLottoNumber(lotto: Lotto, winningLotto: WinningLotto): BonusResult {
        if (winningLotto.bonusLottoNumber.value in lotto.lottoNumbers.map { lottoNumber -> lottoNumber.value }) {
            return BonusResult.BONUS_MATCH
        }
        return BonusResult.BONUS_MISMATCH
    }

    fun sumTotalPrizeMoney(lottoBunch: LottoBunch, winningLotto: WinningLotto): Int {
        var totalPrize = 0
        lottoBunch.value.forEach { lotto -> totalPrize += getPrizeMoney(lotto, winningLotto) }
        return totalPrize
    }

    private fun getPrizeMoney(lotto: Lotto, winningLotto: WinningLotto): Int =
        Rank.convertToPrizeMoney(getRank(lotto, winningLotto))

    fun getYieldRate(purchaseMoney: PurchaseMoney, totalPrizeMoney: Int): Double =
        totalPrizeMoney / purchaseMoney.value.toDouble()

    fun getRank(lotto: Lotto, winningLotto: WinningLotto): Rank =
        Rank.convertToGrade(
            countMatchedMainLottoNumber(lotto, winningLotto),
            checkMatchedBonusLottoNumber(lotto, winningLotto),
        )
}
