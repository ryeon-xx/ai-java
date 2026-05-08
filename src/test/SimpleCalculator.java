package test;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleCalculator extends JFrame implements ActionListener {

    private JTextField num1Field;
    private JTextField num2Field;
    private JLabel resultLabel;
    private JButton[] numberButtons = new JButton[10];
    private JButton addButton, subtractButton, multiplyButton, divideButton, clearButton, equalButton;

    // Pastel Theme Colors
    private final Color PASTEL_BLUE = new Color(204, 229, 255);
    private final Color BUTTON_BG = Color.WHITE;
    private final Color BORDER_COLOR = Color.BLACK;
    private final Color SHADOW_COLOR = new Color(180, 180, 180);
    private final Color TEXT_COLOR = new Color(50, 50, 50);

    private JTextField activeField;
    private String lastOperation = "+";

    public SimpleCalculator() {
        setTitle("Pastel Simple Calculator");
        setSize(400, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(PASTEL_BLUE);

        // --- Display Area ---
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new GridLayout(3, 1, 10, 10));
        displayPanel.setOpaque(false);
        displayPanel.setBorder(new EmptyBorder(20, 20, 10, 20));

        num1Field = createStyledTextField();
        num2Field = createStyledTextField();
        activeField = num1Field; // Default active field

        num1Field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                activeField = num1Field;
            }
        });
        num2Field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                activeField = num2Field;
            }
        });

        resultLabel = new JLabel("결과 : 0");
        resultLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 24));
        resultLabel.setForeground(TEXT_COLOR);
        resultLabel.setHorizontalAlignment(JLabel.CENTER);

        displayPanel.add(num1Field);
        displayPanel.add(num2Field);
        displayPanel.add(resultLabel);

        // --- Buttons Area ---
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(4, 4, 10, 10));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setBorder(new EmptyBorder(0, 20, 20, 20));

        // Create number buttons
        for (int i = 1; i <= 9; i++) {
            numberButtons[i] = createStyledButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
        }
        numberButtons[0] = createStyledButton("0");
        numberButtons[0].addActionListener(this);

        // Create operation buttons
        addButton = createStyledButton("+");
        subtractButton = createStyledButton("-");
        multiplyButton = createStyledButton("*");
        divideButton = createStyledButton("/");
        clearButton = createStyledButton("C");
        equalButton = createStyledButton("=");

        addButton.addActionListener(this);
        subtractButton.addActionListener(this);
        multiplyButton.addActionListener(this);
        divideButton.addActionListener(this);
        clearButton.addActionListener(this);
        equalButton.addActionListener(this);

        // Add buttons to panel (Layout: 4x4)
        // Row 1: 7, 8, 9, /
        buttonsPanel.add(numberButtons[7]);
        buttonsPanel.add(numberButtons[8]);
        buttonsPanel.add(numberButtons[9]);
        buttonsPanel.add(divideButton);

        // Row 2: 4, 5, 6, *
        buttonsPanel.add(numberButtons[4]);
        buttonsPanel.add(numberButtons[5]);
        buttonsPanel.add(numberButtons[6]);
        buttonsPanel.add(multiplyButton);

        // Row 3: 1, 2, 3, -
        buttonsPanel.add(numberButtons[1]);
        buttonsPanel.add(numberButtons[2]);
        buttonsPanel.add(numberButtons[3]);
        buttonsPanel.add(subtractButton);

        // Row 4: C, 0, =, +
        buttonsPanel.add(clearButton);
        buttonsPanel.add(numberButtons[0]);
        buttonsPanel.add(equalButton);
        buttonsPanel.add(addButton);

        // Add panels to frame
        add(displayPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Malgun Gothic", Font.PLAIN, 20));
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setBackground(Color.WHITE);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return textField;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Malgun Gothic", Font.BOLD, 18));
        button.setBackground(BUTTON_BG);
        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);

        // Custom Border for Shadow Effect
        Border line = BorderFactory.createLineBorder(BORDER_COLOR, 1);
        Border shadow = BorderFactory.createMatteBorder(0, 0, 3, 3, SHADOW_COLOR);
        button.setBorder(new CompoundBorder(shadow, line));

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // Handle number buttons
        if (command.length() == 1 && Character.isDigit(command.charAt(0))) {
            activeField.setText(activeField.getText() + command);
            return;
        }

        if (command.equals("C")) {
            num1Field.setText("");
            num2Field.setText("");
            resultLabel.setText("결과 : 0");
            activeField = num1Field;
            lastOperation = "+";
            num1Field.requestFocus();
            return;
        }

        if (command.equals("=")) {
            calculate(lastOperation);
            return;
        }

        // Operation button clicked
        lastOperation = command;
        calculate(command);
    }

    private void calculate(String operation) {
        double num1, num2;
        try {
            String t1 = num1Field.getText();
            String t2 = num2Field.getText();
            if (t1.isEmpty()) t1 = "0";
            if (t2.isEmpty()) t2 = "0";
            num1 = Double.parseDouble(t1);
            num2 = Double.parseDouble(t2);
        } catch (NumberFormatException ex) {
            resultLabel.setText("결과 : 입력 오류");
            return;
        }

        double result = 0;
        switch (operation) {
            case "+": result = num1 + num2; break;
            case "-": result = num1 - num2; break;
            case "*": result = num1 * num2; break;
            case "/":
                if (num2 == 0) {
                    resultLabel.setText("결과 : 0으로 나눌 수 없음");
                    return;
                }
                result = num1 / num2;
                break;
        }

        String resultStr;
        if (result == (long) result) {
            resultStr = String.format("%d", (long) result);
        } else {
            resultStr = String.format("%.2f", result);
        }
        resultLabel.setText("결과 : " + resultStr);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimpleCalculator());
    }
}
