package inscaparrella.controller;

import inscaparrella.model.*;
import inscaparrella.utils.SudokuConstants;

import java.io.File;
import java.util.*;

public class SudokuController {

    private SudokuBoard sudokuBoard;
    private Player player;

    public SudokuController() {
        this.sudokuBoard = new SudokuBoard();
        this.player = null;
    }

    public List<Board> getSudokuFilesByMode(int mode) {
        List<Board> boardsByDifficulty = new ArrayList<>();
        Set<Board> boardset = new TreeSet<>();


        String modeText = "";
        if (mode == SudokuConstants.EASY_MODE) modeText = SudokuConstants.EASY_SUDOKU_EXT;
        else if (mode == SudokuConstants.MEDIUM_MODE) modeText = SudokuConstants.MEDIUM_SUDOKU_EXT;
        else if (mode == SudokuConstants.HARD_MODE) modeText = SudokuConstants.HARD_SUDOKU_EXT;
        else if (mode == SudokuConstants.EXPERT_MODE) modeText = SudokuConstants.EXPERT_SUDOKU_EXT;
        else if (mode == SudokuConstants.MASTER_MODE) modeText = SudokuConstants.MASTER_SUDOKU_EXT;

        Map<String, Set<Board>> allBoards = BoardDAO.getAllBoards();
        boardset = allBoards.get(modeText);
        Iterator<Board> ite = boardset.iterator();
        while (ite.hasNext()) {
            boardsByDifficulty.add(ite.next());
        }

        return boardsByDifficulty;
    }

    public boolean setPlayer(String uname) {
        this.player = PlayerDAO.insertPlayer(uname);
        if (player == null) {
            return false;
        } else return true;
    }

    public Player getPlayer() {
        return new Player(this.player);
    }

    public List<Board> getSavedSudokuFilesByUser() {

        List<Board> boardlistbyplayer = new ArrayList<>();
        if (!player.getUname().isEmpty()) {
            Iterator<Board> ite = player.getSavedBoards().iterator();
            while (ite.hasNext()) {
                boardlistbyplayer.add(ite.next());
            }

            return boardlistbyplayer;
        } else return null;

    }

    public boolean loadSudoku(String filename) {
        if (this.player != null) {
            Board newboard = new Board();
            newboard.setFilename(filename);
            return sudokuBoard.loadSudoku(newboard);
        } else return false;
    }

    public boolean loadSavedSudoku(String filename) {
        if (this.player != null) {
            Board newboard = new Board();
            newboard.setFilename(filename);
            return sudokuBoard.loadSavedSudoku(this.player.getUname(), newboard);
        } else return false;
    }

    public boolean saveSudoku() {
        if (this.player != null) {
            if (this.sudokuBoard.saveSudoku(this.player.getUname()) && PlayDAO.insertSaved(this.sudokuBoard.getBoard(), this.player.getUname())) {
                return true;
            }
        }
        return false;
    }

    public void solveSudoku() {
        //Recursiu
    }

    public boolean setCellValue(int row, int col, int value) {
        return sudokuBoard.setCellValue(row, col, value);
    }

    public boolean isEnded() {
        if (sudokuBoard.isEnded() && PlayDAO.insertPlay(sudokuBoard.getBoard(), player.getUname())) {
            return true;
        } else return false;
    }

    public String toString() {
        return sudokuBoard.toString();
    }

    public String sudokuHintsToString() {
        return this.sudokuBoard.sudokuHintsToString();
    }

    public String solvedSudokuString() {
        //recursivitat
        return "";
    }

    public boolean addNewBoard(String filename) {
        File board = new File(SudokuConstants.SUDOKU_FOLDER + filename);

        if (!board.exists()) {
            Board newboard = new Board(filename);
            return BoardDAO.insertNewBoard(newboard);
        } else return false;

    }

    public Map<String, Set<Board>> getAllBoards() {
        return BoardDAO.getAllBoards();
    }

    public boolean isBoardPlayedByUser(Board board) {
        if (!player.getUname().isEmpty()) {
            return false;
        } else if (player.isBoardPlayed(board)) {
            return true;
        } else return false;
    }
}
