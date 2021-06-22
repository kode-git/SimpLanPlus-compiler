package ast;

import java.util.ArrayList;

import util.Environment;
import util.*;
//import util.SemanticError;

public interface Node extends Cloneable {
   
  String toPrint(String indent);

  Node typeCheck();

  String codeGeneration();

  int checkEffects(Environment env);

  void setEffectDecFun(int effectDecFun);

  ArrayList<SemanticError> checkSemantics(Environment env);

  Node clone();
}  