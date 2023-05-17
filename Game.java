import java.awt.Color;

/**
 * This class contains the Minesweeper related code.
 * @author CS1027 Chaewon Eom
 *
 */
public class Game {
	
	/**
	 * the game board containing Character type elements
	 */
	private LinkedGrid<Character> board;
	
	/**
	 * cells containing GUICell type elements
	 */
	private LinkedGrid<GUICell> cells;
	
	/**
	 * width of the game board
	 */
	public static int width;
	
	/**
	 * height of the game board
	 */
	public static int height;
	
	/**
	 * boolean variable to check if the game is being played or not
	 */
	private boolean isPlaying;
	
	/**
	 * graphical user interface that is used to play the game
	 */
	private GUI gui;
	
	/**
	 * Constructor creates a game board that has randomly placed bombs
	 * @param width width of the game board
	 * @param height height of the game board
	 * @param fixedRandom boolean variable used to decide if the board 
	 * 					  is going to be set with fixed seed or not
	 * @param seed number used to initialize pseudorandom number generator
	 */
	public Game(int width, int height, boolean fixedRandom, int seed) {
		Game.width = width;
		Game.height = height;
		
		board = new LinkedGrid<Character>(width, height);
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				board.setElement(i, j, '_');
			}
		}
		
		BombRandomizer.placeBombs(board, fixedRandom, seed);
		
		isPlaying = true;
		
		cells = new LinkedGrid<GUICell>(width, height);
		
		determineNumbers();
		
		gui = new GUI(this, cells);
	}
	
	/**
	 * Coonstructor creates a game board with given board which is already set
	 * @param board the game board
	 */
	public Game(LinkedGrid<Character> board) {
		this.board = board;
		width = board.getWidth();
		height = board.getHeight();
		isPlaying = true;
		cells = new LinkedGrid<GUICell>(width, height);
		determineNumbers();
		gui = new GUI(this, cells);
	}
	
	/**
	 * Getter method for width
	 * @return width width of the board
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Getter method for height
	 * @return height height of the board
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Getter method for cells
	 * @return cells cells containing GUICell type elements
	 */
	public LinkedGrid<GUICell> getCells() {
		return cells;
	}
	
	/**
	 * Method to determine how many bombs are in surrounding cells,
	 * and insert the number into the corresponding node in the cells grid
	 */
	public void determineNumbers() {
		int count = 0;
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				GUICell cell = new GUICell(j, i);
				
				if(board.getElement(i, j) == 'x') {   
					cell.setNumber(-1);
					cells.setElement(i, j, cell);
					continue;
				}
				
				for(int k = i-1; k <= i+1; k++) {             
					for(int l = j-1; l <= j+1; l++) {           
						if((k==i && l==j) || k < 0 || l < 0 ||
							k > width-1 || l > height-1) {
							continue;
						}
						else if(board.getElement(k,l) == 'x') {
							count++;
						}										
					}
				}
				cell.setNumber(count);
				cells.setElement(i, j, cell);
				count = 0;
			}
		}
		
	}
	
	/**
	 * Method to process a cell being clicked or simulated click,
	 * and returns how many cells that are being revealed 
	 * @param col column of the board grid
	 * @param row row of the board grid
	 * @return int value representing how many cells that are being revealed
	 * 		   (-1 if a bomb is revealed or -10 if a bomb has previously been revealed)
	 */
	public int processClick(int col, int row) {
		GUICell cell = cells.getElement(col, row);
		
		if(!isPlaying) {
			return -10;
		}
		
		if(cell.getNumber() == -1) {
			cell.setBackground(Color.red);
			cell.reveal();
			isPlaying = false;
			return -1;
		}
		
		if(cell.getNumber() == 0) {
			return recClear(col, row);
		}
		
		if(cell.getNumber() > 0) {
			if(cell.isRevealed()) {
				return 0;
			}
			cell.reveal();
			cell.setBackground(Color.white);
		}
		return 1;
	}
	
	/**
	 * Helper method to return an int value representing the number of cells being revealed 
	 * @param col column of the board grid
	 * @param row row of the board grid
	 * @return int value representing the number of cells being revealed
	 */
	private int recClear(int col, int row) {
		int result = 0;
		
		if(col < 0 || row < 0 || col > width-1 || row > height-1) {
			return 0;
		}
		
		GUICell cell = cells.getElement(col, row);
		
		if(cell.isRevealed()) {
			return 0;
		}
		
		if(cell.getNumber() == -1) {
			return 0;
			
		}
		else if(cell.getNumber() > 0) {
			cell.reveal();
			if(gui != null) {
				cell.setBackground(Color.white);
			}
			return 1;
		}
		else {
			cell.reveal();
			if(gui != null) {
				cell.setBackground(Color.white);
				result = 1;
				result += recClear(col-1, row);
				result += recClear(col+1, row);
				result += recClear(col, row-1);
				result += recClear(col, row+1);
				result += recClear(col-1, row-1);
				result += recClear(col+1, row-1);
				result += recClear(col-1, row+1);
				result += recClear(col+1, row+1);
			}
			return result;
		}
	}
}