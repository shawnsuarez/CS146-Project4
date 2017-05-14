package sjsu.ferrariAndSuarez.cs146.project4;

import java.util.*;

public class DFSSolve
{
	private Maze m;
	
	public DFSSolve(Maze m)
	{
		this.m = m;
	}
	
	public void solve()
	{
		m.resetCellData();
		
		Cell[][] cellMaze = m.getCellMaze();
		int size = m.getMazeSize();
		
		Cell currentCell = cellMaze[0][0];
		Stack<Cell> cellStack = new Stack<>();
		cellStack.push(currentCell);
		
		int time = 0;
		
		while(!cellStack.isEmpty() && !currentCell.equals(cellMaze[size-1][size-1]))
		{
			currentCell = cellStack.pop();
			currentCell.setConnectedAsNeigbors();
			currentCell.color = "Black";
			currentCell.data = "" + time++ % 10;
			
			for(Cell c : currentCell.neighbors)
			{
				if(c.color.equals("White"))
				{
					c.color = "Gray";
					cellStack.push(c);
				}
			}
		}
		
		while(!currentCell.equals(cellMaze[size-1][size-1]))
		{
			currentCell.data = " ";
			currentCell = currentCell.parent;
		}
	}
}
