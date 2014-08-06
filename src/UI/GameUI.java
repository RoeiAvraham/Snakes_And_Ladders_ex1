/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Exception.NumberOutOfRangeExceptionUI;
import Engine.Cell;
import Engine.Player;
import Engine.TurnData;
import Exception.FileSuffixsIsNotXmlException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Roei
 */
public class GameUI
{
    public static final int MIN_BOARD_SIZE = 5;
    public static final int MAX_BOARD_SIZE = 8;
    public static final int MIN_NUM_PLAYERS = 2;
    public static final int MAX_NUM_PLAYERS = 4;
    public static final int MIN_NUM_OF_SOLDIERS_TO_WIN = 1;
    public static final int MAX_NUM_OF_SOLDIERS_TO_WIN = 4;
    public static final int FIRST_MENU_OPTION = 1;
    public static final int SECOND_MENU_OPTION = 2;
    public static final int MAIN_MENU_LAST_OPTION=3;
    public static final int TURN_MENU_LAST_OPTION=3;    
    public static final int MIN_NUM_OF_SNAKES = 0;

    public final int getFirstChoice() throws InputMismatchException, NumberOutOfRangeExceptionUI
    {
        int choice = getChoiceInt();
        if (choice < FIRST_MENU_OPTION || choice > MAIN_MENU_LAST_OPTION)
        {
            throw new NumberOutOfRangeExceptionUI();
        }
        return choice;
    }
    
    public final int getBoardSize() throws InputMismatchException, NumberOutOfRangeExceptionUI
    {
        System.out.print(">Please enter board size, between " + MIN_BOARD_SIZE + "-" + MAX_BOARD_SIZE + ": ");
        int choice = getChoiceInt();
        if (choice < MIN_BOARD_SIZE || choice > MAX_BOARD_SIZE)
        {
            throw new NumberOutOfRangeExceptionUI();
        }
        return choice;
    }

    public final int getNumberOfLadders(final int boardSize) throws InputMismatchException, NumberOutOfRangeExceptionUI
    {
        System.out.print(">Please enter number of ladders (and snakes), between " +MIN_NUM_OF_SNAKES+" to " + ((boardSize * boardSize) / 4) + ": ");
        int choice = getChoiceInt();
        if (choice < MIN_NUM_OF_SNAKES || choice > ((boardSize * boardSize) / 4))
        {
            throw new NumberOutOfRangeExceptionUI();
        }        
        return choice;
    }

    public final int getNumberOfPlayers() throws InputMismatchException, NumberOutOfRangeExceptionUI
    {
        System.out.print(">Please enter number of players, between " + MIN_NUM_PLAYERS + "-" + MAX_NUM_PLAYERS + ": ");
        int choice = getChoiceInt();
        if (choice < MIN_NUM_PLAYERS || choice > MAX_NUM_PLAYERS)
        {
            throw new NumberOutOfRangeExceptionUI();
        }
        return choice;
    }

    public final int getNumberOfSoldiersToWin() throws InputMismatchException, NumberOutOfRangeExceptionUI
    {
        System.out.print("\n>Please enter number of soldiers to win, between " + MIN_NUM_OF_SOLDIERS_TO_WIN + "-" + MAX_NUM_OF_SOLDIERS_TO_WIN + ":");
        int choice = getChoiceInt();
        if (choice < MIN_NUM_OF_SOLDIERS_TO_WIN || choice > MAX_NUM_OF_SOLDIERS_TO_WIN)
        {
            throw new NumberOutOfRangeExceptionUI();
        }
        return choice;
    }

    public final int getPlayerType(int playerInd) throws InputMismatchException, NumberOutOfRangeExceptionUI
    {
        System.out.println(">Is Player " + (playerInd + 1) + " Computer or Human?");
        System.out.println("1. Computer.");
        System.out.println("2. Human.");
        System.out.print("Your choice: ");
        int choice = getChoiceInt();
        if (choice < FIRST_MENU_OPTION || choice > Player.PlayerType.values().length)
        {
            throw new NumberOutOfRangeExceptionUI();
        }
        
        return choice;
    }

