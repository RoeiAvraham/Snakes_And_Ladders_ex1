/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exception;

/**
 *
 * @author Anat
 */
public class LadderEndOutOfRangeXmlException extends XmlIsInvalidException
{
    @Override
    public void printMessage()
    {
        System.out.println("One ladder's end is out of the board's range.");
    }
}
