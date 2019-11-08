public class Board {

    public Board(int size) {
        this.size = size;
        // currentPlayer == 0 if human is next.
        // currentPlayer == 1 if AI is next.
        this.currentPlayer = 0;
        this.won = false;
        this.full = false;
        this.gameBoard = new Tile[size][size][size];
        for(int i = 0; i < this.size; i++) {
            for(int j = 0; j < this.size; j++) {
                for(int p = 0; p < this.size; p++) {
                    this.gameBoard[i][j][p] = new Tile();
                }
            }
        }
    }

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

    public void printBoard() {

    }

    public boolean isGameFull() {
        // checks if all moves have been made.
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
    public boolean isGameWon() {
        // checks if a row, column, diagonal, and 3D space has the same value.. if so, the game is finished.
        if(checkRow()) {
            return true;
        }
        if(checkCol()) {
            return true;
        }
        if(checkDiag()) {
            return true;
        }
        if(check3D()) {
            return true;
        }
        return false;
    }

    public boolean checkRow() {
        return false;
    }

    public boolean checkCol() {
        return false;
    }

    public boolean checkDiag() {
        return false;
    }
    public boolean check3D() {
        return false;
    }




}