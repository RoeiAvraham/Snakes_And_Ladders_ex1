/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exception;

/**
 *
 * @author Anat
 */
public class SnakesAndLaddersQuantityNotEqualXmlException extends XmlIsInvalidException
{
    @Override
    public void printMessage()
    {
        System.out.println("Number of snakes is not equal to number of ladders.");
    }
}
