/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mco364;

import mco364.CalcPanel.Operations;

/**
 *
 * @author Mark
 */
class MathLogic {
    
    public double calculate(double firstNumber, double secondNumber, Operations operator) {
        double answer = 0;
  //ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION, SQUAREROOT, RECIPROCAL, PERCENT, EQUALS
         switch (operator) {
             case ADDITION:
                 answer = add(firstNumber, secondNumber);
                 break;
             
             case SUBTRACTION:
                 answer = subtract(firstNumber, secondNumber);
                 break;
                 
             case MULTIPLICATION:
                 answer = multiply(firstNumber, secondNumber);
                 break;
             
             case DIVISION:
                 answer = divide(firstNumber, secondNumber);
                 break;
                 
                 
             case SQUAREROOT:
                 answer = sqroot(firstNumber);
                 break;
                 
             case PERCENT:
                 answer = percent(firstNumber, secondNumber);
         }
         return answer;
    }

    private double add(double firstNumber, double secondNumber) {
        double number = 0;
        number = firstNumber + secondNumber;
        return number;
    }

    private double subtract(double firstNumber, double secondNumber) {
        double number = 0;
        number = firstNumber - secondNumber;
        return number;
    }

    private double multiply(double firstNumber, double secondNumber) {
        double number = 0;
        number = firstNumber * secondNumber;
        return number;
    }

    private double divide(double firstNumber, double secondNumber) {
        double number = 0;
        number = firstNumber + secondNumber;
        return number;
    }

    private double sqroot(double firstNumber) {
       double number = 0;
       number = Math.sqrt(firstNumber);
       return number;
    }

    private double percent(double firstNumber, double secondNumber) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
