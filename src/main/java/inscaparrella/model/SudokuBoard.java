package inscaparrella.model;

import inscaparrella.utils.CellType;
import inscaparrella.utils.SudokuConstants;

import java.io.*;
import java.util.Scanner;

public class SudokuBoard {
    private static final int PLAY_MODE = 0;
    private static final int SOLVE_MODE = 1;
    private static final int SIZE = 9;
    private static final int SQUARES_SIZE = 3;
    private Cell[][] sudokuCells;
    private Cell[][] solvedSudokuCells;
    private Board board;
    private boolean boardLoaded;

    public SudokuBoard() {
        this.sudokuCells = new Cell[SIZE][SIZE];
        this.solvedSudokuCells = new Cell[SIZE][SIZE];
        this.board = null;
        this.boardLoaded = false;
    }


    public boolean loadSudoku(Board board) {
        int tmp;

        try {
            File taulerguardat = new File(SudokuConstants.SUDOKU_FOLDER + File.separator + board.getFilename());
            FileReader freader = new FileReader(taulerguardat);
            BufferedReader breader = new BufferedReader(freader);
            Scanner scan = new Scanner(breader);

            this.board = new Board(board);

            /*this.filename = filename;*/
            this.boardLoaded = true;
            while (scan.hasNextInt()) {
                for (int i = 0; i < this.sudokuCells.length; i++) {
                    for (int e = 0; e < this.sudokuCells[i].length; e++) {
                        tmp = scan.nextInt();
                        if (tmp == 0) this.sudokuCells[i][e] = new ValueCell();
                        else this.sudokuCells[i][e] = new ConstantCell(tmp);
                    }
                }
            }
            scan.close();
            breader.close();
            freader.close();

        } catch (FileNotFoundException fnf) {
            System.err.println("No s'ha trobat el fitxer guardat");
            fnf.printStackTrace();
            return false;
        } catch (IOException ioe) {
            System.err.println("Error d'entrada/sortida");
            ioe.printStackTrace();
            return false;
        }
        updateAllHints();
        return this.boardLoaded;
    }

    public boolean loadSavedSudoku(String uname, Board board) {
        int tmp;

        try {
            File taulerBase = new File(SudokuConstants.SUDOKU_FOLDER + File.separator + board.getFilename());
            FileReader freader = new FileReader(taulerBase);
            BufferedReader breader = new BufferedReader(freader);
            Scanner scan = new Scanner(breader);

            this.board = new Board(board);
            this.boardLoaded = true;
            while (scan.hasNextInt()) {
                for (int i = 0; i < this.sudokuCells.length; i++) {
                    for (int x = 0; x < this.sudokuCells[i].length; x++) {
                        tmp = scan.nextInt();
                        if (tmp == 0) this.sudokuCells[i][x] = new ValueCell();
                        else this.sudokuCells[i][x] = new ConstantCell(tmp);
                    }
                }
            }
            scan.close();
            breader.close();
            freader.close();

            File taulerguardat = new File(SudokuConstants.SAVED_SUDOKU_FOLDER + File.separator + uname + "_" + board.getFilename());
            FileReader freader2 = new FileReader(taulerguardat);
            BufferedReader breader2 = new BufferedReader(freader2);
            Scanner scan2 = new Scanner(breader2);

            while (scan2.hasNextInt()) {
                for (int i = 0; i < this.sudokuCells.length; i++) {
                    for (int x = 0; x < this.sudokuCells[i].length; x++) {
                        tmp = scan2.nextInt();
                        if (tmp != 0 && this.sudokuCells[i][x].getCellType() == CellType.VALUE_CELL)
                            this.sudokuCells[i][x].setValue(tmp);
                    }
                }
            }
            scan2.close();
            breader2.close();
            freader2.close();

        } catch (FileNotFoundException fnf) {
            System.err.println("No s'ha trobat el fitxer guardat");
            fnf.printStackTrace();
            return false;
        } catch (IOException ioe) {
            System.err.println("Error d'entrada/sortida");
            ioe.printStackTrace();
            return false;
        }
        updateAllHints();
        return this.boardLoaded;
    }

    private void updateHintsFromRow(boolean[] hints, int row, int col) {
        if (this.boardLoaded) {
            for (int i = 0; i < this.sudokuCells[row].length; i++) {
                if (i != col && this.sudokuCells[row][i].getValue() != 0)
                    hints[this.sudokuCells[row][i].value - 1] = false;
            }
        }
    }

    private void updateHintsFromCol(boolean[] hints, int row, int col) {
        if (this.boardLoaded) {
            for (int i = 0; i < this.sudokuCells.length; i++)
                if (i != row && this.sudokuCells[i][col].getValue() != 0)
                    hints[this.sudokuCells[i][col].value - 1] = false;
        }
    }

