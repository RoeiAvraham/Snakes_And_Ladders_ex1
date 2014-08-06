/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exception;

/**
 *
 * @author Anat
 */
public class DuplicateCellNumbersXmlException extends XmlIsInvalidException
{
    public void printMessage()
    {
        System.out.println("There are two cells with same number.");
    }
}
