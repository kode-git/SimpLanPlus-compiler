package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ast.STentry;

public class Environment implements Cloneable {
	

	
	private ArrayList<HashMap<String,STentry>>  symTable = new ArrayList<HashMap<String,STentry>>();
	private int nestingLevel = -1;
	private int offset = 0;

	public ArrayList<HashMap<String, STentry>> getSymTable() {
		return symTable;
	}

	public void setSymTable(ArrayList<HashMap<String, STentry>> symTable) {
		this.symTable = symTable;
	}

	public SemanticError newVarNode(int nestingLevelIntern, String id, STentry entry){
		HashMap<String, STentry> hm = (HashMap)symTable.get(nestingLevelIntern);
		if(hm.put(id, entry)!=null) return new SemanticError("Var id " + id + " already declared");
		return null;
	}

	public STentry lookup(int nestingLevelIntern,String id){
		STentry tmp;
		for(tmp = null; nestingLevelIntern >= 0 && tmp == null; tmp = (STentry)((HashMap)this.symTable.get(nestingLevelIntern--)).get(id)) {
			// lookup
		}
		if (tmp == null) {
			return null;//TODO making SemanticError when call this method
		} else {
			return tmp;

		}

	}

	public void addTable(HashMap<String, STentry> hm){
		this.symTable.add(hm);
	}


	public void removeTable(){
		this.symTable.remove(symTable.size()-1);
		this.nestingLevel--;
	}




	public void setNestingLevel(int nestingLevel) {
		this.nestingLevel = nestingLevel;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getNestingLevel(){
		return nestingLevel;
	}



	// TODO checking if there are NullPointerException for clone() invocation on some Node
	public Environment clone(){
		try{
			Environment cloned = (Environment) super.clone();
			cloned.symTable = new ArrayList<HashMap<String,STentry>>();
			System.out.println(cloned);
			for(int c=0; c<this.symTable.size();c++) {
				HashMap<String, STentry> newMap = new HashMap<String, STentry>();
				for (Map.Entry<String, STentry> entry : this.symTable.get(c).entrySet()) {
					// entry of the HashMap c
					newMap.put(entry.getKey(), (STentry) entry.getValue().clone());
				}
				cloned.symTable.add(c, newMap);
			}
			return cloned;

		}catch(CloneNotSupportedException e){

			return null; // Never happen because Environment implements Cloneable interface
		}
	}
	
	
}



