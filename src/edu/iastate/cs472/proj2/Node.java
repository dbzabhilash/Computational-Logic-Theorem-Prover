package edu.iastate.cs472.proj2;

import java.util.LinkedList;

/**
 * @author Abhilash Tripathy
 */
public class Node {
    private String nodeValue;
    private Node left;
    private Node right;

    private ConjuctiveNormalForm CNF;

    public Node(String nodeValue) {
        this.nodeValue = nodeValue;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public String getNodeValue() {
        return nodeValue;
    }

    public void updateNodeValue(String nodeValue) {
        this.nodeValue = nodeValue;
    }


    public void calculateCNF() {
        CNF = new ConjuctiveNormalForm();
        CNF.buildTree(this);
    }

    public ConjuctiveNormalForm getCNF () {
        return CNF;
    }

    public void setCNF(ConjuctiveNormalForm CNF) {
        this.CNF = CNF;
    }

    @Override
    public String toString() {
        LinkedList<Clause> clausesList = getCNF().getClauses_list();
        StringBuilder tempClauseString = new StringBuilder();
        for(Clause tempClause : clausesList) {
            for(Literal tempLiteral : tempClause.getLiterals_list()) {
                if(tempLiteral != tempClause.getLiterals_list().getLast()) {
                    tempClauseString.append(" ").append(tempLiteral.toString()).append(" ||");
                }
                else tempClauseString.append(" ").append(tempLiteral.toString());
            }
            tempClauseString.append("\n");
        }
        return tempClauseString.toString();
    }
}
