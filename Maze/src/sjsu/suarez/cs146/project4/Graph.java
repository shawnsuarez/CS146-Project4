package sjsu.suarez.cs146.project4;

import java.util.Random;
import java.util.Stack;
import java.util.ArrayList;

public class Graph
{
	private Random rand;
	private int totalCells;
	private Cell[][] cellMaze; //yes
	
	public Graph(int size)
	{
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
			if(currentCell.x < cellMaze.length && cellMaze[currentCell.x + 1][currentCell.y].isVisited == false)
			{
				currentCell.addNeighbor(cellMaze[currentCell.x + 1][currentCell.y]);
			}
			if(currentCell.y < cellMaze[0].length && cellMaze[currentCell.x][currentCell.y + 1].isVisited == false)
			{
				currentCell.addNeighbor(cellMaze[currentCell.x][currentCell.y + 1]);
			}
			
			int neighSize = currentCell.neighbors.size();
			if(neighSize > 0)
			{
				Cell currentNeighbor = currentCell.neighbors.get((int)random()*neighSize);
				
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
		if(c1.x == c2.x && c1.y > c2.y)
		{
			c1.setSouth(c2);
		}
		else if(c1.x == c2.x && c1.y < c1.y )
		{
			c1.setNorth(c2);
		}
		else if(c1.x < c2.x && c1.y == c2.y)
		{
			c1.setWest(c2);
		}
		else if(c1.x > c2.x && c1.y == c2.y)
		{
			c1.setEast(c2);
		}
	}
	
	public double random()
	{
		return rand.nextDouble();
	}
	
	public String toSting()
	{
		return "";
	}
	
	class Cell
	{
		Cell north;
		Cell south;
		Cell west;
		Cell east;
		
		Cell parent;
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
		}
		
		public void addNeighbor(Cell c)
		{
			neighbors.add(c);
		}
		
		public void setNorth(Cell c2)
		{
			this.north = c2;
			c2.south = this;
			c2.parent = this;
		}
		
		public void setSouth(Cell c2)
		{
			this.south = c2;
			c2.north = this;
			c2.parent = this;
		}
		
		public void setWest(Cell c2)
		{
			this.west = c2;
			c2.east = this;
			c2.parent = this;
		}
		
		public void setEast(Cell c2)
		{
			this.east = c2;
			c2.west = this;
			c2.parent = this;
		}
	}
}
