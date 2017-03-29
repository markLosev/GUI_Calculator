
package mco364;


import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;

class CalcPanel extends JPanel {
    
    //this acts as the window where numbers in the calulator will be displayed.
    private JTextPane pane;
    
    // The whitspace is used to shift the M to the lower left corner 
    // in the case where MS used.
    private final String whiteSpace = "&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;";
    
    // this will be used to display the number that we are currently typing into
    // the calculator. It will also be used to display the answer to a calculation.
    private StringBuilder mainDisplay;
    
    // this is the display which goes on top of the mainDisplay. It is used after
    // some sort of operation is called such as add. It will keep track and be 
    // used to display all numbers and operators that are being used for a given equation.
    private StringBuilder secondaryDisplay;
    
    // checks if MS was pressed 
    private boolean memorySaved;
    private double memory;
    private double currentEntry;
    private double secondEntry;
    private double answer;
    private MathLogic logic;
    private Operations currentOperator;
    private String secondaryDisplaySnapShot;
    private boolean equationCalculated;
    private boolean decPressed;
    private boolean onlyMainDisplayed;
    private boolean negated;
    private boolean firstNumberPressed;
    private boolean plusClicked;
    private boolean minusClicked;
    private boolean multiplyClicked;
    private boolean divideClicked;
    private boolean switchOperator;
    private boolean dividedByZero;
    
    
    //// must be set to false after calculator is cleared
    
    private boolean setSecondEntry;
    private boolean secondaryEntrySet;
    private boolean memoryRecalled;

