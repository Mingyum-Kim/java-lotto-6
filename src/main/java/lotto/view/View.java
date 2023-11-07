package lotto.view;

import static lotto.view.constants.MessageType.BONUS_NUMBER_REQUEST_MESSAGE;
import static lotto.view.constants.MessageType.COST_REQUEST_MESSAGE;
import static lotto.view.constants.MessageType.LOTTO_COUNT_MESSAGE;
import static lotto.view.constants.MessageType.LOTTO_RESULT_MESSAGE;
import static lotto.view.constants.MessageType.WINNING_NUMBERS_REQUEST_MESSAGE;
import static lotto.view.constants.SymbolType.INPUT_SEPARATOR;
import static lotto.view.constants.SymbolType.OUTPUT_SEPARATOR;
import static lotto.view.constants.SymbolType.POSTFIX;
import static lotto.view.constants.SymbolType.PREFIX;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;
import lotto.domain.Lotto;
import lotto.domain.Lottos;
import lotto.global.exception.ErrorMessage;
import lotto.global.exception.LottoException;
import lotto.view.constants.MessageType;

public final class View {
    public static String requestCost() {
        printlnMessage(COST_REQUEST_MESSAGE);
        return Validator.validateCost(enterMessage());
    }

    public static void printLottos(Lottos lottos) {
        printLottosCount(lottos.getSize());
        printLottosInfo(lottos.getLottos());
    }

    public static String requestWinningNumbers() {
        printlnMessage(WINNING_NUMBERS_REQUEST_MESSAGE);
        return Validator.validateWinningNumbers(enterMessage());
    }

    public static String requestBonusNumber() {
        printlnMessage(BONUS_NUMBER_REQUEST_MESSAGE);
        return Validator.validateBonusNumber(enterMessage());
    }

    public static void printLottoResult() {
        printlnMessage(LOTTO_RESULT_MESSAGE);
        
    }

    private static void printLottosCount(int count) {
        printlnFormat(LOTTO_COUNT_MESSAGE, count);
    }

    private static void printLottosInfo(List<Lotto> lottos) {
        for (Lotto lotto : lottos) {
            printLottoInfo(lotto);
        }
    }

    private static void printLottoInfo(Lotto lotto) {
        String result = String.join(OUTPUT_SEPARATOR.getSymbol(), convertNumbers(lotto.getNumbers()));
        printlnResult(PREFIX + result + POSTFIX);
    }

    private static String[] convertNumbers(List<Integer> numbers) {
        return numbers
                .stream()
                .map(Object::toString)
                .toArray(String[]::new);
    }

    /* Output View */
    private static void printlnResult(String result) {
        System.out.println(result);
    }

    private static void printlnMessage(MessageType messageType) {
        System.out.println(messageType.getMessage());
    }

    private static void printlnFormat(MessageType message, Object... args) {
        System.out.println(String.format(message.getMessage(), args));
    }

    /* Input View */
    private static String enterMessage() {
        return Validator.validate(Console.readLine());
    }

    private static class Validator {
        private static String validate(String message) {
            if (isBlank(message)) {
                throw LottoException.from(ErrorMessage.BLANK_INPUT_ERROR);
            }
            return message;
        }

        private static boolean isBlank(String message) {
            return message.isBlank();
        }

        private static String validateCost(String cost) {
            validateNumber(cost);
            validateUnit(cost);
            return cost;
        }

        private static void validateNumber(String message) {
            if (isNotNumber(message)) {
                throw LottoException.from(ErrorMessage.NOT_NUMBER_ERROR);
            }
        }

        private static boolean isNotNumber(String message) {
            return !message.matches("\\d+");
        }

        private static void validateUnit(String cost) {
            if (isNotDivisible(cost)) {
                throw LottoException.from(ErrorMessage.INVALID_UNIT_ERROR);
            }
        }

        private static boolean isNotDivisible(String cost) {
            return Integer.parseInt(cost) % 1000 != 0;
        }

        private static String validateWinningNumbers(String message) {
            validateInvalidSeparators(message);
            return message;
        }

        private static void validateInvalidSeparators(String message) {
            if (hasEdgeSeparator(message) || hasDuplicatedSeparator(message)) {
                throw LottoException.from(ErrorMessage.INVALID_SEPARATOR_ERROR);
            }
        }

        private static boolean hasEdgeSeparator(String message) {
            return startsWithSeparator(message) || endsWithSeparator(message);
        }

        private static boolean startsWithSeparator(String message) {
            return message.startsWith(INPUT_SEPARATOR.getSymbol());
        }

        private static boolean endsWithSeparator(String message) {
            return message.endsWith(INPUT_SEPARATOR.getSymbol());
        }

        private static boolean hasDuplicatedSeparator(String message) {
            return message.contains(INPUT_SEPARATOR.getSymbol().repeat(2));
        }

        private static String validateBonusNumber(String message) {
            validateNumber(message);
            return message;
        }
    }
}
