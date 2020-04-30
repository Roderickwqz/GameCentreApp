package csc207.fall2018.gamecentreapp.SubtractSquareGame;

public class MiniMaxTest {

    public static void main(String[] args) {
        SubtractSquareGame subtractSquareGame = new SubtractSquareGame("j", "i");
        System.out.println(subtractSquareGame.getCurrentState().getCurrentTotal());
        System.out.println(subtractSquareGame.getCurrentState().getPossibleMoves());
        MiniMaxNode miniMaxNode = new MiniMaxNode();

        int move = miniMaxNode.iterativeMiniMax(subtractSquareGame);
        System.out.println(move);
    }
}
