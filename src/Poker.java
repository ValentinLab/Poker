/**
 * Mini projet: Jeu de Poker
 *
 * Valentin Perignon et Fabian Devel
 * Groupe C1 CMI Informatique
 */

public class Poker {

	/******************************
	 *     Type agrégé Gobelet    *
	 ******************************/

	/**
	 * Type agrégé symbolisant un gobelet contenant 5 dés. <br>
	 * Le type agrégé contient 5 entier symbolisant la valeur des 5 dés
	 */
	public static class Gobelet {
		int de1;
		int de2;
		int de3;
		int de4;
		int de5;
	}

	/**
	 * Lancer les 5 dés
	 *
	 * @param inf Borne inférieure (valeur minimum du dé)
	 * @param sup Borne supérieure (valeur maximum du dé)
	 * @return Le gobelet avec les 5 dés lancés
	 */
	public static Gobelet lancerDes(int inf, int sup) {
		Gobelet gblt = new Gobelet();

		gblt.de1 = tirerHasard(sup, inf);
		gblt.de2 = tirerHasard(sup, inf);
		gblt.de3 = tirerHasard(sup, inf);
		gblt.de4 = tirerHasard(sup, inf);
		gblt.de5 = tirerHasard(sup, inf);

		return(gblt);
	}

	/**
	 * Calculer la combinaison la plus forte réalisée par les dés
	 * Combinaison 1: Paire
	 * Combinaison 2: Double paire
	 * Combinaison 3: Brelan
	 * Combinaison 4: Full
	 * Combinaison 5: Carré
	 * Combinaison 6: Petite suite
	 * Combinaison 7: Suite
	 * Combinaison 8: Poker
	 *
	 * @param gblt Gobelet contenant les 5 dés
	 * @return La plus forte combinaison réalisée
	 */
	public static String calculerCombinaison(Gobelet gblt) {
		int combinaisonNb, combinaisonIdentique, combinaisonDouble, combinaisonSuite;
		String combinaison = "";

		// tri du gobelet
		Gobelet gbltTri = trierGobelet(gblt);

		// calcul d'une combinaison de type paire / brelan / carré / poker
		combinaisonIdentique = calculerIdentique(gbltTri);

		// calcul d'une combinaison de type double paire / full
		combinaisonDouble = calculerDouble(gbltTri);

		// calcul d'une combinaison de type petite suite / suite
		combinaisonSuite = calculerSuite(gbltTri);

		// calcul de la plus forte combinaison
		combinaisonNb = obtenirMax(combinaisonIdentique, obtenirMax(combinaisonDouble, combinaisonSuite));
		// TODO vérifier le résultat
		switch(combinaisonNb) {
			case 0:
				combinaison = "Rien";
				break;
			case 8:
				combinaison = "Poker";
				break;
			case 7:
				combinaison = "Suite";
				break;
			case 6:
				combinaison = "Petite Suite";
				break;
			case 5:
				combinaison = "Carré";
				break;
			case 4:
				combinaison = "Full";
				break;
			case 3:
				combinaison = "Brelan";
				break;
			case 2:
				combinaison = "Double paire";
				break;
			case 1:
				combinaison = "Paire";
				break;
		}

		return combinaison;
	}

	/**
	 * Calculer une combinaison de type pair / brelan / carré / poker
	 *
	 * @param gblt Gobelet contenant les dés (en ayant trié le gobelet)
	 * @return Le numéro correspondant à une combinaison
	 */
	public static int calculerIdentique(Gobelet gblt) {
		int combinaison = 0;

		// comparaison des dés entre eux
		if(gblt.de1 == gblt.de2) { // comparaison de dé 1 avec les 4 autres
			combinaison = 1; // paire
			if(gblt.de1 == gblt.de3) {
				combinaison = 3; // brelan
				if(gblt.de1 == gblt.de4) {
					combinaison = 5; // carré
					if(gblt.de1 == gblt.de5) {
						combinaison = 8; // poker
					}
				}
			}
		} else {
			if(gblt.de2 == gblt.de3) { // comparaison de dé 2 avec les 3 autres
				combinaison = 1; // paire
				if(gblt.de2 == gblt.de4) {
					combinaison = 3; // brelan
					if(gblt.de2 == gblt.de5) {
						combinaison = 5; // carré
					}
				}
			} else {
				if(gblt.de3 == gblt.de4) { // comparaison du dé 3 avec les 2 autres
					combinaison = 1; // paire
					if(gblt.de3 == gblt.de5) {
						combinaison = 3; // brelan
					}
				} else {
					if(gblt.de4 == gblt.de5) { // comparaison du dé 4 avec le 5e
						combinaison = 1; // paire
					}
				}
			}
		}

		return combinaison;
	}

