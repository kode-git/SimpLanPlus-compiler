package ast;

import java.util.ArrayList;

import util.Environment;
import util.Offset;
import util.SemanticError;

public class IntTypeNode implements Node, GenericTypeNode, Cloneable {
  

    public IntTypeNode () {}

    // toPrint, typeCheck, checkSemantics, checkEffects, codeGeneration


    public String toPrint(String s) {
	    return s+"IntType ";
    }


    public Node typeCheck() {
    return this;
  }



    // not used
    public int checkEffects(Environment env) {
        return 0;
    }

    @Override
    public void setEffectDecFun(int effectDecFun) {
        // not used
    }

    @Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
	  return new ArrayList<SemanticError>();
	}

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env, Offset offset) {
        return null;
    }

    @Override
    public Node clone() {
        try{
            IntTypeNode cloned = (IntTypeNode) super.clone();
            return cloned;
        } catch(CloneNotSupportedException e){
            return null;
        }
    }

    public String codeGeneration() {
        return "";
    }

}  