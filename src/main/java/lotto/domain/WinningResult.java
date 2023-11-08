package lotto.domain;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

public class WinningResult {
    private Map<WinningType, Integer> winningResult;

    private WinningResult(Lottos lottos, DrawnNumbers drawnNumbers) {
        this.winningResult = new EnumMap<>(WinningType.class);
        generateWinningResult(lottos, drawnNumbers);
    }

    public static WinningResult of(Lottos lottos, DrawnNumbers drawnNumbers) {
        return new WinningResult(lottos, drawnNumbers);
    }

    private void generateWinningResult(Lottos lottos, DrawnNumbers drawnNumbers) {
        for (Lotto lotto : lottos.getLottos()) {
            LottoResultDto lottoResultDto = drawnNumbers.compare(lotto);
            WinningType winningType = WinningType.findWinningType(
                    lottoResultDto.getWinningCount(),
                    lottoResultDto.hasBonusNumber());
            countWinningType(winningType);
        }
    }

    private void countWinningType(WinningType winningType) {
        initializeWinningResult();
        winningResult.put(winningType, winningResult.get(winningType) + 1);
    }

    private void initializeWinningResult() {
        Arrays.stream(WinningType.values())
                .forEach(winning -> winningResult.put(winning, 0));
    }

    public Integer getValue(WinningType winningType) {
        return winningResult.get(winningType);
    }

    public double calculateEarningsRate(int cost) {
        return getEarnings() / cost;
    }

    private double getEarnings() {
        return winningResult.entrySet()
                .stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }
}