	/**
	 * Calculer une combinaison de type double paire / full
	 *
	 * @param gblt Gobelet contenant les dés (en ayant trié le gobelet)
	 * @return Le numéro correspondant à une combinaison
	 */
	public static int calculerDouble(Gobelet gblt) {
		// TODO compléter la fonction calculerDouble()
		int combinaison = 0;

		return combinaison;
	}

	/**
	 * Calculer une combinaison de type petite suite / suite
	 *
	 * @param gblt Gobelet contenant les dés (en ayant trié le gobelet)
	 * @return Le numéro correspondant  une combinaison
	 */
	public static int calculerSuite(Gobelet gblt) {
		int combinaison = 0;

		// traitement
		if(gblt.de2 == gblt.de1 + 1 && gblt.de3 == gblt.de2 + 1 && gblt.de4 == gblt.de3 + 1 && gblt.de5 == gblt.de4 + 1) {
			combinaison = 7; // suite
		} else {
			if((gblt.de2 == gblt.de1 + 1 && gblt.de3 == gblt.de2 + 1 && gblt.de4 == gblt.de3 + 1) || (gblt.de3 == gblt.de2 + 1 && gblt.de4 == gblt.de3 + 1 && gblt.de5 == gblt.de4 + 1)) {
				combinaison = 6; // petite suite
			}
		}

		return combinaison;
	}

	/**
	 * Trier les dés dans l'ordre croissant
	 *
	 * @param gblt Gobelet contenant les dés
	 * @return Le gobelet avec les dés triés
	 */
	public static Gobelet trierGobelet(Gobelet gblt) {
		Gobelet gbltTri = gblt;

		// triage des dés
		int tmp;
		while(!(gbltTri.de1 <= gbltTri.de2 && gbltTri.de2 <= gbltTri.de3 && gbltTri.de3 <= gbltTri.de4 && gbltTri.de4 <= gbltTri.de5)) {
			if(gbltTri.de2 < gbltTri.de1) {
				tmp = gbltTri.de1;
				gbltTri.de1 = gbltTri.de2;
				gbltTri.de2 = tmp;
			}
			if(gbltTri.de3 < gbltTri.de2) {
				tmp = gbltTri.de2;
				gbltTri.de2 = gbltTri.de3;
				gbltTri.de3 = tmp;
			}
			if(gbltTri.de4 < gbltTri.de3) {
				tmp = gbltTri.de3;
				gbltTri.de3 = gbltTri.de4;
				gbltTri.de4 = tmp;
			}
			if(gbltTri.de5 < gblt.de4) {
				tmp = gbltTri.de4;
				gbltTri.de4 = gbltTri.de5;
				gbltTri.de5 = tmp;
			}
		}

		return gbltTri;
	}

	/**
	 * Afficher le lancer réalisé et la plus forte combinaison
	 *
	 * @param gblt Gobelet contenant les dés
	 */
	public static void afficherLancer(Gobelet gblt) {
		// affichage de la valeur des dés
		Ecran.afficher("(", gblt.de1, " ", gblt.de2, " ", gblt.de3, " ", gblt.de4, " ", gblt.de5, ")");
		// affichage de la combinaison
		Ecran.afficher(" - ", calculerCombinaison(gblt));
	}

	/******************************
	 *     Fonctions & Actions    *
	 ******************************/

	/**
	 * Tirer un nombre au hasard dans un intervalle donné
	 *
	 * @param inf Borne inférieure
	 * @param sup Borne supérieure
	 * @return Le nombre tiré au hasard
	 */
	public static int tirerHasard(int inf, int sup) {
		return((int)((sup - inf)*Math.random() + inf));
	}

	/**
	 * Obtenir l'entier maximum (comparaison entre deux entiers)
	 *
	 * @param a Premier nombre
	 * @param b Deuxième nombre
	 * @return Le plus grand nombre des deux
	 */
	public static int obtenirMax(int a, int b) {
		int resultat;

		if(a > b) {
			resultat = a;
		} else {
			resultat = b;
		}

		return resultat;
	}

	/******************************
	 *            Main            *
	 ******************************/

	public static void main(String ars[]) {
		// déclaration des données
		final int INF = 1;
		final int SUP = 6;

		// lancement des dés et affichage
		Gobelet gblt = lancerDes(INF, SUP); // nouveau lancer
		afficherLancer(gblt); // affichage du lancer


		// ///////////////////////
		// DEBUG
		// ///////////////////////
		Ecran.sautDeLigne();
		afficherLancer(trierGobelet(gblt));
	}

}
