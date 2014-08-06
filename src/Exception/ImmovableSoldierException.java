/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exception;

/**
 *
 * @author Anat
 */
public class ImmovableSoldierException extends Exception
{
    public void printMessage(int soldierNum)
    {
        System.out.println("Soldier #"+soldierNum +" has already FINISHED the game!");
    }
}
