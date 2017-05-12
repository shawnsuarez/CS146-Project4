package sjsu.suarez.cs146.project4;

import java.util.Random;
import java.util.Stack;
import java.util.ArrayList;

//Graph
public class Graph
{
	private Random rand;
	private int size;
	private int totalCells;
	private Cell[][] cellMaze;
	
	public Graph(int size)
	{
		this.size = size;
		this.totalCells = size*size;
		this.rand = new Random(0);
		
		this.cellMaze = new Cell[size][size];
		
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				cellMaze[i][j] = new Cell(i, j);
			}
		}
	}
	
	public void generateMaze()
	{
		int visitedCells = 1;
		
		int cellNum = 0;// testing
		
		Stack<Cell> cellStack = new Stack<>();
		
		Cell currentCell = cellMaze[0][0];
		currentCell.isVisited = true;
		
		while (visitedCells < totalCells)
		{
			if(currentCell.x > 0 && cellMaze[currentCell.x -1][currentCell.y].isVisited == false)
			{
				currentCell.addNeighbor(cellMaze[currentCell.x -1][currentCell.y]);
			}
			if(currentCell.y > 0 && cellMaze[currentCell.x][currentCell.y - 1].isVisited == false)
			{
				currentCell.addNeighbor(cellMaze[currentCell.x][currentCell.y - 1]);
			}
			if(currentCell.x < cellMaze.length-1 && cellMaze[currentCell.x + 1][currentCell.y].isVisited == false)
			{
				currentCell.addNeighbor(cellMaze[currentCell.x + 1][currentCell.y]);
			}
			if(currentCell.y < cellMaze[0].length-1 && cellMaze[currentCell.x][currentCell.y + 1].isVisited == false)
			{
				currentCell.addNeighbor(cellMaze[currentCell.x][currentCell.y + 1]);
			}
			
			int neighSize = currentCell.neighbors.size();
			
			currentCell.setData("" + cellNum);
			cellNum++;
			
			if(neighSize > 0)
			{	
				int randomIndex = rand.nextInt(neighSize);
				System.out.println(neighSize + " | " + randomIndex);
				
				Cell currentNeighbor = currentCell.neighbors.get(randomIndex);
				
				//Connect the two cells
				connectCells(currentCell, currentNeighbor);
				
				cellStack.push(currentCell);
				currentCell = currentNeighbor;
				currentCell.isVisited = true;
				
				visitedCells++;
			}
			else
			{
				currentCell = cellStack.pop();
			}
		}
	}
	
	public Cell[][] getCellMaze()
	{
		return this.cellMaze;
	}
	
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
	
	//Method to print the maze
	public void printMaze()
	{
		String[][] mazeString = new String[2*size + 1][2*size + 1];
		
		for(int i = 0; i < mazeString.length; i++)
		{
			for(int j = 0; j < mazeString[0].length; j++)
			{
				System.out.println("i: " + i + " | j: " + j);
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
	
	public double random()
	{
		return rand.nextDouble();
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
			this.data = "?";
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
}
