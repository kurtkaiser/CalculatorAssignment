/*
Kurt Kaiser
CTIM 168
07.31.2018
Homework: C9PP2
 */

import java.util.Scanner;

public class Calculator
{
    private double result;
    private double memory;
    private double precision = 0.0001;
    //Numbers this close to zero are treated as if equal to zero.

    public static void main(String[] args)
    {
        Calculator clerk = new Calculator();
        try
        {
            System.out.println("Calculator on:");
            System.out.println("Required format: operator space number\nInput format example: + 3");
            System.out.println( "Options: c to clear, m to save to memory, " +
                    "r to recall from memory, or e to end");
            clerk.doCalculation();
        }
        catch(UnknownOpException e)
        {
            clerk.handleUnknownOpException(e);
        }
        catch(DivideByZeroException e)
        {
            clerk.handleDivideByZeroException(e);
        }
        System.out.println("The final result is " +
                clerk.getResult());
        System.out.println("Calculator program ending.");
    }

    public Calculator()
    {
        result = 0;
    }

    public void clear()
    {
        result = 0;
    }

    public void setMemory(){
        memory = result;
    }

    public double getMemory(){
        return memory;
    }

    public double getResult()
    {
        return result;
    }

    public void doCalculation()
            throws UnknownOpException, DivideByZeroException
    {
        Scanner keyboard = new Scanner(System.in);
        Scanner keyboardCopy;
        boolean done = false;
        while (!done) {
            String inputString = keyboard.nextLine();
            keyboardCopy = new Scanner(inputString);
            char op = (keyboardCopy.next()).charAt(0);
            if (Character.isLetter(op)) {
                System.out.println("Char is letter.");
                done = options(op);
            } else if (inputString.length() > 2 && Character.isDigit(inputString.charAt(2))
                    && Character.isDigit(inputString.charAt(inputString.length()- 1)))
            {
                double nextNumber = keyboardCopy.nextDouble();
                result = evaluate(op, result, nextNumber);
                System.out.println("result " + op + " " + nextNumber + " = " + result);
                System.out.println("updated result = " + result);
            } else{
                throw new UnknownOpException();
            }
        }
    }

    public boolean options(char op) throws UnknownOpException {
        //Character.toLowerCase(op);
        switch (op) {
            case 'e':
                System.out.println("End of Program");
                return true;
            case 'c':
                clear();
                System.out.println("result = " + result);
                break;
            case 'm':
                setMemory();
                System.out.println("memory value = " + getMemory());
                break;
            case 'r':
                System.out.println("recalled memory value = " + getMemory());
                break;
            default:
                throw new UnknownOpException();
        }
        return false;
    }

    public double evaluate(char op, double n1, double n2)
            throws DivideByZeroException, UnknownOpException
    {
        double answer;
        switch (op)
        {
            case '+':
                answer = n1 + n2;
                break;
            case '-':
                answer = n1 - n2;
                break;
            case '*':
                answer = n1 * n2;
                break;
            case '/':
                if ((-precision < n2) && (n2 < precision))
                    throw new DivideByZeroException();
                answer = n1 / n2;
                break;

            default:
                throw new UnknownOpException();
        }
        return answer;
    }

    public void handleDivideByZeroException
            (DivideByZeroException e)
    {
        System.out.println("Dividing by zero.");
        System.out.println("Program aborted");
        System.exit(0);
    }

    public void handleUnknownOpException(UnknownOpException e)
    {
        System.out.println(e.getMessage());
        System.out.println("Try again from the beginning:");
        try
        {
            System.out.println("Required format: operator space number\nInput format example: + 3");
            System.out.println( "Options: c to clear, m to save to memory, " +
                    "r to recall from memory, or e to end");
            doCalculation();
        }
        catch(UnknownOpException e2)
        {
            System.out.println(e2.getMessage());
            System.out.println("Try again at some other time.");
            System.out.println("Program ending.");
            System.exit(0);
        }
        catch(DivideByZeroException e3)
        {
            handleDivideByZeroException(e3);
        }
    }

}