package utils;

// Tipi di errori che possono essere lanciati durante la compilazione

public enum SimplePlusErrorTypes {
	
	VAR_FOUND, 					// Variabile gi� dichiarata
	VAR_NOT_FOUND, 				// Variabile non dichiarata
	VAR_NOT_VAR, 				// Il parametro formale � un riferimento ma il parametro attuale non � una varExp
	
	FUN_FOUND, 					// Funzione gi� dichiarata
	FUN_NOT_FOUND, 				// Funzione non dichiarata
	FUN_FEW_ARGS, 				// Funzione chiamata con pochi argomenti
	FUN_MANY_ARGS, 				// Funzione chiamato con troppi argomenti
	
	TYPE_MISMATCH, 				// I tipi sono differenti
	TYPE_MISMATCH_ITE,			// I tipi di ritorno nel costrutto ite sono differenti
	TYPE_VAR_VOID, 				// La variabile � dichiarata con tipo 'void'
	
	BEHAVIOR_SEQ_ERROR, 		// Viene applicato un comportamento non conforme al comportamento attuale
	BEHAVIOR_VAR_FOUND			// Variabile gi� dichiarata e attiva

}
