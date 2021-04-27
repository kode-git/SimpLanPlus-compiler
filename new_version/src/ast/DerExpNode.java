package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;


public class DerExpNode implements Node {

    Node derExp;
    public DerExpNode(Node derExp) {
        this.derExp=derExp;
    }

    @Override
    public String toPrint(String s) {

        return s+"" + derExp.toPrint(s + "") + "";


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
        if(derExp instanceof LhsNode) {
            // lhs
            res.addAll(derExp.checkSemantics(env));
        } else {
            // id
            env.checkId(env.getNestingLevel(), derExp + "");
        }

        return res;
    }
}
