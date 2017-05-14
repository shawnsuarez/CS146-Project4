package sjsu.ferrariAndSuarez.cs146.project4;

import java.util.Random;
import java.util.Stack;
import java.util.ArrayList;

public class Maze
{
	public static void main(String[] args)
	{
		Maze m = new Maze(4);
		System.out.println("Maze: ");
		m.generateMaze();
		m.printMaze(false);
		
		System.out.println("DFS: ");

		DFSSolve dfs = new DFSSolve(m);
		dfs.solve();
		m.printMaze(false);
		System.out.println("");
		m.printMaze(true);
		
		//System.out.println(m.cellMaze[3][3].parent.x + "|" + m.cellMaze[3][3].parent.y);
	}
	
	private Random rand;
	private int size;
	private int totalCells;
	Cell[][] cellMaze;
	private String[][] mazeString;
	
	/**
	 * Generates a Maze with a given size
	 * @param size
	 */
	public Maze(int size)
	{
		this.size = size;
		this.totalCells = size*size;
		this.rand = new Random(0);
		
		this.cellMaze = new Cell[size][size];
		this.mazeString = new String[2*size + 1][2*size + 1];
		
		for(int i = 0; i < cellMaze.length; i++)
		{
			for(int j = 0; j < cellMaze[0].length; j++)
			{
				cellMaze[i][j] = new Cell(i, j);
			}
		}
	}
	
	/**
	 * Generates the maze
	 */
	public void generateMaze()
	{
		int visitedNum = 1;
		
		Stack<Cell> cellStack = new Stack<>();
		
		Cell currentCell = cellMaze[0][0];
		currentCell.isVisited = true;
		
		while(visitedNum < totalCells)
		{
			addNeighbors(currentCell);

			ArrayList<Cell> unvisitedNeighbors = new ArrayList<>();
			for(Cell c: currentCell.neighbors)
			{
				if(c.isVisited == false)
				{
					unvisitedNeighbors.add(c);
				}
			}
			
			int neighSize = unvisitedNeighbors.size();
			
			if(neighSize > 0)
			{
				cellStack.push(currentCell);
				int randomIndex = rand.nextInt(neighSize);
				Cell neighbor = unvisitedNeighbors.get(randomIndex);
				
				neighbor.isVisited = true;
				
				connectCells(currentCell, neighbor);
				
				currentCell = neighbor;
				
				visitedNum += 1;
			}
			else
			{
				currentCell = cellStack.pop();
			}
		}
	}
	
	/**
	 * Adds all the unvisited neighbors of a given cell
	 * @param c The given cell
	 */
	public void addNeighbors(Cell c)
	{
		if(c.x > 0 && cellMaze[c.x -1][c.y].isVisited == false)
		{
			c.addNeighbor(cellMaze[c.x -1][c.y]); //Adds North
		}
		if(c.y > 0 && cellMaze[c.x][c.y - 1].isVisited == false)
		{
			c.addNeighbor(cellMaze[c.x][c.y - 1]); //Adds West
		}
		if(c.x < cellMaze.length-1 && cellMaze[c.x + 1][c.y].isVisited == false)
		{
			c.addNeighbor(cellMaze[c.x + 1][c.y]); //Adds South
		}
		if(c.y < cellMaze[0].length-1 && cellMaze[c.x][c.y + 1].isVisited == false)
		{
			c.addNeighbor(cellMaze[c.x][c.y + 1]); //Adds East
		}
	}

	/**
	 * Connects two adjacent cells based on their relative coordinates
	 * @param c1 The first cell
	 * @param c2 The second cell
	 */
	public static void connectCells(Cell c1, Cell c2)
	{
		if(c1.x == c2.x && c1.y > c2.y) //Same row, diff column
		{
			c1.setWest(c2);
			c1.setAsParentOf(c2);
		}
		else if(c1.x == c2.x && c1.y < c2.y ) //Same row, diff column
		{
			c1.setEast(c2);
			c1.setAsParentOf(c2);
		}
		else if(c1.x < c2.x && c1.y == c2.y) //Diff row, same column
		{
			c1.setSouth(c2);
			c1.setAsParentOf(c2);
		}
		else if(c1.x > c2.x && c1.y == c2.y) //Diff row, same column
		{
			c1.setNorth(c2);
			c1.setAsParentOf(c2);
		}
	}
	
