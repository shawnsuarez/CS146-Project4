package sjsu.suarez.cs146.project4;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

public class GraphTester
{
	/**
	 * Tests that the Maze constructor method actually creates a maze with dimensions size*size and all walls intact
	 * 
	 * @param size The size of the graph the user would like to test
	*/
		
	public void testConstructGraph(int size) 
	{
		Graph maze = new Graph(size);
		int cells = maze.getNumCells();
		assertTrue(size*size == cells);
		Graph.Cell[][] cellMaze = maze.getCellMaze();
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				assertFalse(cellMaze[i][j] == null);
				Graph.Cell cell = cellMaze[i][j];
				assertTrue(cell.x == i);
				assertTrue(cell.y == j);
				ArrayList<Graph.Cell> neighbors = cell.neighbors;
				assertFalse(neighbors == null);
				assertTrue(neighbors.size() == 0);
				assertFalse(cell.isVisited);
				assertTrue(cell.north == null);
				assertTrue(cell.south == null);
				assertTrue(cell.east == null);
				assertTrue(cell.west == null);
				assertTrue(cell.parent == null);
			}
		}
	}
		
	/**
	 * Runs the constructor tester method on various sizes
	 */
	@Test
	public void testMultConstructGraph()
	{
		testConstructGraph(1);
		testConstructGraph(10);
		testConstructGraph(51);
		testConstructGraph(100);
		testConstructGraph(105);
		testConstructGraph(1000);
	}

}
