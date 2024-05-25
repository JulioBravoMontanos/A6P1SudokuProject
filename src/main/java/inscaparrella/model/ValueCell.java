package inscaparrella.model;

import inscaparrella.utils.CellType;
import inscaparrella.utils.ConsoleColors;

public class ValueCell extends Cell {
    private static final CellType CELL_TYPE = CellType.VALUE_CELL;

    public ValueCell() {
        super();
    }

    public ValueCell(int value) {
        super(value);
    }

    @Override
    public CellType getCellType() {
        return ValueCell.CELL_TYPE;
    }

    @Override
    public void setValue(int value) {
        super.value = value;
    }

    @Override
    public void resetCell() {
        super.value = 0;
        super.hints = new boolean[MAX_VALUE];
        for (int i = 0; i < super.hints.length; i++) {
            super.hints[i] = true;
        }
    }

    @Override
    public void setHints(boolean[] hints) {
        super.hints = new boolean[hints.length];
        for (int i = 0; i < hints.length; i++) {
            super.hints[i] = hints[i];
        }
    }

    @Override
    public boolean isValueCorrect() {
        if (super.value == 0) return false;
        else return super.hints[super.value - 1];
    }

    @Override
    public String toString() {
        if (super.value == 0) return " ";
        else if (this.isValueCorrect()) return ConsoleColors.GREEN + super.value + ConsoleColors.RESET;
        else return ConsoleColors.RED + super.value + ConsoleColors.RESET;
    }
}
