package inscaparrella.model;

import java.security.Key;
import java.util.*;

public class Player {

    private String uname;
    private Date creationDate;
    private int points;
    private Map<String, List<Board>> playedBoards;
    private Set<Board> savedBoards;


    public Player() {
        this.uname = "";
        this.creationDate = new GregorianCalendar().getTime();
        this.points = 0;
        this.playedBoards = new HashMap<>();
        this.savedBoards = new TreeSet<>();

    }

    public Player(Player player) {
        this.uname = player.uname;
        this.creationDate = player.creationDate;
        this.points = player.points;
        this.playedBoards = new HashMap<>();
        Set<String> keys = player.playedBoards.keySet();
        List<Board> playerBoard, newPlayerBoard;
        String level;
        Board tauler;
        Iterator<String> ita = keys.iterator();
        while (ita.hasNext()) {
            level = ita.next();
            playerBoard = player.playedBoards.get(level);
            newPlayerBoard = new LinkedList<>();

            Iterator<Board> itb = playerBoard.iterator();
            while (itb.hasNext()) {
                tauler = itb.next();
                newPlayerBoard.add(new Board(tauler));
            }

            this.playedBoards.put(level, newPlayerBoard);
        }
        this.savedBoards = new TreeSet<>();
        Iterator<Board> itera = player.savedBoards.iterator();
        Board board;
        while (itera.hasNext()) {
            board = itera.next();
            this.savedBoards.add(new Board(board));
        }
    }

    public String getUname() {
        return uname;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public int getPoints() {
        return points;
    }

    public Map<String, List<Board>> getPlayedBoards() {
        return playedBoards;
    }

    public Set<Board> getSavedBoards() {
        return savedBoards;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setPoints(int points) {
        this.points += points;
    }

    public void setPlayedBoards(List<Board> playedBoards) {
        List<Board> copia = new ArrayList<>();
        for (int i = 0; i < playedBoards.size(); i++) {
            copia.add(new Board(playedBoards.get(i)));
        }
        this.playedBoards.put(this.getUname(), copia);
    }


    public void setSavedBoards(Set<Board> savedBoards) {
        this.savedBoards = new TreeSet<>();
        Iterator<Board> ita = savedBoards.iterator();
        Board bord;
        while (ita.hasNext()) {
            bord = ita.next();
            this.savedBoards.add(new Board(bord));
        }
    }

    public boolean isBoardPlayed(Board board) {

        Set<String> llista = this.playedBoards.keySet();
        String guardar;
        List<Board> boards;
        Iterator<String> ita = llista.iterator();
        Board copia;
        while (ita.hasNext()) {
            guardar = ita.next();
            boards = playedBoards.get(guardar);
            Iterator<Board> ite = boards.iterator();
            while (ite.hasNext()) {
                copia = ite.next();
                if (copia.equals(board)) {
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public String toString() {
        String mostrar =
                "Dades jugador:\n" +
                        "Nom= " + this.getUname() + '\n' +
                        "Data de creació= " + this.getCreationDate() +
                        "\nPunts totals= " + this.getPoints() +
                        "\nPartides finalitzades=\n";
        Set<String> keys = this.playedBoards.keySet();
        List<Board> playerBoard;
        String level;
        Board tauler;
        Iterator<String> ita = keys.iterator();
        while (ita.hasNext()) {
            level = ita.next();
            playerBoard = this.playedBoards.get(level);
            Iterator<Board> itb = playerBoard.iterator();
            while (itb.hasNext()) {
                tauler = itb.next();
                mostrar += tauler.getFilename() + "\n";
            }
        }
        mostrar += "Partides guardades=\n";
        Iterator<Board> itera = this.savedBoards.iterator();
        Board board;
        while (itera.hasNext()) {
            board = itera.next();
            mostrar += board.getFilename() + "\n";
        }
        return mostrar;
    }
}
