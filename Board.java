//------------------------------------------------------------------//
// Board.java                                                       //
//                                                                  //
// Class used to represent a 2048 game board                        //
//                                                                  //
// Author:  Jiawei Zhou		cs8bffb                            //
// Date:    10/18/16                                                //
//------------------------------------------------------------------//

/**
 * Sample Board
 * <p/>
 * 0   1   2   3
 * 0   -   -   -   -
 * 1   -   -   -   -
 * 2   -   -   -   -
 * 3   -   -   -   -
 * <p/>
 * The sample board shows the index values for the columns and rows
 * Remember that you access a 2D array by first specifying the row
 * and then the column: grid[row][column]
 *
 *
 *
 *
 *
 *
 */

import java.util.*;
import java.io.*;
/*
 *@Describe: this java contains constructor and method 
 *1it fist creat constructor then it add a save board method
 * it can save the board
 *2 then it add a new tile after each move
 *3 roatate the board
 *4 check the input is correct
 *5 make it a string
 *6 check if the numbre can move or not
 *7 move the number and combine the same number
 *
 *
 *
 *
 * */
public class Board {
	public final int NUM_START_TILES = 2;
	public final int TWO_PROBABILITY = 90;
	public final int GRID_SIZE;


	private final Random random;
	private int[][] grid;
	private int score;


	// Constructs a fresh board with random tiles
	public Board(int boardSize, Random random) {
		this.random = random; //set the random 
		this.score = score;//set score
		this.GRID_SIZE = boardSize; //set grid size
		this.grid = new int[GRID_SIZE][GRID_SIZE];
		this.addRandomTile();//add two random tile in to grid
		this.addRandomTile();

	}


	// Construct a board based off of an input file
	public Board(String inputBoard, Random random) throws IOException {
		this.random = random; 
		//scan the input file
		Scanner input = new Scanner (new File(inputBoard));
		//set some based variable into board, score, grid, 
		this.GRID_SIZE = input.nextInt();
		this.score = input.nextInt();
		this.grid = new int[GRID_SIZE][GRID_SIZE];
		//loop through the grid
		for(int row = 0; row < GRID_SIZE; row++){
			for(int column = 0; column < GRID_SIZE;
					column ++){
				//set the int into m grid
				this.grid[row][column] = input.nextInt();
			}
		}
	}


	/*
	 * Name: saveBoard(String outputBoard) throws IOException 
	 *Purpose: to save my board into a file 
	 *Parameter: String objectwhich
	 *no return
	 * */
	public void saveBoard(String outputBoard) throws IOException {
		//create a file in order to save
		File file = new File(outputBoard);
		//create a printwriter
		PrintWriter result = new PrintWriter(file);
		//print the things in the board
		result.println(this.GRID_SIZE);
		result.println(this.score);
		for(int row3 = 0; row3 < this.GRID_SIZE; row3++){
			for(int column3 = 0; column3 < this.GRID_SIZE;
					column3++ ){
				result.print(grid[row3][column3] + " ");
			}
			result.println();
		}
		//close the file
		result.close();

	}

	/*
	 *Describe:Adds a random tile (of value 2 or 4) to a
	 *random empty space on the board
	 *Parameter: no
	 * no return
	 * */
	public void addRandomTile() {
		//set a count
		int count = 0;
		//loop through the grid and count the number of 0
		for (int row = 0;row < this.GRID_SIZE;row ++ ){
			for (int column = 0; column < this.GRID_SIZE;
					column ++){
				if ( this.grid[row][column]== 0){
					count++;

				}
			}
		}
		//if there is no 0, then return 
		if (count == 0 ){
			return;
		}
		//set a spot which is a random number between 0 and count-1
		int spot = random.nextInt(count);
		//create a value which is a random number between 0-99
		int value = random.nextInt(100);
		//create the location
		int location = 0;
		//loop through the grid again
		for (int row2 = 0;row2 < this.GRID_SIZE;row2 ++ ){
			for (int column2 = 0; column2 < this.GRID_SIZE;
					column2 ++){
				//if hit a 0 point
				if(this.grid[row2][column2]==0){
					//if location equal to spot 
					if(location == spot){
						//within the prob if add 2, 
						//then add 2
						if(value < TWO_PROBABILITY){
							this.grid[row2][column2]=2;
						}
						//add 4
						else{
							this.grid[row2][column2]=4;
						}
					}	
					//increase location if not hit
					location++;
				}
			}
		}
	}


