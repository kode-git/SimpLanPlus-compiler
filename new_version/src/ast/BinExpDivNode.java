package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class BinExpDivNode implements Node {

    private Node left;
    private Node right;

    public BinExpDivNode(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toPrint(String s) {
        return s + "ExpDiv: " + left.toPrint(s + "") + "/" +
                right.toPrint(s + "") + "";
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

        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        res.addAll(left.checkSemantics(env));
        res.addAll(right.checkSemantics(env));

        return res;
    }
}
