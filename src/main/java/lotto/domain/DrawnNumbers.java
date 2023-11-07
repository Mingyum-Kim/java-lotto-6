package lotto.domain;

import static lotto.global.constants.NumberType.MAX_LOTTO_NUMBER;
import static lotto.global.constants.NumberType.MIN_LOTTO_NUMBER;

import java.util.List;
import lotto.dto.DrawnNumbersDto;
import lotto.global.exception.ErrorMessage;
import lotto.global.exception.LottoException;

public class DrawnNumbers {
    private final List<Integer> winningNumbers;

    private final Integer bonusNumber;

    private DrawnNumbers(List<Integer> winningNumbers, Integer bonusNumber) {
        validateWinningNumbers(winningNumbers);
        validateBonusNumber(bonusNumber);
        this.winningNumbers = winningNumbers;
        this.bonusNumber = bonusNumber;
    }

    public DrawnNumbers from(DrawnNumbersDto drawnNumbersDto) {
        return new DrawnNumbers(drawnNumbersDto.getWinningNumbers(), drawnNumbersDto.getBonusNumber());
    }

    private void validateWinningNumbers(List<Integer> winningNumbers) {
        validateInvalidRange(winningNumbers);
        validateDuplication(winningNumbers);
    }

    private void validateDuplication(List<Integer> winningNumbers) {
        if (isDuplicated(winningNumbers)) {
            throw LottoException.from(ErrorMessage.DUPLICATED_NUMBER_ERROR);
        }
    }

    private boolean isDuplicated(List<Integer> winningNumbers) {
        int uniqueSize = getUniqueSize(winningNumbers);
        return uniqueSize != winningNumbers.size();
    }

    private static int getUniqueSize(List<Integer> winningNumbers) {
        return (int) winningNumbers.stream()
                .distinct()
                .count();
    }

    private static void validateInvalidRange(List<Integer> winningNumbers) {
        if (isInvalidRange(winningNumbers)) {
            throw LottoException.from(ErrorMessage.INVALID_RANGE_ERROR);
        }
    }

    private boolean isInvalidRange(List<Integer> winningNumbers) {
        return winningNumbers.stream()
                .allMatch(number ->
                        number >= MIN_LOTTO_NUMBER.getValue() && number <= MAX_LOTTO_NUMBER.getValue()
                );
    }

    private void validateBonusNumber(Integer bonusNumber) {
        validateInvalidRange(bonusNumber);
        validateDuplication(bonusNumber);
    }

    private void validateInvalidRange(Integer bonusNumber) {
        if (isInvalidRange(bonusNumber)) {
            throw LottoException.from(ErrorMessage.INVALID_RANGE_ERROR);
        }
    }

    private boolean isInvalidRange(Integer bonusNumber) {
        return bonusNumber < MIN_LOTTO_NUMBER.getValue() || bonusNumber > MAX_LOTTO_NUMBER.getValue();
    }

    private void validateDuplication(Integer bonusNumber) {
        if (isDuplicatedWithWinningNumbers(bonusNumber)) {
            throw LottoException.from(ErrorMessage.DUPLICATED_NUMBER_ERROR);
        }
    }

    private boolean isDuplicatedWithWinningNumbers(Integer bonusNumber) {
        return winningNumbers.contains(bonusNumber);
    }
}
