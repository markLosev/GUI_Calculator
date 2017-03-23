/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mco364;


//***LIFF - I RENAMED THE BUTTONS SO THEY FOLLOW THE FUNCTION (EX, buttonAdd)
//          ALSO JUST TO MAKE SURE, THE SECONDARY IS THE PREVIOUSLY ENTERED
//          ENTRY (EX, 7+, 7 GETS PASSED TO THE SECONDARY SCREEN
//       - ALSO, SERIOUSLY HAS TO BE EASIER WAY FOR THE NUMBER BUTTONS
//         THIS IS WHAT I USED EARLIER
//         private class ButtonListener implements ActionListener {
//
//                public ButtonListener() {
//                }
//
//                @Override
//                public void actionPerformed(ActionEvent e) {
//
//                    String command = e.getActionCommand();
//                    if (command.matches("\\d") || command.equalsIgnoreCase(".")) {
//                        currentEntry += command;
//                        bottom.setText(currentEntry);

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;

class CalcPanel extends JPanel {
    
    //this acts as the window where numbers in the calulator will be displayed.
    private JTextPane pane;
    
    // The whitspace is used to shift the M to the lower left corner 
    // in the case where MS used.
    private final String whiteSpace = "&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;";
    
    // this will be used to display the number that we are currently typing into
    // the calculator. It will also be used to display the answer to a calculation.
    private StringBuffer mainDisplay;
    
    // this is the display which goes on top of the mainDisplay. It is used after
    // some sort of operation is called such as add. It will keep track and be 
    // used to display all numbers and operators that are being used for a given equation.
    private StringBuffer secondaryDisplay;
    
    // checks if MS was pressed (this hasn't been implemented yet)
    private boolean memorySaved;
    
