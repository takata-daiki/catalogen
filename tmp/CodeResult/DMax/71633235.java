package common.partie.unite;

import java.io.Serializable;

import common.Constante;
import common.ElementPlateau;
import common.partie.plateau.Case;

/**
 * @author omar
 */
public class Unite extends ElementPlateau implements Serializable{
	
	private static final long serialVersionUID = Constante.NUMERO_DE_VERSION;
	private int deplacementRestant;// nombre de case que l'unite peut encore parcourrir
	
	public Unite(TypeUnite type,int niveau,Case position){
		super(type,niveau,position);
		deplacementRestant = type.getVitesse(niveau);
	}
	
	@Override
	public Case getCentre(){
		int x = position.getX();
		int y = position.getY();
		Case centre = new Case(x + Constante.LARGEUR_CASE / 2,y + Constante.HAUTEUR_CASE / 2);
		return centre;
	}
	
	public int getDeplacementRestant(){
		return deplacementRestant;
	}
	
	public void setDeplacementRestant(int deplacementRestant){
		this.deplacementRestant = deplacementRestant;
	}
	
	public boolean deplacementPossibleVersPosition(Case caseClique){
		boolean deplacementPossible = false;
		
		//coordonnée de l'unité
		int xUnite = getPosition().getX();
		int yUnite = getPosition().getY();
		
		//coordonnée de la case cliqué
		int xDestination = caseClique.getX();
		int yDestination = caseClique.getY();
		
		//limite de la zone cliquable
		int xMin = xUnite - getDeplacementRestant()*Constante.LARGEUR_CASE;
		int xMax = xUnite + Constante.LARGEUR_CASE + getDeplacementRestant()*Constante.LARGEUR_CASE;
		
		int yMin = yUnite - getDeplacementRestant()*Constante.HAUTEUR_CASE;
		int yMax = yUnite + Constante.HAUTEUR_CASE + getDeplacementRestant()*Constante.HAUTEUR_CASE;
		
		//si la case cliqué est dans la zone autorisé
		if (xDestination >= xMin && xDestination < xMax && yDestination >= yMin && yDestination < yMax){
			deplacementPossible = true;
		}
		
		return deplacementPossible;
	}
	
	public void decrementDeplacementRestant(int nbCases){
		deplacementRestant -= nbCases;
	}
	
	// methode deplacement possible avec calcul plus precis pour les deplacement en ligne et colonne
	public boolean peutBougerVers(int x, int y, char direction) {
		
		boolean deplacementPossible = false;

		// on calcul la distance maximal que peut parcourrir l'unite 
		double dMax;
		if ( direction == 'h' ){//deplacement horizontal, calcul basé sur les x
			dMax = deplacementRestant * Constante.LARGEUR_CASE;
		}else{//deplacement vertical, calcul basé sur les y
			dMax = deplacementRestant * Constante.HAUTEUR_CASE;
		}
		
		int dTotal;
		// on calcul la distance total a parcourir
		if ( direction == 'h' ){//deplacement horizontal, calcul basé sur les x
			dTotal = position.getX() - x;
		}else{//deplacement vertical, calcul basé sur les y
			dTotal = position.getY() - y;
		}
		if (dTotal < 0){dTotal = (-1)*dTotal;} //valeur absolu
			
		
		if (dTotal <= dMax){
			deplacementPossible = true;
		}
		
		return deplacementPossible;
	}
}
