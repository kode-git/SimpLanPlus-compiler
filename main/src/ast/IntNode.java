package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

public class IntNode implements Node {

  private Integer val;
  
  public IntNode (Integer n) {
    val=n;
  }
  
  public String toPrint(String s) {
    return s+"Int:" + Integer.toString(val) +"";
  }
  
  public Node typeCheck() {
    return new IntTypeNode();
  } 
  
  @Override
 	public ArrayList<SemanticError> checkSemantics(Environment env) {

 	  return new ArrayList<SemanticError>();
 	}
  
  public String codeGeneration() {
	return "push "+val+"\n";
  }

    @Override
    public int checkEffects(Environment env) {
        return 0;
    }

  @Override
  public void setEffectDecFun(int effectDecFun) {
    // not used
  }

}  