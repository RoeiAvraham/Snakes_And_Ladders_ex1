/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exception;

/**
 *
 * @author Anat
 */
public class SnakeHeadInIllegalCellXmlException extends XmlIsInvalidException
{
    @Override
    public void printMessage()
    {
        System.out.println("There is a snake head at the first or last cell.");
    }
}
