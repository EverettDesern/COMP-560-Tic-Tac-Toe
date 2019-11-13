import java.util.Scanner;

public class TrainAI {
    public int num;
    public int num2;
    public int num3;

    public TrainAI() {
        num = 0;
        num2 = 0;
        num3 = 0;

    }

    // this method trains the AI by simulating an epoch amount of games.
    public void train(int epochs, AI AIOne, AI AITwo) {
        // AIOne is winningCounts[0] and AITwo is winningCounts[1]
        int[] winningCounts = new int[2];
        winningCounts[0] = 0;
        winningCounts[1] = 0;

        for(int i = 0; i < epochs; i++) {
            Board newGame = new Board(4);
            if(i == this.num || i == this.num2 || i == this.num3-1) {
                if(i == this.num) {
                    System.out.println("-------------------------------------");
                    System.out.println("TRAINING NUMBER: " + this.num);
                    System.out.println("-------------------------------------");
                }
                if(i == this.num2) {
                    System.out.println("-------------------------------------");
                    System.out.println("TRAINING NUMBER: " + this.num2);
                    System.out.println("-------------------------------------");
                }
                if(i == this.num3-1) {
                    System.out.println("-------------------------------------");
                    System.out.println("TRAINING NUMBER: " + this.num3);
                    System.out.println("-------------------------------------");
                }
                AIOne.printValues(newGame);
            }
            while(newGame.won == 0 && newGame.full == false) {
                newGame.currentPlayer = 0;
                Point coordinates = AIOne.selectMove(newGame);
                int e = coordinates.i;
                int j = coordinates.j;
                int p = coordinates.p;
                newGame.humanMove(e, j, p);
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
        }
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

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter three numbers for the amount of training");
        TrainAI trainer = new TrainAI();
        trainer.num = scan.nextInt();
        trainer.num2 = scan.nextInt();
        trainer.num3 = scan.nextInt();
        trainer.train(trainer.num3, AIOne, AITwo);

        // once we reach here, we're done training and are ready to play against a human.
        AIOne.exploration = 0.0;
        //AIOne.print = true;
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