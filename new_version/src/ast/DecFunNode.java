package ast;

import util.Environment;
import util.SemanticError;
import util.VoidNode;

import java.util.ArrayList;
import java.util.HashMap;

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
        this.type = new VoidNode();
        this.id = id;
        this.args = args;
        this.block = block;
    }

    // void id ( ) {}
    public DecFunNode(String id, BlockNode block){
        this.type = new VoidNode();
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
        if(!(this.type instanceof VoidNode)){
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

        ArrayList<SemanticError> res = new ArrayList();
        int offset = env.getOffset();
        STentry entry = new STentry(env.getNestingLevel(), offset );

        if(!(type instanceof GenericTypeNode)){
            // is void
            System.out.println(type);
            entry.addType(new ArrowTypeNode(args, new VoidNode()));
        }
        else {
            entry.addType(new ArrowTypeNode(args, type));
        }
        env.setOffset(offset - 1);

        SemanticError err = env.newVarNode(env.getNestingLevel(), this.id, entry);


        if (err != null) {
            res.add(err);
        } else {
            env.setNestingLevel(env.getNestingLevel() + 1);
            env.addTable(new HashMap<String, STentry>());

            for(ArgNode arg : this.args){
                res.addAll(arg.checkSemantics(env));
            }
            env.setNestingLevel(env.getNestingLevel() - 1); // this is because in BlockNode checkSemantics we have NestingLevel + 1
            res.addAll(this.block.checkSemantics(env));
        }

        return res;
    }
}
