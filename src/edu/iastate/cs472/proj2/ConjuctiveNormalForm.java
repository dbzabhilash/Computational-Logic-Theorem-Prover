package edu.iastate.cs472.proj2;

import java.util.*;

/**
 * @author Abhilash Tripathy
 */
public class ConjuctiveNormalForm {

    private LinkedList<Clause> clauses_list;

    private ConjuctiveNormalForm leftCNF;
    private ConjuctiveNormalForm rightCNF;

    public ConjuctiveNormalForm () {
        clauses_list = new LinkedList<>();
    }

    public void buildTree (Node node) {
        /* TODO:
            if (nodeValue is an operand)
                clauses_list.add(operand)
                exit
             else if (nodeValue is an operator)
                clauses_list.add(leftCNF)
                clauses_list.add(rightCNF)
         */
        if(!isOperator(node.getNodeValue())) {
            clauses_list.add(new Clause(new Literal(node.getNodeValue(), true)));
        }
        else {
            if(node.getLeft() != null) {
                node.getLeft().calculateCNF();
                leftCNF = node.getLeft().getCNF();
            }
            if(node.getRight() != null) {
                node.getRight().calculateCNF();
                rightCNF = node.getRight().getCNF();
            }
            performConjunction(node);
        }
    }


    private void performConjunction (Node node) {
        /* TODO:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
         */
        switch (node.getNodeValue()) {
            case "~": clauses_list = performNegation(rightCNF);
                break;
            case "&&": clauses_list = performAND(leftCNF.getClauses_list(), rightCNF.getClauses_list());
                break;
            case "||": clauses_list = performOR(leftCNF.getClauses_list(), rightCNF.getClauses_list());
                break;
            case "=>": clauses_list = performImplies(leftCNF, rightCNF);
                break;
            case "<=>": clauses_list = performDoubleImplies(leftCNF, rightCNF);
                break;
            default: System.out.println("Unexpected nodeValue in performConjunction()");
        }
    }

    public LinkedList<Clause> performAND(LinkedList<Clause> leftClauseList, LinkedList<Clause> rightClauseList) {
        LinkedList<Clause> clauses_list_AND = new LinkedList<>();
        clauses_list_AND.addAll(leftClauseList);
        clauses_list_AND.addAll(rightClauseList);
        return clauses_list_AND;
    }

    public LinkedList<Clause> performOR(LinkedList<Clause> leftClauseList, LinkedList<Clause> rightClauseList) {
        LinkedList<Clause> clauses_list_OR = new LinkedList<>();

        for(Clause tempLeftClause : leftClauseList) {
            for(Clause tempRightClause : rightClauseList) {
                Clause tempClause = new Clause();
                tempClause = combineLiteralLists(tempLeftClause.getLiterals_list(), tempRightClause.getLiterals_list());
                clauses_list_OR.add(tempClause);
            }

        }
        return clauses_list_OR;
    }

    public LinkedList<Clause> performImplies(ConjuctiveNormalForm leftCNF, ConjuctiveNormalForm rightCNF) {
        LinkedList<Clause> negatedLeftClauseList = performNegation(leftCNF);
        return performOR(negatedLeftClauseList, rightCNF.getClauses_list());
    }

    public LinkedList<Clause> performDoubleImplies(ConjuctiveNormalForm leftCNF, ConjuctiveNormalForm rightCNF) {
        return performAND(performImplies(leftCNF, rightCNF), performImplies(rightCNF, leftCNF));
    }

    public LinkedList<Clause> performNegation(ConjuctiveNormalForm rightCNF) {
        Stack<ConjuctiveNormalForm> CNFstack = new Stack<>();

        for(Clause tempClause : rightCNF.getClauses_list()) {
            ConjuctiveNormalForm tempCNF = new ConjuctiveNormalForm();
            for(Literal tempLiteral : tempClause.getLiterals_list()) {
                tempCNF.getClauses_list().add(new Clause(new Literal(tempLiteral.getLiteralValue(), !tempLiteral.isPositive())));
            }
            CNFstack.add(tempCNF);
        }
        /* TODO:
            CNF1 or CNF2 or.... CNFn
         */
        return recursiveOR(CNFstack);
    }

    private LinkedList<Clause> recursiveOR (Stack<ConjuctiveNormalForm> CNFstack) {
        if(CNFstack.size()==1) return CNFstack.pop().getClauses_list();
        if(CNFstack.size()==2) {
            ConjuctiveNormalForm cnf1 = CNFstack.pop();
            ConjuctiveNormalForm cnf2 = CNFstack.pop();
            return performOR(cnf1.getClauses_list(), cnf2.getClauses_list());
        }
        return performOR(CNFstack.pop().getClauses_list(), recursiveOR(CNFstack));
    }

//    Getters and setters
    public LinkedList<Clause> getClauses_list () {
        return clauses_list;
    }

    public void setClauses_list(LinkedList<Clause> clauses_list) {
        this.clauses_list = clauses_list;
    }

    //    Helper methods
    private boolean isOperator(String str) {
        switch(str) {
            case "~":
            case "&&":
            case "||":
            case "=>":
            case "<=>": return true;
            default: return false;
        }
    }

    public Clause combineLiteralLists (LinkedList<Literal> l1, LinkedList<Literal> l2) {
        Clause combinedClause = new Clause();
        LinkedList<Literal> l2_deepCopy = new LinkedList<>(l2);

        Literal sameVar_literal = null;

        for(Literal tempLiteral1 : l1) {
            sameVar_literal = null;
            for(Literal tempLiteral2: l2_deepCopy) {
                if(tempLiteral1.getLiteralValue().equals(tempLiteral2.getLiteralValue())) {
                    sameVar_literal = tempLiteral2;
                    break;
                }
            }

            if(sameVar_literal != null) {
                if(tempLiteral1.isPositive() == sameVar_literal.isPositive()) {
                    combinedClause.addLiteral(tempLiteral1);
                    l2_deepCopy.remove(sameVar_literal);
                }
                else if(tempLiteral1.isPositive() != sameVar_literal.isPositive()) {
                    l2_deepCopy.remove(sameVar_literal);
                }
            }
            else {
                combinedClause.addLiteral(tempLiteral1);
            }
        }

        for(Literal tempLiteral2: l2_deepCopy) {
            combinedClause.addLiteral(tempLiteral2);
        }

        return combinedClause;
    }
}
