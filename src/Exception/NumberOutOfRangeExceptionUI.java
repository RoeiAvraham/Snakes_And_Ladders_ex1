/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exception;

/**
 *
 * @author Anat
 */
public class NumberOutOfRangeExceptionUI extends Exception
{
    public final void printMessage()
    {
        System.out.println("Number entered is out of range.");
    }    
}
