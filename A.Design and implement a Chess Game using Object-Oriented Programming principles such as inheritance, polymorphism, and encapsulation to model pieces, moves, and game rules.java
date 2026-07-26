import java.util.*;

public class ChessGame {

    // Helper to convert algebraic notation (e.g., "e2") to board array coordinates
    public static int[] parseAlgebraic(String pos) {
        if (pos == null || pos.length() != 2) return null;
        char colChar = Character.toLowerCase(pos.charAt(0));
        char rowChar = pos.charAt(1);

        int col = colChar - 'a';
        int row = 8 - (rowChar - '0');

        if (col < 0 || col > 7 || row < 0 || row > 7) return null;
        return new int[]{row, col};
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Game game = new Game();

        System.out.println("=== Chess Game (Console Version) ===");
        System.out.println("Initial Board Setup:");
        game.getBoard().printBoard();

        // Sample interactive CLI loop following the algorithm
        while (!game.isEnd()) {
            Player current = game.getTurn();
            String colorStr = current.isWhite() ? "White" : "Black";
            System.out.println("\n" + colorStr + "'s turn.");
            System.out.print("Enter move (e.g., e2 e4): ");

            if (!sc.hasNext()) break;
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit")) {
                System.out.println("Game terminated by user.");
                break;
            }

            String[] parts = input.split("\\s+");
            if (parts.length != 2) {
                System.out.println("Error: Invalid format! Use notation like 'e2 e4'.");
                continue;
            }

            int[] start = parseAlgebraic(parts[0]);
            int[] end = parseAlgebraic(parts[1]);

            if (start == null || end == null) {
                System.out.println("Error: Invalid square coordinates.");
                continue;
            }

            boolean success = game.playerMove(current, start[0], start[1], end[0], end[1]);
            if (success) {
                game.getBoard().printBoard();
            }
        }
        sc.close();
    }
}

class Spot {
    private int x;
    private int y;
    private Piece piece;

    public Spot(int x, int y, Piece piece) {
        this.x = x;
        this.y = y;
        this.piece = piece;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public Piece getPiece() { return piece; }
    public void setPiece(Piece piece) { this.piece = piece; }
}

abstract class Piece {
    private boolean white;
    private boolean killed = false;

    public Piece(boolean white) {
        this.white = white;
    }

    public boolean isWhite() { return white; }
    public boolean isKilled() { return killed; }
    public void setKilled(boolean killed) { this.killed = killed; }

    public abstract String getSymbol();
    public abstract String getName();

    // Polymorphic move validation
    public abstract boolean canMove(Board board, Spot start, Spot end);
}

class Pawn extends Piece {
    public Pawn(boolean white) { super(white); }

    @Override
    public String getSymbol() { return isWhite() ? "P" : "p"; }

    @Override
    public String getName() { return "Pawn"; }

    @Override
    public boolean canMove(Board board, Spot start, Spot end) {
        if (end.getPiece() != null && end.getPiece().isWhite() == this.isWhite()) {
            return false; // Cannot capture own piece
        }

        int direction = isWhite() ? -1 : 1;
        int dx = end.getX() - start.getX();
        int dy = Math.abs(end.getY() - start.getY());

        if (dy == 0 && dx == direction && end.getPiece() == null) {
            return true;
        }
        int startRank = isWhite() ? 6 : 1;
        if (dy == 0 && start.getX() == startRank && dx == 2 * direction) {
            int intermediateX = start.getX() + direction;
            if (board.getSpot(intermediateX, start.getY()).getPiece() == null && end.getPiece() == null) {
                return true;
            }
        }
        if (dy == 1 && dx == direction && end.getPiece() != null) {
            return true;
        }
        if (dy == 1 && dx == direction && end.getPiece() == null) {
            System.out.println("Error: Pawn cannot move diagonally without capturing.");
            return false;
        }

        return false;
    }
}

class Rook extends Piece {
    public Rook(boolean white) { super(white); }

    @Override
    public String getSymbol() { return isWhite() ? "R" : "r"; }

    @Override
    public String getName() { return "Rook"; }

