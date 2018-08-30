package utils;

/**
 * 
 * @author Gaetan Inidjel
 *
 */
public class MethodesPratiques {

	/**
	 * Cette méthode prend en entrée un nombre décimal, et le renvoie avec deux chiffres significatifs
	 * 
	 * @param nombre
	 * 			nombre de type Double en entrée
	 * @return nombre de type Double arrondi à deux chiffres significatifs
	 */
	public static Double deuxChiffresSignificatifs(Double nombre) {
		
		return Math.round(nombre*100d)/100d;	
		
	}
}
