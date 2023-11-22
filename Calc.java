import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class Calc {
    static double a = 0, b = 0, result = 0;
    static char operator = ' ';
    static boolean operatorClicked = false;

    public static void main(String []args){
        JFrame f = new JFrame("계산기");
        JTextField tf = new JTextField("0");
        tf.setEditable(false);
        f.setSize(190, 160);
        f.setLocation(300,300);

        f.add("North", tf);
        JPanel numPanel = new JPanel();
        numPanel.setLayout(new GridLayout(4,5,4,4));
        numPanel.setBackground(Color.lightGray);
        f.add("Center", numPanel);

        String []numStr = {"7", "8", "9", "/", "CE", "4", "5", "6", "*", "BS", "1", "2", "3", "-", "1/x", "0", "+/-", ".", "+", "=", "√", "(", ")"};

        JButton[] numButtons = new JButton[numStr.length];
        for (int i = 0; i<numStr.length; i++){
            numButtons[i] = new JButton(numStr[i]);
            numButtons[i].setForeground(Color.blue);
            numPanel.add(numButtons[i]);
            numButtons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String button = e.getActionCommand();
                    if (button.charAt(0) >= '0' && button.charAt(0) <= '9' || button.charAt(0) == '.') {
                        if (!tf.getText().isEmpty() && !operatorClicked)
                            tf.setText(tf.getText() + button);
                        else {
                            tf.setText(button);
                            operatorClicked = false;
                        }
                    } else if (button.charAt(0) == 'C') {
                        tf.setText("");
                    } else if (button.charAt(0) == '=') {
                        tf.setText(tf.getText() + " = " + calculate(tf.getText()));
                    } else {
                        if (!tf.getText().isEmpty()) {
                            tf.setText(tf.getText() + " " + button + " ");
                            operatorClicked = true;
                        }
                    }
                }
            });
        }
        f.setResizable(true);
        f.setVisible(true);
    }

    public static double calculate(String expression) {
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (String token : expression.split(" ")) {
            if ("+-*/".contains(token)) {
                while (!operators.isEmpty() && precedence(token.charAt(0)) <= precedence(operators.peek())) {
                    process(numbers, operators);
                }
                operators.push(token.charAt(0));
            } else if (token.equals("(")) {
                operators.push('(');
            } else if (token.equals(")")) {
                while (operators.peek() != '(') {
                    process(numbers, operators);
                }
                operators.pop(); // Discard the '('
            } else if (!token.equals("=")) {
                numbers.push(Double.parseDouble(token));
            }
        }

        while (!operators.isEmpty()) {
            process(numbers, operators);
        }

        return numbers.pop();
    }

    private static void process(Stack<Double> numbers, Stack<Character> operators) {
        double b = numbers.pop();
        double a = numbers.pop();
        switch (operators.pop()) {
            case '+':
                numbers.push(a + b);
                break;
            case '-':
                numbers.push(a - b);
                break;
            case '*':
                numbers.push(a * b);
                break;
            case '/':
                numbers.push(a / b);
                break;
        }
    }

    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }
}
