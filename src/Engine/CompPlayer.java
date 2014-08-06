/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;

import java.util.Random;

/**
 *
 * @author Roei
 */
public class CompPlayer extends Player
{
    public CompPlayer(int playerNum, String playerName, GameBoard board, LoadedFrom source)
    {
        super(playerNum, playerName, board, source);
    }
    
    @Override
     public PlayerType getType()
    {
        return PlayerType.COMP;
    }
    
    @Override
    public int chooseSoldierToMove()
    {
        Random r = new Random();
        return (r.nextInt(Player.NUM_SOLDIERS) + 1);
    }
}