    public CalcPanel() {
       
       logic = new MathLogic();
       
       pane = new JTextPane();
       pane.setContentType("text/html");
       pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
       // This sets the screen to initialy show 0 like in a regular calculator.
       pane.setText("</h1><br><h1>" + "0"+ "</h1>");
       
       
       
       mainDisplay = new StringBuilder();
       secondaryDisplay = new StringBuilder();

       setLayout (new GridBagLayout ());
       GridBagConstraints c = new GridBagConstraints();
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
       buttonMC.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if (memoryRecalled) {
                   flush(mainDisplay);
                   attachToMain(Integer.toString(trimDouble(memory)));
                   attachToSecondary(Integer.toString(trimDouble(memory)));
                   memoryRecalled = false;
               }
               memory = 0;
               memorySaved = false;
               checkScreenSettings();
           }
       }); 
       
       JButton buttonMR = new JButton ("MR");
       c.gridx = 1;
       c.gridy = 0;
       add(buttonMR, c);
       buttonMR.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               memoryRecalled = true;
               removeMainFromSecondary();
               int memoryTrimmed = trimDouble(memory);
               checkScreenSettings(Integer.toString(memoryTrimmed));
           }
       }); 
       JButton buttonMS = new JButton ("MS");
       c.gridx = 2;
       c.gridy = 0;
       add(buttonMS, c);
       c.gridx = 0;
       buttonMS.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               memorySaved = true;
               memory = Double.parseDouble(mainDisplay.toString());
               checkScreenSettings();
           }
       }); 
       
       JButton buttonMPlus = new JButton ("M+");
       c.gridx = 3;
       c.gridy = 0;
       add(buttonMPlus, c);
       buttonMPlus.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               memory = logic.calculate(Double.parseDouble(mainDisplay.toString()), memory,Operations.ADDITION);
           }
       }); 
       
       JButton buttonMMinus = new JButton ("M-");
       c.gridx = 4;
       c.gridy = 0;
       add(buttonMMinus, c);
       buttonMMinus.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               memory = logic.calculate(memory,Double.parseDouble(mainDisplay.toString()),Operations.SUBTRACTION);
           }
       }); 
       
       JButton buttonBackspace = new JButton ("\u2190");
       c.gridx = 0;
       c.gridy = 1;
       add(buttonBackspace, c);
       buttonBackspace.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               removeLastIndex(mainDisplay);
               removeLastIndex(secondaryDisplay);
               checkScreenSettings();
           }
       });
       
       JButton buttonClearEntry = new JButton ("CE");
       c.gridx = 1;
       c.gridy = 1;
       add(buttonClearEntry, c);
       buttonClearEntry.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               removeMainFromSecondary();
               flush(mainDisplay);
               checkScreenSettings("0");
           } 
       }); 
       
       JButton buttonClearAll = new JButton ("C");
       c.gridx = 2;
       c.gridy = 1;
       add(buttonClearAll, c);
       buttonClearAll.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
              flush(mainDisplay, secondaryDisplay);
              secondaryDisplaySnapShot = "";
              equationCalculated = true;
              checkScreenSettings("0");
           }
       }); 
       JButton buttonPosNeg = new JButton ("\u00B1");
       c.gridx = 3;
       c.gridy = 1;
       add(buttonPosNeg, c);
       buttonPosNeg.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if (memoryRecalled) {
                   flush(mainDisplay);
                   attachToMain(Integer.toString(trimDouble(memory)));
                   memoryRecalled = false;
               }
               String str = mainDisplay.toString();
               removeMainFromSecondary();
               flush(mainDisplay);
               if (negated) {
                   str = str.substring(1);
                   negated = false;
               }
               else {
                   str = "-" + str;
                   negated = true;
               }
               attachToMain(str);
               attachToSecondary(str);
               checkScreenSettings();
           }
       });
        
       JButton buttonSqrt = new JButton ("\u221A");
       c.gridx = 4;
       c.gridy = 1;  
       add(buttonSqrt, c);
       buttonSqrt.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               checkMemoryAppend();
               if(Double.parseDouble(mainDisplay.toString()) >= 0) { 
                   double num = 0;
                   prepPreFix("sqrt");
                   num = Double.parseDouble(mainDisplay.toString());
                   answer = logic.sqrt(num);
                   flush(mainDisplay);
                   checkTrailingZeroes();
                   equationCalculated = true;
                   secondaryDisplaySnapShot = secondaryDisplay.toString();
                   setScreen();
               }
               else {
                   displayError("sqrt", "invalid input");
               }
           }
       });
       
       JButton button7 = new JButton ("7");
       c.gridx = 0;
       c.gridy = 2;
       add(button7, c);
       button7.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
              checkNewCalculation();
              checkMemoryRecalled();
              if (currentOperator == null || setSecondEntry) {
                   attachToMain("7");
                   attachToSecondary("7");
                   firstNumberPressed = true;
                   if (currentOperator == null) {
                       setMainText(); 
                   }
                   else {
                       setScreen();
                   }
              }
              else {
                   flush(mainDisplay);
                   attachToMain("7");
                   attachToSecondary("7");
                   setScreen();
                   setSecondEntry = true;
                   secondaryEntrySet = true;
                   switchOperator = false;
              }             
           }
       });
       
       JButton button8 = new JButton ("8");
       c.gridx = 1;
       c.gridy = 2;
       add(button8, c);
       button8.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
              checkNewCalculation();
              checkMemoryRecalled();
               if (currentOperator == null || setSecondEntry) {
                   attachToMain("8");
                   attachToSecondary("8");
                   firstNumberPressed = true;
                   if (currentOperator == null) {
                       setMainText(); 
                   }
                   else {
                       setScreen();
                   } 
               }
               else {
                   flush(mainDisplay);
                   attachToMain("8");
                   attachToSecondary("8");
                   setScreen();
                   setSecondEntry = true;
                   secondaryEntrySet = true;
                   switchOperator = false;
               }              
           }
       });
       
       JButton button9 = new JButton ("9");
       c.gridx = 2;
       c.gridy = 2;
       add(button9, c);
       button9.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
              checkNewCalculation();
              checkMemoryRecalled();
              if (currentOperator == null || setSecondEntry) {
                   attachToMain("9");
                   attachToSecondary("9");
                   firstNumberPressed = true;
                   if (currentOperator == null) {
                       setMainText(); 
                   }
                   else {
                       setScreen();
                   }
               }
               else {
                   flush(mainDisplay);
                   attachToMain("9");
                   attachToSecondary("9");
                   setScreen();
                   setSecondEntry = true;
                   secondaryEntrySet = true;
                   switchOperator = false;
               }              
           }
       });
       
       JButton buttonDivide = new JButton ("/");
       c.gridx = 3;
       c.gridy = 2;
       add(buttonDivide, c);
       buttonDivide.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
              checkForPreviousOperator();
              if (!divideClicked && firstNumberPressed) {
                    attachToSecondary("&emsp;" + "/" + "&emsp;");               
                    setUpOperator(Operations.ADDITION);
                    divideClicked = true;
                    firstNumberPressed = false;
                    switchOperator = true;
              }
              if (switchOperator) {
                  removeLastOperator(secondaryDisplay);
                  setOperator("&emsp;" + "/" + "&emsp;",Operations.DIVISION);
              }
              if (secondaryEntrySet) {
                  setOperator("&emsp;" + "/" + "&emsp;",Operations.DIVISION);
                  secondaryEntrySet = false;
                  switchOperator = true;
              }
           }
       });
       
       JButton buttonPercent = new JButton ("%");
       c.gridx = 4;
       c.gridy = 2;
       add(buttonPercent, c);
       //A + (A × B/100) 
       buttonPercent.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               checkMemoryAppend();
               double num = 0;
               answer = logic.percent(Double.parseDouble(mainDisplay.toString()));
               answer = logic.calculate(answer, currentEntry, Operations.MULTIPLICATION);
               flush(mainDisplay);
               checkTrailingZeroes();
              // equationCalculated = true;
               attachToSecondary("%");
               secondaryDisplaySnapShot = secondaryDisplay.toString();
               setScreen();
           }
       }); 
       JButton button4 = new JButton ("4");
       c.gridx = 0;
       c.gridy = 3;
       add(button4, c);
       button4.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
              checkNewCalculation();
              checkMemoryRecalled();
              if (currentOperator == null || setSecondEntry) {
                   attachToMain("4");
                   attachToSecondary("4");
                   firstNumberPressed = true;
                 if (currentOperator == null) {
                       setMainText(); 
                 }
                 else {
                       setScreen();
                 }                  
              }
              else {
                   flush(mainDisplay);
                   attachToMain("4");
                   attachToSecondary("4");
                   setScreen();
                   setSecondEntry = true;
                   secondaryEntrySet = true;
                   switchOperator = false;
               }              
           }
       });
       
       JButton button5 = new JButton ("5");
       c.gridx = 1;
       c.gridy = 3;
       add(button5, c);
       button5.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
             checkNewCalculation();
             checkMemoryRecalled();
             if (currentOperator == null || setSecondEntry) {
                   attachToMain("5");
                   attachToSecondary("5");
                   firstNumberPressed = true;
                 if (currentOperator == null) {
                     setMainText(); 
                 }
                 else {
                       setScreen();
                 }
             }
             else {
                 flush(mainDisplay);
                 attachToMain("5");
                 attachToSecondary("5");
                 setScreen();
                 setSecondEntry = true;
                 secondaryEntrySet = true;
                 switchOperator = false;
             }                
           }
       });
       
       JButton button6 = new JButton ("6");
       c.gridx = 2;
       c.gridy = 3;
       add(button6, c);
       button6.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               checkNewCalculation();
               checkMemoryRecalled();
               if (currentOperator == null || setSecondEntry) {
                   attachToMain("6");
                   attachToSecondary("6");
                   firstNumberPressed = true;
                   if (currentOperator == null) {
                       setMainText(); 
                   }
                   else {
                       setScreen();
                   } 
               }
               else {
                   flush(mainDisplay);
                   attachToMain("6");
                   attachToSecondary("6");
                   setScreen();
                   setSecondEntry = true;
                   secondaryEntrySet = true;
                   switchOperator = false;
               }               
           }
       });
       
       JButton buttonMult = new JButton ("*");
       c.gridx = 3;
       c.gridy = 3;
       add(buttonMult, c);
       buttonMult.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               checkForPreviousOperator();
               if (!multiplyClicked && firstNumberPressed) {
                    attachToSecondary("&emsp;" + "*" + "&emsp;");               
                    setUpOperator(Operations.ADDITION);
                    multiplyClicked = true;
                    firstNumberPressed = false;
                    switchOperator = true;
               }
               if (switchOperator) {
                   removeLastOperator(secondaryDisplay);
                   setOperator("&emsp;" + "*" + "&emsp;",Operations.MULTIPLICATION);
               }
               if (secondaryEntrySet) {
                   setOperator("&emsp;" + "*" + "&emsp;",Operations.MULTIPLICATION);
                   secondaryEntrySet = false;
                   switchOperator = true;
               }
           }
       });
      
       
       JButton buttonReciprocal = new JButton ("1/x");
       c.gridx = 4;
       c.gridy = 3;
       add(buttonReciprocal, c);
       buttonReciprocal.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
                checkMemoryAppend();
                if(Double.parseDouble(mainDisplay.toString()) > 0 || Double.parseDouble(mainDisplay.toString()) < 0) { 
                   double num = 0;
                   prepPreFix("reciproc");
                   num = Double.parseDouble(mainDisplay.toString());
                   answer = logic.reciprocal(num);
                   flush(mainDisplay);
                   checkTrailingZeroes();
                   equationCalculated = true;
                   secondaryDisplaySnapShot = secondaryDisplay.toString();
                   setScreen();
               }
                else {
                   displayError("reciproc", "cannot divide by zero");
               }
           }
           
       });
        
       JButton button1 = new JButton ("1");
       c.gridx = 0;
       c.gridy = 4;
       add(button1, c);
       button1.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               checkNewCalculation();
               checkMemoryRecalled();
               if (currentOperator == null || setSecondEntry) {
                    attachToMain("1");
                    attachToSecondary("1");
                    firstNumberPressed = true;
                    if (currentOperator == null) {
                       setMainText(); 
                    }
                    else {
                       setScreen();
                    }
               }
               else {
                   flush(mainDisplay);
                   attachToMain("1");
                   attachToSecondary("1");
                   setScreen();
                   setSecondEntry = true;
                   secondaryEntrySet = true;
                   switchOperator = false;
               }               
           }
       });
       
       JButton button2 = new JButton ("2");
       c.gridx = 1;
       c.gridy = 4;
       add(button2, c);
       button2.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               checkNewCalculation();
               checkMemoryRecalled();
               if (currentOperator == null || setSecondEntry) {
                   attachToMain("2");
                   attachToSecondary("2");
                   firstNumberPressed = true;
                  if (currentOperator == null) {
                       setMainText(); 
                  }
                  else {
                      setScreen();
                  } 
               }
               else {
                   flush(mainDisplay);
                   attachToMain("2");
                   attachToSecondary("2");
                   setScreen();
                   setSecondEntry = true;
                   secondaryEntrySet = true;
                   switchOperator = false;
               }               
           }
       });
       
       JButton button3 = new JButton ("3");
       c.gridx = 2;
       c.gridy = 4;
       add(button3, c);
       button3.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               checkNewCalculation();
               checkMemoryRecalled();
               if (currentOperator == null || setSecondEntry) {
                   attachToMain("3");
                   attachToSecondary("3");
                   firstNumberPressed = true;
                   if (currentOperator == null) {
                       setMainText(); 
                   }
                   else {
                       setScreen();
                   } 
               }
               else {
                   flush(mainDisplay);
                   attachToMain("3");
                   attachToSecondary("3");
                   setScreen();
                   setSecondEntry = true;
                   secondaryEntrySet = true;
                   switchOperator = false;
               }               
           }
       });
       
       JButton buttonSubtract = new JButton ("-");
       c.gridx = 3;
       c.gridy = 4;
       add(buttonSubtract, c);
       buttonSubtract.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               checkForPreviousOperator();
               if (!minusClicked && firstNumberPressed) {
                    attachToSecondary("&emsp;" + "-" + "&emsp;");               
                    setUpOperator(Operations.ADDITION);
                    minusClicked = true;
                    firstNumberPressed = false;
                    switchOperator = true;
               }
               if (switchOperator) {
                  removeLastOperator(secondaryDisplay);
                  setOperator("&emsp;" + "-" + "&emsp;",Operations.SUBTRACTION);
               }
               if (secondaryEntrySet) {
                   setOperator("&emsp;" + "-" + "&emsp;",Operations.SUBTRACTION);
                   secondaryEntrySet = false;
                   switchOperator = true;
               }
           }
       });
       
       
       /**3 options for what calc does *after* equals (which clear secondaryDisplay)
        *   1- Press Number, changes mainDisplay to that Number, start new calculation
        *   2- Press Operator, continues with that Number
        *   ((OPTIONAL 3- Press Equals, takes the last operator + number entered & repeats))
        */  
       c.gridheight = 2;
       JButton buttonEquals = new JButton ("=");
       c.gridx = 4;
       c.gridy = 4;
       add(buttonEquals, c);
       c.gridheight = 1;
       buttonEquals.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if (currentOperator == null) {
                   setMainText();
               }
               else {
                   calculate();
               }
           }
       });
       
       /**Corner Cases involving 0
        if only 0 in mainDisplay    - if press 0, nothing should happen
        *(just opened / pressed 0)  - if press +,-,/,*,sqrt, decimal, reciprocal, treat as 0
        *                           - if press 1-9, overwrite 0 (and ten begin to append)
        *                           - we need to set a reciprocal error.
        */
       
       c.gridwidth = 2;
       JButton button0 = new JButton ("0");
       c.gridx = 0;
       c.gridy = 5;
       add(button0, c);
       c.gridwidth = 1;
       button0.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
                checkNewCalculation();
                checkMemoryRecalled();
                if (currentOperator == null || setSecondEntry) {
                   attachToMain("0");
                   attachToSecondary("0");
                   firstNumberPressed = true;
                   if (currentOperator == null) {
                       setMainText(); 
                   }
                   else {
                       setScreen();
                   } 
                }
                else {                  
                   flush(mainDisplay);
                   attachToMain("0");
                   attachToSecondary("0");
                   setScreen();
                   setSecondEntry = true;
                   secondaryEntrySet = true;
                   switchOperator = false;
               }   
           }
       });
       
       JButton buttonDec = new JButton (".");
       c.gridx = 2;
       c.gridy = 5;
       add(buttonDec, c);
       buttonDec.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if (!(decPressed)) {
                   attachToMain(".");
                   attachToSecondary("."); 
                   checkScreenSettings();
                   decPressed = true;
               }
           }
       });
        
       JButton buttonAdd = new JButton ("+");
       c.gridx = 3;
       c.gridy = 5;
       add(buttonAdd, c);
       buttonAdd.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               checkForPreviousOperator();
               // this allows the plus operator to be used for the first time.
               if (!plusClicked && firstNumberPressed) {
                    attachToSecondary("&emsp;" + "+" + "&emsp;");               
                    setUpOperator(Operations.ADDITION);
                    plusClicked = true;
                    firstNumberPressed = false;
                    switchOperator = true;
               }
               if (switchOperator) {
                   removeLastOperator(secondaryDisplay);
                   setOperator("&emsp;" + "+" + "&emsp;",Operations.ADDITION);
               }
               if (secondaryEntrySet) {
                   setOperator("&emsp;" + "+" + "&emsp;",Operations.ADDITION);
                   secondaryEntrySet = false;
                   switchOperator = true;
               }
           }         
       }); 
    }
    
    private void setOperator (String mathProcess, Operations operator) {
        attachToSecondary(mathProcess);
        setUpOperator(operator);
    }
    
    private void checkForPreviousOperator() {
        if (!(currentOperator == null) && setSecondEntry) {
            mathCalculation();
            setSecondEntry = false;
        }
    }
    
    private void setUpOperator(Operations operator) {
        currentOperator = operator;
        currentEntry = Double.parseDouble(mainDisplay.toString());
        secondaryDisplaySnapShot = secondaryDisplay.toString();
        equationCalculated = false;
        decPressed = false;
        negated = false;
        setScreen();
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

    private void resetSecondaryDisplay() {
       flush(secondaryDisplay);
       attachToSecondary(mainDisplay.toString());
        
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
    
    /**
     * This method will trim the end of a number if it ends in .0 ie. 2.0
     * will become 2.
     */
    public int trimDouble(double num) {
        String str = "" + num;
        DecimalFormat formatter = new DecimalFormat("0.##");
        str = formatter.format(num);
        return Integer.parseInt(str);          
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
           pane.setText("<br><h1>" + mainDisplay + whiteSpace + "M" + "</h1>");//<br><h1>" + whiteSpace + "M" + "</h1>");
       }
       onlyMainDisplayed = true;
    }
    
    // This sets the secondaryDisplay and the mainDisplay. The calculator will never 
    // show the secondary display without the mainDisplay so there is no individual method
    // for setting the text of the secondaryDisplay alon.

    public void setScreen() {
       if (!memorySaved) {
            pane.setText("<small>" + secondaryDisplaySnapShot + "</small><br><h1>" + mainDisplay + "</h1>");
       }
       else {
            pane.setText("<small>" + secondaryDisplaySnapShot + "</small><br><h1>" + mainDisplay + whiteSpace + "M" + "</h1>");//<br><h1>" + whiteSpace + "M" + "</h1>"); 
       }
       onlyMainDisplayed = false;
    }
   
    public void flush(StringBuilder builder) {
        builder.setLength(0);
    }
    
    public void flush(StringBuilder builder1, StringBuilder builder2) {
        builder1.setLength(0);
        builder2.setLength(0);
    }
    
    public void checkNewCalculation() {
        if (equationCalculated) {
            equationCalculated = false;
            flush(mainDisplay,secondaryDisplay);
            currentOperator = null;
            currentEntry = 0;
            secondEntry = 0;
            setSecondEntry = false;
            secondaryEntrySet = false;
            switchOperator = false;
            firstNumberPressed = false;
            resetClicks();
            decPressed = false;
            dividedByZero = false;
        }
    }
    
    public void calculate() throws NumberFormatException {
        mathCalculation();
        if (!dividedByZero) {
            setMainText();
            equationCalculated = true;
            setSecondEntry = false;
            secondaryEntrySet = false;
            switchOperator = false;
            resetClicks();
            firstNumberPressed = true;
            currentOperator = null;
            resetSecondaryDisplay();
        }
    }

    public void mathCalculation() throws NumberFormatException {
        checkMemoryAppend();
        if (currentOperator == Operations.DIVISION && Double.parseDouble(mainDisplay.toString()) == 0.0) {
            displayError("cannot divide by 0");
            dividedByZero = true;
        }
        if (!dividedByZero) {
            secondEntry = Double.parseDouble(mainDisplay.toString());
            answer = logic.calculate(currentEntry, secondEntry, currentOperator);
            flush(mainDisplay);
            checkTrailingZeroes();
            decPressed = false;
            negated = false;
        }
    }
    
    public void checkTrailingZeroes() {
        if (!(Double.toString(answer).matches("\\-?\\d+\\.0$"))) {
            attachToMain(Double.toString(answer));
        }
        else {
            int number = trimDouble(answer);
            attachToMain(Integer.toString(number));
        }
    }
    
    public void removeLastIndex(StringBuilder builder) {
       String str = builder.toString();
       flush(builder);
       str = str.substring(0, str.length() - 1);
       builder.append(str);
   }
    
   public void removeLastOperator(StringBuilder builder) {
       String str = builder.toString();
       flush(builder);
       str = str.substring(0, str.length() - 13);
       builder.append(str);
   }
    
   public void displayError(String function, String error) {
       if (!memorySaved) {
           pane.setText("<small>" +  function + "(" + mainDisplay.toString() + ")" + "</small><br><h1>" + error + "</h1>");       
       }
       else {
           pane.setText("<small>" + function + "(" + mainDisplay.toString() + "</small><br><h1>" + error + "</h1><br><h1>" + whiteSpace + "M" + "</h1>"); 
       }
       equationCalculated = true;
   }
   
   public void displayError(String error) {
       System.out.println("this has been called");
       if (!memorySaved) {
           pane.setText("<small>" +  trimDouble(currentEntry) + "&emsp;" + "/" + "&emsp;" + "</small><br><h1>" + error + "</h1>");       
       }
       else {
           pane.setText("<small>" + trimDouble(currentEntry) + "&emsp;" + "/" + "&emsp;" + "</small><br><h1>" + error + "</h1><br><h1>" + whiteSpace + "M" + "</h1>"); 
       }
       equationCalculated = true;
   }
   
   public void prepPreFix(String str) {
       removeLastIndex(secondaryDisplay);
       attachToSecondary( str + "(" + mainDisplay.toString() + ")");
   }
   
   public void checkScreenSettings() {
       if (onlyMainDisplayed) {
           setMainText();
       }
       else {
           setScreen();
       }
   }
   
   public void checkScreenSettings(String display) {
       if (onlyMainDisplayed) {
           if (!memorySaved) {
               pane.setText("</h1><br><h1>" + display + "</h1>");
           }
           else {
               pane.setText("<br><h1>" + display + whiteSpace + "M" + "</h1>");//<br><h1>" + whiteSpace + "M" + "</h1>");
           }
           onlyMainDisplayed = true;
       }
       else {
           if (!memorySaved) {
              pane.setText("<small>" + secondaryDisplaySnapShot + "</small><br><h1>" + display + "</h1>");
           }
           else {
              pane.setText("<small>" + secondaryDisplaySnapShot + "</small><br><h1>" + display + whiteSpace + "M" + "</h1>");//<br><h1>" + whiteSpace + "M" + "</h1>"); 
           }
           onlyMainDisplayed = false;
       }
   }
   
   public void removeMainFromSecondary() {
       if (!(secondaryDisplay.toString().contains(mainDisplay.toString()))) {
           attachToSecondary(mainDisplay.toString()); // this if statement takes care of a 
                                                      // corner case where CE is used after
                                                      // chaining which doesn't put the mainDisplay
                                                      // into the secondaryDisplay.
       } 
       String str = secondaryDisplay.toString();
       System.out.println("this is the secondary display befor any removal: " + secondaryDisplay);
       int index = str.indexOf(mainDisplay.toString());
       str = str.substring(0, index);
       System.out.println("this is str: " + str);
       flush(secondaryDisplay);
       attachToSecondary(str);
       System.out.println("this is the secondary display after removal: " + secondaryDisplay);
   }
   
   public void checkMemoryRecalled() {
       if (memoryRecalled) {
           flush(mainDisplay);
           memoryRecalled = false;
       }
   }
   
   public void checkMemoryAppend() {
       if (memoryRecalled) {
           flush(mainDisplay);
           attachToMain(Integer.toString(trimDouble(memory)));
           attachToSecondary(Integer.toString(trimDouble(memory)));
        }
   }
   
   public void resetClicks() { 
       plusClicked = false;
       minusClicked = false;
       multiplyClicked = false;
       divideClicked = false;    
   }
}