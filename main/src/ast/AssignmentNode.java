package ast;

import util.Environment;
import util.SemanticError;
import util.SimpLanlib;
import util.VoidNode;

import java.util.ArrayList;

public class AssignmentNode implements Node, Cloneable{

    private Node lhs;
    private Node exp;
    private int effectsST;
    private int effectDecFun;

    public AssignmentNode(Node lhs, Node exp){
        this.lhs = lhs;
        this.exp = exp;
    }

    // getter and setter

    public Node getLhs() {
        return lhs;
    }

    public void setLhs(Node lhs) {
        this.lhs = lhs;
    }

    public Node getExp() {
        return exp;
    }

    public void setExp(Node exp) {
        this.exp = exp;
    }

    public int getEffectsST() {
        return effectsST;
    }

    public void setEffectsST(int effectsST) {
        this.effectsST = effectsST;
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
        return s+"Assignment:" + lhs.toPrint(s + "") + " = "
                + exp.toPrint(s+"") + "";
    }

    @Override
    public Node typeCheck() {
        Node expType= exp.typeCheck();
        Node lhsType= lhs.typeCheck();
        if (!SimpLanlib.isSubtype(expType,lhsType)) {
            System.out.println("Assignment type failed");
            System.exit(0);
        }
        return new VoidNode(); // return void because this statement don't need to be checked in high level
        // void -> f : void on upper level

    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public int checkEffects(Environment env) {
        if(effectDecFun == 0) {
            // getting the lhsEffects in the table
            int lhsEffects = ((LhsNode) lhs).getEffectsST();
            if (lhsEffects >= 0 && lhsEffects <= 1) {
                // check if the left-side of the assignment is deleted or not,
                // if we are here, the left-a-side is bottom or rw state

                Node typeExp = exp.typeCheck();


                // Pointer Reference Assignment
                if(((exp instanceof DerExpNode))&&(typeExp instanceof PointerTypeNode)){
                    // Pointer assignment when x = exp, exp is a DerExp with LhsNode as PointerType
                    int diffCount= (((LhsNode<?>) lhs).getCounterST()-((LhsNode<?>) lhs).getCounter());
                    LhsNode<?> myDerExp = ((LhsNode<?>)((DerExpNode)exp).getDerExp());
                    if(diffCount
                            ==
                            (myDerExp.getCounterST()-myDerExp.getCounter())){
                        int counterLhs= ((LhsNode<?>)lhs).getCounter();
                        int counterExp= myDerExp.getCounter();
                        for(int i=0; i<=diffCount;i++){
                            ((LhsNode<?>)lhs).setEffectsST(counterLhs,myDerExp.getEffectsST(counterExp));
                            counterLhs++;
                            counterExp++;
                        }



                    }else {
                        System.out.println("Wrong pointer assignment" );
                         System.exit(1);
                    }

                }


                ((LhsNode<?>) lhs).setEffectsST(1);
                this.effectsST = 1;

                // End of Pointer Reference Assignment
                // Effect propagation checking
                if (lhs instanceof LhsNode && exp instanceof DerExpNode){

                    LhsNode left = (LhsNode) lhs;
                    LhsNode right = (LhsNode) ((DerExpNode) exp).getDerExp();

                    if(left.getCounterST() > 0 && right.getCounterST() > 0) {
                        // in this case they are pointer
                        // adding propagation to the left from the right
                        // this is needed to focus the deletion on EffectState[] of the rightEntry from the Counter to 0 index equals to d
                        /*
                        Case:
                            x^^ = new, y^^ = new; y ^ = 3; x^ = y; x.propagation[ y : 0 ]
                        */
                        // getting the entry of the left and right terms
                        STentry leftEntry = left.getEntry();
                        STentry tmp = env.lookup(env.getNestingLevel(), right.getId());
                        if (tmp == null) {
                            // this case is caught from checkSemantics
                            System.out.println("cannot find symbol " + right.getId());
                            System.exit(1);
                        }
                        leftEntry.addPropagation(right.getId(), right.getCounter());
                    }


                } else {
                    // nothing
                }



                // End Effect Propagation checking
            } else {
                System.out.println("error: cannot find symbol " + ((LhsNode<?>) lhs).getId());
                System.exit(0);
            }

        } else {
            // do nothing
        }
        if(lhs instanceof LhsNode){
            System.out.println(((LhsNode) lhs).getEntry().toPrint("lhs "));
        }
        return 1;
    }


    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        // lhs
        lhs.setEffectDecFun(this.effectDecFun);
        res.addAll(lhs.checkSemantics(env));
        // exp
        exp.setEffectDecFun(this.effectDecFun);
        res.addAll(exp.checkSemantics(env));
        if(res.size()==0){
            this.checkEffects(env);
        }
        return res;

    }

    @Override
    public Node clone(){
        try{
            AssignmentNode cloned = (AssignmentNode) super.clone();
            cloned.lhs = (Node) this.lhs.clone();
            cloned.exp = (Node) this.exp.clone();
            return cloned;
        } catch(CloneNotSupportedException e){
            return null;
        }
    }
}