	/*
	 *Describe:Rotates the board by 90 degrees clockwise or 
	 *90 degrees counter-clockwise
	 *If rotateClockwise == true, rotates the board clockwise , else rotates
	 *the board counter-clockwise
	 *Parameter: no
	 * no return
	 * */
	public void rotate(boolean rotateClockwise) {
		//create a new int array

		int[][] source = new int [GRID_SIZE][GRID_SIZE];
		//loop through the array in board
		for(int rowIndex = 0; rowIndex < this.GRID_SIZE; rowIndex++){
			for(int columnIndex = 0; columnIndex < this.GRID_SIZE;
					columnIndex++ ){
				//copy the whole array
				source[rowIndex][columnIndex] = 
					this.grid[rowIndex][columnIndex];
			}
		}
		//if rotateclockwise 
		if (rotateClockwise == true){
			//loop through the grid
			for (int row = 0;row < this.GRID_SIZE;row ++ ){
				for (int column = 0; column < this.GRID_SIZE;
						column ++){
					//give a new number to it 
					this.grid[row][column]= 
					source[this.GRID_SIZE - column-1][row];
				}
			}
		}
		//rotate counterclockwise
		else{
			//loop through the grid
			for (int row = 0;row < GRID_SIZE;row ++ ){
				for (int column = 0; column < GRID_SIZE;
						column ++){
					//set a new number to grid
					this.grid[row][column]= 
					source[column][this.GRID_SIZE - row-1];
				}
			}
		}
	}
	/*
 	 * Describe:
	 *Complete this method ONLY if you want 
	 *to attempt at getting the extra credit
	 *Returns true if the file to be read is 
	 *in the correct format, else return false
	 *Parameter : String
	 *return true or false
	 */
	public static boolean isInputFileCorrectFormat(String inputFile) {
		//The try and catch block are used to handle any exceptions
		//Do not worry about the details, just write all 
		//your conditions inside the
		//try block
		try {
			//write your code to check for all conditions 
			//and return true if it satisfies
			//all conditions else return false
			return true;
		} catch (Exception e) {
			return false;
		}
	}






