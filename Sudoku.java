package Sudoko;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.*;

public class Sudoku extends JFrame {
	
	//Name - constants for the game properties
	
	public static final int GRID_SIZE = 9;
	public static final int SUBGRID_SIZE = 3;
	
	//Name - constants for UI control (sizes , colours and font)
	
	public static final int CELL_SIZE = 60;
	public static final int CANVAS_WIDTH = CELL_SIZE*GRID_SIZE;
	public static final int CANVAS_HEIGHT = CELL_SIZE*GRID_SIZE;
	
	//Board width / height in pixels 
	
	public static final Color OPEN_CELL_BGCOLOR = Color.yellow;
	public static final Color OPEN_TEXT_YES = new Color(0,255,0);
	
	public static final Color OPEN_CELL_TEXT_NO = Color.red;
	public static final Color CLOSED_CELL_BGCOLOR = new Color(240,240,240);
	
	public static final Color CLOSED_CELL_TEXT = Color.black;
	
	public static final Font FONT_NUMBERS = new Font("Monospaced",Font.BOLD,20);
	
	private JTextField[][] tFCells = new JTextField[GRID_SIZE][GRID_SIZE];
	
	private int puzzle[][] = 
		{
			{5,3,4,6,7,8,9,1,2},
			{6,7,2,1,9,5,3,4,8},
			{1,9,8,3,4,2,5,6,7},
			{8,5,9,7,6,1,4,2,3},
			{4,2,6,8,5,3,7,9,1},
			{7,1,3,9,2,4,8,5,6},
			{9,6,1,5,3,7,2,8,4},
			{2,8,4,7,1,9,6,3,5},
			{3,4,5,2,8,6,1,7,9}};
	
	//For testing , we open only 4 cells
	
	private boolean[][] masks =
		{
			{false,true,false,false,false,false,false,false,false},
			{false,false,false,false,true,false,false,false,false},
			{false,false,false,false,false,false,false,false,false},
			{false,false,false,false,false,false,false,false,false},
			{false,false,false,false,false,false,false,false,false},
			{false,false,true,false,true,false,false,false,false},
			{false,false,false,false,false,false,false,false,false},
			{false,false,false,false,false,false,false,false,false},
			{false,false,false,false,false,false,false,false,false}};
			
	public Sudoku()
	{

		Container cp = getContentPane();
		JPanel panel = new JPanel();
		JPanel[] p = new JPanel[9];
		panel.setLayout(new GridLayout(SUBGRID_SIZE,SUBGRID_SIZE));
		Border border = BorderFactory.createLineBorder(Color.black,5);
		Border cellBorder = BorderFactory.createLineBorder(Color.black,1);
		panel.setBorder(border);
		Border panelBorder = BorderFactory.createLineBorder(Color.red,2);
		
		MenuBar mb = new MenuBar();
		Menu game = new Menu("Game");
		MenuItem m1 = new MenuItem("   4    X    4 			");
		MenuItem m2 = new MenuItem("   9    X    9          ");
		MenuItem m3 = new MenuItem("   16  X    16         ");
		MenuItem close = new MenuItem("   Close             ");

		game.add(m1);
		game.add(m2);
		game.add(m3);
		game.addSeparator();
		game.add(close);
		
		setMenuBar(mb);
		mb.add(game);
		
		
		for(int i = 0 ; i < 9 ; i++)
		{	
			p[i] = new JPanel();
			p[i].setLayout(new GridLayout(SUBGRID_SIZE,SUBGRID_SIZE));
			p[i].setBorder(panelBorder);
		}
		
	InputListener listener = new InputListener();
	
	int index = 0;
	for(int row = 0; row < GRID_SIZE ; ++row){
		if(row>=3 && row<6)
			index=3;
		else if(row>=6)
			index = 6;
	
		for(int col = 0 ; col < GRID_SIZE ; ++col)
		{
			tFCells[row][col] = new JTextField();
			tFCells[row][col].setBorder(cellBorder);
			p[index + (int)(col/3)].add(tFCells[row][col]);
			
			if(masks[row][col])
			{
				tFCells[row][col].setText(" ");
				tFCells[row][col].setEditable(true);
				tFCells[row][col].setBackground(OPEN_CELL_BGCOLOR);
				
				tFCells[row][col].addActionListener(listener);
				
			}
			
			else
			{
				tFCells[row][col].setText(puzzle[row][col]+"");
				tFCells[row][col].setEditable(false);
				tFCells[row][col].setBackground(CLOSED_CELL_BGCOLOR);
				
				tFCells[row][col].setForeground(CLOSED_CELL_TEXT);
			}
			
			tFCells[row][col].setHorizontalAlignment(JTextField.CENTER);
			tFCells[row][col].setFont(FONT_NUMBERS);
		}
	}
	
	for(int i = 0 ; i < 9 ; i++)
		panel.add(p[i]);
	
	cp.add(panel);
	cp.setPreferredSize(new Dimension(CANVAS_WIDTH,CANVAS_HEIGHT));
	pack();
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	setTitle("Sudoku");
	setVisible(true);
	
	close.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			setVisible(false);
		}
	});
	
	}
	
	class InputListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			int rowSelected = -1;
			int colSelected = -1;
			
		
			
			JTextField source = (JTextField)e.getSource();
		
			boolean found = false;
		
			for(int row = 0 ; row < GRID_SIZE && !found ; ++row)
				for(int col = 0 ; col < GRID_SIZE && !found ; ++col)
				{
					if(tFCells[row][col] == source)
					{
						rowSelected = row;
						colSelected = col;
						found = true;
					}
				}

			String str = tFCells[rowSelected][colSelected].getText();
			
			char [] a = str.toCharArray();
//			JOptionPane.showMessageDialog(null,""+str.length()+" character :="+a[0]);
			
//			JOptionPane.showMessageDialog(null,""+str.length()+" character :="+a[1]);
			
			int number;
			
			if(a[0] == ' ')
				 number = (int)a[1]-(int)'0';
			else
				 number = (int)a[0]-(int)'0';
			
//			JOptionPane.showMessageDialog(null,""+number);
			
//			JOptionPane.showMessageDialog(null,""+puzzle[rowSelected][colSelected]);
			
			if(puzzle[rowSelected][colSelected] == number)
			{
				tFCells[rowSelected][colSelected].setBackground(Color.green);
				masks[rowSelected][colSelected] = false;
			}
		
			else
			{
				tFCells[rowSelected][colSelected].setBackground(Color.red);
			}
		
			boolean solved = true;
			
			for(int row = 0 ; row < GRID_SIZE ; ++row)
				for(int col = 0 ; col < GRID_SIZE ; ++col)
					if(masks[row][col])
						solved = false;
			
			if(solved){
				JOptionPane.showMessageDialog(null, "Congrats");
				String name = JOptionPane.showInputDialog("Name");
				DataBase db = new DataBase();
				db.dataConnection();
				db.upsert(name);
				db.close();
				
				new ScoreBoard();
			}
		}

	}
		
}
