/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exception;

/**
 *
 * @author Anat
 */
public class DuplicatePlayerNamesException extends Exception
{
    public void printMessage(String name)
    {
        System.out.println("Player name: "+name+" already exists!");
    }
}
