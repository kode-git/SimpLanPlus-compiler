package ast;

import util.Environment;
import util.Offset;
import util.SemanticError;
import util.SimpLanlib;

import java.util.ArrayList;

public class BinExpAndNode implements Node, Cloneable {


    private Node left;
    private Node right;
    private int effectDecFun;

    public BinExpAndNode(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    // getter and setter

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
            BinExpAndNode cloned = (BinExpAndNode) super.clone();
            cloned.left = (Node) this.left.clone();
            cloned.right = (Node) this.right.clone();
            return cloned;
        } catch(CloneNotSupportedException e){
            return null;
        }
    }

    @Override
    public String codeGeneration() {
        return left.codeGeneration() + right.codeGeneration() + "and\n";
    }


}
