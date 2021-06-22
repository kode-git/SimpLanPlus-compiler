package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;
import util.VoidNode;

public class PrintNode implements Node, Cloneable {

  private Node val;
  private int effectDecFun;
  
  public PrintNode (Node v) {
    val=v;
  }


  // getter and setter

  public Node getVal() {
    return val;
  }

  public void setVal(Node val) {
    this.val = val;
  }

  public int getEffectDecFun() {
    return effectDecFun;
  }

  @Override
  public void setEffectDecFun(int effectDecFun) {
    this.effectDecFun = effectDecFun;
  }


  // toPrint, TypeCheck, checkSemantics, checkEffects, codeGeneration

  public String toPrint(String s) {
    return s+"Print: " + val.toPrint(s+"") ;
  }

  public Node typeCheck() {
    return new VoidNode();
  }

  @Override
 	public ArrayList<SemanticError> checkSemantics(Environment env) {

      ArrayList<SemanticError> res = new ArrayList<SemanticError>();
      val.setEffectDecFun(this.effectDecFun);
      res.addAll(val.checkSemantics(env));
 	  return res;
 	}

  @Override
  public Node clone() {
    try{
      PrintNode cloned = (PrintNode) super.clone();
      cloned.val = (Node) this.val.clone();
      return cloned;
    } catch(CloneNotSupportedException e){
      return null;
    }
  }

  public String codeGeneration() {
		return val.codeGeneration()+"print\n";
  }

    @Override
    public int checkEffects(Environment env) {
        return 0;
    }

}  