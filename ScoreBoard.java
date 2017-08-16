package Sudoko;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;
import javax.swing.JPanel;

import java.awt.event.*;
import java.awt.*;
import java.sql.SQLException;

public class ScoreBoard extends JFrame {
	private int charIndex;
	public ScoreBoard()
	{	
		super("Score Board");
		Container cp = getContentPane();
		setDesign();
		setSize(200,300);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main,BoxLayout.Y_AXIS));
		main.setPreferredSize(new Dimension(400,100));
		JPanel title = new JPanel();
		title.setLayout(new GridLayout(1,2));
		title.setBorder(BorderFactory.createLineBorder(Color.black,5));
		
		JLabel name = new JLabel("NAME",SwingConstants.CENTER);
		JLabel win = new JLabel("WIN",SwingConstants.CENTER);
		name.setBorder(BorderFactory.createLineBorder(Color.black,2));
		win.setBorder(BorderFactory.createLineBorder(Color.black,2));
		Font font = new Font("AR JULIAN",Font.BOLD,20);
		name.setFont(font);
		win.setFont(font);
		name.setOpaque(true);
		win.setOpaque(true);
		name.setBackground(Color.cyan);
		win.setBackground(Color.cyan);
		
		
		title.add(name);
		title.add(win);
		
		//////////////////////////Animation Of Text ////////////////////
		
		final JLabel label = new JLabel("");
		final String text = " SCORE-BOARD ";
		label.setPreferredSize(new Dimension(70,50));
		//label.setOpaque(true);
		//label.setBackground(Color.lightGray);
		label.setForeground(Color.red);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setFont(new Font("Chiller",Font.BOLD,33));
		
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
		main.add(label);
		main.add(title);
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
		
		DataBase db = new DataBase();
		int countVal = 0 ;
		String[] nameList = null ;
		String[] pointsList = null;
		
		try
		{
			db.dataConnection();
			countVal = db.count();
			nameList = new String[countVal];
			pointsList = new String[countVal];
			
			db.DisplayScore(nameList, pointsList);
			db.close();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		JLabel[] names = new JLabel[countVal];
		JLabel[] points = new JLabel[countVal];
		JPanel[] dataItems = new JPanel[countVal];
		
		main.setPreferredSize(new Dimension(400,100 + (countVal*20)));
		
		for(int i = 0 ; i < countVal ; i++)
		{
			names[i] = new JLabel(nameList[i],SwingConstants.CENTER);
			points[i] = new JLabel(pointsList[i],SwingConstants.CENTER);
			dataItems[i] = new JPanel();
			
			dataItems[i].setLayout(new GridLayout(1,2));
			dataItems[i].setBorder(BorderFactory.createLineBorder(Color.black,1));
			
			names[i].setBorder(BorderFactory.createLineBorder(Color.black,1));
			points[i].setBorder(BorderFactory.createLineBorder(Color.black,1));
			Font font1 = new Font("AR JULIAN",Font.BOLD,14);
			names[i].setFont(font1);
			points[i].setFont(font1);
			names[i].setOpaque(true);
			points[i].setOpaque(true);
			names[i].setBackground(Color.lightGray);
			points[i].setBackground(Color.lightGray);
			
			dataItems[i].add(names[i]);
			dataItems[i].add(points[i]);
			main.add(dataItems[i]);
		}
		
		
		cp.add(main);
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
