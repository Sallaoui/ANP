package com.polytech.Matrice;

import com.polytech.calcule.NormalisationMatrice;
import com.polytech.calcule.SuperMatriceCalcul;

import Jama.Matrix;

public class SuperMatriceData {

	private double[][] superMatrice;
	public Matrix creationSuperMatrice(int nbCritere, int nbIndicateurTot, int nbAlternative, float[] vecteurPropreCrOg, double[][] matriceCCDep, double[][]  matriceIndicateurArray, double[][] matriceAltInd ,double[][] matriceIIDep ){
		
	
	SuperMatriceCalcul calculSuperMatrice =	new SuperMatriceCalcul();
	superMatrice = new double[nbCritere + nbIndicateurTot + nbAlternative+1][nbCritere + nbIndicateurTot
	                                                                    				+ nbAlternative+1];

	superMatrice[0][0]=1;

	// CrOg
	for(
	int i = 1;i<=nbCritere;i++)
	{
		superMatrice[i][0] = vecteurPropreCrOg[i - 1];
	}

	// CCDep
	for(
	int i = 1;i<=nbCritere;i++)
	{
		for (int j = 1; j <= nbCritere; j++) {
			superMatrice[i][j] = matriceCCDep[i - 1][j - 1];
		}
	}

	// ICr
	for(
	int i = 1 + nbCritere;i<=(nbCritere+nbIndicateurTot);i++)
	{
		for (int j = 1; j <= nbCritere; j++) {
			superMatrice[i][j] = matriceIndicateurArray[i - 1 - nbCritere][j - 1];
		}
	}

	// IIDep
	for(
	int i = 1 + nbCritere;i<=(nbCritere+nbIndicateurTot);i++)
	{
		for (int j = 1 + nbCritere; j <= (nbCritere + nbIndicateurTot); j++) {
			superMatrice[i][j] = matriceIIDep[i - 1 - nbCritere][j - nbCritere - 1];
		}
	}

	// Donnees Reelles
	for(
	int i = nbCritere + nbIndicateurTot + 1;i<=nbCritere+nbIndicateurTot+nbAlternative;i++)
	{
		for (int j = nbCritere + 1; j <= nbCritere + nbIndicateurTot; j++) {
			superMatrice[i][j] = matriceAltInd[i - nbCritere - nbIndicateurTot - 1][j - nbCritere - 1];
		}
	}

	// Matrice identité
	for(
	int i = nbCritere + nbIndicateurTot + 1;i<=nbCritere+nbIndicateurTot+nbAlternative;i++)
	{
		superMatrice[i][i] = 1;
	}

	System.out.println("Super Matrice");new Matrix(superMatrice).print(10,3);

	System.out.println("Super Matrice stochastique");

	double[][] superMatriceStoch = new double[nbCritere + nbIndicateurTot + nbAlternative + 1][nbCritere
			+ nbIndicateurTot + nbAlternative
			+ 1];superMatriceStoch=new NormalisationMatrice().normalisationMatrice(superMatrice,nbCritere+nbIndicateurTot+nbAlternative+1);
			
	new Matrix(superMatriceStoch).print(10,3);

	System.out.println("Super Matrice stationnaire");

//	return calculSuperMatrice.multiplicationSM(superMatriceStoch);
	return new Matrix(superMatrice);

}
	public double[][] getSuperMatrice() {
		return superMatrice;
	}
	public void setSuperMatrice(double[][] superMatrice) {
		this.superMatrice = superMatrice;
	}
	
}