    public final String getPlayerName(int playerInd)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print(">Please enter Player " + (playerInd + 1) + "'s name: ");
        return scanner.nextLine();
    }

    public void printWelcomeMessage()
    {
        clearConsole();
        System.out.println("Welcome to Snakes and Ladders!!!");
        printMainMenu();
    }
    
    public void printGameOverMessage()
    {
        System.out.println("Game over! What would you like to do now?");
        printMainMenu();
    }
    
    public final void printMainMenu()
    {
        System.out.println(">Please enter your choice:");
        System.out.println("1. Start new game.");
        System.out.println("2. Load previously saved game from XML.");
        System.out.println("3. Quit.");
        System.out.print("Your choice: ");
    }

    public final int getChoiceInt() throws InputMismatchException
    {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public final int getSoldierToMove(final int[] soldierPos, int lastCell, String playerName) throws InputMismatchException, NumberOutOfRangeExceptionUI
    {
        System.out.println(">" + playerName + ", Which soldier would you like to play with?");
        int i;
        for (i = 0; i < Player.NUM_SOLDIERS; i++)
        {
            if (soldierPos[i] != lastCell)
            {
                System.out.println("Soldier #" + (i + 1) + ": located in cell " + soldierPos[i] + ".");
            }
            else
            {
                System.out.println("Soldier #" + (i + 1) + " has already FINISHED the game.");
            }
        }
        System.out.print("Your choice: ");
        int choice = getChoiceInt();
        if (choice < MIN_NUM_OF_SOLDIERS_TO_WIN || choice > MAX_NUM_OF_SOLDIERS_TO_WIN )
        {
            throw new NumberOutOfRangeExceptionUI();
        }
        
        return choice;

    }

    public static void clearConsole()
    {
        try
        {
            String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (Exception exception)
        {
            //  I dont care :-)
        }
    }

    public int getPlayerTurnOption(String playerName) throws InputMismatchException, NumberOutOfRangeExceptionUI
    {
        clearConsole();
        System.out.println(">" + playerName + "'s Turn, what would you like to do?");
        System.out.println("1. Continue playing.");
        System.out.println("2. Save game to XML file.");
        System.out.println("3. Quit.");
        System.out.print("Your choice: ");
        int choice = getChoiceInt();
        if (choice < FIRST_MENU_OPTION || choice > TURN_MENU_LAST_OPTION)
        {
            throw new NumberOutOfRangeExceptionUI();
        }

        return choice;
    }

    //public final void printCell(int cellNum, int cellDest, int[] numSoldiers)
    public final void printCell(Cell cell)
    {
        System.out.printf("%02d|", cell.getCellNum());

        if (cell.getDest() != Cell.NO_DEST)
        {
            System.out.printf("%02d|", cell.getDest());
        }
        else
        {
            System.out.print("--|");
        }

        int i;
        int[] numSoldiers = cell.getSoldiersInCell();
        for (i = 0; i < numSoldiers.length; i++)
        {
            System.out.print(numSoldiers[i]);
        }
        System.out.print(" ");
    }

    public final void printGameBoard(final int boardSize, Cell[] cells, final LinkedList<Player> players)
    {
        int i;
        int j;

        for (i = boardSize; i > 0; i--)
        {
            for (j = 0; j < boardSize; j++)
            {
                printCell(cells[boardSize * i - boardSize + j]);
                //System.out.print m_cells[boardSize * i - boardSize + j];
            }
            System.out.println("");
        }
        printPlayerNames(players);
    }

    public void printCompTurn(String playerName)
    {
        System.out.println("-- It's " + playerName + "'s Turn...");
    }

    public void printPlayerNames(LinkedList<Player> players)
    {
        System.out.println("------------------------------------------------------");
        
        for (Player player : players)
        {
            System.out.print("Player " + player.getPlayerNum() + ": " + player.getPlayerName() + " ");
        }
        System.out.println("");
        System.out.println("");
    }

    public void printWinner(String winner)
    {
        clearConsole();
        System.out.println("***************And the WINNER is: " + winner + " !!!***************\n");
    }

    public void printQuitter(String playerName)
    {
        clearConsole();
        System.out.println(playerName + " has LEFT the game!");
    }

    public void printTurnData(TurnData turn, String playerName)
    {
        System.out.println("-- Dice result: " + turn.getTurnDiceRes() + ".");
        System.out.println("-- " + playerName + " played with soldier #" + turn.getTurnSoldierNum() + " and reached cell " + turn.getTurnDest() + ".");
    }
    
    public void printInputMisMatchExceptionMessage()
    {
        System.out.println("You entered an illegal character. Only numbers are allowed. ");
    }
    
    public void printJAXBExceptionMessage()
    {
        System.out.println("JAXB Binding error.");
    }
    
    public void printIOExceptionMessage()
    {
        System.out.println("Can't validate file provided because it is unavailable.");
    }
    
    public int getSaveToXmlOption() throws InputMismatchException, NumberOutOfRangeExceptionUI
    {
        System.out.println(">Do you want to save again to the last saved file, or save to a new file?");
        System.out.println("1. Save to last saved file.");
        System.out.println("2. Save to a new file.");
        System.out.print("Your choice: ");
        int choice = getChoiceInt();
        if (choice < FIRST_MENU_OPTION || choice > SECOND_MENU_OPTION)
        {
            throw new NumberOutOfRangeExceptionUI();
        }
        
        return choice;
    }
    
    public void printValidateErrorMessage()
    {
        System.out.println("Xml provided is invalid by schema.");
    }
    
    public String getXmlFilePath() throws FileSuffixsIsNotXmlException
    {
        String res;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter XML file path: ");
        res = scanner.nextLine();
        if (!res.endsWith(".xml"))
        {
            throw new FileSuffixsIsNotXmlException();
        }
        return res;
    }
}