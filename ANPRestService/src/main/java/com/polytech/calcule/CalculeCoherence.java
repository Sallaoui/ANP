package com.polytech.calcule;


public class CalculeCoherence {

	
	public CalculeCoherence() {
		// TODO Auto-generated constructor stub
	}

	public String calculCoherence(double[][] arrayMatrice, int tailleMatrice) {

		double tableauIA[] = { 0.0, 0.0, 0.58, 0.90, 1.12, 1.24, 1.32, 1.41, 1.45, 1.49, 1.51, 1.48, 1.56, 1.57, 1.59 };

		// Calcul Lambda Max

		CalculVecteurPropre vect = new CalculVecteurPropre();
		float[] vecteurPropre = new float[tailleMatrice];
		float[] vecteurSommeLignes = new float[tailleMatrice];
		float[] vecteurDivision = new float[tailleMatrice];
		float lambda = 0;
		float IC, RC;
		vecteurPropre = vect.calculVecteurPropore(arrayMatrice, tailleMatrice);

		double[][] arrayMatrice2 = new double[tailleMatrice][tailleMatrice];
		int i, j;
		for (i = 0; i < tailleMatrice; i++)
			for (j = 0; j < tailleMatrice; j++)
				arrayMatrice2[i][j] = vecteurPropre[j] * arrayMatrice[i][j];

		for (i = 0; i < tailleMatrice; i++)
			for (j = 0; j < tailleMatrice; j++)
				vecteurSommeLignes[i] += arrayMatrice2[i][j];

		for (i = 0; i < tailleMatrice; i++)
			vecteurDivision[i] = vecteurSommeLignes[i] / vecteurPropre[i];

		for (i = 0; i < tailleMatrice; i++)
			lambda += vecteurDivision[i];

		lambda = lambda / tailleMatrice;

		// Calcul Indice de Coherence
		IC = (lambda - tailleMatrice) / (tailleMatrice - 1);

		// Calcul Ratio de Coherence
		RC = (float) (IC / tableauIA[tailleMatrice - 1]);

		if (RC < 0.1) {
			System.out.println("OK : RC = " + RC);
			return "OK";
		} else {
			System.out.println("NOK : RC = " + RC);
			return "NOK";
		}
	}

}

