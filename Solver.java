package Sudoko;

import javax.swing.border.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Solver extends JFrame {
	private int[][] puzzle ;
	private boolean[][] masks;
	
	private JTextField[][] tFCells ;
	public int GRID_SIZE;
	public int SUBGRID_SIZE;
	public int CANVAS_HEIGHT,CANVAS_WIDTH;
	
	public Solver(int n)
	{
		GRID_SIZE = n;
		CANVAS_HEIGHT = CANVAS_WIDTH = GRID_SIZE*60;
		SUBGRID_SIZE = (int)Math.sqrt(GRID_SIZE);
		tFCells = new JTextField[GRID_SIZE][GRID_SIZE];
		puzzle = new int[GRID_SIZE][GRID_SIZE];
		masks = new boolean[GRID_SIZE][GRID_SIZE];
		
		for(int row = 0 ; row < GRID_SIZE ; ++row)
			for(int col = 0 ; col < GRID_SIZE ; ++col)
			{
				masks[row][col] = true;
				puzzle[row][col] = 0 ;
			}
		Container cp = getContentPane();
		
		JPanel mainPanel = new JPanel();
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(1,5));
		JButton solve = new JButton("SOLVE");
		JButton close = new JButton("CLOSE");
		
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		JPanel panel = new JPanel();
		JPanel[] p = new JPanel[GRID_SIZE];
		panel.setLayout(new GridLayout(SUBGRID_SIZE,SUBGRID_SIZE));
		Border border = BorderFactory.createLineBorder(Color.black,5);
		Border cellBorder = BorderFactory.createLineBorder(Color.black,1);
		panel.setBorder(border);
		panel.setPreferredSize(new Dimension(CANVAS_HEIGHT,CANVAS_WIDTH));
		
		Border panelBorder = BorderFactory.createLineBorder(Color.red,2);
		Border controlBorder = BorderFactory.createLineBorder(Color.GRAY);
		
		controlPanel.setBorder(controlBorder);
		controlPanel.setPreferredSize(new Dimension(0,30));
		
		solve.setBackground(Color.green);
		close.setBackground(Color.red);
		
		controlPanel.add(Box.createRigidArea(new Dimension(20,20)));
		controlPanel.add(solve);
		controlPanel.add(Box.createRigidArea(new Dimension(20,20)));
		controlPanel.add(close);
		controlPanel.add(Box.createRigidArea(new Dimension(20,20)));
		
		
		///////////////////////////////////
		
		for(int i = 0 ; i < GRID_SIZE ; i++)
		{	
			p[i] = new JPanel();
			p[i].setLayout(new GridLayout(SUBGRID_SIZE,SUBGRID_SIZE));
			p[i].setBorder(panelBorder);
		}
		
		InputListener listener = new InputListener();
		
		
		int index = 0;
		for(int row = 0; row < GRID_SIZE ; ++row){
			
			index = ((int)row/SUBGRID_SIZE)*SUBGRID_SIZE;
			
			for(int col = 0 ; col < GRID_SIZE ; ++col)
			{
				tFCells[row][col] = new JTextField();
				tFCells[row][col].setBorder(cellBorder);
				p[index + (int)(col/SUBGRID_SIZE)].add(tFCells[row][col]);
				
				tFCells[row][col].setText(" ");
				tFCells[row][col].setEditable(true);
				tFCells[row][col].addActionListener(listener);
				
			
				tFCells[row][col].setBackground(Color.LIGHT_GRAY);
					
				tFCells[row][col].setForeground(Color.black);
				
				
				tFCells[row][col].setHorizontalAlignment(JTextField.CENTER);
				tFCells[row][col].setFont(new Font("Monospaced",Font.BOLD,20));
			}
		}///////////////For Loop Ends Outer
		
		for(int i = 0 ; i < GRID_SIZE ; i++)
			panel.add(p[i]);
		
		mainPanel.add(panel);
		mainPanel.add(controlPanel);
		cp.add(mainPanel);
		pack();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Sudoku");
		setVisible(true);
		
		//////////////////////////Action Listener/////////////////////
		
		solve.addActionListener(new ActionListener(){
			int rowX , colX;
			public void actionPerformed(ActionEvent e)
			{
				
				//setVisible(false);
				final JFrame frame = new JFrame();
				
				if(solved())
					frame.setVisible(true);
				else
				{
					JOptionPane.showMessageDialog(null, "Something Wrong Happened\n :-)");
				}
				
				Container cp = frame.getContentPane();
				
				JPanel mainPanel = new JPanel();
				JPanel controlPanel = new JPanel();
				controlPanel.setLayout(new GridLayout(1,3));
				JButton close = new JButton("CLOSE");
				
				mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
				JPanel panel = new JPanel();
				JPanel[] p = new JPanel[GRID_SIZE];
				panel.setLayout(new GridLayout(SUBGRID_SIZE,SUBGRID_SIZE));
				Border border = BorderFactory.createLineBorder(Color.black,5);
				Border cellBorder = BorderFactory.createLineBorder(Color.black,1);
				panel.setBorder(border);
				panel.setPreferredSize(new Dimension(CANVAS_HEIGHT,CANVAS_WIDTH));
				
				Border panelBorder = BorderFactory.createLineBorder(Color.red,2);
				Border controlBorder = BorderFactory.createLineBorder(Color.GRAY);
				
				controlPanel.setBorder(controlBorder);
				controlPanel.setPreferredSize(new Dimension(0,30));
				
				close.setBackground(Color.red);
				
				controlPanel.add(Box.createRigidArea(new Dimension(20,20)));
				controlPanel.add(close);
				controlPanel.add(Box.createRigidArea(new Dimension(20,20)));
				
				
				///////////////////////////////////
				
				for(int i = 0 ; i < GRID_SIZE ; i++)
				{	
					p[i] = new JPanel();
					p[i].setLayout(new GridLayout(SUBGRID_SIZE,SUBGRID_SIZE));
					p[i].setBorder(panelBorder);
				}
				
				
				int index = 0;
				for(int row = 0; row < GRID_SIZE ; ++row){
					
					index = ((int)row/SUBGRID_SIZE)*SUBGRID_SIZE;
					
					for(int col = 0 ; col < GRID_SIZE ; ++col)
					{
						tFCells[row][col] = new JTextField();
						tFCells[row][col].setBorder(cellBorder);
						p[index + (int)(col/SUBGRID_SIZE)].add(tFCells[row][col]);
						
						tFCells[row][col].setText(""+puzzle[row][col]);
						tFCells[row][col].setEditable(false);
						
					
						tFCells[row][col].setBackground(Color.LIGHT_GRAY);
							
						tFCells[row][col].setForeground(Color.black);
						
						
						tFCells[row][col].setHorizontalAlignment(JTextField.CENTER);
						tFCells[row][col].setFont(new Font("Monospaced",Font.BOLD,20));
					}
				}///////////////For Loop Ends Outer
				
				for(int i = 0 ; i < GRID_SIZE ; i++)
					panel.add(p[i]);
				
					mainPanel.add(panel);
					mainPanel.add(controlPanel);
					cp.add(mainPanel);
					frame.pack();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setTitle(" SOLVED ...");
					frame.setVisible(true);
					
					close.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e)
						{
							frame.setVisible(false);
						}
					});
				
			}

			private boolean solved() {
				int row ,col;
				if(!FindUnassignedLocation())
					return true;
				row = rowX;
				col = colX;
				
				for(int num = 1 ; num <= GRID_SIZE ; num++)
				{
					rowX = row;
					colX = col;
					if(isSafe(num))
					{
						puzzle[row][col] = num;
						masks[row][col] = false;
					
						if(solved())
							return true;
						
						puzzle[row][col] = 0;
						masks[row][col] = true;
					}
						
				}
				
				return false;
			}

			private boolean isSafe(int num) {
				boolean hCheck = true , vCheck = true , boxCheck = true;
				
				for(int i = 0 ; i < GRID_SIZE ; i++)
				{
					if(!masks[i][colX])
					{
						if(puzzle[i][colX] == num)
						{
							vCheck = false;
							break;
						}
					}
				}
				
				for(int i = 0 ; i < GRID_SIZE ; i++)
				{
					if(!masks[rowX][i])
					{
						if(puzzle[rowX][i] == num)
						{
							hCheck = false;
							break;
						}
					}
				}
				
				int boxRS = ((int)(rowX/SUBGRID_SIZE))*SUBGRID_SIZE;
				int boxRE = boxRS + SUBGRID_SIZE;
				
				int boxCS = ((int)(colX/SUBGRID_SIZE))*SUBGRID_SIZE;
				int boxCE = boxCS + SUBGRID_SIZE;
				
				for(int row = boxRS ; row < boxRE ; ++row)
					for(int col = boxCS ; col < boxCE ; ++col)
					{
						if(!masks[row][col])
						{
							if(puzzle[row][col] == num)
							{
								boxCheck = false;
								break;
							}
						}
					}
				
				return (hCheck && vCheck && boxCheck);
			}

			private boolean FindUnassignedLocation() {
				for(int i = 0 ; i < GRID_SIZE ; i++)
					for(int j = 0 ; j < GRID_SIZE ; j++)
					{
						if(masks[i][j])
						{
							rowX = i;
							colX = j;
							return true;
						}
					}
				return false;
			}
		});
		
		close.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
			}
		});
		
		////////////////////////////Action Listener Ends//////////////////
	}
	
	///////////////////INPUT LISTENER INNER CLASS///////////////////////
	
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
			
			puzzle[rowSelected][colSelected] = number;
			masks[rowSelected][colSelected] = false;
			tFCells[rowSelected][colSelected].setBackground(Color.yellow);
			
		}

}

	///////////////////////Class Ends//////////////////////////
	
}
