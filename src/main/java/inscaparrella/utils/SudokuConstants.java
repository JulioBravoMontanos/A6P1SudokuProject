package inscaparrella.utils;

import java.io.File;

public class SudokuConstants {
    public static int EASY_MODE = 0;
    public static int MEDIUM_MODE = 1;
    public static int HARD_MODE = 2;
    public static int EXPERT_MODE = 3;
    public static int MASTER_MODE = 4;
    public static String SUDOKU_FOLDER = "sudoku_files" + File.separator + "sudokus" + File.separator;
    public static String SAVED_SUDOKU_FOLDER = "sudoku_files" + File.separator + "saved_sudokus" + File.separator;
    public static String EASY_SUDOKU_EXT = "easy";
    public static String MEDIUM_SUDOKU_EXT = "medium";
    public static String HARD_SUDOKU_EXT = "hard";
    public static String EXPERT_SUDOKU_EXT = "expert";
    public static String MASTER_SUDOKU_EXT = "master";
}
