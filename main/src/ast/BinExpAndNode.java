package ast;

import util.Environment;
import util.SemanticError;
import util.SimpLanlib;

import java.util.ArrayList;

public class BinExpAndNode implements Node {


    Node left;
    Node right;

    public BinExpAndNode(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toPrint(String s) {
        return s + "BinAndExp: " + left.toPrint(s + "") + "&&" +
                right.toPrint(s +" ") + "";
    }

    @Override
    public Node typeCheck() {
        if (!(SimpLanlib.isSubtype(left.typeCheck(), new BoolTypeNode()) &&
                SimpLanlib.isSubtype(right.typeCheck(), new BoolTypeNode()))) {
            System.out.println("error: bad operand types for binary operator '&&'");
            System.exit(0);
        }
        return new BoolTypeNode();
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public int checkEffects(Environment env) {
        return 0;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        res.addAll(left.checkSemantics(env));
        res.addAll(right.checkSemantics(env));

        return res;
    }
}
