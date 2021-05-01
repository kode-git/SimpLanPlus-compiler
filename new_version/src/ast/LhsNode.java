package ast;

import com.sun.source.tree.ReturnTree;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;
import java.util.HashMap;

public class LhsNode<T>implements Node,Cloneable{
    private T lhVar;
    private STentry entry;
    private int nestingLevel;
    public LhsNode(T myNode){
        this.lhVar=myNode;
    }


    // used only on clone
    public LhsNode(){}

    @Override
    public String toPrint(String s) {
        if(lhVar instanceof Node){
            return s+ ((Node) lhVar).toPrint(s+ "") + "^ ";
        }else {
            return s+ lhVar +"";
        }

    }


    // getter and setter

    public T getLhVar() {
        return lhVar;
    }

    public void setLhVar(T lhVar) {
        this.lhVar = lhVar;
    }

    public STentry getEntry() {
        return entry;
    }

    public void setEntry(STentry entry) {
        this.entry = entry;
    }

    public int getNestingLevel() {
        return nestingLevel;
    }

    public void setNestingLevel(int nestingLevel) {
        this.nestingLevel = nestingLevel;
    }

    // typeCheck, CodeGeneration, CheckSemantics

    @Override
    public Node typeCheck() {
        if(! (lhVar instanceof LhsNode) ){
            if (entry.getType() instanceof ArrowTypeNode) { //
                System.out.println("Wrong usage of function identifier");
                System.exit(0);
            }

        /*
            if(!(entry.getType() instanceof IntTypeNode||entry.getType() instanceof BoolTypeNode)) {
                System.out.println("this type"+ entry.getType().typeCheck() );
                return entry.getType().typeCheck();
            }

                if((entry.getType() instanceof PointerTypeNode)) {
                System.out.println("this type"+ entry.getType().typeCheck() );
                return entry.getType().typeCheck();
            }
        */

            return entry.getType();
        }else{
            System.out.println("else statement");
            System.out.println("is it recursive?  "+((LhsNode)lhVar).typeCheck());

            // if((entry.getType() instanceof PointerTypeNode)) {
            //    System.out.println("this type"+ entry.getType().typeCheck() );
            //    return entry.getType().typeCheck();
            }
                return ((LhsNode)lhVar).typeCheck();
        }


    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList();
        if (lhVar instanceof String) {
            int j = env.getNestingLevel();
            STentry myEntry = env.checkId( env.getNestingLevel(), (String)lhVar);


            if (myEntry == null) {
                res.add(new SemanticError("Id " + (String)lhVar + " not declared"));
            } else {
                this.entry = myEntry;
                this.nestingLevel = env.getNestingLevel();
            }

        }else{

            /*
                Entry Problem:
                - We need to setup the entry of the last level on the rest of LhsNode
                - Can't do it because miss clone() or other method to save the current reference on lhVar
                We assign to the pointer the entry type to which it is referred
            */
            Node myVar = (Node) lhVar;
            res.addAll(((Node)lhVar).checkSemantics(env));

           while(((LhsNode)myVar).getEntry()==null){
              myVar= (Node) ((LhsNode<?>) myVar).getLhVar();
            }

            this.entry=((LhsNode<?>) myVar).getEntry();

        }
        return res;


    }

}
