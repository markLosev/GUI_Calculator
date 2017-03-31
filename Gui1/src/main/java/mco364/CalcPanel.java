
package mco364;


import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
    private ArrayList<Coordinate> buttonList;
    private boolean setSecondEntry;
    private boolean secondaryEntrySet;
    private boolean memoryRecalled;
    
    GridBagConstraints c = new GridBagConstraints();

    public CalcPanel() {
       
       logic = new MathLogic();
       buttonList = new ArrayList<>();
       
       pane = new JTextPane();
       pane.setContentType("text/html");
       pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
       // This sets the screen to initialy show 0 like in a regular calculator.
       pane.setText("</h1><br><h1>" + "0"+ "</h1>");   
       
       mainDisplay = new StringBuilder();
       secondaryDisplay = new StringBuilder();

       setLayout (new GridBagLayout ());
       c.weightx = 1.0;
       c.weighty = 1.0;
       c.fill = c.BOTH;
       c.insets = new Insets(4,4,4,4);
       
       createButtonList();
       setUpCalculatorButtons();
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
        ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION
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
       int index = str.indexOf(mainDisplay.toString());
       str = str.substring(0, index);
       flush(secondaryDisplay);
       attachToSecondary(str);
   }
   
   public void deleteRecalledMemory() {
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
   
   public void setUpNewDigit(String number) {
       checkNewCalculation();
       deleteRecalledMemory();
       if (currentOperator == null || setSecondEntry) {
           attachToMain(number);
           attachToSecondary(number);
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
            attachToMain(number);
            attachToSecondary(number);
            setScreen();
            setSecondEntry = true;
            secondaryEntrySet = true;
            switchOperator = false;
        }             
   }
   
   public void setUpOperatorSymbol(Boolean clickedOperator, String operationString, Operations operator) {
       checkForPreviousOperator();
       if (!clickedOperator && firstNumberPressed) {
           attachToSecondary(operationString);               
           setUpOperator(operator);
           clickedOperator = true;
           firstNumberPressed = false;
           switchOperator = true;
       }
       if (switchOperator) {
           removeLastOperator(secondaryDisplay);
           setOperator(operationString,operator);
       }
       if (secondaryEntrySet) {
           setOperator(operationString,operator);
           secondaryEntrySet = false;
           switchOperator = true;
       }
   }
   
    private void createButtonList() {
       buttonList.add(new Coordinate("MC", 0, 0));
       buttonList.add(new Coordinate("MR", 1, 0));
       buttonList.add(new Coordinate("MS", 2, 0));
       buttonList.add(new Coordinate("M+", 3, 0));
       buttonList.add(new Coordinate("M-", 4, 0));
       buttonList.add(new Coordinate("\u2190", 0 ,1));
       buttonList.add(new Coordinate("CE", 1, 1));
       buttonList.add(new Coordinate("C", 2, 1));
       buttonList.add(new Coordinate("\u00B1", 3, 1));
       buttonList.add(new Coordinate("\u221A", 4, 1));
       buttonList.add(new Coordinate("7", 0, 2));
       buttonList.add(new Coordinate("8", 1, 2));
       buttonList.add(new Coordinate("9", 2, 2));
       buttonList.add(new Coordinate("/", 3, 2));
       buttonList.add(new Coordinate("%", 4, 2));
       buttonList.add(new Coordinate("4", 0, 3));
       buttonList.add(new Coordinate("5", 1, 3));
       buttonList.add(new Coordinate("6", 2, 3));
       buttonList.add(new Coordinate("*", 3, 3));
       buttonList.add(new Coordinate("1/X", 4, 3));
       buttonList.add(new Coordinate("1", 0, 4));
       buttonList.add(new Coordinate("2", 1, 4));
       buttonList.add(new Coordinate("3", 2, 4));
       buttonList.add(new Coordinate("-", 3, 4));
       buttonList.add(new Coordinate("=", 4, 4));
       buttonList.add(new Coordinate("0", 0, 5));
       buttonList.add(new Coordinate(".", 2, 5));
       buttonList.add(new Coordinate("+", 3, 5));    
    }
    
     public void setUpCalculatorButtons() {
        for (Coordinate coordinate : buttonList) {
            switch (coordinate.symbol) {
                case "MC":
                     JButton mcButton = new JButton (coordinate.symbol);
                     mcButton.addActionListener(new memoryClearButtonListener());
                     c.gridx = coordinate.x;
                     c.gridy = coordinate.y;
                     add(mcButton, c);
                     break;
                case "MR":
                     JButton mrButton = new JButton (coordinate.symbol);
                     mrButton.addActionListener(new memoryRecallButtonListener());
                     c.gridx = coordinate.x;
                     c.gridy = coordinate.y;
                     add(mrButton, c);
                     break;
                case "MS":
                     JButton msButton = new JButton (coordinate.symbol);
                     msButton.addActionListener(new memorySaveButtonListener());
                     c.gridx = coordinate.x;
                     c.gridy = coordinate.y;
                     add(msButton, c);
                     break;
                case "M+":
                     JButton mPlusButton = new JButton (coordinate.symbol);
                     mPlusButton.addActionListener(new memoryPlusButonListener());
                     c.gridx = coordinate.x;
                     c.gridy = coordinate.y;
                     add(mPlusButton, c);
                     break;
                case "M-":
                     JButton mMinusButton = new JButton (coordinate.symbol);
                     mMinusButton.addActionListener(new memoryMinusinusButonListener());
                     c.gridx = coordinate.x;
                     c.gridy = coordinate.y;
                     add(mMinusButton, c);
                     break;
                case "\u2190":
                     JButton backSpaceButton = new JButton (coordinate.symbol);
                     backSpaceButton.addActionListener(new backButtonListener());
                     c.gridx = coordinate.x;
                     c.gridy = coordinate.y;
                     add(backSpaceButton, c);
                     break;
                case "CE":
                     JButton clearEntryButton = new JButton (coordinate.symbol);
                     clearEntryButton.addActionListener(new clearEntryButtonListener());
                     c.gridx = coordinate.x;
                     c.gridy = coordinate.y;
                     add(clearEntryButton, c);
                     break;
                case "C":
                     JButton clearButton = new JButton (coordinate.symbol);
                     clearButton.addActionListener(new clearButtonListener());
                     c.gridx = coordinate.x;
                     c.gridy = coordinate.y;
                     add(clearButton, c);
                     break;
                case "\u00B1":
                     JButton negateButton = new JButton (coordinate.symbol);
                     negateButton.addActionListener(new negateButtonListener());
                     c.gridx = coordinate.x;
                     c.gridy = coordinate.y;
                     add(negateButton, c);
                     break;
                case "\u221A":
                     JButton sqrtButton = new JButton (coordinate.symbol);
                     sqrtButton.addActionListener(new sqrtButtonListener());
                     c.gridx = coordinate.x;
                     c.gridy = coordinate.y;
                     add(sqrtButton, c);
                     break;
                case "/":
                     JButton divideButton = new JButton (coordinate.symbol);
                     divideButton.addActionListener(new divideButtonListener());
                     c.gridx = coordinate.x;
                     c.gridy = coordinate.y;
                     add(divideButton, c);
                     break;
                case "%":
                     JButton percentButton = new JButton (coordinate.symbol);
                     percentButton.addActionListener(new percentButtonListener());
                     c.gridx = coordinate.x;
                     c.gridy = coordinate.y;
                     add(percentButton, c);
                     break;
                case "*":
                     JButton multiplyButton = new JButton (coordinate.symbol);
                     multiplyButton.addActionListener(new multiplyButtonListener());
                     c.gridx = coordinate.x;
                     c.gridy = coordinate.y;
                     add(multiplyButton, c);
                     break;
                case "1/X":
                     JButton reciprocalButton = new JButton (coordinate.symbol);
                     reciprocalButton.addActionListener(new reciprocalButtonListener());
                     c.gridx = coordinate.x;
                     c.gridy = coordinate.y;
                     add(reciprocalButton, c);
                     break;
                case "-":
                     JButton minusButton = new JButton (coordinate.symbol);
                     minusButton.addActionListener(new minusButtonListener());
                     c.gridx = coordinate.x;
                     c.gridy = coordinate.y;
                     add(minusButton, c);
                     break;
                case "=":
                     JButton equalsButton = new JButton (coordinate.symbol);
                     equalsButton.addActionListener(new equalsButtonListener());
                     c.gridheight = 2;
                     c.gridx = coordinate.x;
                     c.gridy = coordinate.y;
                     add(equalsButton, c);
                     c.gridheight = 1;
                     break;
                case "0":
                     JButton zeroButton = new JButton (coordinate.symbol);
                     zeroButton.addActionListener(new DigitButtonListener());
                     c.gridwidth = 2;
                     c.gridx = coordinate.x;
                     c.gridy = coordinate.y;
                     add(zeroButton, c);
                     c.gridwidth = 1;
                     break;
                case ".":
                     JButton decimalButton = new JButton (coordinate.symbol);
                     decimalButton.addActionListener(new decimalButtonListener());
                     c.gridx = coordinate.x;
                     c.gridy = coordinate.y;
                     add(decimalButton, c);
                     break;
                case "+":
                     JButton plusButton = new JButton (coordinate.symbol);
                     plusButton.addActionListener(new plusButtonListener());
                     c.gridx = coordinate.x;
                     c.gridy = coordinate.y;
                     add(plusButton, c);
                     break;
                default:
                     JButton digitButton = new JButton (coordinate.symbol);
                     digitButton.addActionListener(new DigitButtonListener());
                     c.gridx = coordinate.x;
                     c.gridy = coordinate.y;
                     add(digitButton, c);
                     break;
            }
        }
    }
    
    private class DigitButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton b = (JButton) e.getSource();
            setUpNewDigit(b.getText());
        }  
    }
    
    private class plusButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            setUpOperatorSymbol(plusClicked, "&emsp;" + "+" + "&emsp;", Operations.ADDITION);
        }
    }
    
    private class minusButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            setUpOperatorSymbol(plusClicked, "&emsp;" + "-" + "&emsp;", Operations.SUBTRACTION);
        }
    }
     
    private class multiplyButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            setUpOperatorSymbol(plusClicked, "&emsp;" + "*" + "&emsp;", Operations.MULTIPLICATION);
        }
    }
    
    private class divideButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            setUpOperatorSymbol(plusClicked, "&emsp;" + "/" + "&emsp;", Operations.DIVISION);
        }
    }
    
    private class memoryClearButtonListener implements ActionListener {

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
    }
    
    private class memorySaveButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            memorySaved = true;
            memory = Double.parseDouble(mainDisplay.toString());
            checkScreenSettings();
        }
    }
    
    private class memoryRecallButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            memoryRecalled = true;
            removeMainFromSecondary();
            int memoryTrimmed = trimDouble(memory);
            checkScreenSettings(Integer.toString(memoryTrimmed));
        }     
    }
    
    private class memoryPlusButonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            memory = logic.calculate(Double.parseDouble(mainDisplay.toString()), memory,Operations.ADDITION);     
        }
    }
    
    private class memoryMinusinusButonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            memory = logic.calculate(Double.parseDouble(mainDisplay.toString()), memory,Operations.SUBTRACTION);     
        }
    }
    
    private class backButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            removeLastIndex(mainDisplay);
            removeLastIndex(secondaryDisplay);
            checkScreenSettings();
        }     
    }
    
    private class clearEntryButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            removeMainFromSecondary();
            flush(mainDisplay);
            checkScreenSettings("0");
        }      
    }
    
    private class clearButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            flush(mainDisplay, secondaryDisplay);
            secondaryDisplaySnapShot = "";
            equationCalculated = true;
            checkScreenSettings("0");
        }      
    }
    
    private class negateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (memoryRecalled) {
                flush(mainDisplay);
                attachToMain(Integer.toString(trimDouble(memory)));
                memoryRecalled = false;
            }
            if (mainDisplay.toString().equals("")) {
                return;
            }
            if (mainDisplay.toString().contains("-")) {
                negated = true;
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
    }
    
    private class sqrtButtonListener implements ActionListener {

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
    }
    
    private class percentButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            checkMemoryAppend();
            double num = 0;
            answer = logic.percent(Double.parseDouble(mainDisplay.toString()));
            answer = logic.calculate(answer, currentEntry, Operations.MULTIPLICATION);
            flush(mainDisplay);
            checkTrailingZeroes();
            attachToSecondary("%");
            secondaryDisplaySnapShot = secondaryDisplay.toString();
            setScreen();
        }      
    }
    
    private class reciprocalButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("called");
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
    }
    
    private class decimalButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!(decPressed)) {
                attachToMain(".");
                attachToSecondary("."); 
                checkScreenSettings();
                decPressed = true;
            }
        }       
    }
    
    private class equalsButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentOperator == null) {
                setMainText();
            }
            else {
                calculate();
            }      
        }       
    }
}
