//------------------------------------------------------------------//
// Gui2048.java                                                     //
//                                                                  //
// GUI Driver for 2048                                             //
//                                                                  //
// Author:  Jiawei Zhou, cs8bffb
// PID: A92119932
// Date:    11/22/16                                                //
//------------------------------------------------------------------//


import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;
import java.io.*;

/* 
 * Name: public Gui2048 extends Application
 * Purpose: this is the class which contains the whole game, in this class
 * it has update and set all the based element of this game. After each 
 * move, it will update the result.
 * @reture: no
 * @parameter: no
 */
public class Gui2048 extends Application
{
	private String outputBoard; // The filename for where to save the Board
	private Board board; // The 2048 Game Board

	private static final int TILE_WIDTH = 106;

	private static final int TEXT_SIZE_LOW = 55; // Low value tiles (2,4,8,etc)
	private static final int TEXT_SIZE_MID = 45; // Mid value tiles 
	//(128, 256, 512)
	private static final int TEXT_SIZE_HIGH = 35; // High value tiles 
	//(1024, 2048, Higher)

	// Fill colors for each of the Tile values
	private static final Color COLOR_EMPTY = Color.rgb(238, 228, 218, 0.35);
	private static final Color COLOR_2 = Color.rgb(238, 228, 218);
	private static final Color COLOR_4 = Color.rgb(237, 224, 200);
	private static final Color COLOR_8 = Color.rgb(242, 177, 121);
	private static final Color COLOR_16 = Color.rgb(245, 149, 99);
	private static final Color COLOR_32 = Color.rgb(246, 124, 95);
	private static final Color COLOR_64 = Color.rgb(246, 94, 59);
	private static final Color COLOR_128 = Color.rgb(237, 207, 114);
	private static final Color COLOR_256 = Color.rgb(237, 204, 97);
	private static final Color COLOR_512 = Color.rgb(237, 200, 80);
	private static final Color COLOR_1024 = Color.rgb(237, 197, 63);
	private static final Color COLOR_2048 = Color.rgb(237, 194, 46);
	private static final Color COLOR_OTHER = Color.BLACK;
	private static final Color COLOR_GAME_OVER = Color.rgb(238, 228, 218, 0.73);

	private static final Color COLOR_VALUE_LIGHT = Color.rgb(249, 246, 242); 
	// For tiles >= 8

