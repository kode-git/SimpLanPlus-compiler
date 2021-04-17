package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

public class BoolTypeNode implements Node, GenericTypeNode {

  public BoolTypeNode () {
  }
  
  public String toPrint(String s) {
	return s+"BoolType ";
  }
    
  //not used
  public Node typeCheck() {
    return null;
  }
  
  @Override
 	public ArrayList<SemanticError> checkSemantics(Environment env) {

 	  return new ArrayList<SemanticError>();
 	}
  
  //not used
  public String codeGeneration() {
		return "";
  }

    
}  