package lotto.view.constants;

public enum SymbolType {
    NEWLINE("\n"),
    WINNING_RESULT_SEPARATOR(" - ");

    private final String symbol;

    SymbolType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return this.symbol;
    }
}
