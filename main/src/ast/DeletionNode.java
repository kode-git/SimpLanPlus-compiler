package ast;

import util.Environment;
import util.SemanticError;
import util.VoidNode;

import java.util.ArrayList;

public class DeletionNode implements Node{
    private String id;
    private int effectsST;
    private int effectDecFun;
    public DeletionNode(String id){
        this.id=id;
    }



    // getter and setter

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    // toPrint, typeCheck, checkSemantics, checkEffects, codeGeneration

    @Override
    public String toPrint(String s) {

        return s+"Delete: " + id + "\n";
    }

    @Override
    public Node typeCheck() {
        return new VoidNode(); // called only in case of delete as last statement of block
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public int checkEffects(Environment env) {
        if(effectDecFun == 0) {
            STentry entry = env.lookup(env.getNestingLevel(), id);
            effectsST = entry.getEffectState(0);
            if (effectsST >= 0 && effectsST <= 1) {
                int size = entry.getPointerCounter();
                for (int i = 0; i < size + 1; i++) {
                    entry.setEffectState(i, 2); // set every referen to pointer to 2 (delete state)

                }
                effectsST = 2; // setting local effect to 2 (delete state)
            } else {
                System.out.println("error: cannot find symbol " + id);
                System.exit(0);
            }
        } else {
            // do nothing
        }
        return effectsST;
    }

    @Override
    public void setEffectDecFun(int effectDecFun) {
        this.effectDecFun = effectDecFun;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
       ArrayList<SemanticError> res = new ArrayList<SemanticError>();
       STentry entry = env.lookup(env.getNestingLevel(), id);
        if(entry == null){
            res.add(new SemanticError("Id " +this.id + " not declared"));
        }
        if(res.size()==0){
            this.checkEffects(env);
        }
        return res;
    }
}