    @Override
    public boolean canMove(Board board, Spot start, Spot end) {
        if (end.getPiece() != null && end.getPiece().isWhite() == this.isWhite()) return false;
        if (start.getX() != end.getX() && start.getY() != end.getY()) return false;
        return isPathClear(board, start, end);
    }

    private boolean isPathClear(Board board, Spot start, Spot end) {
        int xStep = Integer.compare(end.getX(), start.getX());
        int yStep = Integer.compare(end.getY(), start.getY());
        int currX = start.getX() + xStep;
        int currY = start.getY() + yStep;

        while (currX != end.getX() || currY != end.getY()) {
            if (board.getSpot(currX, currY).getPiece() != null) return false;
            currX += xStep;
            currY += yStep;
        }
        return true;
    }
}

class Knight extends Piece {
    public Knight(boolean white) { super(white); }

    @Override
    public String getSymbol() { return isWhite() ? "N" : "n"; }

    @Override
    public String getName() { return "Knight"; }

    @Override
    public boolean canMove(Board board, Spot start, Spot end) {
        if (end.getPiece() != null && end.getPiece().isWhite() == this.isWhite()) return false;
        int dx = Math.abs(start.getX() - end.getX());
        int dy = Math.abs(start.getY() - end.getY());
        return dx * dy == 2;
    }
}

class Bishop extends Piece {
    public Bishop(boolean white) { super(white); }

    @Override
    public String getSymbol() { return isWhite() ? "B" : "b"; }

    @Override
    public String getName() { return "Bishop"; }

    @Override
    public boolean canMove(Board board, Spot start, Spot end) {
        if (end.getPiece() != null && end.getPiece().isWhite() == this.isWhite()) return false;
        if (Math.abs(start.getX() - end.getX()) != Math.abs(start.getY() - end.getY())) return false;

        int xStep = Integer.compare(end.getX(), start.getX());
        int yStep = Integer.compare(end.getY(), start.getY());
        int currX = start.getX() + xStep;
        int currY = start.getY() + yStep;

        while (currX != end.getX() && currY != end.getY()) {
            if (board.getSpot(currX, currY).getPiece() != null) return false;
            currX += xStep;
            currY += yStep;
        }
        return true;
    }
}

class Queen extends Piece {
    public Queen(boolean white) { super(white); }

    @Override
    public String getSymbol() { return isWhite() ? "Q" : "q"; }

    @Override
    public String getName() { return "Queen"; }

    @Override
    public boolean canMove(Board board, Spot start, Spot end) {
        Rook dummyRook = new Rook(isWhite());
        Bishop dummyBishop = new Bishop(isWhite());
        return dummyRook.canMove(board, start, end) || dummyBishop.canMove(board, start, end);
    }
}

class King extends Piece {
    public King(boolean white) { super(white); }

    @Override
    public String getSymbol() { return isWhite() ? "K" : "k"; }

    @Override
    public String getName() { return "King"; }

    @Override
    public boolean canMove(Board board, Spot start, Spot end) {
        if (end.getPiece() != null && end.getPiece().isWhite() == this.isWhite()) return false;
        int dx = Math.abs(start.getX() - end.getX());
        int dy = Math.abs(start.getY() - end.getY());
        return dx <= 1 && dy <= 1;
    }
}

class Board {
    private Spot[][] spots = new Spot[8][8];

    public Board() {
        resetBoard();
    }

    public Spot getSpot(int x, int y) {
        if (x < 0 || x > 7 || y < 0 || y > 7) return null;
        return spots[x][y];
    }

    public void resetBoard() {
        // Initialize Black pieces (Top: ranks 8 and 7 -> rows 0 and 1)
        spots[0][0] = new Spot(0, 0, new Rook(false));
        spots[0][1] = new Spot(0, 1, new Knight(false));
        spots[0][2] = new Spot(0, 2, new Bishop(false));
        spots[0][3] = new Spot(0, 3, new Queen(false));
        spots[0][4] = new Spot(0, 4, new King(false));
        spots[0][5] = new Spot(0, 5, new Bishop(false));
        spots[0][6] = new Spot(0, 6, new Knight(false));
        spots[0][7] = new Spot(0, 7, new Rook(false));

        for (int j = 0; j < 8; j++) {
            spots[1][j] = new Spot(1, j, new Pawn(false));
        }
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                spots[i][j] = new Spot(i, j, null);
            }
        }
        for (int j = 0; j < 8; j++) {
            spots[6][j] = new Spot(6, j, new Pawn(true));
        }

