public class TrainAI {

    public TrainAI() {

    }

    public void train(int epochs, AI AIOne, AI AITwo) {
        // AIOne is winningCounts[0] and AITwo is winningCounts[1]
        int[] winningCounts = new int[2];
        winningCounts[0] = 0;
        winningCounts[1] = 0;

        for(int i = 0; i < epochs; i++) {
            Board newGame = new Board(4);
            while(newGame.won == false || newGame.full == false) {
                // simulate a move

                rewardOrPunish(newGame, AIOne, AITwo);
            }
            
        }
        // print utility values for each tile
    }

    public void rewardOrPunish(Board game, AI AIOne, AI AITwo) {
        if(game.currentPlayer == 0) {
            // if AIOne wins, reward it.
            AIOne.reward(1);
            AITwo.reward(-1);
        } else if(game.currentPlayer == 1) {
            // if AITwo wins, reward it
            AIOne.reward(-1);
            AITwo.reward(1);
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
        System.out.println("Enter number of epochs");
        int num = scan.nextInt();
        TrainAI trainer = new TrainAI();
        trainer.train(num, AIOne, AITwo);
        AIOne.exploration = 0.0;

        while(game.won == false || game.full == false) {
            // play game
        }
    }



}