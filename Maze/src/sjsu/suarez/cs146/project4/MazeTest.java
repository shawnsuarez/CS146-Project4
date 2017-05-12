package sjsu.suarez.cs146.project4;

public class MazeTest
{
	public static void main(String[] args)
	{
		//Graph.Cell[][] test = new Graph.Cell[4][4];
		
//		if(test[-1][0] != null)
//		{
//			System.out.println("No");
//		}
		//System.out.println(0%2);
		Graph testG = new Graph(4);
		testG.generateMaze();
		testG.printMaze();
		
		Graph.Cell testCell = testG.getCellMaze()[1][0];
		System.out.println("TestCell: " + testCell.data + " | Neighbor: " + testCell.south.getData());
		
		
		//Review on how to do arrays
		//x is up-down
		//y is left-right
		String[][] testArray = new String[4][4];
		
		for(int i = 0; i < testArray.length; i++)
		{
			for(int j = 0; j < testArray.length; j++)
			{
				testArray[i][j] = "(" + i + "," + j + ")";
			}
		}
		
		for(int i = 0; i < testArray.length; i++)
		{
			for(int j = 0; j < testArray.length; j++)
			{
				System.out.print(testArray[i][j]);
			}
			System.out.println("");
		}
	}
}
