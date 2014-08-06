/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Exception.DuplicatePlayerNamesException;
import Exception.ImmovableSoldierException;
import Exception.FileNameEnteredInvalidException;
import Engine.Game;
import Engine.Player;
import Engine.TurnData;
import Exception.*;
import UI.GameUI;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;
import xmlPackage.Snakesandladders;

/**
 *
 * @author Anat
 */
public class Controller
{
    private GameUI ui;
    private Game m_game;
    private File xmlFile = null;
    private static final int COMP = 1;
    private static final int HUMAN = 2;
    private static final int START_NEW_GAME = 1;
    private static final int LOAD_GAME_FROM_XML = 2;
    private static final int QUIT = 3;
    private static final int CONTINUE = 1;
    private static final int SAVE_GAME_TO_XML = 2;
    private static final int SAVE_TO_LAST_SAVED_FILE = 1;
    private static final int SAVE_TO_NEW_FILE = 2;
    private String lastSavedFileName = null;
    boolean isGameActive;

    public Controller()
    {
        ui = new GameUI();
        ui.printWelcomeMessage();
        isGameActive = createNewOrLoadedGame();
        while (isGameActive)
        {
            isGameActive = playGame();
        }
    }

    public boolean isGameActive()
    {
        return isGameActive;
    }

    public boolean createNewOrLoadedGame()
    {
        int choice = 0;
        boolean isGameActive = false;

        boolean isInputLegal = false;
        while (!isInputLegal)
        {
            try
            {
                choice = ui.getFirstChoice();
                isInputLegal = true;
            }
            catch (InputMismatchException e)
            {
                ui.printInputMisMatchExceptionMessage();
                ui.printMainMenu();
            }
            catch (NumberOutOfRangeExceptionUI e)
            {
                e.printMessage();
                ui.printMainMenu();
            }
        }

        switch (choice)
        {
            case START_NEW_GAME:
                createNewGame();
                isGameActive = true;
                break;
            case LOAD_GAME_FROM_XML:
                boolean isXmlFileOK = false;
                while (!isXmlFileOK)
                {
                    try
                    {
                        loadGameFromXML();
                        isXmlFileOK = true;
                    }
                    catch (FileNameEnteredInvalidException f)
                    {
                        f.printMessage();
                    }
                    catch (IOException i)
                    {
                        ui.printIOExceptionMessage();
                    }
                    catch (XmlIsInvalidException x)
                    {
                        x.printMessage();
                    }
                    catch (JAXBException j)
                    {
                        ui.printJAXBExceptionMessage();
                    }
                    catch (SAXException s)
                    {
                        ui.printValidateErrorMessage();
                    }
                    catch (FileSuffixsIsNotXmlException f)
                    {
                        f.printMessage();
                    }
                }

                isGameActive = true;
                break;

            case QUIT:
                isGameActive = false;
                break;
        }

        return isGameActive;
    }

    @SuppressWarnings("static-access")
    public final void createNewGame()
    {

        int boardSize = 0, numOfLadders = 0, numPlayers = 0, numSoldiersToWin = 0;
        int currPlayerType = 0, numCompPlayers = 1;
        Player.PlayerType[] playerTypes;
        ArrayList<String> playerNames;
        boolean isInputLegal = false;

        while (!isInputLegal)
        {
            try
            {
                boardSize = ui.getBoardSize();
                isInputLegal = true;
            }
            catch (InputMismatchException e)
            {
                ui.printInputMisMatchExceptionMessage();
            }
            catch (NumberOutOfRangeExceptionUI e)
            {
                e.printMessage();
            }
        }

        isInputLegal = false;
        while (!isInputLegal)
        {
            try
            {
                numOfLadders = ui.getNumberOfLadders(boardSize);
                isInputLegal = true;
            }
            catch (InputMismatchException e)
            {
                ui.printInputMisMatchExceptionMessage();
            }
            catch (NumberOutOfRangeExceptionUI e)
            {
                e.printMessage();
            }
        }

        isInputLegal = false;
        while (!isInputLegal)
        {
            try
            {
                numPlayers = ui.getNumberOfPlayers();
                isInputLegal = true;
            }
            catch (InputMismatchException e)
            {
                ui.printInputMisMatchExceptionMessage();
            }
            catch (NumberOutOfRangeExceptionUI e)
            {
                e.printMessage();
            }
        }

        playerTypes = new Player.PlayerType[numPlayers];
        playerNames = new ArrayList<String>();

        int i;
        for (i = 0; i < numPlayers; i++)
        {
            isInputLegal = false;
            while (!isInputLegal)
            {
                try
                {
                    currPlayerType = ui.getPlayerType(i);
                    isInputLegal = true;
                }
                catch (InputMismatchException e)
                {
                    ui.printInputMisMatchExceptionMessage();
                }
                catch (NumberOutOfRangeExceptionUI e)
                {
                    e.printMessage();
                }
            }

            if (currPlayerType == COMP)
            {
                playerTypes[i] = Player.PlayerType.COMP;
                playerNames.add(i, "Comp" + numCompPlayers);
                numCompPlayers++;
            }
            else if (currPlayerType == HUMAN)
            {
                isInputLegal = false;
                playerTypes[i] = Player.PlayerType.HUMAN;
                String tmpName = null;
                while (!isInputLegal)
                {
                    try
                    {
                        tmpName = ui.getPlayerName(i);
                        if (playerNames.contains(tmpName))
                        {
                            throw (new DuplicatePlayerNamesException());
                        }
                        else
                        {
                            playerNames.add(i, tmpName);
                            isInputLegal = true;
                        }

                    }
                    catch (DuplicatePlayerNamesException e)
                    {
                        e.printMessage(tmpName);
                    }
                }
            }
        }

        isInputLegal = false;
        while (!isInputLegal)
        {
            try
            {
                numSoldiersToWin = ui.getNumberOfSoldiersToWin();
                isInputLegal = true;
            }
            catch (InputMismatchException e)
            {
                ui.printInputMisMatchExceptionMessage();
            }
            catch (NumberOutOfRangeExceptionUI e)
            {
                e.printMessage();
            }
        }

        m_game = new Game(boardSize, numOfLadders, numSoldiersToWin, numPlayers, playerNames, playerTypes);
        ui.printGameBoard(boardSize, m_game.getBoard().getCells(), m_game.getPlayerList());

    }

