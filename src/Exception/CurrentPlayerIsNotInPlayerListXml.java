/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exception;

/**
 *
 * @author Anat
 */
public class CurrentPlayerIsNotInPlayerListXml extends XmlIsInvalidException
{
    @Override
    public void printMessage()
    {
        System.out.println("Current player name is illegal, not one of the valid players.");
    }
}