	private static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101); 
	// For tiles < 8

	private GridPane pane;

	/** Add your own Instance Variables here */
	private Rectangle[][] rectangleArray;
	private Text[][] textArray;
	private Text score;
	private Text gameOverText;
	private Rectangle gameOver;
	private StackPane StackPane;
	private Scene scene;

	/* 
	 * Name:  start(Stage primaryStage)
	 * Purpose:this is a void method, which conatain the first secene of this 
	 * gui game. It set the the color of tile and number and color, font of 
	 * title.
	 * @return: no
	 * @parameter: stage
	 * */
	@Override
		public void start(Stage primaryStage)
		{
			// Process Arguments and Initialize the Game Board
			processArgs(getParameters().getRaw().toArray(new String[0]));

			// Create the pane that will hold all of the visual objects
			pane = new GridPane();
			pane.setAlignment(Pos.CENTER);
			pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
			pane.setStyle("-fx-background-color: rgb(187, 173, 160)");
			// Set the spacing between the Tiles
			pane.setHgap(15); 
			pane.setVgap(15);

			/** Add your Code for the GUI H*/

			//create a new secene which will show
			scene = new Scene(pane);
			primaryStage.setTitle("Gui2048");
			primaryStage.setScene(scene);
			primaryStage.show();

			//set the name of this game in pane
			Text name = new Text();
			name.setText("2048");
			//set the font format and color
			name.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
			name.setFill(Color.BLACK);

			//set the score
			score = new Text();
			score.setText("Score: " + board.getScore());
			//set color and font format
			score.setFill(Color.BLACK);
			score.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));

			//add name to this pane
			pane.add(name,0,0);
			//span it 
			pane.setColumnSpan(name, board.GRID_SIZE/2);
			//add score and span it
			pane.add(score, board.GRID_SIZE/2,0);
			pane.setColumnSpan(score, board.GRID_SIZE/2);
			//make it in the center
			pane.setHalignment(name, HPos.CENTER);
			pane.setHalignment(score, HPos.CENTER);

			//create a rectangle array and textarray, 
			//and this array is have same as the 
			//board
			rectangleArray = new Rectangle[board.GRID_SIZE][board.GRID_SIZE];
			textArray = new Text[board.GRID_SIZE][board.GRID_SIZE];
			//get the grid
			int[][] grid = board.getGrid();
			for(int column = 0; column<board.GRID_SIZE; column++){
				for(int row = 0; row<board.GRID_SIZE;row++){

					//make some little square for tile
					Rectangle aLonelySquare = new Rectangle();
					aLonelySquare.setWidth(TILE_WIDTH);
					aLonelySquare.setHeight(TILE_WIDTH);

					//set the color of little square base on different
					//situation 
					if(grid[column][row] == 0){ 
						aLonelySquare.setFill(COLOR_EMPTY); 
					}
					else if(grid[column][row]==2){
						aLonelySquare.setFill(COLOR_2);
					}

					else { aLonelySquare.setFill(COLOR_4);}

					//set the text 
					Text number = new Text();
					if(grid[column][row] == 0){
						number.setText(" ");
					}
					else {number.setText(Integer.toString(grid[column][row]));}

					//set the font fromat in text base on different situation
					if(grid[column][row] < 100){
						number.setFont(Font.font("Times New Roman",
									FontWeight.BOLD,TEXT_SIZE_LOW));
					}
					else if(grid[column][row]>=100 && grid[column][row]<1000){
						number.setFont(Font.font("Times New Roman",
									FontWeight.BOLD,TEXT_SIZE_MID));
					}
					else{
						number.setFont(Font.font("Times New Roman", 
									FontWeight.BOLD,TEXT_SIZE_HIGH));						
					}

					//set font color of text base on different situation 
					if(grid[column][row]<8){
						number.setFill(COLOR_VALUE_DARK);	
					}
					else{
						number.setFill(COLOR_VALUE_LIGHT);
					}

					//add the retangleArray and TextAttay to the pane and store
					pane.add(aLonelySquare, row, column+1);
					pane.add(number, row, column+1);
					rectangleArray[column][row] = aLonelySquare;
					textArray[column][row] = number;

					//make pane be in the center
					pane.setHalignment(number, HPos.CENTER);
					pane.setHalignment(aLonelySquare,HPos.CENTER);


				}

			}
			scene.setOnKeyPressed(new myKeyHandler());
		}

	/* 
	 * Name: update()
	 * * Purpose:this is the update the the things show on screen after each movement
	 * @return: no
	 * @parameter: no
	 * * */
	public void update(){
		//first get grif from the board
		int [][] grid = board.getGrid();
		//loop thought the whole grid and reset the color
		//and font
		for (int column =0;column < board.GRID_SIZE; column++){
			for(int row =0; row< board.GRID_SIZE;row++){
				if(grid[column][row] == 0){
					rectangleArray[column][row].setFill(COLOR_EMPTY);
				}
				else if(grid[column][row] == 2){
					rectangleArray[column][row].setFill(COLOR_2);
				}
				else if(grid[column][row] == 4){
					rectangleArray[column][row].setFill(COLOR_4);
				}
				else if(grid[column][row] == 8){
					rectangleArray[column][row].setFill(COLOR_8);
				}
				else if(grid[column][row] == 16){
					rectangleArray[column][row].setFill(COLOR_16);
				}
				else if(grid[column][row] == 32){
					rectangleArray[column][row].setFill(COLOR_32);
				}
				else if(grid[column][row] == 64){
					rectangleArray[column][row].setFill(COLOR_64);
				}
				else if(grid[column][row] == 128){
					rectangleArray[column][row].setFill(COLOR_128);
				}
				else if(grid[column][row] == 256){
					rectangleArray[column][row].setFill(COLOR_256);
				}
				else if(grid[column][row] == 512){
					rectangleArray[column][row].setFill(COLOR_512);
				}
				else if(grid[column][row] == 1024){
					rectangleArray[column][row].setFill(COLOR_1024);
				}
				else if(grid[column][row] == 2048){
					rectangleArray[column][row].setFill(COLOR_2048);
				}
				else{
					rectangleArray[column][row].setFill(COLOR_OTHER);
				}
				//if no number, set as the sitution 0
				if(grid[column][row] == 0){
					textArray[column][row].setText(" ");
				}
				else {
					textArray[column][row].setText
						(Integer.toString(grid[column][row]));}

				//set the font fromat in text base on the length of number
				if(grid[column][row] < 100){
					textArray[column][row].setFont(Font.font("Times New Roman", 
								FontWeight.BOLD,TEXT_SIZE_LOW));
				}
				else if(grid[column][row]>=100 && grid[column][row]<1000){
					textArray[column][row].setFont(Font.font("Times New Roman", 
								FontWeight.BOLD,TEXT_SIZE_MID));
				}
				else{
					textArray[column][row].setFont(Font.font("Times New Roman", 
								FontWeight.BOLD,TEXT_SIZE_HIGH));						
				}
				//set font color of text
				if(grid[column][row]<8){
					textArray[column][row].setFill(COLOR_VALUE_DARK);	
				}
				else{
					textArray[column][row].setFill(COLOR_VALUE_LIGHT);
				}

			}
		}
		//update the score 
		score.setText("Score: " + board.getScore());

	}
	/* 
	 * Name: gameOver()
	 * Purpose: check the game is over or not, if it is game over, 
	 * it will show "game over" and then stop the game
	 * @return: no
	 * @paramter: no
	 * */
	public void gameOver(){
		//check game is over or not
		if(board.isGameOver() == true){
			//create gameover rectangle
			Rectangle gameOver = 
				new Rectangle(pane.getHeight(),pane.getWidth());
			gameOver.setFill(COLOR_GAME_OVER);
			//centerStackPane = new StackPane();
			//set text
			gameOverText = new Text();
			gameOverText.setText("Game Over!");
			gameOverText.setFont(Font.font("Times New Roman", 75));
			gameOverText.setFill(Color.BLACK);
			
			//add this gameover and text to gui, and show it up
			pane.add(gameOver,0,0,board.GRID_SIZE,board.GRID_SIZE+1 );
			pane.add(gameOverText,0,1,board.GRID_SIZE,board.GRID_SIZE);
			gameOver.widthProperty().bind(pane.widthProperty());
			gameOver.heightProperty().bind(pane.heightProperty());

			//make it in the center
			pane.setHalignment(gameOver, HPos.CENTER);
			pane.setHalignment(gameOverText,HPos.CENTER);

			//pane.setColumnSpan(gameOverText,board.GRID_SIZE);
		}
	}

	/* 
	 * Name:  myKeyHandler implements EventHandler<KeyEvent>
	 * * Purpose:this is the most important part of this gui, because this contain
	 * the moving order in this game.
	 * @return :no
	 * @parameter:no
	 * * */
	/** Add your own Instance Methods Here */
	private class myKeyHandler implements EventHandler<KeyEvent>{

		/*Name: handle(keyCode e)
		 *Description: as user type in different key, the game will do 
		 *some special movement.
		 *@return : keycode e
		 *@parameter: no
		 * */
		public void handle(KeyEvent e){

			//type in up, move up , and update the gui, and check if 
			//it is game over
			if(e.getCode()==KeyCode.UP){
				if(board.canMove(Direction.UP)){
					System.out.println("Moving Up");
					board.move(Direction.UP);
					board.addRandomTile();
					update();
					gameOver();
				}
			}

			//type in down, move down , and update the gui, and check if 
			//it is game over
			else if(e.getCode()==KeyCode.DOWN){
				if(board.canMove(Direction.DOWN)){
					System.out.println("Moving Down");
					board.move(Direction.DOWN);
					board.addRandomTile();
					update();
					gameOver();
				}
			}

			//type in left, move left , and update the gui, and check if 
			//it is game over
			else if(e.getCode()==KeyCode.LEFT){
				if(board.canMove(Direction.LEFT)==true){
					System.out.println("Moving Left");
					board.move(Direction.LEFT);
					board.addRandomTile();
					update();
					gameOver();

				}
			}

			//type in right, move right , and update the gui, and check if 
			//it is game over
			else if(e.getCode()==KeyCode.RIGHT){
				if(board.canMove(Direction.RIGHT)){
					System.out.println("Moving Right");
					board.move(Direction.RIGHT);
					board.addRandomTile();
					update();
					gameOver();

				}
			}

			//type in q, exit
			else if (e.getCode()==KeyCode.Q){
				System.exit(1);
			}

			//type in s, move up , check is gameover, and then save game
			else if (e.getCode()==KeyCode.S){
				if(board.isGameOver()==false){
					try{
						board.saveBoard(outputBoard);
					}	catch (IOException exception){
						System.out.println("saveBoard threw an Exception");
					}
					System.out.println("Saving Board to " +outputBoard);
				}
				else{ gameOver();}
			}

			//type in r, rotate , and update the gui, and check if 
			//it is game over
			else if(e.getCode()==KeyCode.R){
				if(board.isGameOver()==false){
					board.rotate(true);
					System.out.println("Rotating");
					update();
				}
				else {
					gameOver();
				}
			}


		}
	}

	/** DO NOT EDIT BELOW */

	// The method used to process the command line arguments
	private void processArgs(String[] args)
	{
		String inputBoard = null;   // The filename for where to load the Board
		int boardSize = 0;          // The Size of the Board

		// Arguments must come in pairs
		if((args.length % 2) != 0)
		{
			printUsage();
			System.exit(-1);
		}

		// Process all the arguments 
		for(int i = 0; i < args.length; i += 2)
		{
			if(args[i].equals("-i"))
			{   // We are processing the argument that specifies
				// the input file to be used to set the board
				inputBoard = args[i + 1];
			}
			else if(args[i].equals("-o"))
			{   // We are processing the argument that specifies
				// the output file to be used to save the board
				outputBoard = args[i + 1];
			}
			else if(args[i].equals("-s"))
			{   // We are processing the argument that specifies
				// the size of the Board
				boardSize = Integer.parseInt(args[i + 1]);
			}
			else
			{   // Incorrect Argument 
				printUsage();
				System.exit(-1);
			}
		}

		// Set the default output file if none specified
		if(outputBoard == null)
			outputBoard = "2048.board";
		// Set the default Board size if none specified or less than 2
		if(boardSize < 2)
			boardSize = 4;

		// Initialize the Game Board
		try{
			if(inputBoard != null)
				board = new Board(inputBoard, new Random());
			else
				board = new Board(boardSize, new Random());
		}
		catch (Exception e)
		{
			System.out.println(e.getClass().getName() + 
					" was thrown while creating a " +
					"Board from file " + inputBoard);
			System.out.println("Either your Board(String, Random) " +
					"Constructor is broken or the file isn't " +
					"formated correctly");
			System.exit(-1);
		}
	}

	// Print the Usage Message 
	private static void printUsage()
	{
		System.out.println("Gui2048");
		System.out.println("Usage:  Gui2048 [-i|o file ...]");
		System.out.println();
		System.out.println("  Command line arguments come in pairs of the "+ 
				"form: <command> <argument>");
		System.out.println();
		System.out.println("  -i [file]  -> Specifies a 2048 board that " + 
				"should be loaded");
		System.out.println();
		System.out.println("  -o [file]  -> Specifies a file that should be " + 
				"used to save the 2048 board");
		System.out.println("                If none specified then the " + 
				"default \"2048.board\" file will be used");  
		System.out.println("  -s [size]  -> Specifies the size of the 2048" + 
				"board if an input file hasn't been"); 
		System.out.println("                specified.  If both -s and -i" + 
				"are used, then the size of the board"); 
		System.out.println("                will be determined by the input" +
				" file. The default size is 4.");
	}
}
