import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeApp extends JFrame {
    private JButton[][] buttons = new JButton[3][3];
    private boolean isPlayerX = true; // True for X, False for O
    private JLabel statusLabel;

    public TicTacToeApp() {
        // Set up the JFrame
        setTitle("Tic Tac Toe");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set up the status label
        statusLabel = new JLabel("Player X's turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Create the grid for the game
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(3, 3));
        initializeGrid(gridPanel);

        // Add a reset button
        JButton resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Arial", Font.BOLD, 16));
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        // Layout the components
        setLayout(new BorderLayout());
        add(statusLabel, BorderLayout.NORTH);
        add(gridPanel, BorderLayout.CENTER);
        add(resetButton, BorderLayout.SOUTH);

        // Make the frame visible
        setVisible(true);
    }

    private void initializeGrid(JPanel gridPanel) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton("");
                buttons[row][col].setFont(new Font("Arial", Font.BOLD, 40));
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].addActionListener(new ButtonClickListener(row, col));
                gridPanel.add(buttons[row][col]);
            }
        }
    }

    private void resetGame() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setEnabled(true);
            }
        }
        isPlayerX = true;
        statusLabel.setText("Player X's turn");
    }

    private boolean checkWinner(String symbol) {
        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(symbol) &&
                buttons[i][1].getText().equals(symbol) &&
                buttons[i][2].getText().equals(symbol)) {
                return true;
            }

            if (buttons[0][i].getText().equals(symbol) &&
                buttons[1][i].getText().equals(symbol) &&
                buttons[2][i].getText().equals(symbol)) {
                return true;
            }
        }

        if (buttons[0][0].getText().equals(symbol) &&
            buttons[1][1].getText().equals(symbol) &&
            buttons[2][2].getText().equals(symbol)) {
            return true;
        }

        if (buttons[0][2].getText().equals(symbol) &&
            buttons[1][1].getText().equals(symbol) &&
            buttons[2][0].getText().equals(symbol)) {
            return true;
        }

        return false;
    }

    private boolean isDraw() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private class ButtonClickListener implements ActionListener {
        private int row, col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!buttons[row][col].getText().isEmpty()) {
                return;
            }

            String currentPlayer = isPlayerX ? "X" : "O";
            buttons[row][col].setText(currentPlayer);
            buttons[row][col].setEnabled(false);

            if (checkWinner(currentPlayer)) {
                statusLabel.setText("Player " + currentPlayer + " wins!");
                disableAllButtons();
            } else if (isDraw()) {
                statusLabel.setText("It's a draw!");
            } else {
                isPlayerX = !isPlayerX;
                statusLabel.setText("Player " + (isPlayerX ? "X" : "O") + "'s turn");
            }
        }

        private void disableAllButtons() {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    buttons[row][col].setEnabled(false);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToeApp::new);
    }
}
