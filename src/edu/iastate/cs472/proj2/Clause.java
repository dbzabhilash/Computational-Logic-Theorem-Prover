package edu.iastate.cs472.proj2;

import java.util.LinkedList;

/**
 * @author Abhilash Tripathy
 */
public class Clause {
    private LinkedList<Literal> literals_list;

    public Clause () {
        literals_list = new LinkedList<>();
    }

    public Clause (Literal literal) {
        literals_list = new LinkedList<>();

        literals_list.add(literal);
    }

    public void addLiteral (Literal literal) {
        literals_list.add(literal);
    }

    public void addLiteralLists (LinkedList<Literal> literalLists) {
        literals_list.addAll(literalLists);
    }


    public LinkedList<Literal> getLiterals_list() {
        return literals_list;
    }

    @Override
    public String toString() {
        StringBuilder tempClauseString = new StringBuilder();

        for(Literal tempLiteral : literals_list) {
            if(tempLiteral != literals_list.getLast()) {
                tempClauseString.append(" ").append(tempLiteral.toString()).append(" ||");
            }
            else tempClauseString.append(" ").append(tempLiteral.toString());
        }
        return tempClauseString.toString();
    }

}
