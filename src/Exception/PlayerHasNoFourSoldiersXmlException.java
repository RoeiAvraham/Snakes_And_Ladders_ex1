/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exception;

/**
 *
 * @author Anat
 */
public class PlayerHasNoFourSoldiersXmlException extends XmlIsInvalidException
{
    @Override
    public void printMessage()
    {
        System.out.println("At least one player does not have 4 soldiers.");
    }
}