    public CalcPanel() {
       
       
       pane = new JTextPane();
       pane.setContentType("text/html");
       pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
       // This sets the screen to initialy show 0 like in a regular calculator.
       pane.setText("</h1><br><h1>" + "0"+ "</h1>");
       
       mainDisplay = new StringBuffer();
       secondaryDisplay = new StringBuffer();
       
       setLayout (new GridBagLayout ());
       GridBagConstraints c = new GridBagConstraints ();
       // These are global variables that are set for all buttons that are used
       // we already discussed what they are doing.
       c.weightx = 1.0;
       c.weighty = 1.0;
       c.fill = c.BOTH;
       c.insets = new Insets(4,4,4,4);
       
       // all buttons are set by giving a coordinate on the grid and then 
       // placing it into the Jpanel aka our calcPanel (the name of this class
       // which extends Jpanel.
       JButton buttonMC = new JButton ("MC");
       c.gridx = 0;
       c.gridy = 0;
       add(buttonMC, c);
       
       JButton buttonMR = new JButton ("MR");
       c.gridx = 1;
       c.gridy = 0;
       add(buttonMR, c);
       
       JButton buttonMS = new JButton ("MS");
       c.gridx = 2;
       c.gridy = 0;
       add(buttonMS, c);
       c.gridx = 0;
       
       JButton buttonMPlus = new JButton ("M+");
       c.gridx = 3;
       c.gridy = 0;
       add(buttonMPlus, c);
       
       JButton buttonMMinus = new JButton ("M-");
       c.gridx = 4;
       c.gridy = 0;
       add(buttonMMinus, c);
       
       JButton buttonBackspace = new JButton ("\u2190");
       c.gridx = 0;
       c.gridy = 1;
       add(buttonBackspace, c);
       
       JButton buttonClearEntry = new JButton ("CE");
       c.gridx = 1;
       c.gridy = 1;
       add(buttonClearEntry, c);
       
       JButton buttonClearAll = new JButton ("C");
       c.gridx = 2;
       c.gridy = 1;
       add(buttonClearAll, c);
       
       JButton buttonPosNeg = new JButton ("\u00B1");
       c.gridx = 3;
       c.gridy = 1;
       add(buttonPosNeg, c);
       
       JButton buttonSqrt = new JButton ("\u221A");
       c.gridx = 4;
       c.gridy = 1;  
       add(buttonSqrt, c);
       
       JButton button7 = new JButton ("7");
       c.gridx = 0;
       c.gridy = 2;
       add(button7, c);
       button7.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               // see documentation of this method.
               attachToMain("7");
               // ""                              ""
               attachToSecondary("7");
               // ""                              ""
               setMainText();              
           }
       });
       
       JButton button8 = new JButton ("8");
       c.gridx = 1;
       c.gridy = 2;
       add(button8, c);
       button8.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               attachToMain("8");
               attachToSecondary("8");
               setMainText();              
           }
       });
       
       JButton button9 = new JButton ("9");
       c.gridx = 2;
       c.gridy = 2;
       add(button9, c);
       button9.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               attachToMain("9");
               attachToSecondary("9");
               setMainText();              
           }
       });
       
       JButton buttonDivide = new JButton ("/");
       c.gridx = 3;
       c.gridy = 2;
       add(buttonDivide, c);
       
       JButton buttonPercent = new JButton ("%");
       c.gridx = 4;
       c.gridy = 2;
       add(buttonPercent, c);
       
       JButton button4 = new JButton ("4");
       c.gridx = 0;
       c.gridy = 3;
       add(button4, c);
       button4.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               attachToMain("4");
               attachToSecondary("4");
               setMainText();              
           }
       });
       
       JButton button5 = new JButton ("5");
       c.gridx = 1;
       c.gridy = 3;
       add(button5, c);
       button5.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               attachToMain("5");
               attachToSecondary("5");
               setMainText();              
           }
       });
       
       JButton button6 = new JButton ("6");
       c.gridx = 2;
       c.gridy = 3;
       add(button6, c);
       button6.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               attachToMain("6");
               attachToSecondary("6");
               setMainText();              
           }
       });
       
       JButton buttonMult = new JButton ("*");
       c.gridx = 3;
       c.gridy = 3;
       add(buttonMult, c);
       
       JButton buttonReciprocal = new JButton ("1/x");
       c.gridx = 4;
       c.gridy = 3;
       add(buttonReciprocal, c);
       
       JButton button1 = new JButton ("1");
       c.gridx = 0;
       c.gridy = 4;
       add(button1, c);
       button1.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               attachToMain("1");
               attachToSecondary("1");
               setMainText();              
           }
       });
       
       JButton button2 = new JButton ("2");
       c.gridx = 1;
       c.gridy = 4;
       add(button2, c);
       button2.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               attachToMain("2");
               attachToSecondary("2");
               setMainText();               
           }
       });
       
       JButton button3 = new JButton ("3");
       c.gridx = 2;
       c.gridy = 4;
       add(button3, c);
       button3.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               attachToMain("3");
               attachToSecondary("3");
               setMainText();              
           }
       });
       
       JButton buttonSubtract = new JButton ("-");
       c.gridx = 3;
       c.gridy = 4;
       add(buttonSubtract, c);
       
       c.gridheight = 2;
       JButton buttonEquals = new JButton ("=");
       c.gridx = 4;
       c.gridy = 4;
       add(buttonEquals, c);
       c.gridheight = 1;
       
       c.gridwidth = 2;
       JButton button0 = new JButton ("0");
       c.gridx = 0;
       c.gridy = 5;
       add(button0, c);
       c.gridwidth = 1;
       button0.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               attachToMain("0");
               attachToSecondary("0");
               setMainText();             
           }
       });
       
       JButton buttonDec = new JButton (".");
       c.gridx = 2;
       c.gridy = 5;
       add(buttonDec, c);
       
       JButton buttonAdd = new JButton ("+");
       c.gridx = 3;
       c.gridy = 5;
       add(buttonAdd, c);
    }
    
    /**
     * append the given string to the mainDisplay StringBuffer
     */
    private void attachToMain(String string) {
        mainDisplay.append(string);
    }
    
    /**
     * append the given string to secondaryDisplay StringBuffer. 
     */
    private void attachToSecondary(String string) {
         secondaryDisplay.append(string);
    }

   
    // This set of enums represent different types of operations that can be done.
    // The way I see it we will use them to keep track of what the current operator
    // is before making a calculation (this has not been implemented yet)
    public enum Operations {
        ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION, SQUAREROOT, RECIPROCAL, PERCENT, EQUALS
    }
    // this method is used in the Calc class in order to retrieve the Jtextpane
    // and place it in the north so it will look like a standard calculator.
    public JTextPane getPane() {
        return pane;
    }
    
    
    //***LIFF - SHOULD WE USE A BOOLEAN FOR THE SAVE OR A STRING AND SEE IF EMPTY?
    
    // This method is used to display our mainDisplay field in the lower right
    // corner of the calculator. As of now this will be done every time a number
    // is pressed by the user. If a number has been saved then it will display
    // a M in the lower left corner.
    public void setMainText() {
       if (!memorySaved) {
           pane.setText("</h1><br><h1>" + mainDisplay + "</h1>");
       }
       else {
           pane.setText("<br><h1>" + mainDisplay + "</h1><br><h1>" + whiteSpace + "M" + "</h1>");
       }
    }
    // This sets the secondaryDisplay and the mainDisplay. The calculator will never 
    // show the secondary display without the mainDisplay so there is no individual method
    // for setting the text of the secondaryDisplay alon.
    
    // Don't worry about the html, to my knowledge I don't think we will need to
    // tinker with it anymore.
    public void setScreen() {
       if (!memorySaved) {
            pane.setText("<small>" + secondaryDisplay + "</small><br><h1>" + mainDisplay + "</h1>");
       }
       else {
            pane.setText("<small>" + secondaryDisplay + "</small><br><h1>" + mainDisplay + "</h1><br><h1>" + whiteSpace + "M" + "</h1>"); 
      }
    }
}