    public void loadGameFromXML() throws FileNameEnteredInvalidException, IOException, JAXBException,
                                         XmlIsInvalidException, SAXException, FileSuffixsIsNotXmlException
    {
        File file = new File(ui.getXmlFilePath());
        if (!file.canRead())
        {
            throw new FileNameEnteredInvalidException();
        }
        Source xmlFile = new StreamSource(file);
        JAXBContext jc = JAXBContext.newInstance(Snakesandladders.class);
        Unmarshaller u = jc.createUnmarshaller();
        Snakesandladders sal_xml = (Snakesandladders) u.unmarshal(xmlFile);
        SchemaFactory sFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sFactory.newSchema(new File("snakesandladders.xsd"));
        Validator validator = schema.newValidator();
        validator.validate(xmlFile);

        m_game = new Game(sal_xml);
        if (m_game != null)
        {
            ui.printGameBoard(m_game.getBoard().getBoardSize(), m_game.getBoard().getCells(), m_game.getPlayerList());
        }
    }

    public void setIteratorOnFirstPlayer(Iterator<Player> itr, Player first)
    {
        Player tmpPlayer = null;
        while (itr.hasNext() && first != tmpPlayer)
        {
            tmpPlayer = itr.next();
        }
    }

    public boolean playGame()
    {
        boolean isThereWinner = false, gameIsActive = true;
        LinkedList<Player> players = m_game.getPlayerList();
        int choice = 0, soldierNum = 0;
        String winner = null;
        TurnData currTurnData;
        ListIterator<Player> itr = players.listIterator();
        Player currPlayer = m_game.getCurrPlayer();
        setIteratorOnFirstPlayer(itr, currPlayer);

        while (!isThereWinner)
        {
            if (currPlayer.getType() == Player.PlayerType.HUMAN)
            {
                boolean isInputLegal = false;
                while (!isInputLegal)
                {
                    try
                    {
                        choice = ui.getPlayerTurnOption(currPlayer.getPlayerName());
                        isInputLegal = true;
                    }
                    catch (InputMismatchException e)
                    {
                        ui.printInputMisMatchExceptionMessage();
                    }
                    catch (NumberOutOfRangeExceptionUI e)
                    {
                        e.printMessage();
                    }
                }//end trycatch while
                switch (choice)
                {
                    case CONTINUE:
                    {
                        isInputLegal = false;
                        while (!isInputLegal)
                        {
                            try
                            {
                                soldierNum = ui.getSoldierToMove(currPlayer.getSoldiersPos(), m_game.getBoard().getLastCellNum(), currPlayer.getPlayerName());
                                if (currPlayer.getSoldiersPos()[soldierNum - 1] == m_game.getBoard().getLastCellNum())
                                {
                                    throw new ImmovableSoldierException();
                                }
                                isInputLegal = true;
                            }
                            catch (InputMismatchException e)
                            {
                                ui.printInputMisMatchExceptionMessage();
                            }
                            catch (NumberOutOfRangeExceptionUI e)
                            {
                                e.printMessage();
                            }
                            catch (ImmovableSoldierException e)
                            {
                                e.printMessage(soldierNum);
                            }
                        }
                        currTurnData = currPlayer.playTurn(soldierNum);
                        ui.printTurnData(currTurnData, currPlayer.getPlayerName());
                        ui.printGameBoard(m_game.getBoard().getBoardSize(), m_game.getBoard().getCells(), players);
                        isThereWinner = isWinner(currPlayer);
                        if (isThereWinner)
                        {
                            winner = currPlayer.getPlayerName();
                        }
                        else
                        {
                            if (itr.hasNext())
                            {
                                currPlayer = itr.next();

                            }
                            else
                            {
                                currPlayer = players.getFirst();
                                itr = players.listIterator();
                                if (itr.hasNext())
                                {
                                    itr.next();
                                }
                            }
                            m_game.setCurrPlayer(currPlayer);
                        }
                        break;
                    }
                    case SAVE_GAME_TO_XML:
                    {
                        boolean isFileToSaveOk = false;
                        if (lastSavedFileName != null)
                        {
                            isInputLegal = false;
                            while (!isInputLegal)
                            {
                                try
                                {
                                    choice = ui.getSaveToXmlOption();
                                    isInputLegal = true;
                                }
                                catch (InputMismatchException e)
                                {
                                    ui.printInputMisMatchExceptionMessage();
                                }
                                catch (NumberOutOfRangeExceptionUI e)
                                {
                                    e.printMessage();
                                }
                            }

                            if (choice == SAVE_TO_LAST_SAVED_FILE)
                            {
                                xmlFile = new File(lastSavedFileName);
                            }
                            else
                            {
                                try
                                {
                                    lastSavedFileName = ui.getXmlFilePath();
                                    xmlFile = new File(lastSavedFileName);
                                    isFileToSaveOk = true;
                                }
                                catch (FileSuffixsIsNotXmlException f)
                                {
                                    f.printMessage();
                                }
                            }
                        }
                        else
                        {
                            try
                            {
                                lastSavedFileName = ui.getXmlFilePath();
                                xmlFile = new File(lastSavedFileName);
                                isFileToSaveOk = true;
                            }
                            catch (FileSuffixsIsNotXmlException f)
                            {
                                f.printMessage();
                            }
                        }

                        if (isFileToSaveOk)
                        {
                            try
                            {
                                m_game.saveGameToXml(xmlFile);
                            }
                            catch (JAXBException j)
                            {
                                ui.printJAXBExceptionMessage();
                            }
                        }
                        break;
                    }
                    case QUIT:
                    {
                        ui.printQuitter(currPlayer.getPlayerName());
                        m_game.removePlayerFromGame(itr, currPlayer);
                        if (itr.hasNext())
                        {
                            currPlayer = itr.next();
                        }
                        else
                        {
                            currPlayer = players.getFirst();
                            itr = players.listIterator();
                            if (itr.hasNext())
                            {
                                itr.next();
                            }
                        }
                        m_game.setCurrPlayer(currPlayer);

                        if (m_game.getPlayerList().size() == 1)
                        {
                            isThereWinner = true;
                            winner = m_game.getPlayerList().getFirst().getPlayerName();
                        }
                        break;
                    }
                }//end switch
            }//end if 
            else if (currPlayer.getType() == Player.PlayerType.COMP)
            {
                //print what happens
                ui.printCompTurn(currPlayer.getPlayerName());
                currTurnData = currPlayer.playTurn(Player.COMP_PLAYER_SOLDIER);
                ui.printTurnData(currTurnData, currPlayer.getPlayerName());
                ui.printGameBoard(m_game.getBoard().getBoardSize(), m_game.getBoard().getCells(), players);
                isThereWinner = isWinner(currPlayer);
                if (isThereWinner)
                {
                    winner = currPlayer.getPlayerName();
                }
                else
                {
                    if (itr.hasNext())
                    {
                        currPlayer = itr.next();
                    }
                    else
                    {
                        currPlayer = players.getFirst();
                        itr = players.listIterator();
                        if (itr.hasNext())
                        {
                            itr.next();
                        }
                    }
                    m_game.setCurrPlayer(currPlayer);
                }
            }
        }
        ui.printWinner(winner);
        ui.printGameOverMessage();

        return createNewOrLoadedGame();
    }

    private boolean isWinner(Player player)
    {
        int lastCell = m_game.getBoard().getLastCellNum();
        if (m_game.getBoard().getCell(lastCell).getSoldiersInCell()[player.getPlayerNum() - 1] == m_game.getNumSoldiersToWin())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
