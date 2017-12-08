package com.polytech.calcule;

import java.util.ArrayList;
import java.util.Arrays;

import Jama.Matrix;

public class CalculVecteurPropre {

	public void afficherVecteur(float[] vecteurPropre) {
		System.out.print("[");
		for (int i = 0; i < vecteurPropre.length - 1; i++)
			System.out.print(vecteurPropre[i] + " , ");
		System.out.println(vecteurPropre[vecteurPropre.length - 1] + "]");
	}

	public float[] calculVecteurPropore(double[][] arrayMatrice, int tailleMatrice) {

		Matrix matriceOriginal = new Matrix(arrayMatrice); // smStatio

		double[][] arraye = new double[1][tailleMatrice];
		for (int i = 0; i < tailleMatrice; i++) {
			for (int j = 0; j < tailleMatrice; j++) {
				arraye[0][j] = 1;
			}
		}

		Matrix e = new Matrix(arraye);

		Matrix matrice2 = new Matrix(arrayMatrice); // nouvelle matrice
		Matrix matrice3 = new Matrix(arrayMatrice); // previous matrix
		Matrix matrice4 = new Matrix(arrayMatrice); // previous matrix
		Matrix matrice5 = new Matrix(arrayMatrice); // previous matrix
		float[] mat5, mat6;

		int k = 1;
		do {
			Matrix matrice6 = matrice5.copy();
			for (int i = 1; i < k; i++)
				matrice2 = matrice2.times(matriceOriginal); // cc(k)

			matrice3 = matrice2.times(e.transpose()); // multplication1 ->
														// CC^k*e^T
			matrice4 = e.times(matrice2).times(e.transpose()); // e * CC^k * e^T

			matrice5 = matrice3.timesEquals(1 / matrice4.get(0, 0)); // CrOg

			k++;

			mat5 = convertDoubleToFloat(matrice5.getArrayCopy());
			mat6 = convertDoubleToFloat(matrice6.getArrayCopy());

		} while (!Arrays.equals(mat5, mat6));

		// System.out.println("Vecteur propre obtenu après " + k + "
		// itérations");

		// matrice5.print(10, 3);

		return mat5;
	}

	private float[] convertDoubleToFloat(double[][] arrayDouble) {
		float[] floatArray = new float[arrayDouble.length];
		for (int i = 0; i < arrayDouble.length; i++) {
			floatArray[i] = (float) arrayDouble[i][0];
		}

		return floatArray;
	}

	public double[][] matriceComposeeVP(ArrayList<double[][]> listMatriceInput, int nbIndicateurTot, int nbCritere,
			ArrayList<Integer> listNbIndicateur) {

		double[][] matriceIndicateurArray = new double[nbIndicateurTot][nbCritere];
		ArrayList<float[]> vecteurProrpres = new ArrayList<float[]>();

		for (int i = 0; i < nbCritere; i++) {

			vecteurProrpres.add(i, new float[listNbIndicateur.get(i)]);
		}

		for (int i = 0; i < listMatriceInput.size(); i++) {
			vecteurProrpres.set(i,
					calculVecteurPropore(listMatriceInput.get(i), listMatriceInput.get(i).length));
		}

		int cpt = 0, j = 0;

		for (int i = 0; i < nbCritere; i++) {
			for (j = cpt; j < listNbIndicateur.get(i) + cpt; j++) {
				matriceIndicateurArray[j][i] = vecteurProrpres.get(i)[j - cpt];
			}
			cpt = j;
		}

		return matriceIndicateurArray;
	}

}
