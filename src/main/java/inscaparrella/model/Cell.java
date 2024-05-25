package inscaparrella.model;

import inscaparrella.utils.CellType;

public abstract class Cell {
    public static final int MIN_VALUE = 1;
    public static final int MAX_VALUE = 9;
    protected int value;
    protected boolean[] hints;

    public Cell() {
        this.value = 0;
        this.hints = new boolean[MAX_VALUE];
        for (int i = 0; i < this.hints.length; i++) {
            this.hints[i] = true;
        }
    }

    public Cell(int value) {
        if (value >= MIN_VALUE && value <= MAX_VALUE) this.value = value;
        else this.value = 0;
        this.hints = new boolean[MAX_VALUE];
        for (int i = 0; i < this.hints.length; i++) {
            this.hints[i] = true;
        }
    }

    public abstract CellType getCellType();

    public int getValue() {
        return this.value;
    }

    public abstract void setValue(int value);

    public abstract void resetCell();

    public boolean[] getHints() {
        boolean[] res = new boolean[this.hints.length];

        for (int i = 0; i < this.hints.length; i++) {
            res[i] = this.hints[i];
        }
        return res;
    }

    public abstract void setHints(boolean[] hints);

    public abstract boolean isValueCorrect();
}
