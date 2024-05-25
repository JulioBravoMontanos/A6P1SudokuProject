package inscaparrella.model;

import inscaparrella.utils.CellType;
import inscaparrella.utils.ConsoleColors;

public class ConstantCell extends Cell {

    private static final CellType CELLTYPE = CellType.CONSTANT_CELL;


    public ConstantCell() {
        super();
    }

    public ConstantCell(int value) {
        super(value);
    }

    @Override
    public CellType getCellType() {
        return ConstantCell.CELLTYPE;
    }

    @Override
    public void setValue(int value) {
    }

    @Override
    public void resetCell() {
    }

    @Override
    public void setHints(boolean[] hints) {
    }

    @Override
    public boolean isValueCorrect() {
        if (this.value == 0) {
            return false;
        } else {
            return this.hints[this.value - 1];
        }
    }

    @Override
    public String toString() {
        if (super.value != 0) {
            return ConsoleColors.PURPLE_BOLD + super.value + ConsoleColors.RESET;
        } else {
            return " ";
        }
    }
}
