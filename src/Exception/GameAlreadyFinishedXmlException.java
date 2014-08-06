/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exception;

/**
 *
 * @author Anat
 */
public class GameAlreadyFinishedXmlException extends XmlIsInvalidException
{
    @Override
    public void printMessage()
    {
        System.out.println("This game already has a winner.");
    }
}
