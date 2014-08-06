/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exception;

/**
 *
 * @author Anat
 */
public class LaddersFromIsBiggerThanToXmlException extends XmlIsInvalidException
{
    @Override
    public void printMessage()
    {
        System.out.println("There is a ladder with top lower than its bottom.");
    }
}
