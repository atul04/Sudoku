package Sudoko;

import java.awt.*;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.text.*;
import java.net.URL;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Board extends JFrame{
	private int charIndex;
	
	Board()
	{
		super("SUDOKU");
		setDesign();
		setSize(600,1200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.setPreferredSize(new Dimension(310,210));
		//////////////////////////Animation Of Text ////////////////////
		final JLabel label = new JLabel("");
		final String text = "WELCOME";
		label.setPreferredSize(new Dimension(70,50));
		label.setForeground(new Color(255,223,0));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setFont(new Font("Snap ITC",Font.BOLD,24));
		
		final Timer timer = new Timer(1000,new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String labelText = label.getText();
				labelText+=" ";
				labelText += text.charAt(charIndex);
		
				label.setText(labelText);
				charIndex++;
				if(charIndex >= text.length())
				{
					((Timer)e.getSource()).stop();
				}
			}
		});
		panel.add(label);
		timer.start();
		label.addMouseMotionListener(new MouseMotionListener(){
			public void mouseMoved(MouseEvent e)
			{
			}
			public void mouseDragged(MouseEvent e)
			{
				label.setText("");
				charIndex = 0;
				timer.start();
			}
			
		});
		
		/////////////////////////Animation's Ends////////////////////////
		
		Dimension dm = new Dimension(100,20);
		Dimension dm1 = new Dimension(10,5);
		
		JButton puzzle = new JButton("	Enter Puzzle	");
		JButton start = new JButton("	Start	");
		JButton inst = new JButton("	Instructions	");
		JButton score = new JButton("   Score Board     ");
		JButton close = new JButton("	Exit	");
		
		Font font = new Font("Georgia",Font.BOLD,15);
		
		
		puzzle.setFont(font);
		puzzle.setBackground(Color.blue);
		start.setFont(font);
		start.setBackground(Color.green);
		inst.setFont(font);
		inst.setBackground(Color.orange);
		score.setFont(font);
		score.setBackground(Color.yellow);
		close.setFont(font);
		close.setBackground(Color.red);
		
		puzzle.setAlignmentX(Component.CENTER_ALIGNMENT);
		start.setAlignmentX(Component.CENTER_ALIGNMENT);
		inst.setAlignmentX(Component.CENTER_ALIGNMENT);
		score.setAlignmentX(Component.CENTER_ALIGNMENT);
		close.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		puzzle.setPreferredSize(dm);
		start.setPreferredSize(dm);
		inst.setPreferredSize(dm);
		score.setPreferredSize(dm);
		close.setPreferredSize(dm);
		
		puzzle.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String [] choices =
					{
						"    4   X   4    ",
						"    9   X   9    ",
						"   16  X   16    "
					};
				String sizeInput = (String)JOptionPane.showInputDialog(null,"Board Size : ","Sudoku Solver    ",JOptionPane.QUESTION_MESSAGE,null,choices,choices[1]);
				if(sizeInput.equalsIgnoreCase(choices[0]))
				{
					new Solver(4);
				}
				
				else if(sizeInput.equalsIgnoreCase(choices[1]))
				{
					new Solver(9);
				}
				
				else
				{
					new Solver(16);
				}
			}
		});
		
		
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				new Sudoku();
			}
		});
		
		inst.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				new Instruction();
			}
		});
		
		score.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				new ScoreBoard();
			}
		});
		
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		
		panel.add(Box.createRigidArea(dm1));
		panel.add(puzzle,BorderLayout.CENTER);
		
		
		panel.add(Box.createRigidArea(dm1));
		panel.add(start,BorderLayout.CENTER);
		
		
		panel.add(Box.createRigidArea(dm1));
		panel.add(inst,BorderLayout.CENTER);
		
		panel.add(Box.createRigidArea(dm1));
		panel.add(score,BorderLayout.CENTER);
		
		
		panel.add(Box.createRigidArea(dm1));
		panel.add(close,BorderLayout.CENTER);
		
		
		panel.add(Box.createRigidArea(dm1));
		
		getContentPane().add(panel);
		
		pack();
		setVisible(true);
	}
	
	public final void setDesign()
	{
		try
		{
			for(LookAndFeelInfo info :UIManager.getInstalledLookAndFeels())
			{
				if("Nimbus".equals(info.getName()))
				{
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}


}
