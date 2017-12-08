package com.polytech.calcule;

public class NormalisationMatrice {

	public double[][] normalisationMatrice(double[][] matriceOld, int tailleMatrice) {

		double[] somme = new double[tailleMatrice];
		double[][] matriceNew = new double[tailleMatrice][tailleMatrice];

		int p = 0;
		for (int j = 0; j < tailleMatrice; j++) {
			for (int i = 0; i < tailleMatrice; i++) {
				somme[p] = somme[p] + matriceOld[i][j];
			}
			p++;
		}

		for (int j = 0; j < tailleMatrice; j++) {
			for (int i = 0; i < tailleMatrice; i++) {
				matriceNew[i][j] = matriceOld[i][j] / somme[j];
			}
		}
		return matriceNew;
	}

	public double[][] normalisationMatriceDR(double[][] matriceAltInd, int nbAlternative, int nbIndicateurTot) {

		double[] somme = new double[nbIndicateurTot];
		double[][] matriceNew = new double[nbAlternative][nbIndicateurTot];

		int p = 0;
		for (int j = 0; j < nbIndicateurTot; j++) {
			for (int i = 0; i < nbAlternative; i++) {
				somme[p] = somme[p] + matriceAltInd[i][j];
			}
			p++;
		}

		for (int j = 0; j < nbIndicateurTot; j++) {
			for (int i = 0; i < nbAlternative; i++) {
				matriceNew[i][j] = matriceAltInd[i][j] / somme[j];
			}
		}
		return matriceNew;
	}

}
