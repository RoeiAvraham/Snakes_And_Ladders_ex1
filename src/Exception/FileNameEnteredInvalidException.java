/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exception;

/**
 *
 * @author Anat
 */
public class FileNameEnteredInvalidException extends Exception
{
    public void printMessage()
    {
        System.out.println("File name entered is invalid.");
    }
}
