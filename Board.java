public class Board {
    public int size;
    public int currentPlayer;
    public boolean won;
    public boolean full;
    public Tile[][][] gameBoard;

    public Board(int size) {
        this.size = size;
        // currentPlayer == 0 if human is next.
        // currentPlayer == 1 if AI is next.
        this.currentPlayer = 0;
        this.won = false;
        this.full = false;
        this.gameBoard = new Tile[size][size][size];

        // fills 3D array of tile objects.
        for(int i = 0; i < this.size; i++) {
            for(int j = 0; j < this.size; j++) {
                for(int p = 0; p < this.size; p++) {
                    this.gameBoard[i][j][p] = new Tile();
                }
            }
        }
    }

    // sets up a new game.
    public void newGame() {
        for(int i = 0; i < this.size; i++) {
            for(int j = 0; j < this.size; j++) {
                for(int p = 0; p < this.size; p++) {
                    this.gameBoard[i][j][p] = new Tile();
                }
            }
        }
        this.won = false;
        this.full = false;
    }

    // allows human to place a tile.. humans have "X" as their symbol.
    public void humanMove(int i, int j, int p) {
        if(i > this.size-1 || j > this.size-1 || p > this.size-1) {
            return;
        }
        playerMove("X", i, j, p);
    }

    // allows an AI to place a tile.. AIs have "O" as their symbol.
    public void aiMove(int i, int j, int p) {
        if(i > this.size-1 || j > this.size-1 || p > this.size-1) {
            return;
        }
        playerMove("O", i, j, p);
    }

    // places the symbol onto the tile.. also checks if game is full or if someone won.
    public void playerMove(String symbol, int i, int j, int p) {
        if(this.gameBoard[i][j][p].getValue() == " ") {
            this.gameBoard[i][j][p].setValue(symbol);
            printBoard();
        }
        if(isGameFull()) {
            System.out.println("Tie!");
        }
        if(isGameWon(i, j, p)) {
            if(symbol == "O") {
                System.out.println("You Lost!");
            }
            if(symbol == "X") {
                System.out.println("You Won!");
            }
        }
    }

    // prints board
    public void printBoard() {
        for(int p = 0; p < this.size; p++) {
            for(int i = 0; i < this.size; i++) {
                for(int j = 0; j < this.size; j++) {
                    System.out.println(this.gameBoard[i][j][p]);
                }
                System.out.println(" ");
            }
            System.out.println(" ");
        }
    }

    // checks if all moves have been made.
    public boolean isGameFull() {
        for(int i = 0; i < this.size; i++) {
            for(int j = 0; j < this.size; j++) {
                for(int p = 0; p < this.size; p++) {
                    if(this.gameBoard[i][j][p].getValue() == " ") {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // checks if a row, column, diagonal, and 3D space has the same value.. if so, the game is finished.
    public boolean isGameWon(int i, int j, int p) {
        if(checkRow(j, p)) {
            return true;
        }
        if(checkCol(i, p)) {
            return true;
        }
        if(checkLeftDiag(p)) {
            return true;
        }
        if(checkRightDiag(p)) {
            return true;
        }
        if(check3DLeftDiag()) {
            return true;
        }
        if(check3DRightDiag()) {
            return true;
        }
        if(check3D(i, j)) {
            return true;
        }
        return false;
    }

    public boolean checkRow(int j, int p) {
        String val = this.gameBoard[0][j][p].getValue();
        for(int i = 1; i < this.size; i++) {
            if(this.gameBoard[i][j][p].getValue() != val) {
                return false;
            }
        }
        return true;
    }

    public boolean checkCol(int i, int p) {
        String val = this.gameBoard[i][0][p].getValue();
        for(int j = 1; j < this.size; j++) {
            if(this.gameBoard[i][j][p].getValue() != val) {
                return false;
            }
        }
        return true;
    }

    public boolean checkLeftDiag(int p) {
        int j = 1;
        String val = this.gameBoard[0][0][p].getValue();
        for(int i = 1; i < this.size; i++) {
            if(this.gameBoard[i][j][p].getValue() != val) {
                return false;
            }
            j++;
        }
        return true;
    }
    public boolean checkRightDiag(int p) {
        int j = 1;
        String val = this.gameBoard[this.size-1][0][p].getValue();
        for(int i = this.size-1; i >= 0; i--) {
            if(this.gameBoard[i][j][p].getValue() != val) {
                return false;
            }
            j++;
        }
        return true;
    }

    public boolean check3DLeftDiag() {
        int j = 0;
        int p = 0;
        String val = this.gameBoard[0][0][0].getValue();
        for(int i = 1; i < this.size; i++) {
            if(this.gameBoard[i][j][p].getValue() != val) {
                return false;
            }
            j++;
            p++;
        }

        return true;
    }
    public boolean check3DRightDiag() {
        int j = 0;
        int p = 0;
        String val = this.gameBoard[this.size-1][0][0].getValue();
        for(int i = this.size-2; i >= 0; i--) {
            if(this.gameBoard[i][j][p].getValue() != val) {
                return false;
            }
            j++;
            p++;
        }

        return true;
    }

    public boolean check3D(int i, int j) {
        String val = this.gameBoard[i][j][0].getValue();
        for(int p = 1; p < this.size; p++) {
            if(this.gameBoard[i][j][p].getValue() != val) {
                return false;
            }
        }
        return true;
    }





}