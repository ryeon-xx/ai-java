package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleCalculator extends JFrame implements ActionListener {

    private JTextField num1Field;
    private JTextField num2Field;
    private JButton addButton, subtractButton, multiplyButton, divideButton; // Operations
    private JButton clearButton; // Added for a "Start/Select" feel
    private JLabel resultLabel;

    // Retro console colors
    private final Color CONSOLE_BODY_COLOR = new Color(50, 50, 50);       // Dark Grey
    private final Color SCREEN_BEZEL_COLOR = new Color(120, 120, 120);    // Lighter Grey
    private final Color SCREEN_BACKGROUND_COLOR = new Color(0, 100, 0);  // Dark Green (like old monitors)
    private final Color PIXEL_TEXT_COLOR = new Color(0, 255, 0);         // Bright Green (like old monitors)
    private final Color BUTTON_RED = new Color(200, 50, 50);
    private final Color BUTTON_BLUE = new Color(50, 50, 200);
    private final Color BUTTON_YELLOW = new Color(200, 200, 50);
    private final Color BUTTON_GREY = new Color(180, 180, 180);
    private final Color BUTTON_DARK_GREY = new Color(80, 80, 80);


    public SimpleCalculator() {
        setTitle("Pixel Game Console Calculator");
        setSize(500, 600); // Larger size for console look
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 0)); // No gaps initially, manage padding with panels

        // --- Console Body Panel ---
        JPanel consolePanel = new JPanel();
        consolePanel.setLayout(new BorderLayout(10, 10)); // Some internal spacing for console elements
        consolePanel.setBackground(CONSOLE_BODY_COLOR);
        consolePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5)); // Outer console border

        // --- Screen Area ---
        JPanel screenPanel = new JPanel(new GridBagLayout());
        screenPanel.setBackground(SCREEN_BEZEL_COLOR);
        screenPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3)); // Bezel border
        screenPanel.setPreferredSize(new Dimension(300, 150)); // Make screen area larger

        GridBagConstraints gbcScreen = new GridBagConstraints();
        gbcScreen.insets = new Insets(5, 5, 5, 5);
        gbcScreen.fill = GridBagConstraints.HORIZONTAL;

        // Input Fields (styled to look pixelated/retro)
        num1Field = new JTextField();
        num2Field = new JTextField();
        configurePixelTextField(num1Field);
        configurePixelTextField(num2Field);

        // Result Label (styled to look pixelated/retro)
        resultLabel = new JLabel("0"); // Start with 0, like a console display
        configurePixelResultLabel(resultLabel);

        // Add components to screen panel
        gbcScreen.gridx = 0; gbcScreen.gridy = 0; gbcScreen.weightx = 1.0;
        screenPanel.add(num1Field, gbcScreen);
        gbcScreen.gridx = 0; gbcScreen.gridy = 1;
        screenPanel.add(num2Field, gbcScreen);
        gbcScreen.gridx = 0; gbcScreen.gridy = 2; gbcScreen.gridwidth = 2;
        screenPanel.add(resultLabel, gbcScreen);

        // --- Controls Area (Buttons) ---
        JPanel controlsPanel = new JPanel();
        controlsPanel.setBackground(CONSOLE_BODY_COLOR); // Same as console body
        controlsPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for precise button placement

        GridBagConstraints gbcControls = new GridBagConstraints();
        gbcControls.insets = new Insets(5, 5, 5, 5);

        // D-Pad like structure (simulated with buttons)
        JButton upButton = createPixelButton(" "); // Placeholder for D-pad up
        JButton downButton = createPixelButton(" "); // Placeholder for D-pad down
        JButton leftButton = createPixelButton(" "); // Placeholder for D-pad left
        JButton rightButton = createPixelButton(" "); // Placeholder for D-pad right

        // Operation Buttons (A/B style)
        addButton = createPixelButton("+");
        subtractButton = createPixelButton("-");
        multiplyButton = createPixelButton("*");
        divideButton = createPixelButton("/");

        // Clear Button (like Start/Select)
        clearButton = createPixelButton("CLR"); // Clear button
        clearButton.setBackground(BUTTON_DARK_GREY);
        clearButton.setForeground(Color.WHITE);

        // Layout for D-pad
        gbcControls.gridx = 1; gbcControls.gridy = 0; controlsPanel.add(upButton, gbcControls);
        gbcControls.gridx = 0; gbcControls.gridy = 1; controlsPanel.add(leftButton, gbcControls);
        gbcControls.gridx = 1; gbcControls.gridy = 1; controlsPanel.add(new JLabel(""), gbcControls); // Center gap
        gbcControls.gridx = 2; gbcControls.gridy = 1; controlsPanel.add(rightButton, gbcControls);
        gbcControls.gridx = 1; gbcControls.gridy = 2; controlsPanel.add(downButton, gbcControls);

        // Layout for operation buttons (A/B style)
        gbcControls.gridx = 3; gbcControls.gridy = 0; controlsPanel.add(multiplyButton, gbcControls); // M
        gbcControls.gridx = 4; gbcControls.gridy = 0; controlsPanel.add(divideButton, gbcControls); // /

        gbcControls.gridx = 3; gbcControls.gridy = 1; controlsPanel.add(addButton, gbcControls); // +
        gbcControls.gridx = 4; gbcControls.gridy = 1; controlsPanel.add(subtractButton, gbcControls); // -

        // Layout for Start/Select buttons
        gbcControls.gridx = 2; gbcControls.gridy = 3; gbcControls.gridwidth = 1;
        controlsPanel.add(clearButton, gbcControls); // Clear Button

        // Add action listeners
        addButton.addActionListener(this);
        subtractButton.addActionListener(this);
        multiplyButton.addActionListener(this);
        divideButton.addActionListener(this);
        clearButton.addActionListener(this); // Listener for clear button

        // Add screen and controls to the main console panel
        consolePanel.add(screenPanel, BorderLayout.NORTH);
        consolePanel.add(controlsPanel, BorderLayout.CENTER);

        // Add the console panel to the frame
        add(consolePanel, BorderLayout.CENTER);

        // Center the window
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Helper method to configure text fields for pixel look
    private void configurePixelTextField(JTextField textField) {
        textField.setBackground(SCREEN_BACKGROUND_COLOR);
        textField.setForeground(PIXEL_TEXT_COLOR);
        // Attempting a pixel font is hard with standard Swing. Let's use a monospaced font and adjust size.
        // A fixed-width font can give a blocky feel.
        textField.setFont(new Font("Monospaced", Font.BOLD, 24)); // Larger, bold, monospaced
        textField.setCaretColor(PIXEL_TEXT_COLOR); // Make caret visible
        textField.setHorizontalAlignment(JTextField.RIGHT); // Align text to the right
        textField.setEditable(true); // User will type numbers into these fields
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SCREEN_BEZEL_COLOR, 5), // Outer bezel for the field
            BorderFactory.createEmptyBorder(5, 5, 5, 5) // Inner padding
        ));
    }
     private void configurePixelResultLabel(JLabel label) {
        label.setBackground(SCREEN_BACKGROUND_COLOR);
        label.setForeground(PIXEL_TEXT_COLOR);
        label.setFont(new Font("Monospaced", Font.BOLD, 36)); // Very large and bold for result
        label.setHorizontalAlignment(JLabel.RIGHT); // Align text to the right
        // Result label doesn't need a border itself, it's within the screen panel
    }

    // Helper method to create pixel-style buttons
    private JButton createPixelButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(60, 60)); // Large buttons
        button.setFont(new Font("Monospaced", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(BUTTON_GREY); // Default button color
        button.setBorderPainted(true);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createRaisedBevelBorder()); // Give it a 3D look

        // Specific button colors
        if (text.equals("+")) button.setBackground(BUTTON_RED);
        if (text.equals("-")) button.setBackground(BUTTON_BLUE);
        if (text.equals("*")) button.setBackground(BUTTON_YELLOW);
        if (text.equals("/")) button.setBackground(new Color(100, 100, 255)); // A shade of blue-purple
        if (text.equals("CLR")) button.setBackground(BUTTON_DARK_GREY);
        if (text.equals(" ")) { // D-pad placeholders
            button.setPreferredSize(new Dimension(50, 50));
            button.setBackground(BUTTON_DARK_GREY);
            button.setBorder(BorderFactory.createLoweredBevelBorder()); // Make it look like a recessed button
            button.setEnabled(false); // These are just visual placeholders
        }

        return button;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // This part remains largely the same, but we might need to adjust how input is handled.
        // For a true game console style, numbers would be entered via number buttons.
        // However, the user asked for a calculator, and kept the input fields.
        // So, I'll keep the input fields and let the user type numbers into them.
        // The operation buttons will trigger the calculation.

        String command = e.getActionCommand();

        if (command.equals("CLR")) {
            num1Field.setText("");
            num2Field.setText("");
            resultLabel.setText("0");
            return;
        }

        double num1 = 0, num2 = 0;
        boolean validInput = false;

        try {
            String num1Text = num1Field.getText().trim();
            String num2Text = num2Field.getText().trim();

            if (num1Text.isEmpty() || num2Text.isEmpty()) {
                resultLabel.setText("Error");
                return;
            }

            num1 = Double.parseDouble(num1Text);
            num2 = Double.parseDouble(num2Text);
            validInput = true;
        } catch (NumberFormatException ex) {
            resultLabel.setText("Error");
            return;
        }

        if (validInput) {
            double result = 0;
            String operationSymbol = "";

            switch (command) {
                case "+":
                    result = num1 + num2;
                    operationSymbol = "+";
                    break;
                case "-":
                    result = num1 - num2;
                    operationSymbol = "-";
                    break;
                case "*":
                    result = num1 * num2;
                    operationSymbol = "*";
                    break;
                case "/":
                    if (num2 == 0) {
                        resultLabel.setText("Error");
                        return;
                    }
                    result = num1 / num2;
                    operationSymbol = "/";
                    break;
                default:
                    // Should not happen with current buttons
                    return;
            }

            // Format result
            String resultText;
            if (result == (long) result) {
                resultText = String.format("%,.0f", result);
            } else {
                resultText = String.format("%,.2f", result);
            }
            // Display calculation in result label for clarity
            resultLabel.setText(num1 + " " + operationSymbol + " " + num2 + " = " + resultText);
        }
    }

    // Main method
    public static void main(String[] args) {
        // Swing applications should be created and shown on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SimpleCalculator();
            }
        });
    }
}