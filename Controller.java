package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;
import java.util.Stack;

public class Controller {

    @FXML
    private TextField inputField;
    @FXML
    private TextField solutionField;
    private Alert alert = new Alert(Alert.AlertType.ERROR);

    @FXML
    private ImageView img1;
    @FXML
    private ImageView img2;
    @FXML
    private ImageView img3;
    @FXML
    private ImageView img4;

    private static final int SCORE = 24;
    public int[] cards = new int[4];

    public void findSolution() {
        Solution s = new Solution(cards);
        solutionField.setText(s.getFinalResult());
    }

    public void refresh() {
        //generating 4 random numbers which represents types from which card belongs
        Random rand = new Random();
        int[] types = new int[4];
        for (int i = 0; i < 4; i++) types[i] = rand.nextInt(4) + 1;

        //now generating 4 random numbers which will represent cards
        for (int i = 0; i < 4; i++) {
            cards[i] = rand.nextInt(13) + 1;
        }
        ;

        //now concatinating these random values and making path to original images
        String[] paths = new String[4];
        //making IMage object arrays
        Image[] images = new Image[4];
        for (int i = 0; i < paths.length; i++) {
//            String tempPath = "file:///C:/Users/Hasnain__Ali/IdeaProjects/Game/src/sample/type" + types[i] + "/" + cards[i] + ".jpg";
            String tempPath = "sample/type" + types[i] + "/" + cards[i] + ".jpg";
            images[i] = new Image(tempPath);
        }
        //resetting images
        img1.setImage(images[0]);
        img2.setImage(images[1]);
        img3.setImage(images[2]);
        img4.setImage(images[3]);


    }

    //this function will check whether user score is 24 or not
    public void validate() {
        String result = inputField.getText();
        //firstly validating input
        boolean isValidInput = checkInput(result);
        //if input is valid then calculating score
        if (isValidInput) {
            //first converting to postfix
            String postfix = infixToPostfix(result);
            //now evaluating postfix
            int ans = evaluatePostfix(postfix);
            if (ans == SCORE) {
                alert.setContentText("Hurrah You Won");
                inputField.setText("");
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.showAndWait();
            } else {
                alert.setContentText("Sorry!! The result of given expression is not equal to 24");
                alert.showAndWait();
            }
        } else {
            alert.setContentText("Invalid Input");
            alert.showAndWait();
        }
    }


    //this function will check whether input is valid or not
    public boolean checkInput(String result) {
        if (result.length() <= 0) {
            return false;
        } else {
            boolean isValid = true;
            for (int i = 0; i < result.length(); i++) {
                if (((int) result.charAt(i) >= 97 && (int) result.charAt(i) <= 122) ||
                        ((int) result.charAt(i) >= 65 && (int) result.charAt(i) <= 90)) {
                    isValid = false;
                    break;
                }
            }
            return isValid;
        }

    }

    //these below functions will help calculating user input
    //using infix to postfix and then postfix solver algorithm
    static int Precedence(char ch) {
        switch (ch) {
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
                return 2;

            case '^':
                return 3;
        }
        return -1;
    }

    // The main method that converts
    // given infix expression
    // to postfix expression.
    static String infixToPostfix(String exp) {
        // initializing empty String for result
        String result = new String("");

        // initializing empty stack
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < exp.length(); ++i) {
            char c = exp.charAt(i);

            // If the scanned character is an
            // operand, add it to output.
            if (Character.isLetterOrDigit(c))
                result += c;

                // If the scanned character is an '(',
                // push it to the stack.
            else if (c == '(')
                stack.push(c);

                //  If the scanned character is an ')',
                // pop and output from the stack
                // until an '(' is encountered.
            else if (c == ')') {
                while (!stack.isEmpty() &&
                        stack.peek() != '(')
                    result += stack.pop();

                stack.pop();
            } else // an operator is encountered
            {
                while (!stack.isEmpty() && Precedence(c)
                        <= Precedence(stack.peek())) {

                    result += stack.pop();
                }
                stack.push(c);
            }

        }

        // pop all the operators from the stack
        while (!stack.isEmpty()) {
            if (stack.peek() == '(')
                return "Invalid Expression";
            result += stack.pop();
        }
        return result;
    }

    static int evaluatePostfix(String exp) {

        //create a stack
        Stack<Integer> stack = new Stack<>();

        // Scan all characters one by one
        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);
            // If the scanned character is an operand (number here),
            // push it to the stack.
            if ((int) c >= 48 && (int) c <= 57) stack.push(c - '0');

                //  If the scanned character is an operator, pop two
                // elements from stack apply the operator
            else {
                int val1 = stack.pop();
                int val2 = stack.pop();

                switch (c) {
                    case '+':
                        stack.push(val2 + val1);
                        break;

                    case '-':
                        stack.push(val2 - val1);
                        break;

                    case '/':
                        stack.push(val2 / val1);
                        break;

                    case '*':
                        stack.push(val2 * val1);
                        break;
                }
            }

        }

        return stack.pop();
    }

}
