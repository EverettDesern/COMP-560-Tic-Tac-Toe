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
    public boolean isGameWon(int i, int j, int p) {
        // checks if a row, column, diagonal, and 3D space has the same value.. if so, the game is finished.
        if(checkRow(j, p)) {
            return true;
        }
        if(checkCol(i, p)) {
            return true;
        }
        if(checkDiag(i, j, p)) {
            return true;
        }
        if(check3D(i, j)) {
            return true;
        }
        return false;
    }

    public boolean checkRow(int j, int p) {
        String val = this.gameBoard[0][j][p];
        for(int i = 1; i < this.size; i++) {
            if(this.gameBoard[i][j][p] != val) {
                return false;
            }
        }
        return true;
    }

    public boolean checkCol(int i, int p) {
        String val = this.gameBoard[i][0][p];
        for(int j = 1; j < this.size; j++) {
            if(this.gameBoard[i][j][p] != val) {
                return false;
            }
        }
        return true;
    }

    public boolean checkDiag(int i, int j, int p) {
        // need to implement this..
        // we need to check right diag, left diag, and 3D diags.
        return false;
    }

    public boolean check3D(int i, int j) {
        String val = this.gameBoard[i][j][0];
        for(int p = 1; p < this.size; p++) {
            if(this.gameBoard[i][j][p] != val) {
                return false;
            }
        }
        return true;
    }





}