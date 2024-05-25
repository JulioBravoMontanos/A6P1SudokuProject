package inscaparrella.model;

import inscaparrella.controller.SudokuController;
import inscaparrella.utils.SudokuConstants;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class PlayerDAO {

    public static Player insertPlayer(String uname) {
        Player jugador = new Player();
        if (checkPlayerExists(uname)) {
            jugador.setUname(uname);
            Connection dbconnection = null;
            PreparedStatement preparedStatement = null;
            ResultSet result;
            String sqlete = "SELECT * FROM player WHERE uname LIKE ?;";
            try {

                dbconnection = DriverManager.getConnection(DAOConstants.URL, DAOConstants.USER, DAOConstants.PASSWORD);
                preparedStatement = dbconnection.prepareStatement(sqlete);
                preparedStatement.setString(1, uname);
                result = preparedStatement.executeQuery();

                if (result.next()) {
                    jugador.setCreationDate(result.getDate("creation_date"));
                }

                List<Board> playedboards = (PlayDAO.getPlayedBoardsByUser(uname));
                jugador.setPlayedBoards(playedboards);


                Set<Board> savedboards = new TreeSet<>(PlayDAO.getSavedBoardsByUser(uname));
                jugador.setSavedBoards(savedboards);

                result.close();
                preparedStatement.close();
                dbconnection.close();


            } catch (SQLException sqle) {
                System.err.println("SQl Exception PlayDao insertPlayer 1");
                sqle.printStackTrace();
                jugador = null;
                return jugador;
            }
            HashMap<String, List<Board>> playedboards;
            playedboards = (HashMap<String, List<Board>>) jugador.getPlayedBoards();

            Set<String> keys = playedboards.keySet();
            List<Board> playerBoard, newPlayerBoard;
            String level;
            Board tauler;
            Iterator<String> ita = keys.iterator();
            ArrayList<String> llistanoms = new ArrayList<>();
            while (ita.hasNext()) {
                level = ita.next();
                playerBoard = playedboards.get(level);
                newPlayerBoard = new LinkedList<>();

                Iterator<Board> itb = playerBoard.iterator();
                while (itb.hasNext()) {
                    tauler = itb.next();
                    llistanoms.add(itb.next().getFilename());
                    newPlayerBoard.add(new Board(tauler));
                }

                playedboards.put(level, newPlayerBoard);
            }
            int points = 0;
            String dificulty;
            for (int i = 0; i < llistanoms.size(); i++) {
                dificulty = llistanoms.get(i).split(".")[1];
                if (dificulty.equals(SudokuConstants.EASY_SUDOKU_EXT)) {
                    points += 5;
                } else if (dificulty.equals(SudokuConstants.MEDIUM_SUDOKU_EXT)) {
                    points += 10;
                } else if (dificulty.equals(SudokuConstants.HARD_SUDOKU_EXT)) {
                    points += 15;
                } else if (dificulty.equals(SudokuConstants.EXPERT_SUDOKU_EXT)) {
                    points += 30;
                } else if (dificulty.equals(SudokuConstants.MASTER_SUDOKU_EXT)) {
                    points += 60;
                }
            }
            jugador.setPoints(points);


        } else {

            jugador.setUname(uname);

            try {

                Connection dbconnection = null;
                PreparedStatement preparedStatement = null;
                String sqlete = "INSERT INTO PLAYER VALUES (?,?);";


                dbconnection = DriverManager.getConnection(DAOConstants.URL, DAOConstants.USER, DAOConstants.PASSWORD);
                preparedStatement = dbconnection.prepareStatement(sqlete);
                Date sqldate = new Date(jugador.getCreationDate().getTime());  //no funciona i esta al pdf
                preparedStatement.setString(1, uname);
                preparedStatement.setDate(2, sqldate);
                preparedStatement.executeUpdate(); //peta aqui
                jugador.setCreationDate(sqldate);


                preparedStatement.close();
                dbconnection.close();


            } catch (SQLException sqle) {
                System.err.println("SQlete Exceptional PlayDao insertPlayer 1");
                sqle.printStackTrace();
                jugador = null;
                return jugador;
            }
        }
        return jugador;
    }

    private static boolean checkPlayerExists(String uname) {

        Connection dbconnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result;
        String sqlete = "SELECT * FROM player WHERE uname LIKE ?;";
        try {

            dbconnection = DriverManager.getConnection(DAOConstants.URL, DAOConstants.USER, DAOConstants.PASSWORD);
            preparedStatement = dbconnection.prepareStatement(sqlete);
            preparedStatement.setString(1, uname);
            result = preparedStatement.executeQuery();

            if (result.next()) {
                return true;
            }

            result.close();
            preparedStatement.close();
            dbconnection.close();


        } catch (SQLException sqle) {
            System.err.println("SQlete Exceptional PlayDao checkPlayerExists");
            sqle.printStackTrace();
        }

        return false;
    }

}
