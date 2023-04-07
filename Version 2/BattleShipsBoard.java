import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class BattleShipsBoard extends JFrame {
    
    private JButton[][] playerBoard;
    private JButton[][] computerBoard;
    private int boardSize;
    
    public BattleShipsBoard() {
        boardSize = 10; //default board size is 10
        initComponents();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel boardPanel = new JPanel(new GridLayout(boardSize+1, boardSize+1));
        JPanel playerPanel = new JPanel(new GridLayout(boardSize, boardSize));
        JPanel computerPanel = new JPanel(new GridLayout(boardSize, boardSize));
        JLabel playerLabel = new JLabel("Player Board");
        JLabel computerLabel = new JLabel("Computer Board");
        JButton resizeButton = new JButton("Resize Board");
        
        //create the player board
        playerBoard = new JButton[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                playerBoard[i][j] = new JButton("");
                playerPanel.add(playerBoard[i][j]);
            }
        }
        
        //create the computer board
        computerBoard = new JButton[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                computerBoard[i][j] = new JButton("");
                computerPanel.add(computerBoard[i][j]);
            }
        }
        
        //add the labels and boards to the board panel
        boardPanel.add(new JLabel(""));
        for (int i = 0; i < boardSize; i++) {
            boardPanel.add(new JLabel(Character.toString((char)(65+i))));
        }
        for (int i = 0; i < boardSize; i++) {
            boardPanel.add(new JLabel(Integer.toString(i+1)));
            for (int j = 0; j < boardSize; j++) {
                boardPanel.add(playerBoard[i][j]);
            }
        }
        boardPanel.add(playerLabel);
        for (int i = 0; i < boardSize; i++) {
            boardPanel.add(new JLabel(Character.toString((char)(65+i))));
        }
        for (int i = 0; i < boardSize; i++) {
            boardPanel.add(new JLabel(Integer.toString(i+1)));
            for (int j = 0; j < boardSize; j++) {
                boardPanel.add(computerBoard[i][j]);
            }
        }
        boardPanel.add(computerLabel);
        
        //add the resize button to the main panel
        resizeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Enter new board size: ");
                try {
                    int newSize = Integer.parseInt(input);
                    if (newSize > 0 && newSize <= 26) {
                        boardSize = newSize;
                        refreshBoards();
                    } else {
                        JOptionPane.showMessageDialog(null, "Board size must be between 1 and 26.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number between 1 and 26.");
                }
            }
        });
        mainPanel.add(resizeButton, BorderLayout.NORTH);
        mainPanel.add(boardPanel, BorderLayout.CENTER);
        
        //set up the frame
    }
}  
