/**
 * Mini projet: Jeu de Poker d'As
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

		gblt.de1 = tirerHasard(inf, sup);
		gblt.de2 = tirerHasard(inf, sup);
		gblt.de3 = tirerHasard(inf, sup);
		gblt.de4 = tirerHasard(inf, sup);
		gblt.de5 = tirerHasard(inf, sup);

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
	 * @return La plus forte combinaison réalisée (l'entier corréspondant)
	 */
	public static int calculerCombinaison(Gobelet gblt) {
		int combinaisonNb, combinaisonIdentique, combinaisonDouble, combinaisonSuite;
		Gobelet aTrier = clonerGobelet(gblt);

		// tri du gobelet
		Gobelet gbltTri = trierGobelet(aTrier);
		// tri du gobelet pour la suite
		Gobelet gbltTriSuite = trierGobeletSuite(gbltTri);

		// calcul d'une combinaison de type paire / brelan / carré / poker
		combinaisonIdentique = calculerIdentique(gbltTri);

		// calcul d'une combinaison de type double paire / full
		combinaisonDouble = calculerDouble(gbltTri);

		// calcul d'une combinaison de type petite suite / suite
		combinaisonSuite = calculerSuite(gbltTriSuite);

		// calcul de la plus forte combinaison
		combinaisonNb = obtenirMax(combinaisonIdentique, obtenirMax(combinaisonDouble, combinaisonSuite));

		return combinaisonNb;
	}

	public static String convertirNom(int combinaisonNb) {
		String combinaison = "";

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
	 * @return Le numéro correspondant à  une combinaison
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
	 * @return Le numéro correspondant à  une combinaison
	 */
	public static int calculerDouble(Gobelet gblt) {
		int combinaison = 0;

		if((gblt.de1 == gblt.de2 && gblt.de3 == gblt.de4 && gblt.de4 == gblt.de5) || (gblt.de4 == gblt.de5 && gblt.de1 == gblt.de2 && gblt.de2 == gblt.de3)) {
			combinaison = 4; // full
		} else {
			if((gblt.de1 == gblt.de2 && gblt.de3 == gblt.de4 && gblt.de2 != gblt.de3) || (gblt.de1 == gblt.de2 && gblt.de4 == gblt.de5 && gblt.de2 != gblt.de4) || (gblt.de2 == gblt.de3 && gblt.de4 == gblt.de5 && gblt.de3 != gblt.de4)) {
				combinaison = 2; // double paire
			}
		}

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
	 * Trier les dés spécialement pour détecter les suites
	 *
	 * @param gblt Gobelet contenant les dés
	 * @return Le gobelet avec les dés triés
	 */
	public static Gobelet trierGobeletSuite(Gobelet gblt) {
		Gobelet gbltTriS = clonerGobelet(gblt);

		// triage des dés
		if(gbltTriS.de1 == gbltTriS.de2) {
			gbltTriS.de2 = 9;
		} else {
			if(gbltTriS.de2 == gbltTriS.de3) {
				gbltTriS.de3 = 9;
			} else {
				if(gbltTriS.de3 == gbltTriS.de4) {
					gbltTriS.de4 = 9;
				} else {
					if(gbltTriS.de4 == gbltTriS.de5) {
						gbltTriS.de5 = 9;
					}
				}
			}
		}
		gbltTriS = trierGobelet(gbltTriS);

		return gbltTriS;
	}

	/**
	 * Afficher le lancer réalisé et la plus forte combinaison
	 *
	 * @param joueur Joueur qui réalise le lancer
	 */
	public static void afficherLancer(Joueur joueur) {
		// affichage de la valeur des dés
		Ecran.afficher(joueur.nom, " : ( ", joueur.gblt.de1, " ", joueur.gblt.de2, " ", joueur.gblt.de3, " ", joueur.gblt.de4, " ", joueur.gblt.de5, " )");
		// affichage de la combinaison
		Ecran.afficher(" - ", convertirNom(calculerCombinaison(joueur.gblt)));
	}

	/**
	 * Cloner un gobelet
	 *
	 * @param gblt Gobelet contenant les dés
	 * @return Le nouveau gobelet trié
	 */
	public static Gobelet clonerGobelet(Gobelet gblt) {
		Gobelet nvGblt = new Gobelet();

		nvGblt.de1 = gblt.de1;
		nvGblt.de2 = gblt.de2;
		nvGblt.de3 = gblt.de3;
		nvGblt.de4 = gblt.de4;
		nvGblt.de5 = gblt.de5;

		return nvGblt;
	}

	/******************************
	 *     Type agrégé Relance    *
	 ******************************/

	/**
	 * Type agrégé symbolisant la relance des 5 dés. <br>
	 * Le type agrégé contient la réponse de l'utilisateur, le nombre de dés à  relancer et le numéro du dés à  relancer
	 */
	public static class Relance {
		boolean reponse;
		int nbDes;
		int numDes;
	}

	/**
	 * Choisir les dés à  relancer
	 *
	 * @param relance Type agrégé modélisant la relance de dés
	 * @param gobelet Gobelet contenant les dés à  relancer
	 * @param SUP Borne supérieur pour les valeurs des dés
	 * @param INF Borne inférieure pour les valeurs des dés
	 */
	public static void choixRelances (Relance relance, Gobelet gobelet, int SUP, int INF) {
		Ecran.afficher("Voulez-vous relancer des dés ? (taper o pour oui, une autre touche pour non)  ");
		char reponse = Clavier.saisirChar();
		if (reponse == 'o'){
			relance.reponse = true;
		} else {
			relance.reponse = false;
		}

		if(relance.reponse == true){
			Ecran.afficher("\nCombien de dés voulez-vous relancer ? ");
			relance.nbDes = Clavier.saisirInt();
			while (relance.nbDes < 1 || relance.nbDes > 5){
				Ecran.afficher("\nLe nombre de dés que vous avez saisi n'est pas valable car non compris entre 1 et 5. Réessayez la saisie: ");
				relance.nbDes = Clavier.saisirInt();
			}
			
			if(relance.nbDes == 5) {
				relance.numDes = 1;
				for(int i = 0; i<5; i++){
					actionRelance (relance, gobelet, SUP, INF);
					relance.numDes = relance.numDes + 1;
				}
			} else {
				Ecran.afficher("\nQuel(s) dé(s) voulez-vous relancer ? ");
				relanceMultiple(relance, gobelet, SUP, INF);
			}
		}
	}

	/**
	 * Vérification de la saisie pour la relance
	 *
	 * @param relance Type agrégé modélisant la relance de dés
	 */
	public static void saisieNumDesCorrect (Relance relance) {
		relance.numDes = Clavier.saisirInt();
		while (relance.numDes < 1 || relance.numDes > 5){

			Ecran.afficher("\nLe dé que vous avez saisi n'est pas valable car son numéro doit être compris entre 1 et 5. Réessayez la saisie: ");
			relance.numDes = Clavier.saisirInt();
		}
	}
	

	/**
	 * Relancer plusieurs dés
	 *
	 * @param relance Type agrégé modélisant la relance de dés
	 * @param gobelet Gobelet contenant les dés à  relancer
	 * @param SUP Borne supérieur pour les valeurs des dés
	 * @param INF Borne inférieure pour les valeurs des dés
	 * @param compteur Nombre de dés à  relancer
	 */
	public static void relanceMultiple (Relance relance, Gobelet gobelet, int SUP, int INF) {
		Ecran.afficher("\nEntrez le numéro du premier dé que vous voulez relancez (de 1 à  5, de gauche à  droite): ");
		saisieNumDesCorrect (relance);
		actionRelance (relance, gobelet, SUP, INF);
		for (int i=1; i<relance.nbDes; i++){
			Ecran.afficher("\nEntrez le numéro du prochain dé : ");
			saisieNumDesCorrect (relance);
			actionRelance (relance, gobelet, SUP, INF);
		}

	}

	/**
	 * Donner une nouvelle valeur aux dés
	 *
	 * @param relance Type agrégé modélisant la relance de dés
	 * @param gobelet Gobelet contenant les dés à  relancer
	 * @param SUP Borne supérieur pour les valeurs des dés
	 * @param INF Borne inférieure pour les valeurs des dés
	 */
	public static void actionRelance (Relance relance, Gobelet gobelet, int SUP, int INF) {
		Ecran.afficher("Vous lancez le dé "+ relance.numDes+"\n");
		switch(relance.numDes) {
			case 1 : {
				gobelet.de1 = tirerHasard(INF, SUP);
				break;
			}
			case 2 : {
				gobelet.de2 = tirerHasard(INF, SUP);
				break;
			}
			case 3 : {
				gobelet.de3 = tirerHasard(INF, SUP);
				break;
			}
			case 4 : {
				gobelet.de4 = tirerHasard(INF, SUP);
				break;
			}
			case 5 : {
				gobelet.de5 = tirerHasard(INF, SUP);
				break;
			}
		}

	}

	/******************************
	 *     Type agrégé Joueur     *
	 ******************************/

	/**
	 * Type agrégé symbolisant un joueur. <br>
	 * Le type agrégé contient le nom du joueur, un gobelet et son score
	 */
	public static class Joueur {
		String nom;
		Gobelet gblt = new Gobelet();
		int score = 0;
	}

	/**
	 * Demander le noms des joueurs
	 *
	 * @param J1 Premier joueur
	 * @param J2 Second joueur
	 */
	public static void demandeNom (Joueur J1, Joueur J2) {
		Ecran.afficher("Quel est le nom du joueur 1 ? ");
		J1.nom = Clavier.saisirString();
		Ecran.afficher("Quel est le nom du joueur 2 ? ");
		J2.nom = Clavier.saisirString();
		Ecran.sautDeLigne();
		Ecran.afficherln("Bonjour " + J1.nom + " et " + J2.nom + " et bienvenue au Poker d'As! ");
		Ecran.sautDeLigne();
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
		return inf + (int)(Math.random() * (sup - inf + 1));
	}

	/**
	 * Obtenir l'entier maximum (comparaison entre deux entiers)
	 *
	 * @param a Premier nombre
	 * @param b Deuxià¨me nombre
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

	/**
	 * Calculer le score des joueurs en fonction des coups
	 *
	 * @param joueur1 Premier joueur
	 * @param joueur2 Second joueur
	 */
	public static void calculerScore(Joueur joueur1, Joueur joueur2) {
		// initialisation des variables
		int combinaisonJ1 = calculerCombinaison(joueur1.gblt);
		int combinaisonJ2 = calculerCombinaison(joueur2.gblt);

		// calcul du score et affichage
		Ecran.afficherln("\n##############################");
		if(combinaisonJ1 > combinaisonJ2) {
			joueur1.score++;
			Ecran.afficherln(joueur1.nom, " gagne le coup !");
		} else {
			if(combinaisonJ1 < combinaisonJ2) {
				joueur2.score++;
				Ecran.afficherln(joueur2.nom, " gagne le coup !");
			} else {
				Ecran.afficherln("Match nul !");
			}
		}

		// affichage du score
		Ecran.afficherln("Score:\n - ", joueur1.nom, " : ", joueur1.score, "\n - ", joueur2.nom, " : ", joueur2.score);
		Ecran.afficherln("##############################\n");
		Ecran.sautDeLigne();
	}

	/**
	 * Réalisation des différents tours de jeu
	 *
	 * @param j1 Premier joueur
	 * @param j2 Second joueur
	 * @param inf Borne inférieure
	 * @param sup Borne supérieure
	 * @param relance Type agrégé symbolisant la relance des dés
	 */
	public static void tourJeu(Joueur j1, Joueur j2, int inf, int sup, Relance relance) {
		// déclaration des variables
		int compteur = 1;
		int tourJeu = tirerPremierJoueur(j1, j2);
		boolean continuerJeu = true;

		// boucle de jeu
		do {
			if(tourJeu == 1) {
				if(compteur%6 == 1 || compteur%6 == 2) {
					// affichage du premier joueur
					if(compteur%6 == 1) {
						Ecran.afficherln("C'est " + j1.nom + " qui va commencer !");
					}

					// lancement des dés et affichage
					j1.gblt = lancerDes(inf, sup); // nouveau lancer
					if(compteur%2 != 0) { // afficher qu'il s'agit d'un nouveau coup
						Ecran.sautDeLigne();
						Ecran.afficherln("Nouveau coup: ");
					}
					afficherLancer(j1); // affichage du lancer
					Ecran.sautDeLigne();
				} else {
					// affichage du joueur
					Ecran.afficherln("\nAu tour de ", j1.nom, "...");
					// relance
					choixRelances(relance, j1.gblt, sup, inf);
					afficherLancer(j1);
					Ecran.sautDeLigne();
				}
			} else {
				if(compteur%6 == 1 || compteur%6 == 2) {
					// affichage du premier joueur
					if(compteur%6 == 1) {
						Ecran.afficherln("C'est " + j2.nom + " qui va commencer !");
					}

					// lancement des dés et affichage
					j2.gblt = lancerDes(inf, sup); // afficher qu'il s'agit d'un nouveau coup
					if(compteur%2 != 0) {
						Ecran.sautDeLigne();
						Ecran.afficherln("Nouveau coup: ");
					}
					afficherLancer(j2); // affichage du lancer
					Ecran.sautDeLigne();
				} else {
					// affichage du joueur
					Ecran.afficherln("\nAu tour de ", j2.nom, "...");
					// relance
					choixRelances(relance, j2.gblt, sup, inf);
					Ecran.sautDeLigne();
					afficherLancer(j2);
					Ecran.sautDeLigne();
				}
			}

			// fin de coup
			if(compteur%6 == 0) {
				// calcul et affichage du score
				Ecran.sautDeLigne();
				calculerScore(j1, j2);

				// changement du premier joueur
				if(tourJeu == 1) {
					tourJeu = 2;
				} else {
					tourJeu = 1;
				}
			}

			// proposition de fin de jeu
			if(compteur%6 == 0) {
				continuerJeu = choisirContinuer();
			}

			// changement de joueur
			if(tourJeu == 1) {
				tourJeu = 2;
			} else {
				tourJeu = 1;
			}

			// compteur de tours
			compteur++;
		} while(continuerJeu);
	}

	public static boolean choisirContinuer() {
		boolean continuer = true;
		char choix;

		Ecran.afficher("Voulez-vous jouer un nouveau coup ? (o/n) ");
		choix = Clavier.saisirChar();
		while(choix != 'o' && choix != 'O' && choix != 'n' && choix != 'N') {
			Ecran.afficher("Voulez-vous jouer un nouveau coup ? (o/n) ");
			choix = Clavier.saisirChar();
		}

		switch (choix) {
			case 'o':
				continuer = false;
				break;
			case 'n':
				continuer = false;
				break;
		}

		return continuer;
	}

	/**
	 * Tirer au sort le joueur qui joue en premier
	 *
	 * @param j1 Premier joueur
	 * @param j2 Second joueur
	 * @return Le numéro du joueur qui joue le premier
	 */
	public static int tirerPremierJoueur(Joueur j1, Joueur j2) {
		int nb = tirerHasard(1, 2);
		if (nb == 1) {
			Ecran.afficher("Tirage au sort pour le commencement du jeu ... ");
		} else {
			Ecran.afficher("Tirage au sort pour le commencement du jeu ...");
		}
		Ecran.sautDeLigne();
		return(nb);
	}

	/******************************
		*            Main            *
	 ******************************/

	public static void main(String ars[]) {
		// déclaration des données
		Joueur joueur1 = new Joueur();
		Joueur joueur2 = new Joueur();
		Relance relance = new Relance();
		final int INF = 1;
		final int SUP = 6;

		// titre du jeu
		Ecran.afficherln("POKER D'AS\n");

		// initialisation des variables
		demandeNom(joueur1, joueur2);

		// tours de jeu
		tourJeu(joueur1, joueur2, INF, SUP, relance);
	}

}

// TODO verifier la saisie pour ne pas relancer plusieurs fois, le meme de