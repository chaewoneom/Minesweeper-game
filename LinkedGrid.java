/**
 * This class represents a 2D grid that is created as an array of singly linked lists.
 * @author CS1027 Chaewon Eom 
 *
 * @param <T> generic type
 */
public class LinkedGrid<T> {
	
	/**
	 * width of the grid
	 */
	private int width;
	
	/**
	 * height of the grid
	 */
	private int height;
	
	/**
	 * 1D array which contains the front nodes of each of the linked lists
	 */
	private LinearNode<T>[] grid;
	
	/**
	 * Constructor creates a grid with LinearNode objects and connects them as singly linked lists
	 * @param width width of the grid
	 * @param height height of the grid
	 */
	public LinkedGrid(int width, int height) {
		this.width = width;
		this.height = height;
		
		grid = new LinearNode[width];
		
		for(int i = 0; i < width; i++) {
			grid[i] = new LinearNode<T>();
			for(int j = 0; j < height - 1; j++) {
				LinearNode<T> node = new LinearNode<T>();
				if(grid[i].getNext() == null) {
					grid[i].setNext(node);
				}
				else {
					LinearNode<T> curr = grid[i].getNext();
					LinearNode<T> next = curr.getNext();
					while(next != null) {
						curr = next;
						next = curr.getNext();
					}
					curr.setNext(node);
				}
			}
		}
	}
	
	/**
	 * Method to set the element of the node to the given value
	 * @param col column of the grid
	 * @param row row of the grid
	 * @param data data value that is being set at the given column and row pair
	 * @throws LinkedListException exception thrown if the column or row 
	 * 		   					   	is outside the bounds of the grid
	 */
	public void setElement(int col, int row, T data) throws LinkedListException {
		if(col > width - 1 || row > height - 1 || col < 0 || row < 0) {
			throw new LinkedListException("out of bounds");
		}
		else {
			LinearNode<T> temp = grid[col];
			for(int i = 0; i < row; i++) {
				temp = temp.getNext();
			}
			temp.setElement(data);
		}
	}
	
	/**
	 * Method to return the element contained in the node at the given column and row pair
	 * @param col column of the grid
	 * @param row row of the grid
	 * @return elem element contained in the given node
	 * @throws LinkedListException exception thrown if the column or row 
	 * 		   					   	is outside the bounds of the grid
	 */
	public T getElement(int col, int row) throws LinkedListException {
		T elem;
		if(col > width - 1 || row > height - 1 || col < 0 || row < 0) {
			throw new LinkedListException("out of bounds");
		}
		else {
			LinearNode<T> temp = grid[col];
			for(int i = 0; i < row; i++) {
				temp = temp.getNext();
			}
			elem = temp.getElement();
		}
		return elem;
	}
	
	/**
	 * Getter method for width
	 * @return width width of the grid
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Getter method for height
	 * @return height height of the grid
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Method to format the entire grid of elements
	 */
	public String toString() {
		String res = "";

		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				if(i == 0) {
					res += (grid[j]).getElement() + "  ";
				}
				else {
					LinearNode<T> curr = grid[j];
					for(int k = 0; k < i; k++) {
						curr =  curr.getNext();
					}
					res += curr.getElement() + "  ";
				}
			} 
			res += "\n";
		}
		
		return res;
		
	}
}
