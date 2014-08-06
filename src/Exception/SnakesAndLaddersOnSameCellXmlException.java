/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exception;

/**
 *
 * @author Anat
 */
public class SnakesAndLaddersOnSameCellXmlException extends XmlIsInvalidException
{
    @Override
    public void printMessage()
    {
        System.out.println("There is at least one end of ladder on the same cell with one end of a snake.");
    }
}