	/**
	 * Prints the maze
	 */
	public void printMaze(boolean showSolution)
	{
		for(int i = 0; i < mazeString.length; i++)
		{
			for(int j = 0; j < mazeString[0].length; j++)
			{
				if(i % 2 == 0 && j % 2 == 0)
				{
					mazeString[i][j] = "+";
				}
				else if(i%2 == 1 && j % 2 == 1)
				{
					int x = (i-1)/2;
					int y = (j-1)/2;
					Cell currentCell = cellMaze[x][y];
					
					if(currentCell.north == null)
					{
						mazeString[i-1][j] = "-";
					}
					else
					{
						mazeString[i-1][j] = " ";
					}
					
					if(currentCell.south == null)
					{
						mazeString[i+1][j] = "-";
					}
					else
					{
						mazeString[i+1][j] = " ";
					}
					
					if(currentCell.west == null)
					{
						mazeString[i][j-1] = "|";
					}
					else
					{
						mazeString[i][j-1] = " ";
					}
					
					if(currentCell.east == null)
					{
						mazeString[i][j+1] = "|";
					}
					else
					{
						mazeString[i][j+1] = " ";
					}
					

					if(showSolution)
					{
						mazeString[1][1] = "#";
						showSolution();
					}
					
					mazeString[i][j] = currentCell.getData();
				}
			}
		}
		
		if(!showSolution)
		{
			mazeString[0][1] = " ";
			mazeString[mazeString.length-1][mazeString.length-2] = " ";
		}
		
		for(int x = 0; x < mazeString.length; x++)
		{
			for(int y = 0; y < mazeString[0].length; y++)
			{
				System.out.print(mazeString[x][y]);
			}
			System.out.println("");
		}
	}
	
	/**
	 * Shows the solution path
	 */
	public void showSolution()
	{
		resetCellData();
		
		Cell currentCell = cellMaze[size-1][size-1]; //Start at the last cell
		
		while(currentCell.parent != null)
		{
			currentCell.data = "#";
			
			//Converted coordinates from cellMaze to mazeString
			int cellX = 2*currentCell.x + 1;
			int cellY = 2*currentCell.y + 1;
			
			//Sets the string b/w the two cells
			if(currentCell.north != null && currentCell.parent.equals(currentCell.north))
			{
				mazeString[cellX - 1][cellY] = "#"; 
			}
			else if(currentCell.south != null && currentCell.parent.equals(currentCell.south))
			{
				mazeString[cellX + 1][cellY] = "#";
			}
			else if(currentCell.west != null && currentCell.parent.equals(currentCell.west))
			{
				mazeString[cellX][cellY - 1] = "#";
			}
			else if(currentCell.east != null && currentCell.parent.equals(currentCell.east))
			{
				mazeString[cellX][cellY + 1] = "#";
			}
			currentCell.data = "#";
			currentCell = currentCell.parent;
		}
		
		mazeString[0][1] = "#";
		mazeString[mazeString.length-1][mazeString.length-2] = "#";
	}
	
	/**
	 * Resets each cell's color and data
	 */
	public void resetCellData()
	{
		for(int i = 0; i < cellMaze.length; i++)
		{
			for(int j = 0; j < cellMaze[0].length; j++)
			{
				cellMaze[i][j].data = " ";
				cellMaze[i][j].color = "White";
			}
		}
		
		mazeString[0][1] = " ";
		mazeString[mazeString.length-1][mazeString.length-2] = " ";
	}
	
	public Cell[][] getCellMaze()
	{
		return cellMaze;
	}
	
	public int getMazeSize()
	{
		return size;
	}
	
	public String[][] getMazeString()
	{
		return mazeString;
	}
}

class Cell
{
	Cell north;
	Cell south;
	Cell west;
	Cell east;
	
	Cell parent;
	String data;
	ArrayList<Cell> neighbors;
	String color;
	
	int x;
	int y;
	
	boolean isVisited;
	
	public Cell(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.neighbors = new ArrayList<>();
		this.isVisited = false;
		this.data = " ";
		this.color = "White";
		this.parent = null;
	}
	
	public void addNeighbor(Cell c)
	{
		neighbors.add(c);
	}
	
	public void setNorth(Cell c2)
	{
		this.north = c2;
		c2.south = this;
	}
	
	public void setSouth(Cell c2)
	{
		this.south = c2;
		c2.north = this;
	}
	
	public void setWest(Cell c2)
	{
		this.west = c2;
		c2.east = this;
	}
	
	public void setEast(Cell c2)
	{
		this.east = c2;
		c2.west = this;
	}
	
	public void setAsParentOf(Cell c2)
	{
		c2.parent = this;
	}
	
	public boolean equals(Cell c2)
	{
		if(this.x == c2.x && this.y == c2.y)
		{
			return true;
		}
		return false;
	}
	
	public void setData(String s)
	{
		this.data = s;
	}
	
	public String getData()
	{
		return data;
	}
}
