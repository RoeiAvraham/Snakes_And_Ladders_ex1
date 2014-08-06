/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exception;

/**
 *
 * @author Anat
 */
public class SnakesAndLaddersQuantityToBigXmlException extends XmlIsInvalidException
{
    @Override
    public void printMessage()
    {
        System.out.println("Number of snakes and ladders is larger than possible for the specified board size.");
    }
}
