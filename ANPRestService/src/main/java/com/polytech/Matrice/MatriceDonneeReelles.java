package com.polytech.Matrice;

import javax.xml.xpath.XPathExpressionException;

import com.polytech.Outils.ManipulationFichierXML;

import Jama.Matrix;

public class MatriceDonneeReelles {

	private double[][] matriceOutput;

	public double[][] matriceDonneesReelles(int nbIndicateur, int nbAlternative, ManipulationFichierXML xml) {
		matriceOutput = new double[nbAlternative][nbIndicateur];

		try {

			String coeff = "";
			String Str1 = "";

			for (int i = 1; i <= nbAlternative; i++) {
				for (int j = 1; j <= nbIndicateur; j++) {
					coeff = "/racine/alternative/surIndicateur[" + i + "]/indic[" + j + "]";
					Str1 = (String) xml.getPath().evaluate(coeff, xml.getRoot());

					if (Str1 != "") {
						matriceOutput[i - 1][j - 1] = Float.parseFloat(Str1);
					}
				}
			}

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return matriceOutput;
	}

	public Matrix getMatrix(double[][] coeff) {
		return new Matrix(coeff);
	}
}
