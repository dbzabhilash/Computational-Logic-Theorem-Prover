package edu.iastate.cs472.proj2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author Abhilash Tripathy
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("kb.txt");

        Scanner scanner = new Scanner(file);

        LinkedList<Clause> knowledgeBase = new LinkedList<>();
        LinkedList<Clause> goalClauses = new LinkedList<>();

        ArrayList<String> PLsentences = new ArrayList<>();
        ArrayList<String> GOALsentences = new ArrayList<>();

        ArrayList<ExpressionTree> sentenceTrees = new ArrayList<>();
        ArrayList<ExpressionTree> GOALsentenceTrees = new ArrayList<>();

        LinkedList<ConjuctiveNormalForm> CNFtrees_PL = new LinkedList<>();
        LinkedList<ConjuctiveNormalForm> CNFtrees_GOAL = new LinkedList<>();

        while(scanner.hasNextLine()) {
            String expression = scanner.nextLine();

            if(expression.matches("Prove the following sentences by(.*)")) {
                break;
            }
            else if(!expression.matches("Knowledge Base:(.*)")
                    && !expression.equals("")) {
                expression.replaceAll("~", "~ ");
                PLsentences.add(expression);
                sentenceTrees.add(new ExpressionTree(expression));
            }

//            System.out.println(expression);
        }

        while(scanner.hasNextLine()) {
            String expression = scanner.nextLine();
            if(!expression.equals("")) {
                expression.replaceAll("~", "~ ");
                GOALsentences.add(expression);
                GOALsentenceTrees.add(new ExpressionTree(expression));
            }
        }

        System.out.println("Knowledge base in clauses:\n");

        for(ExpressionTree tree: sentenceTrees) {
            tree.getRoot().calculateCNF();

            knowledgeBase.addAll(tree.getRoot().getCNF().getClauses_list());

            CNFtrees_PL.add(tree.getRoot().getCNF());
            System.out.print(tree.getRoot().toString());
        }

        for(ExpressionTree tree: GOALsentenceTrees) {
            tree.getRoot().calculateCNF();
            CNFtrees_GOAL.add(tree.getRoot().getCNF());
        }
        System.out.println();
        performRefutation(knowledgeBase, CNFtrees_GOAL);
    }

    private static void performRefutation(LinkedList<Clause> knowledgeBase, LinkedList<ConjuctiveNormalForm> CNFtrees_goal) {
        ConjuctiveNormalForm dummyCNF = new ConjuctiveNormalForm();
        Node dummyNode = new Node("dummy");

        LinkedList<Clause> negatedClauseList;

        LinkedList<Clause> deepCopy_KB;

        int i=0;
        for (ConjuctiveNormalForm currGoal : CNFtrees_goal) {
            System.out.println("\n****************************************");
            System.out.println("Goal Sentence "+(++i)+":");
            dummyNode.setCNF(currGoal);
            System.out.println(dummyNode.toString());
            System.out.println("****************************************\n");

            negatedClauseList = dummyCNF.performNegation(currGoal);
            dummyCNF.setClauses_list(negatedClauseList);
            dummyNode.setCNF(dummyCNF);

            System.out.println("Negated goal in clauses:");
            System.out.println(dummyNode.toString());

            System.out.println("Proof by refutation:");


            for(Clause GOALclause : negatedClauseList) {

                Clause tempClause = GOALclause;
                boolean KBcompleted = true;
                for(Clause KBclause : knowledgeBase) {
                    System.out.println();
                    System.out.println(tempClause.toString());
                    System.out.println(KBclause.toString());
                    System.out.println("-------------------------------");
                    tempClause = dummyCNF.combineLiteralLists(tempClause.getLiterals_list(), KBclause.getLiterals_list());
                    if(tempClause.getLiterals_list().isEmpty()) {
                        System.out.println("Empty clause!\n");
                        KBcompleted=false;
                        break;
                    }
                    System.out.println(tempClause.toString());
                    System.out.println();


                }
                dummyNode.setCNF(currGoal);
                if(KBcompleted) {
                    System.out.println("No new clauses added ");
                    System.out.println("The KB does not entail: ");
                    System.out.println(dummyNode.toString());
                }
                else {
                    System.out.println("The KB entails: ");
                    System.out.println(dummyNode.toString());
                }
            }
        }
    }


}
