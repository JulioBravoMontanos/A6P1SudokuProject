package inscaparrella.view;

import inscaparrella.controller.SudokuController;
import inscaparrella.model.Board;
import inscaparrella.utils.ConsoleColors;
import inscaparrella.utils.SudokuConstants;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class SudokuMain {

    public static void main(String[] args) {
        SudokuController controller = new SudokuController();
        Scanner keyboard = new Scanner(System.in);
        int menu, mode, selectFile, row, col, value;
        String user, loadFile;
        char opcioJoc;
        List<Board> sudokus;
        ArrayList<String> llistaTaulers;
        boolean validador;

        do {
            System.out.println("~~~ SUDOKU ~~~\n" +
                    "0. Sortir\n" +
                    "1. Nova partida\n" +
                    "2. Carregar partida guardada\n" +
                    "3. Resolució automàtica\n" +
                    "4. Inserir un nou tauler al sistema");


            menu = keyboard.nextInt();
            keyboard.nextLine();

            switch (menu) {
                case 0:
                    System.out.println("Sortint...");
                    break;

                case 1:
                    System.out.println("Introdueix el nom d'usuari: ");
                    user = keyboard.next();
                    controller.setPlayer(user);
                    controller.getAllBoards();

                    System.out.println(controller.getPlayer().toString());

                    do {
                        System.out.println("Introdueix el nivell de dificultat: ");
                        System.out.println("0. Fàcil\n1. Mitjà\n2. Difícil\n3. Expert\n4. Mestre");
                        mode = keyboard.nextInt();
                        keyboard.nextLine();

                        if (mode < 0 || mode > 4) validador = false;
                        else validador = true;

                        if (!validador) System.out.println("Dificultat incorrecta. Torna a provar");
                    } while (!validador);

                    sudokus = controller.getSudokuFilesByMode(mode);

                    String boardColorList = "Llista de sudokus (color verd: sudokus ja jugats): \n";
                    Iterator<Board> ite = sudokus.iterator();
                    int counter = 0;
                    while (ite.hasNext()) {
                        Board temp = ite.next();
                        if (controller.isBoardPlayedByUser(temp)) {
                            boardColorList += counter + ". " + ConsoleColors.GREEN + temp.getFilename() + ConsoleColors.RESET + "\n";
                            counter++;
                        } else {
                            boardColorList += counter + ". " + ConsoleColors.BLUE + temp.getFilename() + ConsoleColors.RESET + "\n";
                            counter++;
                        }
                        System.out.println(boardColorList);
                    }


                    do {
                        System.out.println("Indica el fitxer que vols carregar: ");
                        selectFile = keyboard.nextInt();
                        keyboard.nextLine();

                        if (selectFile < 0 || selectFile >= sudokus.size()) {
                            System.out.println("Fitxer seleccionat incorrecte");
                            validador = false;
                        } else validador = true;

                    } while (!validador);

                    loadFile = sudokus.get(selectFile).getFilename();
                    if (!controller.loadSudoku(loadFile)) System.out.println("Error al carregar fitxer!");
                    do {
                        System.out.println(controller);

                        System.out.println("v: posar valor a la cel·la; h: veure pistes\ns: guardar partida; q: sortir");
                        opcioJoc = keyboard.next().toLowerCase().charAt(0);

                        if (opcioJoc == 'v') {
                            do {
                                System.out.println("Introdueix la posició de la row: ");
                                row = keyboard.nextInt();
                            } while (!SudokuMain.validacioPosicio(row));

                            do {
                                System.out.println("Introdueix la posició de la col: ");
                                col = keyboard.nextInt();
                            } while (!SudokuMain.validacioPosicio(col));

                            do {
                                System.out.println("Introdueix el valor");
                                value = keyboard.nextInt();
                                keyboard.nextLine();
                            } while (!SudokuMain.validacioPosicio(value - 1)); //-1 Per aprofitar la funció anterior

                            controller.setCellValue(row, col, value);

                        } else if (opcioJoc == 'h') System.out.println(controller.sudokuHintsToString());

                        else if (opcioJoc == 's') {
                            if (controller.saveSudoku()) System.out.println("Sudoku guardat correctament");
                            else System.out.println("Error al guardar el sudoku");

                        } else if (opcioJoc == 'q') break;

                    } while (!controller.isEnded());
                    break;

                case 2:
                    System.out.println("Introdueix el nom d'usuari: ");
                    user = keyboard.next();
                    controller.setPlayer(user);
                    controller.getAllBoards();
                    System.out.println(controller.getPlayer().toString());

                    sudokus = controller.getSavedSudokuFilesByUser();
                    String showSavedSudokus = "LLista de partides guardades:";
                    Iterator<Board> iter = sudokus.iterator();
                    int counter2 = 0;
                    while (iter.hasNext()) {

                        showSavedSudokus += "\n " + counter2 + ": " + iter.next().getFilename();
                        counter2++;
                    }
                    System.out.println(showSavedSudokus);

                    do {
                        System.out.println("Indica el numero del fitxer que vols carregar: ");
                        selectFile = keyboard.nextInt();
                        keyboard.nextLine();

                        if (selectFile < 0 || selectFile >= sudokus.size()) {
                            System.out.println("Fitxer seleccionat incorrecte");
                            validador = false;
                        } else validador = true;

                    } while (!validador);

                    loadFile = sudokus.get(selectFile).getFilename();
                    if (!controller.loadSavedSudoku(loadFile))
                        System.out.println("El fitxer no s'ha carregat correctament!");
                    do {
                        System.out.println(controller);

                        System.out.println("v: posar valor a la cel·la; h: veure pistes\ns: guardar partida; q: sortir");
                        opcioJoc = keyboard.next().toLowerCase().charAt(0);

                        if (opcioJoc == 'v') {
                            do {
                                System.out.println("Introdueix la posició de la row: ");
                                row = keyboard.nextInt();
                            } while (!SudokuMain.validacioPosicio(row));

                            do {
                                System.out.println("Introdueix la posició de la col: ");
                                col = keyboard.nextInt();
                            } while (!SudokuMain.validacioPosicio(col));

                            do {
                                System.out.println("Introdueix el valor");
                                value = keyboard.nextInt();
                                keyboard.nextLine();
                            } while (!SudokuMain.validacioPosicio(value - 1)); //-1 Per aprofitar la funció anterior

                            controller.setCellValue(row, col, value);

                        } else if (opcioJoc == 'h') System.out.println(controller.sudokuHintsToString());

                        else if (opcioJoc == 's') {
                            if (controller.saveSudoku()) System.out.println("Sudoku guardat correctament");
                            else System.out.println("Error al guardar el sudoku");

                        } else if (opcioJoc == 'q') break;

                    } while (!controller.isEnded());
                    break;

                case 3:
                    //Part recursivitat
                    break;

                case 4:
                    System.out.println("Quin sudoku vols agregar a la Base de Dades\n copia i pega el nom del fitxer: ");
                    String nom = keyboard.nextLine();
                    controller.addNewBoard(nom);
                    break;

                default:
                    System.out.println("Opció introduida incorrecta");
            }


            System.out.println("Fi de la partida!");

        } while (menu != 0);
    }

    static boolean validacioPosicio(int value) {
        return value >= 0 && value <= 8;
    }
}
