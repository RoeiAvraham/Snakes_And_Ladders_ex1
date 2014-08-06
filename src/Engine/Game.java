/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;

import Exception.*;
import java.io.File;
import java.math.BigInteger;
import java.util.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import xmlPackage.PlayerType;
import xmlPackage.Snakesandladders;

/**
 *
 * @author Roei
 */
public class Game
{
    private int m_numSoldiersToWin;
    private int m_numPlayers;
    private GameBoard m_board;
    private LinkedList<Player> playerList;
    private Player currPlayer;
    private static final String XML_GAME_NAME = "snakesAndLaddersGame";

    public Game(Snakesandladders gameXml) throws XmlIsInvalidException
    {
        LinkedList<String> playerNames = new LinkedList<String>();
        m_numSoldiersToWin = gameXml.getNumberOfSoldiers();
        m_numPlayers = gameXml.getPlayers().getPlayer().size();
        playerList = new LinkedList<Player>();

        m_board = new GameBoard(gameXml);
        int i = 0;

        checkIfXmlGameAlreadyFinished(m_numSoldiersToWin, gameXml.getBoard().getCells().getCell(), gameXml.getBoard().getSize());
        checkNumOfSoldiersXml(gameXml.getBoard().getCells().getCell(), gameXml.getPlayers().getPlayer());

        for (xmlPackage.Players.Player p : gameXml.getPlayers().getPlayer())
        {
            if (playerNames.contains(p.getName()))
            {
                throw new DuplicatePlayerNamesXmlException();
            }
            playerNames.add(i, p.getName());
            if (p.getType() == PlayerType.HUMAN)
            {
                playerList.add(new HumanPlayer(++i, p.getName(), m_board, Player.LoadedFrom.XML));
            }
            else if (p.getType() == PlayerType.COMPUTER)
            {
                playerList.add(new CompPlayer(++i, p.getName(), m_board, Player.LoadedFrom.XML));
            }
        }

        m_board.setPlayersPosFromXml(gameXml, playerList, playerNames);
        getCurrentPlayerFromXml(gameXml, playerNames);
    }

    public Game(int boardSize, int numOfLadders, int numSoldiersToWin, int numPlayers, ArrayList<String> playerNames, Player.PlayerType[] playerTypes)
    {
        m_board = new GameBoard(boardSize, numOfLadders, numPlayers);
        m_numSoldiersToWin = numSoldiersToWin;
        m_numPlayers = numPlayers;
        playerList = new LinkedList<>();
        int i;
        for (i = 0; i < m_numPlayers; i++)
        {
            if (playerTypes[i] == Player.PlayerType.COMP)
            {
                playerList.add(new CompPlayer(i + 1, playerNames.get(i), m_board, Player.LoadedFrom.REG));
            }
            else
            {
                playerList.add(new HumanPlayer(i + 1, playerNames.get(i), m_board, Player.LoadedFrom.REG));
            }
        }

        int j;
        for (i = 0; i < numPlayers; i++)
        {
            for (j = 0; j < Player.NUM_SOLDIERS; j++)
            {
                m_board.getFirstCell().insertSoldier(i + 1);
            }
        }
        currPlayer = playerList.getFirst();
    }

    public void setCurrPlayer(Player curr)
    {
        this.currPlayer = curr;
    }

