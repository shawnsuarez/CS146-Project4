package sjsu.ferrariAndSuarez.cs146.project4;

import java.util.Random;
import java.util.Stack;
import java.util.ArrayList;

public class Maze
{
	public static void main(String[] args)
	{
		Maze m = new Maze(8);
		m.generateMaze();
		m.printMaze();
	}
	
	private Random rand;
	private int size;
	private int totalCells;
	private Cell[][] cellMaze;
	
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
		}
		else if(c1.x == c2.x && c1.y < c2.y ) //Same row, diff column
		{
			c1.setEast(c2);
		}
		else if(c1.x < c2.x && c1.y == c2.y) //Diff row, same column
		{
			c1.setSouth(c2);
		}
		else if(c1.x > c2.x && c1.y == c2.y) //Diff row, same column
		{
			c1.setNorth(c2);
		}
	}
	
	/**
	 * Prints the maze
	 */
	public void printMaze()
	{
		String[][] mazeString = new String[2*size + 1][2*size + 1];
		
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
					System.out.println("(" + ((i-1)/2) + "," + ((j-1)/2) +")");
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
					
					mazeString[i][j] = currentCell.getData();
				}
			}
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
	
	public void setData(String s)
	{
		this.data = s;
	}
	
	public String getData()
	{
		return data;
	}
}
