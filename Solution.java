package sample;

import java.util.HashMap;
import java.util.Map;

public class Solution {

    //This array represents cards which are selected randomly
    static int data[] = new int[4];

    static int updatedData[] = new int[4];

    static String String_data[] = new String[4];

    //used to determine whether there is a solution
    static boolean flag = false;

    //store operator
    static char[] operator = {'+', '-', '*', '/'};

    static String result = "";

    public Solution(int[] cards) {
        for (int i = 0; i < cards.length; i++) {
            data[i] = cards[i];

        }
        findSolution();
    }

    public static int performOperator(int val1, int val2, char operator) {
        if (operator == '+') {
            return val1 + val2;
        } else if (operator == '-') {
            return val1 - val2;
        } else if (operator == '*') {
            return val1 * val2;
        } else if ((operator == '/') && (val2 != 0)) {
            return val1 / val2;
        } else {
            return -1;
        }
    }

    //this methiod will find solution
    public static void findSolution() {

        //we will use hash map for finding solution
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < data.length; i++) {
            if (map.get(data[i]) == null) {
                map.put(data[i], 1);
            } else {
                map.put(data[i], map.get(data[i]) + 1);
            }
        }
        if (map.size() == 1) {
            //If there is only one type, there is only one sorting combination, such as 5, 5, 5, 5
            calculation(data[0], data[1], data[2], data[3]);
        } else if (map.size() == 2) {
            //If there are only 2 numbers, there are 2 cases, such as 1, 1, 2, 2 and 1, 1, 1, 2
            int index = 0;//for data processing
            int state = 0;//Determine which is the case
            for (Integer key : map.keySet()) {
                if (map.get(key) == 1) {
                    //If there is one number and the other three are different, turn data into data[0]=data[1]=data[2],
                    data[3] = key;
                    state = 1;
                } else if (map.get(key) == 2) {
                    data[index++] = key;
                    data[index++] = key;
                } else {
                    data[index++] = key;
                }
            }
            if (state == 1) {
                calculation(data[3], data[1], data[1], data[1]);
                calculation(data[1], data[3], data[1], data[1]);
                calculation(data[1], data[1], data[3], data[1]);
                calculation(data[1], data[1], data[1], data[3]);
            }
            if (state == 0) {
                calculation(data[1], data[1], data[3], data[3]);
                calculation(data[1], data[3], data[1], data[3]);
                calculation(data[1], data[3], data[3], data[1]);
                calculation(data[3], data[3], data[1], data[1]);
                calculation(data[3], data[1], data[3], data[1]);
                calculation(data[3], data[1], data[1], data[3]);
            }
        } else if (map.size() == 3) {
            //There are 3 kinds of numbers
            int index = 0;
            for (Integer key : map.keySet()) {
                if (map.get(key) == 2) {
                    data[2] = key;
                    data[3] = key;
                } else {
                    data[index++] = key;
                }
            }
            calculation(data[0], data[1], data[3], data[3]);
            calculation(data[0], data[3], data[1], data[3]);
            calculation(data[0], data[3], data[3], data[1]);
            calculation(data[1], data[0], data[3], data[3]);
            calculation(data[1], data[3], data[0], data[3]);
            calculation(data[1], data[3], data[3], data[0]);
            calculation(data[3], data[3], data[0], data[1]);
            calculation(data[3], data[3], data[1], data[0]);
            calculation(data[3], data[1], data[3], data[0]);
            calculation(data[3], data[0], data[3], data[1]);
            calculation(data[3], data[0], data[1], data[3]);
            calculation(data[3], data[1], data[0], data[3]);
        } else if (map.size() == 4) {
            //4 cases are different
            calculation(data[0], data[1], data[2], data[3]);
            calculation(data[0], data[1], data[3], data[2]);
            calculation(data[0], data[2], data[1], data[3]);
            calculation(data[0], data[2], data[3], data[1]);
            calculation(data[0], data[3], data[1], data[2]);
            calculation(data[0], data[3], data[2], data[1]);
            calculation(data[1], data[0], data[2], data[3]);
            calculation(data[1], data[0], data[3], data[2]);
            calculation(data[1], data[2], data[3], data[0]);
            calculation(data[1], data[2], data[0], data[3]);
            calculation(data[1], data[3], data[0], data[2]);
            calculation(data[1], data[3], data[2], data[0]);
            calculation(data[2], data[0], data[1], data[3]);
            calculation(data[2], data[0], data[3], data[1]);
            calculation(data[2], data[1], data[0], data[3]);
            calculation(data[2], data[1], data[3], data[0]);
            calculation(data[2], data[3], data[0], data[1]);
            calculation(data[2], data[3], data[1], data[0]);
            calculation(data[3], data[0], data[1], data[2]);
            calculation(data[3], data[0], data[2], data[1]);
            calculation(data[3], data[1], data[0], data[2]);
            calculation(data[3], data[1], data[2], data[0]);
            calculation(data[3], data[2], data[0], data[1]);
            calculation(data[3], data[2], data[1], data[0]);
        }
        if (flag == false) {
            result = "No Solution";
        }
    }

    public static void calculation(int num1, int num2, int num3, int num4) {

        for (int i = 0; i < 4; i++) {
            char operator1 = operator[i];
            //permuting cards and evalutaing results
            int firstResult = performOperator(num1, num2, operator1);
            int midResult = performOperator(num2, num3, operator1);
            int tailResult = performOperator(num3, num4, operator1);
            for (int j = 0; j < 4; j++) {
                char operator2 = operator[j];
                int firstMidResult = performOperator(firstResult, num3, operator2);
                int firstTailResult = performOperator(num3, num4, operator2);
                int midFirstResult = performOperator(num1, midResult, operator2);
                int midTailResult = performOperator(midResult, num4, operator2);
                int tailMidResult = performOperator(num2, tailResult, operator2);
                for (int k = 0; k < 4; k++) {
                    char operator3 = operator[k];
                    //In the above calculations, num1, num2, num3, and num4 are integer values, but if you want to output an expression with A, J, Q, and K, you must change these four numbers to String type. The same below
                    if (performOperator(firstMidResult, num4, operator3) == 24) {
                        updatedData[0] = num1;
                        updatedData[1] = num2;
                        updatedData[2] = num3;
                        updatedData[3] = num4;
                        result = "((" + data[0] + operator1 + data[1] + ")"
                                + operator2 + data[2] + ")" + operator3 + data[3];
                        flag = true;//If there is an expression output, it will explain the solution, the same below
                        break;
                    }
                    if (performOperator(firstResult, firstTailResult, operator3) == 24) {
                        result = "(" + data[0] + operator1 + data[1] + ")"
                                + operator3 + "(" + data[2] + operator2 + data[3] + ")";
                        flag = true;
                        break;
                    }
                    if (performOperator(midFirstResult, num4, operator3) == 24) {
                        updatedData[0] = num1;
                        updatedData[1] = num2;
                        updatedData[2] = num3;
                        updatedData[3] = num4;
                        result = "(" + data[0] + operator2 + "(" + data[1]
                                + operator1 + data[2] + "))" + operator3 + data[3];
                        flag = true;
                        break;
                    }
                    if (performOperator(num1, midTailResult, operator3) == 24) {
                        updatedData[0] = num1;
                        updatedData[1] = num2;
                        updatedData[2] = num3;
                        updatedData[3] = num4;
                        result = " " + data[0] + operator3 + "((" + data[1]
                                + operator1 + data[2] + ")" + operator2 + data[3] + ")";
                        flag = true;
                        break;
                    }
                    if (performOperator(num1, tailMidResult, operator3) == 24) {
                        updatedData[0] = num1;
                        updatedData[1] = num2;
                        updatedData[2] = num3;
                        updatedData[3] = num4;
                        result = " " + data[0] + operator3 + "(" + data[1]
                                + operator2 + "(" + data[2] + operator1 + data[3] + "))";
                        flag = true;
                        break;
                    }
                }
            }
        }
    }

    public String getFinalResult() {
        return this.result;
    }
}
