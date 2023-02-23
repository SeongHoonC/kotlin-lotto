package lotto.controller

import lotto.domain.LottoBunch
import lotto.domain.LottoFactory
import lotto.domain.LottoNumber
import lotto.domain.LottoStore
import lotto.domain.PurchaseMoney
import lotto.domain.WinningLotto
import lotto.domain.WinningResult
import lotto.utils.illegalArgumentExceptionHandler
import lotto.view.InputView
import lotto.view.OutputView

class LottoController {
    fun runLotto() {
        val purchaseMoney = getPurchaseMoney()
        val lottoBunch = getLottoBunch(purchaseMoney)
        val winningLotto = getWinningLotto()
        checkWinningResult(lottoBunch, winningLotto, purchaseMoney)
    }

    private fun getLottoBunch(purchaseMoney: PurchaseMoney): LottoBunch {
        val lottoBunch = LottoStore(LottoFactory()).buyLottoes(purchaseMoney, getManualNumberLines(purchaseMoney))
        OutputView.printLottoBunch(lottoBunch)
        return lottoBunch
    }

    private fun getManualNumberLines(purchaseMoney: PurchaseMoney): List<List<Int>> {
        val manualNumberLines = InputView.getManualNumberLines()
        OutputView.printPurchaseCount(
            manualNumberLines.size,
            purchaseMoney.getPurchaseCount() - manualNumberLines.size,
        )
        return manualNumberLines
    }

    private fun getPurchaseMoney(): PurchaseMoney {
        return runCatching {
            PurchaseMoney(InputView.getPurchaseMoney())
        }.getOrElse { error ->
            illegalArgumentExceptionHandler(error, ::getPurchaseMoney)
        }
    }

    private fun getMainLottoNumber(): Set<LottoNumber> {
        return runCatching {
            InputView.getMainLottoNumbers().map { number -> LottoNumber.from(number) }.toSet()
        }.getOrElse { error ->
            illegalArgumentExceptionHandler(error, ::getMainLottoNumber)
        }
    }

    private fun getBonusLottoNumber(): LottoNumber {
        return runCatching {
            LottoNumber.from(InputView.getBonusLottoNumber())
        }.getOrElse { error ->
            illegalArgumentExceptionHandler(error, ::getBonusLottoNumber)
        }
    }

    private fun getWinningLotto(): WinningLotto {
        return runCatching {
            WinningLotto(getMainLottoNumber(), getBonusLottoNumber())
        }.getOrElse { error ->
            illegalArgumentExceptionHandler(error, ::getWinningLotto)
        }
    }

    private fun checkWinningResult(lottoBunch: LottoBunch, winningLotto: WinningLotto, purchaseMoney: PurchaseMoney) {
        val ranks = lottoBunch.value.map { winningLotto.getRank(it) }
        val winningResult = WinningResult.from(ranks)
        OutputView.printWinningResult(winningResult)
        OutputView.printYieldRate(winningResult.getYieldRate(purchaseMoney))
    }
}