/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exception;

/**
 *
 * @author Anat
 */
public abstract class XmlIsInvalidException extends Exception
{
    public XmlIsInvalidException()
    {
        System.out.print("XML File is invalid. ");
    }
    
    public abstract void printMessage();
}
