package ast;

import util.Environment;
import util.SemanticError;
import util.SimpLanlib;

import java.util.ArrayList;

public class BinExpMultNode implements Node {

    private Node left;
    private Node right;

    public BinExpMultNode(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toPrint(String s) {
        return s + "Mult:" + left.toPrint(s + "") + "*" + right.toPrint(s + "") + "";
    }

    @Override
    public Node typeCheck() {
        if (!(SimpLanlib.isSubtype(left.typeCheck(), new IntTypeNode()) &&
                SimpLanlib.isSubtype(right.typeCheck(), new IntTypeNode()))) {
            System.out.println("error: bad pair statements types for binary operator '*'");
            System.exit(0);
        }
        return new IntTypeNode();
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
