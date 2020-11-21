package edu.iastate.cs472.proj2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

/**
 * @author Abhilash Tripathy
 */
public class ExpressionTree {
    public static int OPERAND = 1000;

    private Node root;

    public ExpressionTree(String expression) {
        String newExpression = expression.replaceAll("~", "~ ");

        Stack<Node> operandStack = new Stack<>();
        Stack<String> operatorStack = new Stack<>();

        String splitExpression[] = newExpression.split("\\s+");
        for(String str: splitExpression) {
            if(isOperator(str)){
                if (str.equals("(")) {
                    operatorStack.push(str);
                }
                else if (str.equals(")")) {
                    while(!operatorStack.isEmpty()) {
                        String t = operatorStack.pop();
                        if(!t.equals("(")) {
                            String op = t;
                            if(op.equals("~")) {
                                Node operatorNode = new Node(op);
                                Node right = operandStack.pop();
                                operatorNode.setRight(right);
                                operandStack.push(operatorNode);
                                continue;
                            }
                            Node operatorNode = new Node(op);
                            Node right = operandStack.pop();
                            Node left = operandStack.pop();
                            operatorNode.setLeft(left);
                            operatorNode.setRight(right);
                            operandStack.push(operatorNode);
                        } else {
                            break;
                        }
                    }
                }
                else {
                    if(operatorStack.isEmpty()) {
                        operatorStack.push(str);
                    }
                    else {
                        while (!operatorStack.isEmpty()) {
                            String t = operatorStack.pop();
                            if(t.equals("(")) {
                                operatorStack.push(t);
                                break;
                            }
                            else if (isOperator(t) && !t.equals(")")) {
                                if(priority(t) < priority(str)) {
                                    operatorStack.push(t);
                                    break;
                                }
                                else {
                                    String op = t;
                                    if(op.equals("~")) {
                                        Node operatorNode = new Node(op);
                                        Node right = operandStack.pop();
                                        operatorNode.setRight(right);
                                        operandStack.push(operatorNode);
                                        continue;
                                    }
                                    Node operatorNode = new Node(op);
                                    Node right = operandStack.pop();
                                    Node left = operandStack.pop();
                                    operatorNode.setLeft(left);
                                    operatorNode.setRight(right);
                                    operandStack.push(operatorNode);
                                }
                            }
                        }
                        operatorStack.push(str);
                    }
                }

//                if(operatorStack.isEmpty()){
//                    operatorStack.push(str);
//                }
//                else if (operatorStack.peek().equals("(") && str.equals(")")) {
//                    operatorStack.pop();
//                    continue;
//                }
//                else if(priority(operatorStack.peek()) < priority(str)) {
//                    operatorStack.push(str);
//                }
//                else {
//                    Node right = operandStack.pop();
//                    Node left = operandStack.pop();
//                    Node operatorNode = new Node(str);
//                    operatorNode.setLeft(left);
//                    operatorNode.setRight(right);
//                    operandStack.push(operatorNode);
//                }
            }
            else {
                operandStack.push(new Node(str));
            }
        }

        while(!operatorStack.isEmpty()) {
            String op = operatorStack.pop();
            if(op.equals("~")) {
                Node operatorNode = new Node(op);
                Node right = operandStack.pop();
                operatorNode.setRight(right);
                operandStack.push(operatorNode);
                continue;
            }
            Node operatorNode = new Node(op);
            Node right = operandStack.pop();
            Node left = operandStack.pop();
            operatorNode.setLeft(left);
            operatorNode.setRight(right);
            operandStack.push(operatorNode);
        }

        root = operandStack.peek();
        operandStack.pop();

    }

    public Node getRoot() {
        return root;
    }

    // Helper methods
    public int priority(String str) {
        int priorityValue;
        switch(str) {
            case "(": priorityValue =-1;
                break;
            case ")": priorityValue =0;
                break;
            case "~": priorityValue =5;
                break;
            case "&&": priorityValue =4;
                break;
            case "||": priorityValue =3;
                break;
            case "=>": priorityValue =2;
                break;
            case "<=>": priorityValue =1;
                break;
            default: priorityValue=OPERAND;
        }
        return priorityValue;
    }

    public boolean isOperator(String str) {
        switch(str) {
            case "(":
            case ")":
            case "~":
            case "&&":
            case "||":
            case "=>":
            case "<=>": return true;
            default: return false;
        }
    }
}
