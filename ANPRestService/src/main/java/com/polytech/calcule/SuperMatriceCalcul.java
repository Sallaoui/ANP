package com.polytech.calcule;

import java.util.Arrays;

import Jama.Matrix;

public class SuperMatriceCalcul {

	Matrix matrice1, matrice2, matrice3;
	int k;

	public Matrix multiplicationSM(double[][] smStocha) {
		Matrix smStatio = new Matrix(smStocha); // smStatio
		matrice2 = new Matrix(smStocha); // nouvelle matrice
		matrice3 = new Matrix(smStocha); // previous matrix

		k = 1;
		do {
			matrice3 = matrice2.copy();
			for (int i = 1; i < 2 * k + 1; i++)
				matrice2 = matrice2.times(smStatio);
			k++;
		} while (!Arrays.deepEquals(matrice3.getArrayCopy(), matrice2.getArrayCopy()));
		return matrice3;
	}
}
 