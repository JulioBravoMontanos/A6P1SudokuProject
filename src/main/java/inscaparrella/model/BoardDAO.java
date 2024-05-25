package inscaparrella.model;

import java.sql.*;
import java.util.*;

public class BoardDAO {

    public static boolean insertNewBoard(Board board) {
        Connection dbconnection;
        PreparedStatement statement;
        String sql = "INSERT INTO Board (filename, id_level) VALUES (?, ?);";
        //int insert = 0;


        if (!checkBoardExists(board)) {
            try {
                dbconnection = DriverManager.getConnection(DAOConstants.URL, DAOConstants.USER, DAOConstants.PASSWORD);
                statement = dbconnection.prepareStatement(sql);
                statement.setString(1, board.getFilename());
                statement.setInt(2, Integer.parseInt(board.getLevel()));
                /*insert =*/
                statement.executeUpdate();


                statement.close();
                dbconnection.close();
                return true;
            } catch (SQLException sqlException) {
                System.err.println("Error d'SQL  BoardDao insertNewBoard");
                sqlException.printStackTrace();
            }
        }
        //return insert == 1;
        return false;
    }

    public static Map<String, Set<Board>> getAllBoards() {

        Set<String> claus = new HashSet<>();
        Connection dbconnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM boardlevel;";
        String sql2 = "SELECT * FROM board;";
        try {
            dbconnection = DriverManager.getConnection(DAOConstants.URL, DAOConstants.USER, DAOConstants.PASSWORD);
            statement = dbconnection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                claus.add(resultSet.getString("name"));
            }

            resultSet.close();
            statement.close();
            dbconnection.close();

        } catch (SQLException sqle) {
            System.err.println("SQL exception BoardDao getAllBoards 1");
            sqle.printStackTrace();
        }

        Map<String, Set<Board>> allboards = new HashMap<>();
        ArrayList<String> llistanoms = new ArrayList<>();
        try {
            dbconnection = DriverManager.getConnection(DAOConstants.URL, DAOConstants.USER, DAOConstants.PASSWORD);
            statement = dbconnection.createStatement();
            resultSet = statement.executeQuery(sql2);
            while (resultSet.next()) {
                llistanoms.add(resultSet.getString("filename"));
            }

            resultSet.close();
            statement.close();
            dbconnection.close();
        } catch (SQLException sqlex) {
            System.err.println("SQL exception BoardDao getAllBoards 2");
            sqlex.printStackTrace();
        }

        Set<Board> playerBoard = new TreeSet<>();
        String level;
        Iterator<String> ita = claus.iterator();
        while (ita.hasNext()) {
            level = ita.next();
            for (int i = 0; i < llistanoms.size(); i++) {
                Board tauler = new Board();
                tauler.setFilename(llistanoms.get(i));
                if (tauler.getLevel().equals(level)) {
                    playerBoard.add(tauler);
                }
            }

            allboards.put(level, playerBoard);

        }
        return allboards;
    }


    private static boolean checkBoardExists(Board board) {
        Connection dbconnection;
        PreparedStatement statement;
        ResultSet resultSet;
        String sql = "SELECT * FROM Board WHERE filename LIKE ?;";
        boolean res = false;

        try {
            dbconnection = DriverManager.getConnection(DAOConstants.URL, DAOConstants.USER, DAOConstants.PASSWORD);
            statement = dbconnection.prepareStatement(sql);
            statement.setString(1, board.getFilename());
            resultSet = statement.executeQuery();

            if (resultSet != null) {
                res = true;
            }

            resultSet.close();
            statement.close();
            dbconnection.close();

        } catch (SQLException sqlException) {
            System.err.println("Error d'SQL BardDao checkBoardExists");
            sqlException.printStackTrace();
        }
        return res;
    }

    private static int getLevelId(String string) {

        return 1;
    }
}
