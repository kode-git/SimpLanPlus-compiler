package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class BinExpMultNode implements ExpNode {

    private ExpNode left;
    private ExpNode right;

    public BinExpMultNode(ExpNode left, ExpNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toPrint(String s) {
        return s + "Mult:" + left.toPrint(s + " ") + "*" + right.toPrint(s + " ") + "\n";
    }

    @Override
    public Node typeCheck() {
        return null;
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return null;
    }
}