	/*Name : canmoveLeft()
 	*@Describe: this method check 2 situations, first is find out if there
 	*is non zero number on right of a 0, second is is there 2 same nonzero
 	*number in same row.
	*@Parameter: none
	*@return: true or false, which means can or cannot move
 	*/
	private boolean canmoveLeft(){
		// create two counting number
		int countZero = 0;
		int countSame = 0;
		//loop through the row
		for(int row = 0; row < GRID_SIZE; row ++){
			//loop throught column, -1 in case of out bound
			for (int column = 0; column < GRID_SIZE-1; column++){
				//if there is a non zero number in right side 
				//of this 0, couuntZero increase
				if (this.grid[row][column]==0){
					if(this.grid[row][column+1] !=0){
						countZero++;
					}

				}
			}
			//loop through the column
			for (int column = 0; column <GRID_SIZE-1; column++){
				//if there is a same nonzero number on right 
				//side of this number, countSame imcrease
				if(this.grid[row][column]==
					this.grid[row][column+1]&&
					this.grid[row][column]!=0){
					countSame++;
				}
			}



		}
		//if there is a 0 on left side of this nonzero number,
		//and this row contain same number
		if (countZero != 0 || countSame != 0){
			//return true
			return true;
		}
		//if not, cannot move left
		else{
			return false;
		}


	}
	/*@Name: canMove()
 	* @Describe: Use the can move method and rotate method above to check
 	* wheather this board can move up down or right
 	*@Parameter: Direction
 	*@return: boolean, which means can or cannot move
	* 	
	*/
	public boolean canMove(Direction direction){
		//can move left?
		if(direction.equals(Direction.LEFT)){ 
			//get whether can move left
			boolean deter = this.canmoveLeft();
			//return
			return deter;
		}
		//can move right?
		if(direction.equals(Direction.RIGHT)){
			//rotate twice and check whether can move left
			//which is equal to whether can move right 
			this.rotate(true);
			this.rotate(true);
			//get can move right or not
			boolean deter = this.canmoveLeft();
			this.rotate(false);
			this.rotate(false);
			//return boolean
			return deter;

		}
		//can move up?
		if(direction.equals(Direction.UP)){
			//rotate counterclockwise 90, and check move left
			//which is equals to if can move up or not
			this.rotate(false);
			//get the boolean if can move up or not
			boolean deter = this.canmoveLeft();
			this.rotate(true);
			//return this boolean
			return deter;
		}
		//can move down?
		if(direction.equals(Direction.DOWN)){ 
			//rotate clockwise 90, and check move left
			//which is same as if can move down or not
			this.rotate(true);
			//get this boolean 
			boolean deter = this.canmoveLeft();
			this.rotate(false);
			//return boolean
			return deter;
		}
		//not in the situation above, then return false
		else
			return false;
	}
	/*@Name: moveLeft()
 	*@Describe: this is the most important part of this game, because
 	*we need to move the board.In this method, I make it possibe to 
 	*let the number in board move to left.
 	*@Parameter: no parameter
	*@Return: true or false, which can move or not
	* */
	private boolean moveLeft(){
		//if we find that we can move to left
		if (this.canmoveLeft()==true){
			//loop through all the grid!
			for (int index = 0; index < GRID_SIZE*GRID_SIZE; 
								index++){
			   //loop through row
			   for(int row = 0; row< GRID_SIZE; row++){
			      //loop through column
			      for (int column = 0; 
			        column <GRID_SIZE-1; column ++){
			           //if the number is 0 and 
			           //the next one is not
				     if (this.grid[row][column]==0 
				       && this.grid[row][column+1]!= 0){
					//switch these two number position 
				        this.grid[row][column] = 
						this.grid[row][column +1];
						this.grid[row][column+1] = 0;
						break;
						}
					}
				}
			}
			//loop through the row again
			for(int row = 0; row < GRID_SIZE; row++){
				//loop through the column
				for(int column = 0; column < GRID_SIZE-1 
								; column++){
					//check if this number is
					//same as the next one
					if (this.grid[row][column]==
						this.grid[row][column+1]){
						//if they are same, then
						//add them together
						//update the score by rule
						this.score += 
						(this.grid[row][column]*2);
						this.grid[row][column]+=
							this.grid[row][column];
						this.grid[row][column+1]=0;
						
					}
				}
			}	

			//loop throught all the grid again 
			for(int index =0; index< GRID_SIZE*GRID_SIZE;index ++){
				//loop throught the grid once
				for(int row = 0; row < GRID_SIZE; row++){
					for (int column = 0; column < 
							GRID_SIZE-1;column++){
						//if I find a 0, then I seitch 
						//the place of this number 
						//and the next one
						if(this.grid[row][column]==0
						&&this.grid[row][column+1]!=0){
						    this.grid[row][column]
						    =this.grid[row][column+1];
						    this.grid[row][column+1]=0;
							break;
						}		
					}
				}
			}
			//It can move left
			return true;
		}
		//if it cannot return false
		else {
			return false;
		}
	}
	/*@Name: move()
 	*@Describe: this is the movement method of this game, it is base on 
 	*rotate method and move to left method
 	*@Parameter: direction
 	*@Return: boolean can it move or not
 	* */
	public boolean move(Direction direction) {
		//move left
		if(direction.equals(Direction.LEFT)){
			//call the method
			this.moveLeft();
			return true;
		}
		//move right
		if(direction.equals(Direction.RIGHT)){
			//rotate twice and move left 
			//it is same as move right
			this.rotate(true);
			this.rotate(true);
			//move to left
			this.moveLeft();
			// change this board back to orginal
			this.rotate(false);
			this.rotate(false);
			return true;

		}
		//move up
		if(direction.equals(Direction.UP)){
			//move the board counterclock wise
			//then move left, which is same as move up
			this.rotate(false);
			this.moveLeft();
			//change this board back to orginal one
			this.rotate(true);
			return true;
		}
		//move down 
		if(direction.equals(Direction.DOWN)){
			//rotate this board clockwise
			this.rotate(true);
			//move to left
			this.moveLeft();
			this.rotate(false);
			return true;
		}
		else {return false;}
	}
	/*@Name:isGameOver()
	 * @Describe: check is it game over, useing canmoveLeft method
	 *@parameter: none
	 *@return: boolean, game over or not
	 * */
	public boolean isGameOver() {
		// if any of canmove method return true, which means 
		// can be moved, then it is not game over
		if (this.canMove(Direction.LEFT) == true || 
				this.canMove(Direction.RIGHT) == true
				||this.canMove(Direction.UP)==true||
				this.canMove(Direction.DOWN)==true){

			return false;
		}
		//if not game over
		else 
			System.out.println("GAME OVER");
		return true;
	}

	// Return the reference to the 2048 Grid
	public int[][] getGrid() {
		return grid;
	}

	// Return the score
	public int getScore() {
		return score;
	}

	@Override
		public String toString() {
			StringBuilder outputString = new StringBuilder();
			outputString.append(String.format
						("Score: %d\n", score));
			for (int row = 0; row < GRID_SIZE; row++) {
				for (int column = 0; 
				column < GRID_SIZE; column++)
				outputString.append(grid[row][column] ==
								 0 ? "    -" :
				   String.format("%5d", grid[row][column]));

				outputString.append("\n");
			}
			return outputString.toString();
		}


}
