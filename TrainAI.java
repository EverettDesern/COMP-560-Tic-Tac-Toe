import java.util.Scanner;

public class TrainAI {

    public TrainAI() {

    }

    // this method trains the AI by simulating an epoch amount of games.
    public void train(int epochs, AI AIOne, AI AITwo) {
        // AIOne is winningCounts[0] and AITwo is winningCounts[1]
        int[] winningCounts = new int[2];
        winningCounts[0] = 0;
        winningCounts[1] = 0;

        for(int i = 0; i < epochs; i++) {
            Board newGame = new Board(4);
            while(newGame.won == 0 && newGame.full == false) {
                // simulate a move.. need to implement
                newGame.currentPlayer = 0;
                Point coordinates = AIOne.selectMove(newGame);
                int e = coordinates.i;
                int j = coordinates.j;
                int p = coordinates.p;
                newGame.humanMove(e, j, p);
                //System.out.println(AIOne.states.size());
                if(!(newGame.won == 0 && newGame.full == false)) {
                    rewardOrPunish(newGame, AIOne, AITwo);
                    break;
                }
                newGame.currentPlayer = 1;
                Point coordinates2 = AITwo.selectMove(newGame);
                int t = coordinates2.i;
                int b = coordinates2.j;
                int c = coordinates2.p;
                newGame.aiMove(t,b,c);
                if(!(newGame.won == 0 && newGame.full == false)) {
                    rewardOrPunish(newGame, AIOne, AITwo);
                    break;
                }
            }
            //System.out.println("AIOne wins: " + AIOne.wins + " AITwo win: " + AITwo.wins);
            //if(i == epochs-2) {
                //AIOne.print = true;
                //for (int s = 0; s < 4; s++) {
                    //for (int j = 0; j < 4; j++) {
                        //for (int p = 0; p < 4; p++) {
                            //if(AIOne.value[s][j][p] > 0) {
                                //System.out.print(AIOne.value[s][j][p]);
                                //System.out.print(" ");
                            //}
                        //}
                        //System.out.println(" ");
                    //}
                    //System.out.println(" ");
                //}
            //}
        }
        // print utility values for each tile.. need to implement
    }

    // this method checks which AI has won and rewards/punishes the AIs.
    public void rewardOrPunish(Board game, AI AIOne, AI AITwo) {
        if(game.won == 1) {
            // if AIOne wins, reward it.
            AIOne.reward(1);
            AITwo.reward(-1);
            AIOne.wins += 1;
        } else if(game.won == 2) {
            // if AITwo wins, reward it
            AIOne.reward(-1);
            AITwo.reward(1);
            AITwo.wins += 1;
        } else {
            // if no one wins, nothing happens
            AIOne.reward(0);
            AITwo.reward(0);
        }
    }

    public static void main(String[] args) {
        // player 0
        AI AIOne = new AI();
        // player 1
        AI AITwo = new AI();
        Board game = new Board(4);
        //game.humanMove(0,0,0);
        //game.humanMove(1,1,1);
        //game.humanMove(2,2,2);
        //game.humanMove(3,3,3);
        //game.printBoard();

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter number of epochs");
        int num = scan.nextInt();
        TrainAI trainer = new TrainAI();
        trainer.train(num, AIOne, AITwo);

        // once we reach here, we're done training and are ready to play against a human.
        AIOne.exploration = 0.0;
        AIOne.print = true;
        Board humanGame = new Board(4);
        // while game is not full or won, play the game.
        while(humanGame.won == 0 && humanGame.full == false) {
            humanGame.currentPlayer = 0;
            Point coordinates = AIOne.selectMove(humanGame);
            int e = coordinates.i;
            int f = coordinates.j;
            int g = coordinates.p;
            humanGame.aiMove(e, f, g);
            if(!(humanGame.won == 0 && humanGame.full == false)) {
                break;
            }
            // play game
            humanGame.currentPlayer = 1;
            System.out.print("Enter coordinate i: ");
            int i = scan.nextInt();
            System.out.print("Enter coordinate j: ");
            int j = scan.nextInt();
            System.out.print("Enter coordinate p: ");
            int p = scan.nextInt();
            humanGame.humanMove(i,j,p);
            if(!(humanGame.won == 0 && humanGame.full == false)) {
                break;
            }
    }
    scan.close();
}



}