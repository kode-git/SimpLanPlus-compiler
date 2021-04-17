package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class DecFunNode implements Node {

    private Node type;
    private String id;
    private ArrayList<ArgNode> args;
    private BlockNode block;


    // type id (args) {}
    public DecFunNode(Node type, String id, ArrayList<ArgNode> args, BlockNode block) {
        this.type = type;
        this.id = id;
        this.args = args;
        this. block = block;
    }

    // void id (args) {}
    public DecFunNode(String id,  ArrayList<ArgNode> args, BlockNode block){
        this.type = null;
        this.id = id;
        this.args = args;
        this.block = block;
    }

    // void id ( ) {}
    public DecFunNode(String id, BlockNode block){
        this.type = null;
        this.id = id;
        this.args = new ArrayList<ArgNode>();
        this.block = block;
    }

    //  type id ( ) {}
    public DecFunNode(Node type, String id, BlockNode block){
        this.type = type;
        this.id = id;
        this.args = new ArrayList<ArgNode>();
        this.block = block;
    }
    @Override
    public String toPrint(String s) {
        if(type != null){
            // type is not void
            if(args.size() == 0){
                // no args
                return s + "\nDecFun:\n\t " + type.toPrint(s + "") + id + "( )" + block.toPrint(s + "") + "\n";
            } else {
                // some args
                String first =  s + "\nDecFun: " + type.toPrint(s + "") + id + "(";
                String last = ")" + block.toPrint(s + "") + "\n";
                String argsPrint = this.args.get(0).toPrint(s + "");
                for(int i = 1; i < this.args.size(); i++){
                    argsPrint += "," + this.args.get(i).toPrint(s + "");
                }
                return first + argsPrint + last;
            }
        } else {
            // type is void
            if (args.size() == 0){
                // no args
                return s + "\nDecFun: " + "void " + id + "( )" + block.toPrint(s + "") + "\n";
            } else {
                // some args
                String first =  s + "\nDecFun: " + "void " + id + "(";
                String last = ")" + block.toPrint(s + " ") + "\n";
                String argsPrint = this.args.get(0).toPrint(s + "");
                for(int i = 1; i < this.args.size(); i++){
                    argsPrint += "," + this.args.get(i).toPrint(s + "");
                }
                return first + argsPrint + last;
            }
        }

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