        spots[7][0] = new Spot(7, 0, new Rook(true));
        spots[7][1] = new Spot(7, 1, new Knight(true));
        spots[7][2] = new Spot(7, 2, new Bishop(true));
        spots[7][3] = new Spot(7, 3, new Queen(true));
        spots[7][4] = new Spot(7, 4, new King(true));
        spots[7][5] = new Spot(7, 5, new Bishop(true));
        spots[7][6] = new Spot(7, 6, new Knight(true));
        spots[7][7] = new Spot(7, 7, new Rook(true));
    }

    public void printBoard() {
        for (int i = 0; i < 8; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < 8; j++) {
                Piece p = spots[i][j].getPiece();
                if (p == null) {
                    System.out.print(". ");
                } else {
                    System.out.print(p.getSymbol() + " ");
                }
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }
}

class Move {
    private Player player;
    private Spot start;
    private Spot end;
    private Piece pieceMoved;
    private Piece pieceKilled;

    public Move(Player player, Spot start, Spot end) {
        this.player = player;
        this.start = start;
        this.end = end;
        this.pieceMoved = start.getPiece();
    }

    public Spot getStart() { return start; }
    public Spot getEnd() { return end; }
    public Piece getPieceMoved() { return pieceMoved; }
    public Piece getPieceKilled() { return pieceKilled; }
    public void setPieceKilled(Piece pieceKilled) { this.pieceKilled = pieceKilled; }
}

class Player {
    private boolean whiteSide;

    public Player(boolean whiteSide) {
        this.whiteSide = whiteSide;
    }

    public boolean isWhite() { return whiteSide; }
}

class Game {
    private Player[] players = new Player[2];
    private Board board;
    private Player currentTurn;
    private List<Move> movesPlayed = new ArrayList<>();
    private boolean isEnd = false;

    public Game() {
        players[0] = new Player(true);  // White
        players[1] = new Player(false); // Black
        board = new Board();
        currentTurn = players[0];
    }

    public Board getBoard() { return board; }
    public Player getTurn() { return currentTurn; }
    public boolean isEnd() { return isEnd; }

    public boolean playerMove(Player player, int startX, int startY, int endX, int endY) {
        Spot startSpot = board.getSpot(startX, startY);
        Spot endSpot = board.getSpot(endX, endY);
        Piece currPiece = startSpot.getPiece();

        if (currPiece == null) {
            System.out.println("Error: No piece at source position.");
            return false;
        }

        if (currPiece.isWhite() != player.isWhite()) {
            System.out.println("Error: You can only move your own pieces.");
            return false;
        }

        if (!currPiece.canMove(board, startSpot, endSpot)) {
            return false;
        }
        Move move = new Move(player, startSpot, endSpot);
        Piece destPiece = endSpot.getPiece();

        if (destPiece != null) {
            destPiece.setKilled(true);
            move.setPieceKilled(destPiece);
        }

        movesPlayed.add(move);
        endSpot.setPiece(currPiece);
        startSpot.setPiece(null);
        String startNotation = "" + (char)('a' + startY) + (8 - startX);
        String endNotation = "" + (char)('a' + endY) + (8 - endX);
        System.out.println(currPiece.getName() + " moved from " + startNotation + " to " + endNotation + ".");
        if (destPiece instanceof King) {
            System.out.println("Checkmate!");
            System.out.println((player.isWhite() ? "White" : "Black") + " wins the game.");
            this.isEnd = true;
            return true;
        }
        this.currentTurn = (this.currentTurn == players[0]) ? players[1] : players[0];
        return true;
    }
}
