import java.util.Random;

public class BombRandomizer {

	private static double BOMB_DENSITY = 0.15; // 0.15 => 15% of cells are bombs
	
	public static void placeBombs (LinkedGrid<Character> board, boolean fixedRandom, int seed) {
		Random rnd;
		if (fixedRandom) {
			rnd = new Random(seed); // Fixed seed.
		} else {
			rnd = new Random();
		}
		
		int numBombs = (int)Math.round(BOMB_DENSITY * board.getWidth() * board.getHeight());

		int b, bx = -1, by = -1;

		// Loop through for each bomb.
		for (b = 0; b < numBombs; b++) {
			// The while loop is necessary to ensure the randomly selected cell doesn't already have a bomb.
			while ((bx == -1 && by == -1) || board.getElement(bx, by).equals('x')) {
				bx = rnd.nextInt(board.getWidth());
				by = rnd.nextInt(board.getHeight());
			}
			// Place the bomb in (bx,by).
			board.setElement(bx, by, 'x');
		}
	}
	

}