    public void saveGameToXml(File file) throws JAXBException
    {
        JAXBContext jc = JAXBContext.newInstance(Snakesandladders.class);
        Marshaller m = jc.createMarshaller();
        xmlPackage.Snakesandladders gameToSave = new Snakesandladders();
        parseBoardToXml(gameToSave);
        gameToSave.setCurrentPlayer(currPlayer.getPlayerName());
        gameToSave.setPlayers(parsePlayersToXml(gameToSave));
        gameToSave.setNumberOfSoldiers(this.m_numSoldiersToWin);
        gameToSave.setName(XML_GAME_NAME);

        m.setProperty(m.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "snakesandladders.xsd");
        m.setProperty(m.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(gameToSave, file);
    }

    public void checkNumOfSoldiersXml(List<xmlPackage.Cell> xmlCells, List<xmlPackage.Players.Player> playerListXml)
            throws XmlIsInvalidException
    {
        HashMap<String, Integer> numSoldiers = new HashMap();
        for (xmlPackage.Players.Player p : playerListXml)
        {
            numSoldiers.put(p.getName(), m_board.EMPTY);
        }

        for (xmlPackage.Cell c : xmlCells)
        {
            for (xmlPackage.Cell.Soldiers s : c.getSoldiers())
            {
                numSoldiers.put(s.getPlayerName(), numSoldiers.get(s.getPlayerName()) + s.getCount());
            }
        }

        for (Map.Entry<String, Integer> entry : numSoldiers.entrySet())
        {
            if (entry.getValue() != Player.NUM_SOLDIERS)
            {
                throw new PlayerHasNoFourSoldiersXmlException();
            }
        }

    }

    private void parseBoardToXml(xmlPackage.Snakesandladders gameToSave)
    {
        xmlPackage.Board xmlBoard = new xmlPackage.Board();
        gameToSave.setBoard(xmlBoard);
        gameToSave.getBoard().setSize(m_board.getBoardSize());
        xmlPackage.Cells xmlCells = new xmlPackage.Cells();
        xmlBoard.setCells(xmlCells);
        xmlPackage.Snakes xmlSnakes = new xmlPackage.Snakes();
        xmlPackage.Ladders xmlLadders = new xmlPackage.Ladders();
        xmlBoard.setLadders(xmlLadders);
        xmlBoard.setSnakes(xmlSnakes);
        for (Cell c : m_board.getCells())
        {
            if (c.isThereSoldiers())
            {
                xmlPackage.Cell xmlCell = new xmlPackage.Cell();
                xmlCell.setNumber(BigInteger.valueOf(c.getCellNum()));

                int i;
                for (i = 0; i < c.getSoldiersInCell().length; i++)
                {
                    if (c.getSoldiersInCell()[i] != GameBoard.EMPTY)
                    {
                        xmlPackage.Cell.Soldiers xmlCellSoldier = new xmlPackage.Cell.Soldiers();
                        xmlCellSoldier.setPlayerName(getPlayerByNum(i + 1).getPlayerName());
                        xmlCellSoldier.setCount(c.getSoldiersInCell()[i]);
                        xmlCell.getSoldiers().add(xmlCellSoldier);
                    }
                }
                xmlCells.getCell().add(xmlCell);
            }

            if (c.getDest() != Cell.NO_DEST)
            {
                if (c.getCellNum() < c.getDest())
                {
                    xmlPackage.Ladders.Ladder xmlLadder = new xmlPackage.Ladders.Ladder();
                    xmlLadder.setFrom(BigInteger.valueOf(c.getCellNum()));
                    xmlLadder.setTo(BigInteger.valueOf(c.getDest()));
                    xmlLadders.getLadder().add(xmlLadder);
                }
                else
                {
                    xmlPackage.Snakes.Snake xmlSnake = new xmlPackage.Snakes.Snake();
                    xmlSnake.setFrom(BigInteger.valueOf(c.getCellNum()));
                    xmlSnake.setTo(BigInteger.valueOf(c.getDest()));
                    xmlSnakes.getSnake().add(xmlSnake);
                }
            }
        }
    }

    private void getCurrentPlayerFromXml(Snakesandladders gameXml, LinkedList<String> playerNames) throws XmlIsInvalidException
    {
        if (!playerNames.contains(gameXml.getCurrentPlayer()))
        {
            throw new CurrentPlayerIsNotInPlayerListXml();
        }
        currPlayer = playerList.get(playerNames.indexOf(gameXml.getCurrentPlayer()));
    }

    private Player getPlayerByNum(int playerNum)
    {
        int i;
        Player res = null;
        boolean isFound = false;
        for (i = 0; i < playerList.size() && !isFound; i++)
        {
            if (playerList.get(i).getPlayerNum() == playerNum)
            {
                isFound = true;
                res = playerList.get(i);
            }
        }
        return res;
    }

    public void checkIfXmlGameAlreadyFinished(int numSoldiersToWin, List<xmlPackage.Cell> cellsXml, int xmlBoardSize)
            throws XmlIsInvalidException
    {
        for (xmlPackage.Cell c : cellsXml)
        {
            if (c.getNumber().intValue() == xmlBoardSize * xmlBoardSize)
            {
                for (xmlPackage.Cell.Soldiers s : c.getSoldiers())
                {
                    if (s.getCount() == numSoldiersToWin)
                    {
                        throw new GameAlreadyFinishedXmlException();
                    }
                }
            }
        }
    }

    public xmlPackage.Players parsePlayersToXml(xmlPackage.Snakesandladders gameToSave)
    {
        xmlPackage.Players xmlPlayers = new xmlPackage.Players();
        for (Player p : playerList)
        {
            xmlPackage.Players.Player xmlPlayer = new xmlPackage.Players.Player();
            xmlPlayer.setName(p.getPlayerName());
            if (p.getType() == Player.PlayerType.COMP)
            {
                xmlPlayer.setType(xmlPackage.PlayerType.COMPUTER);
            }
            else
            {
                xmlPlayer.setType(xmlPackage.PlayerType.HUMAN);
            }
            xmlPlayers.getPlayer().add(xmlPlayer);

        }
        return xmlPlayers;
    }

    public Player getCurrPlayer()
    {
        return currPlayer;
    }

    public GameBoard getBoard()
    {
        return m_board;
    }

    public LinkedList<Player> getPlayerList()
    {
        return playerList;
    }

    public void removePlayerFromGame(Iterator<Player> itr, Player player)
    {
        player.removePlayerSoldiersFromGame();
        itr.remove();
    }

    public int getNumSoldiersToWin()
    {
        return m_numSoldiersToWin;
    }
}
