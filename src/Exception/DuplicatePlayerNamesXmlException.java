/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exception;

/**
 *
 * @author Anat
 */
public class DuplicatePlayerNamesXmlException extends XmlIsInvalidException
{
    @Override
    public void printMessage()
    {
        System.out.println("There are players with the same name. ");
    }
}
