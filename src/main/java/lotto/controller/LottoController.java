package lotto.controller;

import static lotto.view.View.requestBonusNumber;
import static lotto.view.View.requestWinningNumbers;

import lotto.domain.DrawnNumbers;
import lotto.domain.Lottos;
import lotto.dto.DrawnNumbersDto;
import lotto.view.View;

public class LottoController {
    public void run() {
        Lottos lottos = issue();
        DrawnNumbers drawnNumbers = draw();
    }

    private Lottos issue() {
        int cost = Integer.parseInt(View.requestCost());
        Lottos lottos = Lottos.from(getCountFrom(cost));
        View.printLottos(lottos);
        return lottos;
    }

    private DrawnNumbers draw() {
        DrawnNumbersDto dto = DrawnNumbersDto.of(requestWinningNumbers(), requestBonusNumber());
        return DrawnNumbers.from(dto);
    }

    private int getCountFrom(int cost) {
        return cost / 1000;
    }
}