    private void updateHintsFromSquare(boolean[] hints, int row, int col) {
        if (this.boardLoaded) {
            int resRow = SQUARES_SIZE * (row / SQUARES_SIZE), resCol = SQUARES_SIZE * (col / SQUARES_SIZE);

            for (int i = resRow; i < resRow + SQUARES_SIZE; i++) {
                for (int x = resCol; x < resCol + SQUARES_SIZE; x++) {
                    if (this.sudokuCells[i][x].getValue() != 0 && (i != row || x != col))
                        hints[this.sudokuCells[i][x].value - 1] = false;
                }
            }
        }
    }

    private void updateAllHints() {
        if (this.boardLoaded) {
            for (int i = 0; i < this.sudokuCells.length; i++) {
                for (int x = 0; x < this.sudokuCells[i].length; x++) {
                    boolean[] hints = this.sudokuCells[i][x].getHints();
                    updateHintsFromRow(hints, i, x);
                    updateHintsFromCol(hints, i, x);
                    updateHintsFromSquare(hints, i, x);
                    this.sudokuCells[i][x].setHints(hints);
                }
            }
        }
    }

    public boolean saveSudoku(String uname) {
        File savedSudokusFolder = new File(SudokuConstants.SAVED_SUDOKU_FOLDER);
        if (this.boardLoaded) {
            if (!savedSudokusFolder.exists()) {
                savedSudokusFolder.mkdirs();
            }


            File savedSudoku;
            if (this.board.getFilename().startsWith(uname + "_"))
                savedSudoku = new File(SudokuConstants.SAVED_SUDOKU_FOLDER + board.getFilename());
            else
                savedSudoku = new File(SudokuConstants.SAVED_SUDOKU_FOLDER + uname + "_" + this.board.getFilename());

            try {
                FileWriter fileWriter = new FileWriter(savedSudoku);
                BufferedWriter bwriter = new BufferedWriter(fileWriter);
                PrintWriter pwriter = new PrintWriter(bwriter);

                for (int i = 0; i < this.sudokuCells.length; i++) {
                    for (int e = 0; e < this.sudokuCells[i].length; e++) {
                        pwriter.print(this.sudokuCells[i][e].getValue() + " ");
                    }
                    pwriter.println();
                }

                pwriter.close();
                bwriter.close();
                fileWriter.close();

            } catch (IOException ioe) {
                System.err.println("ioe exception");
                ioe.printStackTrace();
                return false;
            }
            return true;
        } else return false;
    }

    private boolean solveSudokuRecursive() { //Funció buida. Falta fer
        return false;
    }

    private boolean checkCellValue(int row, int col, int value) {
        if (row >= 0 && row < SIZE && col >= 0 && col < SIZE && value >= Cell.MIN_VALUE && value <= Cell.MAX_VALUE) {
            return true;
        } else
            return false;
    }

    public boolean setCellValue(int row, int col, int value) {
        if (this.sudokuCells[row][col].getCellType() == CellType.VALUE_CELL && checkCellValue(row,col,value)) {
            this.sudokuCells[row][col].setValue(value);
            updateAllHints();
            return true;
        }
        return false;
    }

    public boolean isEnded() {
        for (int i = 0; i < this.sudokuCells.length; i++) {
            for (int x = 0; x < this.sudokuCells[i].length; x++) {
                if (this.sudokuCells[i][x].getValue() == 0 || !this.sudokuCells[i][x].isValueCorrect()) return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String str = "";

        for (int i = 0; i < this.sudokuCells.length; i++) {
            for (int x = 0; x < this.sudokuCells[i].length; x++) {
                if (x==0){str += "|";}
                if (x%3 ==0){
                    {str += "|";}
                }
                str += "  ";
                str += this.sudokuCells[i][x].toString();
                str += " |";
            }
            {str += "|";}
            str += "\n";
        }
        return str;
    }

    public String sudokuHintsToString() {
        String sudokuHintsString = "";
        sudokuHintsString += "---------------------------------------------------------------\n";
        for (int row = 0; row < sudokuCells.length; row++) {
            for (int subrow = 0; subrow < SQUARES_SIZE; subrow++) {
                for (int col = 0; col < sudokuCells[row].length * SQUARES_SIZE; col++) {
                    if (sudokuCells[row][col / SQUARES_SIZE].getCellType() == CellType.VALUE_CELL) {
                        if (sudokuCells[row][col / SQUARES_SIZE].hints[subrow * SQUARES_SIZE + col % SQUARES_SIZE]) {
                            sudokuHintsString += " " + (subrow * SQUARES_SIZE + col % SQUARES_SIZE + 1);
                        } else sudokuHintsString += "  ";
                    } else sudokuHintsString += "  ";
                    if (col % SQUARES_SIZE == SQUARES_SIZE - 1) sudokuHintsString += "|";
                }
                sudokuHintsString += "\n";
            }
            sudokuHintsString += "---------------------------------------------------------------\n";
        }
        return sudokuHintsString;
    }

    public Board getBoard() {
        Board bord = new Board(this.board);
        return bord;
    }
}
