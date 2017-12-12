package calculator;

import java.awt.*;

import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.*;
import javax.swing.border.Border;

public class Calculator extends JFrame implements ActionListener {
	
	private JPanel numPanel, txtPanel, calcPanel, binaryPanel;
	private JRadioButton hex, dec, oct, bin, QWord, DWord, Word, Byte;
	private GridBagConstraints c;
	private JButton button[], blank[];
	private JTextField result;
	private JLabel binary[], abouthlp;
	private double digit1;
	private String operator, copyScrn, curr, sum, sub;
	private boolean concat = false;
	private JMenuBar menuBar;
	private JMenu edit, view, help;
	private JMenuItem copy, show, hide, helpDoc, paste;
	private ButtonGroup group1, group2;
	private int dig1, dig2, conv;
	private JFrame about;
	
 	public Calculator(){
		//CREATE CALCULATOR WINDOW
		setSize(700,580);
		setResizable(false);
		setTitle("Calculator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //AVOIDS MEMORY LEAKS

		
		//CREATE WINDOW FRAME
		createView();
	}
	
	public void createView(){
		numPanel();	
		txtPanel();
		createMenu();
	}
	

	public void createMenu(){
	
		menuBar = new JMenuBar();
		//MENU ITEMS
		edit = new JMenu("Edit");
		view = new JMenu("View");
		help = new JMenu("Help");
		
		//SUBMENU ITEMS
		copy = new JMenuItem("Copy");
		copy.addActionListener(
				new ActionListener(){
					@Override 
					public void actionPerformed(ActionEvent e)
					{
						//COPY THE TEXT ON THE SCREEN
						copyScrn = result.getText();
					}
				});
		paste = new JMenuItem("Paste");
		paste.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e)
					{
						result.setText(copyScrn);
					}
				}
				);
		show = new JMenuItem("Show");
		show.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e)
					{
						txtPanel.setVisible(true);
						calcPanel.setVisible(true);
					}
				}
				);
		
		hide = new JMenuItem("Hide");
		hide.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e)
					{
						txtPanel.setVisible(false);
						calcPanel.setVisible(false);
					}
				}
				);
		helpDoc = new JMenuItem("About");
		helpDoc.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e){
						about = new JFrame();
						abouthlp = new JLabel();
						about.setVisible(true);
						about.setSize(400,300);
						
						//HELP TEXT
						String help = "Please forward any help requests ";
						String hlp	=	"to david@fakemail.com";
								
						//SET TEXT FOR HELP DATA
						abouthlp.setText(help +hlp);
						about.add(abouthlp);
						
					}
				}
				
				);


		
		//ADD IT TO THE CALCULATOR
		setJMenuBar(menuBar);
		menuBar.add(edit);
		edit.add(copy);
		edit.add(paste);
		menuBar.add(view);
		view.add(hide);
		view.add(show);
		menuBar.add(help);
		help.add(helpDoc);
		
	}
	
	public void numPanel(){
		
		Container cont = new Container();
		add(cont);
		calcPanel = new JPanel();
		add(calcPanel);
		
		//CREATE BORDERS FOR PANELS
		Border b = BorderFactory.createLineBorder(Color.black);
		
		//CREATE THE BINARY PANEL
		binaryPanel = new JPanel(new GridLayout(2,8, 30, 30));
		binary = new JLabel[16];
		binaryPanel.setPreferredSize(new Dimension(650,100));
		calcPanel.add(binaryPanel, BorderLayout.NORTH);
		binaryPanel.setBorder(b);
		for(int i = 15; i>=0; i--)
		{
			binary[i] = new JLabel("0000");
			binaryPanel.add(binary[i]);
		}
				
		//CREATE PANEL FOR THE RADIOBUTTONS
		JPanel rButton = new JPanel(new GridLayout(2,1,0,5));
		calcPanel.add(rButton, BorderLayout.WEST);
		
		//SET THE PANELS FOR THE RADIOBUTTONS
		JPanel panel1 = new JPanel(new GridLayout(0,1));
		JPanel panel2 = new JPanel(new GridLayout(0,1));
		panel1.setBorder(b);
		panel2.setBorder(b);
		rButton.add(panel1);
		rButton.add(panel2);
		// ADD THE PANEL FOR ALL THE DIGITS
		numPanel = new JPanel(new GridBagLayout());
		calcPanel.add(numPanel);
	
		
		
		
		//ADD CONSTRAINTS FOR MY LAYOUT
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;
	    c.fill = GridBagConstraints.BOTH;
	    c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(2, 2, 2, 2);
        
        //CREATE BUTTON GROUPS FOR CLICK BUTTONS
        group1 = new ButtonGroup();
        group2 = new ButtonGroup();
		
		//ADD ALL THE CLICKABLE BUTTONS
		hex = new JRadioButton("Hex");
		hex.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e)
					{
						for(int x = 12; x<=17; x++)
							blank[x].setEnabled(true);
						blank[6].setEnabled(true);
						
						//SET SCREEN TO HEX
						display();
						
						conv = 16;
					}
				}
				);
		panel1.add(hex);
		dec = new JRadioButton("Dec", true);
		dec.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e)
					{
						for(int i = 1; i<blank.length; i++)
							blank[i].setEnabled(false);
						resetBinary();
						display();
					}
				}
				);
		panel1.add(dec);
		oct = new JRadioButton("Oct");
		oct.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e)
					{
						for(int i = 1; i<blank.length; i++)
							blank[i].setEnabled(false);
						display();
						conv = 8;
					}
				}
				);
		
		panel1.add(oct);
		bin = new JRadioButton("Bin");
		bin.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e)
					{
						bin.setSelected(true);
						display();
					}
				}
				);
		
		panel1.add(bin);
		QWord = new JRadioButton("QWord", true);
		panel2.add(QWord);
		DWord = new JRadioButton("DWord"); 
		panel2.add(DWord);
		Word = new JRadioButton("Word");
		panel2.add(Word);
		Byte = new JRadioButton("Byte");
		panel2.add(Byte);
		
		//ADD BUTTONS TO GROUP1
		group1.add(hex);
		group1.add(dec);
		group1.add(oct);
		group1.add(bin);

		//ADD BUTTONS TO GROUP2
		group2.add(DWord);
		group2.add(QWord);
		group2.add(Word);
		group2.add(Byte);
		
		//CREATE HEX BUTTONS
		blank = new JButton[23];
		
		c.gridx  = 2;
		c.gridy = 0;
		blank[6] = new JButton("Quot");
		blank[6].setEnabled(false);
		blank[6].setPreferredSize(new Dimension(70,50));
		numPanel.add(blank[6], c);
		c.gridy++;

		for(int x = 1; x < 6; x++)
		{
			blank[x] = new JButton();
			blank[x].setEnabled(false);
			blank[x].setPreferredSize(new Dimension(70,50));
			numPanel.add(blank[x], c);
			c.gridy++;
		}
		
		c.gridx = 3;
		c.gridy = 0;
		blank[0] = new JButton("Mod");
		blank[0].setPreferredSize(new Dimension(70,50));
		numPanel.add(blank[0], c);
		c.gridy++;
		
		for(int x = 7; x < 12; x++)
		{
			blank[x] = new JButton();
			blank[x].setEnabled(false);
			blank[x].setPreferredSize(new Dimension(70,50));
			numPanel.add(blank[x], c);
			c.gridy++;
		}
		
		//ADD THE HEX VALUES
		c.gridy = 0;
		c.gridx = 4;
		//ADD LETTERS
		blank[12] = new JButton("A");
		numPanel.add(blank[12], c);
		blank[12].setPreferredSize(new Dimension(70,50));
		blank[13] = new JButton("B");
		c.gridy++;
		blank[13].setPreferredSize(new Dimension(70,50));
		numPanel.add(blank[13],c);
		blank[14] = new JButton("C");
		c.gridy++;
		blank[14].setPreferredSize(new Dimension(70,50));
		numPanel.add(blank[14],c);
		blank[15] = new JButton("D");
		c.gridy++;
		blank[15].setPreferredSize(new Dimension(70,50));
		numPanel.add(blank[15],c);
		blank[16] = new JButton("E");
		c.gridy++;
		blank[16].setPreferredSize(new Dimension(70,50));
		numPanel.add(blank[16],c);
		blank[17] = new JButton("F");
		c.gridy++;
		blank[17].setPreferredSize(new Dimension(70,50));
		numPanel.add(blank[17],c);
		
		for(int i = 12; i<17; i++)
			blank[i].addActionListener(this);
		
		//ADD THE NUMBER PANEL
		button = new JButton[23];
		button[0] = new JButton();
		
		
		for(int i = 0; i <=9; i++)
		{
			button[i] = new JButton(""+i);
			button[i].setPreferredSize(new Dimension(70,50));
		}
		
		//ADD OPERATORS AND CLEAR BUTTONS
		button[10] = new JButton("CE");
		button[11] = new JButton("C");
		button[12] = new JButton("<-");
		button[13] = new JButton(".");
		button[14] =  new JButton("+");
		button[15] = new JButton("-");
		button[16] = new JButton("/");
		button[17] = new JButton("*");
		button[18] = new JButton("%");
		button[19] = new JButton("1/x");
		button[20] = new JButton("sqrt");
		button[21] = new JButton("+/-");
		button[22] = new JButton("=");
		
		//SET SIZE OF ALL BUTTONS
		for(int x = 0; x < button.length; x++)
			button[x].setPreferredSize(new Dimension(70,50));

		c.gridy = 0;
		c.gridx = 5;
		//ADD THE FIRST NUMPANEL COLUMN
		blank[18] = new JButton();
		blank[18].setEnabled(false);
		blank[18].setPreferredSize(new Dimension(70,50));
		numPanel.add(blank[18], c);
		c.gridy++;
		numPanel.add(button[12], c);
		c.gridy++;
		numPanel.add(button[7], c);
		c.gridy++;
		numPanel.add(button[4], c);
		c.gridy++;
		numPanel.add(button[1], c);
		c.gridy++;
		c.gridwidth = 3;
		numPanel.add(button[0], c);
		
		
		//ADD SECOND NUMPANEL COLUMN
		c.gridwidth = 1;
		c.gridx = 7;
		c.gridy = 0;
		
		blank[19] = new JButton();
		blank[19].setEnabled(false);
		blank[19].setPreferredSize(new Dimension(70,50));
		numPanel.add(blank[19], c);
		c.gridy++;
		numPanel.add(button[10], c);
		c.gridy++;
		numPanel.add(button[8], c);
		c.gridy++;
		numPanel.add(button[5], c);
		c.gridy++;
		numPanel.add(button[2], c);
		c.gridy++;
		
		//ADD 3RD COLUMN OF BUTTONS
		c.gridx = 9;
		c.gridy = 0;
		
		blank[20] = new JButton();
		blank[20].setEnabled(false);
		blank[20].setPreferredSize(new Dimension(70,50));
		numPanel.add(blank[20], c);
		c.gridy++;
		numPanel.add(button[11], c);
		c.gridy++;
		numPanel.add(button[9], c);
		c.gridy++;
		numPanel.add(button[6], c);
		c.gridy++;
		numPanel.add(button[3], c);
		c.gridy++;
		numPanel.add(button[13], c);
		
		//ADD 4TH ROW OF BUTTONS, OPERATORS
		c.gridx = 10;
		c.gridy = 0;
		
		blank [21]= new JButton();
		blank[21].setEnabled(false);
		blank[21].setPreferredSize(new Dimension(70,50));
		numPanel.add(blank[21], c);
		c.gridy++;
		numPanel.add(button[21], c);
		c.gridy++;
		numPanel.add(button[16], c);
		c.gridy++;
		numPanel.add(button[17], c);
		c.gridy++;
		numPanel.add(button[15], c);
		c.gridy++;
		numPanel.add(button[14], c);
		
		//ADD LAST ROW OF OPERATION PANELS
		c.gridx = 13;
		c.gridy = 0;
		
		blank[22] = new JButton();
		blank[22].setEnabled(false);
		blank[22].setPreferredSize(new Dimension(70,50));
		numPanel.add(blank[22], c);
		c.gridy++;
		numPanel.add(button[20], c);
		c.gridy++;
		numPanel.add(button[18], c);
		c.gridy++;
		numPanel.add(button[19], c);
		c.gridy++;
		c.gridwidth = 1;
        c.gridheight = 2;
		numPanel.add(button[22], c);
		
		for(int x = 12; x<=17;x++)
			blank[x].setEnabled(false);
		
		//ADD ACTION LISTENERS TO ALL BUTTONS
		for(int i = 0; i < button.length; i++)
			button[i].addActionListener(this);		
				
	}
	
	public void txtPanel(){
		
		txtPanel = new JPanel();
		add(txtPanel, BorderLayout.NORTH);
		result = new JTextField();
		result.setPreferredSize(new Dimension(650,50));
		
		txtPanel.add(result);
	}
	
	public void actionPerformed(ActionEvent e){
		
		String num, op;
		
		for (int i=0; i<button.length; i++)
		{
			if(e.getSource() == button[i])
			{
				switch(i)
				{
					case 0:
						num = button[i].getText();
						displayDigit(num);
						break;

					case 1:
						num = button[i].getText();
						displayDigit(num);
						break;

					case 2:
						num = button[i].getText();
						displayDigit(num);
						break;

					case 3:
						num = button[i].getText();
						displayDigit(num);
						break;

					case 4:
						num = button[i].getText();
						displayDigit(num);
						break;

					case 5:
						num = button[i].getText();
						displayDigit(num);
						break;

					case 6:
						num = button[i].getText();
						displayDigit(num);
						break;

					case 7:
						num = button[i].getText();
						displayDigit(num);
						break;

					case 8:
						num = button[i].getText();
						displayDigit(num);
						break;

					case 9:
						num = button[i].getText();
						displayDigit(num);
						break;
					case 10: //CE
						op = button[i].getText();
						clear(op);
						break;
					case 11: //C
						op = button[i].getText();
						clear(op);
						break;
					case 12: //<-
						op = button[i].getText();
						clear(op);
						break;
					case 13: //.
						op = button[i].getText();
						math(op);
						break;
					case 14: //+
						op = button[i].getText();
						arithmetic(op);
						break;
					case 15: //-
						op = button[i].getText();
						arithmetic(op);
						break;
					case 16: //divide
						op = button[i].getText();
						arithmetic(op);
						break;
					case 17: //*
						op = button[i].getText();
						arithmetic(op);
						break;
					case 18: //%
						op = button[i].getText();
						math(op);
						break;
					case 19: //1/X
						op = button[i].getText();
						math(op);
						break;
					case 20: //SQRT
						op = button[i].getText();
						math(op);
						break;
					case 21: //+/-
						op = button[i].getText();
						System.out.println(op);
						math(op);
						break;
					case 22: //+
						mathEquals();
						break;
				}
			}
		}
		
		for(int x = 12; x<blank.length; x++)
		{
			if(e.getSource() == blank[x])
			{
				switch(x)
				{
				case 12: //A
					num = blank[x].getText();
					displayDigit(num);
					break;
				case 13: //B
					num = blank[x].getText();
					displayDigit(num);
					break;
				case 14: //C
					num = blank[x].getText();
					displayDigit(num);
					break;
				case 15: //D
					num = blank[x].getText();
					displayDigit(num);
					break;
				case 16: //E
					num = blank[x].getText();
					displayDigit(num);
					break;
				case 17: //F
					num = blank[x].getText();
					displayDigit(num);
					break;
				}
			}
		}
	}
	
	public void math(String op)
	{
		double x = Double.parseDouble(curr);
		
		//IF OPERATOR IS EQUAL TO
		if (Objects.equals(op,"%"))
			x /= 100;
		else if(Objects.equals(op, "1/x"))
			x = 1/x;
		else if(Objects.equals(op, "sqrt"))
			x = Math.sqrt(x);
		else if(Objects.equals(op, "+/-"))
			x = -x;
		
		//ADD DECIMAL POINT
		if(Objects.equals(op, "."))
		{
			curr += ".";
			result.setText(curr);
		}
		else
		{
			//SET THE VALUE FOR THE UI
			curr = Double.toString(x);
			result.setText(curr);	
		}
	}
	

	public void binaryPanel(String binr)
	{
		
		if(bin.isSelected()){
			//CREATE BINARY FIELDS
			int binary1 = Integer.parseInt(binr);
			binr = Integer.toBinaryString(binary1);
			
			//FILL THE FIELDS
			int numbits = binr.length()/4;
			int rembits = binr.length()%4;
			int n= binr.length() -4;
			int i;
			int x = binr.length();
			String temp;
			
			//FILL IN THE ARRAY
			for(i = 0; i<numbits;i++)
			{
				temp = binr.substring(n, x);
				System.out.println(temp);
				binary[i].setText(temp);
				n-=4;
				x-=4;
			}
			
			//CHECK IF THERE ARE REMAINING STRINGS
			if(rembits !=0)
			{
				String new1 = binary[i].getText();
				temp = binr.substring(0, rembits);
				new1 = new1.substring(0, new1.length() - rembits);
				new1 = new1.concat(temp);
				binary[i].setText(new1);
			}
		}
		
		
	}
	
	public void clear(String clear){
	
		if(Objects.equals(clear, "C"))
		{
			result.setText("");
			curr = null;
			digit1 = 0;
			resetBinary();
		}
		else if(Objects.equals(clear, "CE"))
		{
			result.setText("");
			resetBinary();
		}
		else if(Objects.equals(clear, "<-"))
		{
			curr = result.getText();
			int index = curr.length() -1;
			curr = curr.substring(0, index);
			result.setText(curr);
		}
	}
	
	public void arithmetic(String math)
	{
		concat = true;

		//GET INTEGER FROM SCREEN
		if(hex.isSelected() || oct.isSelected())
		{
		dig1 = Integer.parseInt(result.getText(), conv);
		}
		System.out.println("" +dig1);
		if(!hex.isSelected())
		{
			double firstDigit = Double.parseDouble(result.getText());
			digit1 = firstDigit;
		}
		result.setText("");
		binaryPanel(curr);
		
		//KEEP VALUES UNTIL USER PRESSES EQUAL
		
		operator = math;
		concat = false;
		
	}
	
	public void mathEquals()
	{		
		//HEX MATH
		if(hex.isSelected())
		{
			dig2 = Integer.parseInt(result.getText(),16);
			sum = Integer.toHexString(dig1+dig2);
			sub = Integer.toHexString(dig1 - dig2);
			if(operator.equals("+"))
			{
				result.setText(sum);
			}
			else if(operator.equals("-"))
				result.setText(sub);
		}
		else if(dec.isSelected())
		{
			System.out.println("Im in here");
			double currDigit = Double.parseDouble(result.getText());
			//DO THE ARITHMETIC AND RETURN TO SCREEN
			if(operator.equals("+"))
				result.setText(""+(currDigit + digit1));
			else if(operator.equals("-"))
				result.setText(""+(digit1-currDigit));
			else if(operator.equalsIgnoreCase("*"))
				result.setText(""+(digit1*currDigit));
			else if(operator.equals("/"))
			{
				if(currDigit == 0)
					result.setText("ERROR");
				else
					result.setText(""+(digit1/currDigit));
			}
		}
		else if(oct.isSelected())
		{
			dig2 = Integer.parseInt(result.getText(),8);
			sum = Integer.toHexString(dig1 + dig2);
			sub = Integer.toHexString(dig1 - dig2);
			if(operator.equals("+"))
			{
				result.setText(sum);
			}
			else if(operator.equals("-"))
			{
				result.setText(sub);
			}
		}


	}
	public void displayDigit(String digit)
	{
		if(!concat)
		{
			curr = result.getText();
			result.setText(curr + digit);
			curr = result.getText();
			binaryPanel(curr);
		}
		
	
	}
	
	public void display()
	{
		if(hex.isSelected())
		{
			//CONVERTS THE VALUES ON THE SCREEN TO HEX
			if(conv == 8)
			{
				dig1 = Integer.valueOf(result.getText(),8);
				curr = Integer.toHexString(dig1);
				result.setText(curr);
			}
			else
			{
				dig1 = Integer.valueOf(result.getText(),16);
				curr = Integer.toHexString(dig1);
				System.out.println(curr);
				result.setText(curr);
				System.out.println("Set");

			}
		}
		else if(dec.isSelected())
		{
			//CONVERTS VALUES TO DECIMAL
			if(conv == 8)
			{
				dig1 = Integer.parseInt(result.getText(),8);
				curr = Integer.toString(dig1);
				result.setText(curr);
			}
			else if(conv ==16)
			{
				dig1 = Integer.valueOf(result.getText(), 16);
				curr = Integer.toString(dig1);
				result.setText(curr);
			}
		}
		else if(oct.isSelected())
		{
			//CONVERTS VALUES TO OCTAL VALUES
			if(conv == 16)
			{
				dig1 = Integer.parseInt(result.getText(), 16);
				curr = Integer.toOctalString(dig1);
				result.setText(curr);
			}
			else{
				dig1 = Integer.parseInt(result.getText());
				curr = Integer.toOctalString(dig1);
				result.setText(curr);
				System.out.println("Set");
			}
		}
		else if(bin.isSelected())
		{
			//CONVERTS VALUES ON SCREEN TO BINARY
			if(conv == 8)
			{
				dig1 = Integer.parseInt(result.getText(),8);
				curr = Integer.toString(dig1);
				result.setText(curr);
				binaryPanel(curr);


			}
			else if(conv ==16)
			{
				dig1 = Integer.parseInt(result.getText(),16);
				curr = Integer.toString(dig1);
				result.setText(curr);
				binaryPanel(curr);

			}
			binaryPanel(curr);
		}
	}
	
	public void resetBinary(){
		for(int i = 15; i>=0; i--)
		{
			binary[i].setText("0000");
		}
	}
	
	public static void main(String[] args){
		Calculator test = new Calculator();
		test.setVisible(true);
	}
}
