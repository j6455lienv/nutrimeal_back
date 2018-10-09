package utils;

public class MethodesPratiques {

  private MethodesPratiques() {
  }

  /**
   * Cette méthode prend en entrée un nombre décimal, et le renvoie avec deux chiffres significatifs
   *
   * @param pNombre nombre de type Double en entrée
   * @return nombre de type Double arrondi à deux chiffres significatifs
   */
  public static Double deuxChiffresSignificatifs(Double pNombre) {
    return Math.round(pNombre * 100d) / 100d;
  }
}
