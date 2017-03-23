/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mco364;

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
       JButton button = new JButton ("MC");
       c.gridx = 0;
       c.gridy = 0;
       add(button, c);
       
       JButton button2 = new JButton ("MR");
       c.gridx = 1;
       c.gridy = 0;
       add(button2, c);
       
       JButton button3 = new JButton ("MS");
       c.gridx = 2;
       c.gridy = 0;
       add(button3, c);
       c.gridx = 0;
       
       JButton button4 = new JButton ("M+");
       c.gridx = 3;
       c.gridy = 0;
       add(button4, c);
       
       JButton button5 = new JButton ("M-");
       c.gridx = 4;
       c.gridy = 0;
       add(button5, c);
       
       JButton button6 = new JButton ("\u2190");
       c.gridx = 0;
       c.gridy = 1;
       add(button6, c);
       
       JButton button7 = new JButton ("CE");
       c.gridx = 1;
       c.gridy = 1;
       add(button7, c);
       
       JButton button8 = new JButton ("C");
       c.gridx = 2;
       c.gridy = 1;
       add(button8, c);
       
       JButton button9 = new JButton ("\u00B1");
       c.gridx = 3;
       c.gridy = 1;
       add(button9, c);
       
       JButton button10 = new JButton ("\u221A");
       c.gridx = 4;
       c.gridy = 1;  
       add(button10, c);
       
       JButton button11 = new JButton ("7");
       c.gridx = 0;
       c.gridy = 2;
       add(button11, c);
       button11.addActionListener(new ActionListener() {
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
       
       JButton button12 = new JButton ("8");
       c.gridx = 1;
       c.gridy = 2;
       add(button12, c);
       button12.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               attachToMain("8");
               attachToSecondary("8");
               setMainText();              
           }
       });
       
       JButton button13 = new JButton ("9");
       c.gridx = 2;
       c.gridy = 2;
       add(button13, c);
       button13.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               attachToMain("9");
               attachToSecondary("9");
               setMainText();              
           }
       });
       
       JButton button14 = new JButton ("/");
       c.gridx = 3;
       c.gridy = 2;
       add(button14, c);
       
       JButton button15 = new JButton ("%");
       c.gridx = 4;
       c.gridy = 2;
       add(button15, c);
       
       JButton button16 = new JButton ("4");
       c.gridx = 0;
       c.gridy = 3;
       add(button16, c);
       button16.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               attachToMain("4");
               attachToSecondary("4");
               setMainText();              
           }
       });
       
       JButton button17 = new JButton ("5");
       c.gridx = 1;
       c.gridy = 3;
       add(button17, c);
       button17.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               attachToMain("5");
               attachToSecondary("5");
               setMainText();              
           }
       });
       
       JButton button18 = new JButton ("6");
       c.gridx = 2;
       c.gridy = 3;
       add(button18, c);
       button18.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               attachToMain("6");
               attachToSecondary("6");
               setMainText();              
           }
       });
       
       JButton button19 = new JButton ("*");
       c.gridx = 3;
       c.gridy = 3;
       add(button19, c);
       
       JButton button20 = new JButton ("1/x");
       c.gridx = 4;
       c.gridy = 3;
       add(button20, c);
       
       JButton button21 = new JButton ("1");
       c.gridx = 0;
       c.gridy = 4;
       add(button21, c);
       button21.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               attachToMain("1");
               attachToSecondary("1");
               setMainText();              
           }
       });
       
       JButton button22 = new JButton ("2");
       c.gridx = 1;
       c.gridy = 4;
       add(button22, c);
       button22.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               attachToMain("2");
               attachToSecondary("2");
               setMainText();               
           }
       });
       
       JButton button23 = new JButton ("3");
       c.gridx = 2;
       c.gridy = 4;
       add(button23, c);
       button23.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               attachToMain("3");
               attachToSecondary("3");
               setMainText();              
           }
       });
       
       JButton button24 = new JButton ("-");
       c.gridx = 3;
       c.gridy = 4;
       add(button24, c);
       
       c.gridheight = 2;
       JButton button25 = new JButton ("=");
       c.gridx = 4;
       c.gridy = 4;
       add(button25, c);
       c.gridheight = 1;
       
       c.gridwidth = 2;
       JButton button26 = new JButton ("0");
       c.gridx = 0;
       c.gridy = 5;
       add(button26, c);
       c.gridwidth = 1;
       button26.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               attachToMain("0");
               attachToSecondary("0");
               setMainText();             
           }
       });
       
       JButton button27 = new JButton (".");
       c.gridx = 2;
       c.gridy = 5;
       add(button27, c);
       
       JButton button28 = new JButton ("+");
       c.gridx = 3;
       c.gridy = 5;
       add(button28, c);
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
        ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION, SQAUREROOT, RECIPROCAL, PERCENT, EQUALS
    }
    // this method is used in the Calc class in order to retrieve the Jtextpane
    // and place it in the north so it will look like a standard calculator.
    public JTextPane getPane() {
        return pane;
    }
    
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
