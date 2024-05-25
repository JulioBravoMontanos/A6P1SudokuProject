package inscaparrella.model;

import inscaparrella.utils.SudokuConstants;

import java.util.Objects;

public class Board implements Comparable<Board> {

    private String filename;
    private String level;

    public Board() {
        filename = "";
        level = "";

    }

    public Board(String filename) {
        this.filename = filename;
        if (filename.contains(SudokuConstants.EASY_SUDOKU_EXT)) {
            this.level = SudokuConstants.EASY_SUDOKU_EXT;
        } else if (filename.contains(SudokuConstants.MEDIUM_SUDOKU_EXT)) {
            this.level = SudokuConstants.MEDIUM_SUDOKU_EXT;
        } else if (filename.contains(SudokuConstants.HARD_SUDOKU_EXT)) {
            this.level = SudokuConstants.HARD_SUDOKU_EXT;
        } else if (filename.contains(SudokuConstants.EXPERT_SUDOKU_EXT)) {
            this.level = SudokuConstants.EXPERT_SUDOKU_EXT;
        } else if (filename.contains(SudokuConstants.MASTER_SUDOKU_EXT)) {
            this.level = SudokuConstants.MASTER_SUDOKU_EXT;
        }
    }

    public Board(Board board) {
        this.filename = board.filename;
        this.level = board.level;
    }

    public String getFilename() {
        return this.filename;
    }

    public String getLevel() {
        return this.level;
    }

    public void setFilename(String filename) {
        this.filename = filename;
        if (filename.contains(SudokuConstants.EASY_SUDOKU_EXT)) {
            this.level = SudokuConstants.EASY_SUDOKU_EXT;
        } else if (filename.contains(SudokuConstants.MEDIUM_SUDOKU_EXT)) {
            this.level = SudokuConstants.MEDIUM_SUDOKU_EXT;
        } else if (filename.contains(SudokuConstants.HARD_SUDOKU_EXT)) {
            this.level = SudokuConstants.HARD_SUDOKU_EXT;
        } else if (filename.contains(SudokuConstants.EXPERT_SUDOKU_EXT)) {
            this.level = SudokuConstants.EXPERT_SUDOKU_EXT;
        } else if (filename.contains(SudokuConstants.MASTER_SUDOKU_EXT)) {
            this.level = SudokuConstants.MASTER_SUDOKU_EXT;
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Board) {
            if (((Board) object).filename.equals(this.filename) && ((Board) object).level.equals(this.level)) {
                return true;
            } else return false;
        } else return false;
    }


    @Override
    public String toString() {
        return "Board{" +
                "Filename='" + this.filename + '\'' +
                ", Level='" + this.level + '\'' +
                '}';
    }

    @Override
    public int compareTo(Board board) {
        return this.filename.compareTo(board.filename);
    }
}

