
package mco364;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

class Calc extends JFrame {
    // This will act as the main container of all the individual parts of the 
    // calculator.
    public Calc() {
        super("Calculator");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(400, 500);
       // setResizable(false);        
        
        CalcPanel mainPanel2 = new CalcPanel(); // lightweight -- implement in pure Java
        
        add(mainPanel2, BorderLayout.CENTER);
        
        add(mainPanel2.getPane(), BorderLayout.NORTH);
        
        setVisible(true);
        
    }
}