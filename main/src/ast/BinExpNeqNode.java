package ast;

import util.Environment;
import util.Offset;
import util.SemanticError;
import util.SimpLanlib;

import java.util.ArrayList;

public class BinExpNeqNode implements Node, Cloneable {

    private Node left;
    private Node right;
    private int effectDecFun;

    public BinExpNeqNode(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public int getEffectDecFun() {
        return effectDecFun;
    }

    @Override
    public void setEffectDecFun(int effectDecFun) {
        this.effectDecFun = effectDecFun;
    }

    // toPrint, typeCheck, checkSemantics, checkEffects, codeGeneration


    @Override
    public String toPrint(String s) {
        return s + "Neq: " + left.toPrint(s + "") + "!="
                + right.toPrint(s + "") + "";
    }

    @Override
    public Node typeCheck() {
        GenericTypeNode typeLeft = (GenericTypeNode) left.typeCheck();
        if (!SimpLanlib.isSubtype(typeLeft, right.typeCheck())){
            System.out.println("NEQ Error: bad pair statements types for binary operator '!='");
            System.exit(0);
        }
        return new BoolTypeNode();

    }


    // not used
    public int checkEffects(Environment env) {
        return 0;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        left.setEffectDecFun(this.effectDecFun);
        right.setEffectDecFun(this.effectDecFun);
        res.addAll(left.checkSemantics(env));
        res.addAll(right.checkSemantics(env));

        return res;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env, Offset offset) {
        return null;
    }

    @Override
    public Node clone() {
        try {
            BinExpNeqNode cloned = (BinExpNeqNode) super.clone();
            cloned.left = (Node) this.left.clone();
            cloned.right = (Node) this.right.clone();
            return cloned;
        } catch(CloneNotSupportedException e){
            return null;
        }
    }


    @Override
    public String codeGeneration() {

        String is_equals = SimpLanlib.freshLabel();
        String end_eq = SimpLanlib.freshLabel();

        return   right.codeGeneration() +            // r1 <- cgen(stable, right) ; s -> []
                "lr1\n" +                          // r1 -> top_of_stack; s -> [r1]
                left.codeGeneration() +            // r1 <- cgen(stable, left); s -> [r1]
                "sr2\n" +                            // r2 <- top_of_stack; s -> []
                "beq " + is_equals + "\n" +        // if r1 == r2 :: left == right jump to is_equals; s -> []
                "lir1 1\n" +                       // else is true :: 1; r1 <- 1; ; s -> []
                "b " + end_eq + "\n" +             // go to end to avoid is_equals s -> []
                is_equals + ":\n" +                // is_equals: s -> []
                "lir1 0\n" +                       // is false :: 0; r1 <- 0; s -> []
                end_eq + ":\n";                    // end_q s -> []

    }

}
