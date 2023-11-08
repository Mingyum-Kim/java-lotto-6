package lotto.domain;

import static lotto.global.constants.NumberType.COST_UNIT;

import lotto.global.exception.ErrorMessage;
import lotto.global.exception.LottoException;
import lotto.view.CostRequestView;

/**
 * 로또 구매 비용을 저장하는 클래스
 */
public class Cost {
    private int cost;

    private Cost(int cost) {
        try {
            Validator.validate(cost);
        } catch (IllegalArgumentException e) {
            cost = CostRequestView.request();
        }
        this.cost = cost;
    }

    /**
     * 로또 구매 비용 객체를 생성하는 메서드
     *
     * @param cost 로또 구매 비용의 원시값
     * @return 로또 구매 비용 객체
     */
    public static Cost from(int cost) {
        return new Cost(cost);
    }

    /**
     * 로또 구매 비용으로 살 수 있는 로또 개수를 구하는 메서드
     *
     * @return 구매 가능한 로또 개수
     */
    public int getCount() {
        return cost / COST_UNIT.getValue();
    }

    /**
     * 총 수익을 비용으로 나누는 메서드
     *
     * @param earnings 총 수익
     * @return 수익률
     */
    public double divided(double earnings) {
        return earnings / cost;
    }

    private static class Validator {
        private static int validate(int cost) {
            validateUnit(cost);
            return cost;
        }

        private static void validateUnit(int cost) {
            if (isNotDivisible(cost)) {
                throw LottoException.from(ErrorMessage.INVALID_UNIT_ERROR);
            }
        }

        private static boolean isNotDivisible(int cost) {
            return cost % COST_UNIT.getValue() != 0;
        }
    }
}