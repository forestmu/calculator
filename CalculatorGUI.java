import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;

public class CalculatorGUI extends JFrame {
    private JButton[] buttons = new JButton[20];
    private Double num1 = null;
    private Double num2 = null;
    private String operation = null;
    private Boolean isOperatorClicked = false;
    private Boolean isDecimal = false;

    private Calculator calculator = new Calculator();

    public CalculatorGUI() {

        setTitle("My Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField result = new JTextField(20);
        result.setEditable(false);
        result.setPreferredSize(new java.awt.Dimension(0, 40));
        // first row
        JButton clear = new JButton("C");
        buttons[0] = clear;
        JButton delete = new JButton("Del");
        buttons[1] = delete;
        JButton mod = new JButton("%");
        buttons[2] = mod;  
        JButton divide = new JButton("/");
        buttons[3] = divide;
        // second row
        JButton seven = new JButton("7");
        buttons[4] = seven;
        JButton eight = new JButton("8");
        buttons[5] = eight;
        JButton nine = new JButton("9");
        buttons[6] = nine;
        JButton multiply = new JButton("*");
        buttons[7] = multiply;
        // third row
        JButton four = new JButton("4");
        buttons[8] = four;
        JButton five = new JButton("5");
        buttons[9] = five;
        JButton six = new JButton("6");
        buttons[10] = six;
        JButton subtract = new JButton("-");
        buttons[11] = subtract;
        // fourth row
        JButton one = new JButton("1");
        buttons[12] = one;
        JButton two = new JButton("2");
        buttons[13] = two;
        JButton three = new JButton("3");
        buttons[14] = three;
        JButton add = new JButton("+");
        buttons[15] = add;
        // fifth row
        JButton expo = new JButton("e");
        buttons[16] = expo;
        JButton zero = new JButton("0");
        buttons[17] = zero;
        JButton dot = new JButton(".");
        buttons[18] = dot;
        JButton equals = new JButton("=");
        buttons[19] = equals;

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 0, 5));

        for (JButton button : buttons) {
            panel.add(button);
        }

        JPanel content = new JPanel(new BorderLayout(0, 0));
        content.add(result, BorderLayout.NORTH);
        content.add(panel, BorderLayout.CENTER);
        add(content);
        result.setHorizontalAlignment(JTextField.RIGHT); 
        result.setFont(result.getFont().deriveFont(24f));
        
        JButton[] numbers = { zero, one, two, three, four, five, six, seven, eight, nine };
        for (JButton number : numbers) {
            number.addActionListener(e -> {
                if (!isOperatorClicked) {
                    if (num1 == null) {
                        num1 = Double.parseDouble(number.getText());
                    } else {
                        if (!isDecimal) {
                            num1 *= 10;
                            num1 += Double.parseDouble(number.getText());
                        } else {
                            Double val = Double.parseDouble(number.getText());
                            val /= 10;
                            num1 += val;
                        }
                    }
                    result.setText(result.getText() + number.getText());
                } else {
                    if (num2 == null) {
                        num2 = Double.parseDouble(number.getText());
                    } else {
                        if (!isDecimal) {
                            num2 *= 10;
                            num2 += Double.parseDouble(number.getText());
                        } else {
                            Double val = Double.parseDouble(number.getText());
                            val /= 10;
                            num2 += val;
                        }
                    }
                    result.setText(result.getText() + number.getText());
                }
            });
        }

        JButton[] operators = { add, divide, multiply, subtract };
        for (JButton operator : operators) {
            operator.addActionListener(e -> {
                operation = operator.getText();
                isOperatorClicked = true;
                isDecimal = false;
                result.setText(result.getText() + " " + operation + " ");
            });
        }
        
        dot.addActionListener(e -> {
            if (!isDecimal) {
                result.setText(result.getText() + ".");
                isDecimal = true;
            }
        });

        clear.addActionListener(e -> {
            num1 = null;
            num2 = null;
            operation = null;
            isOperatorClicked = false;
            isDecimal = false;
            result.setText("");
        });

        equals.addActionListener(e -> {
            if (num1 != null && num2 != null && operation != null) {
                double resultValue = 0;
                switch (operation) {
                    case "+":
                        resultValue = calculator.add(num1, num2);
                        break;
                    case "-":
                        resultValue = calculator.subtract(num1, num2);
                        break;
                    case "*":
                        resultValue = calculator.multiply(num1, num2);
                        break;
                    case "/":
                        try {
                            resultValue = calculator.divide(num1, num2);
                        } catch (ArithmeticException ex) {
                            JOptionPane.showMessageDialog(this, "Cannot divide by zero", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        break;
                }
                result.setText(String.valueOf(resultValue));
                // num1 = ans
                num1 = resultValue;
                // reset the rest
                num2 = null;
                isOperatorClicked = false;
                isDecimal = false;
            }
        });

    }
}