package inscaparrella.model;

import java.sql.*;
import java.util.*;

public class PlayDAO {


    public static List<Board> getPlayedBoardsByUser(String uname) {
        LinkedList<Board> boardsByUser = new LinkedList<>();
        ArrayList<Integer> idesByPlayer = new ArrayList<>();
        Connection dbconection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT id_board FROM play WHERE uname like ?;";
        String sql1 = "SELECT filename FROM board WHERE id like ?;";
        try {
            dbconection = DriverManager.getConnection(DAOConstants.URL, DAOConstants.USER, DAOConstants.PASSWORD);
            preparedStatement = dbconection.prepareStatement(sql);
            preparedStatement.setString(1, uname);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                idesByPlayer.add(resultSet.getInt("id_board"));
            }

            resultSet.close();
            preparedStatement.close();
            dbconection.close();


        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.err.println("SQL exception PlayDao getPlayedBoardsByUser 1;");
        }
        if (idesByPlayer.size() > 0) {
            try {
                dbconection = DriverManager.getConnection(DAOConstants.URL, DAOConstants.USER, DAOConstants.PASSWORD);
                preparedStatement = dbconection.prepareStatement(sql1);
                int counter = 0;

                do {
                    preparedStatement.setInt(1, idesByPlayer.get(counter)); //peta oob
                    resultSet = preparedStatement.executeQuery();
                    boardsByUser.add(new Board(resultSet.getString("filename")));
                } while (resultSet.next());


                resultSet.close();
                preparedStatement.close();
                dbconection.close();

            } catch (SQLException sqle) {
                sqle.printStackTrace();
                System.err.println("SQL exception getPlayedBoardsByUser 2");
            }
        }
        return boardsByUser;
    }

    public static List<Board> getSavedBoardsByUser(String uname) {
        LinkedList<Board> boardsByUser = new LinkedList<>();
        ArrayList<Integer> idesByPlayer = new ArrayList<>();
        Connection dbconection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT id_board FROM saved WHERE uname like ?;";
        String sql1 = "SELECT filename FROM board WHERE id = ?;";
        try {
            dbconection = DriverManager.getConnection(DAOConstants.URL, DAOConstants.USER, DAOConstants.PASSWORD);
            preparedStatement = dbconection.prepareStatement(sql);
            preparedStatement.setString(1, uname);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                idesByPlayer.add(resultSet.getInt("id_board"));
            }

            resultSet.close();
            preparedStatement.close();
            dbconection.close();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.err.println("SQL exception PlayDao getSavedBoardsByUser 1");
        }
        if (!idesByPlayer.isEmpty()) {
            try {
                dbconection = DriverManager.getConnection(DAOConstants.URL, DAOConstants.USER, DAOConstants.PASSWORD);
                preparedStatement = dbconection.prepareStatement(sql1);
                int counter = 0;
                do {
                    preparedStatement.setInt(1, idesByPlayer.get(counter));
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        boardsByUser.add(new Board(resultSet.getString("filename")));
                    }

                } while (resultSet.next());

                resultSet.close();
                preparedStatement.close();
                dbconection.close();

            } catch (SQLException sqle) {
                sqle.printStackTrace();
                System.err.println("SQL exception PlayDao getSavedBoardsByUser 2");
            }
        }
        return boardsByUser;
    }

    public static boolean insertPlay(Board board, String uname) {
        List<Board> llistaboards = new ArrayList<>(PlayDAO.getPlayedBoardsByUser(uname));
        boolean chivato = false;
        int idboard = 0;
        for (int i = 0; i < llistaboards.size(); i++) {
            if (board.equals(llistaboards.get(i))) {

                Connection dbconnection = null;
                PreparedStatement preparedStatement = null;
                ResultSet resultSet = null;
                String sql1 = "SELECT id FROM board WHERE filename LIKE ?;";
                String sql2 = "UPDATE [play] set play_date=(SELECT CURRENT_DATE;) WHERE id_board LIKE ?;";
                String sql3 = "DELETE FROM saved  WHERE id_board LIKE ?;";


                try {
                    dbconnection = DriverManager.getConnection(DAOConstants.URL, DAOConstants.USER, DAOConstants.PASSWORD);
                    preparedStatement = dbconnection.prepareStatement(sql1);
                    preparedStatement.setString(1, board.getFilename());
                    resultSet = preparedStatement.executeQuery(sql1);
                    idboard = resultSet.getInt(1);

                    resultSet.close();
                    preparedStatement.close();
                    dbconnection.close();
                } catch (SQLException sqle) {
                    System.err.println("SQL Exception playdao1 insertplay");
                    sqle.printStackTrace();
                }
                try {
                    dbconnection = DriverManager.getConnection(DAOConstants.URL, DAOConstants.USER, DAOConstants.PASSWORD);
                    preparedStatement = dbconnection.prepareStatement(sql2);
                    preparedStatement.setInt(1, idboard);
                    preparedStatement.executeUpdate(sql2);

                    preparedStatement.close();
                    dbconnection.close();
                } catch (SQLException sqle) {
                    System.err.println("SQL Exception playdao1.1 insertplay");
                    sqle.printStackTrace();
                }

                try {
                    dbconnection = DriverManager.getConnection(DAOConstants.URL, DAOConstants.USER, DAOConstants.PASSWORD);
                    preparedStatement = dbconnection.prepareStatement(sql3);
                    preparedStatement.setInt(1, idboard);
                    preparedStatement.executeUpdate(sql3);

                    preparedStatement.close();
                    dbconnection.close();
                } catch (SQLException sqle) {
                    System.err.println("SQL Exception playdao2 insertplay");
                    sqle.printStackTrace();
                }
                chivato = true;
            } else {
                Connection dbconnection = null;
                PreparedStatement preparedStatement = null;
                ResultSet resultSet = null;
                String sql4 = "SELECT id FROM board WHERE filename LIKE ?;";
                String sql5 = "INSERT INTO play VALUES (?,(SELECT CURRENT_DATE;),?);"; //revisar si falla

                try {
                    dbconnection = DriverManager.getConnection(DAOConstants.URL, DAOConstants.USER, DAOConstants.PASSWORD);
                    preparedStatement = dbconnection.prepareStatement(sql4);
                    preparedStatement.setString(1, board.getFilename());
                    resultSet = preparedStatement.executeQuery(sql4);
                    idboard = resultSet.getInt(1);

                    resultSet.close();
                    preparedStatement.close();
                    dbconnection.close();
                } catch (SQLException sqle) {
                    System.err.println("SQL Exception playdao3 insertplay");
                    sqle.printStackTrace();
                }
                try {
                    dbconnection = DriverManager.getConnection(DAOConstants.URL, DAOConstants.USER, DAOConstants.PASSWORD);
                    preparedStatement = dbconnection.prepareStatement(sql5);
                    preparedStatement.setInt(1, idboard);
                    preparedStatement.setString(2, uname);
                    preparedStatement.executeUpdate(sql5);

                    preparedStatement.close();
                    dbconnection.close();
                } catch (SQLException sqle) {
                    System.err.println("SQL Exception playdao4 insertplay");
                    sqle.printStackTrace();
                }


                chivato = true;
            }
        }
        return chivato;
    }

    public static boolean insertSaved(Board board, String uname) {
        List<Board> llistaboards = new ArrayList<>(PlayDAO.getSavedBoardsByUser(uname));
        boolean chivato = false;
        int idboard = 0;
        for (int i = 0; i < llistaboards.size(); i++) {
            if (board.equals(llistaboards.get(i))) {
                chivato = true;

            }

        }

        if (!chivato) {

            Connection dbconnection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            String sql1 = "SELECT id FROM board WHERE filename LIKE ?;";
            String sql2 = "INSERT INTO saved (id_board,uname) VALUES (?,?);"; //treure el insert del for i assegurarse que agafa be

            try {
                dbconnection = DriverManager.getConnection(DAOConstants.URL, DAOConstants.USER, DAOConstants.PASSWORD);
                preparedStatement = dbconnection.prepareStatement(sql1);
                preparedStatement.setString(1, board.getFilename());
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    idboard = resultSet.getInt("id");
                }

                resultSet.close();
                preparedStatement.close();
                dbconnection.close();
            } catch (SQLException sqle) {
                System.err.println("SQL Exception playdao insertsaved1");
                sqle.printStackTrace();
            }
            try {
                dbconnection = DriverManager.getConnection(DAOConstants.URL, DAOConstants.USER, DAOConstants.PASSWORD);
                preparedStatement = dbconnection.prepareStatement(sql2);
                preparedStatement.setInt(1, idboard);
                preparedStatement.setString(2, uname);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                dbconnection.close();

            } catch (SQLException sqle) {
                System.err.println("SQL Exception playdao insertsaved2");
                sqle.printStackTrace();
                return false;
            }

        }

        return true;
    }
}
