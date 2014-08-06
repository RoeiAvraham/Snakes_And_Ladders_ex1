/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exception;

/**
 *
 * @author Anat
 */
public class SnakeEndOutOfRangeXmlException extends XmlIsInvalidException
{
    @Override
    public void printMessage()
    {
        System.out.println("One snake's end is out of the board's range.");
    }
}
