
package mco364;

import mco364.CalcPanel.Operations;

class MathLogic {
    
    public double calculate(double firstNumber, double secondNumber, Operations operator) {
         double answer = 0;
         switch (operator) {
             case ADDITION:
                 answer = firstNumber + secondNumber;
                 break;
             
             case SUBTRACTION:
                 answer = firstNumber - secondNumber;
                 break;
                 
             case MULTIPLICATION:
                 answer = firstNumber * secondNumber;
                 break;
             
             case DIVISION:
                 answer = firstNumber / secondNumber;
                 break;
                 
             case SQUAREROOT:
                 answer = Math.sqrt(firstNumber);
                 break;
                 
             case PERCENT:
                 answer = percent(firstNumber, secondNumber);
         }
         return answer;
    }

    
    private double percent(double firstNumber, double secondNumber) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
