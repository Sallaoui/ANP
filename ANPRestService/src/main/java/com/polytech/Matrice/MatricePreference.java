package com.polytech.Matrice;

import javax.xml.xpath.XPathExpressionException;

import com.polytech.Outils.ManipulationFichierXML;

import Jama.Matrix;

public class MatricePreference {
	private String coeff = "";
	private String ligne = "";
	private String colonne = "";
	private String Str1 = "";
	private String Str2 = "";
	private String Str3 = "";

	private double[][] matriceOutput;

	public double[][] matricePreferenceCritere(int nbCritere, ManipulationFichierXML xml) {
		matriceOutput = new double[nbCritere][nbCritere];

		try {
			for (int i = 1; i <= (nbCritere * nbCritere - nbCritere) / 2; i++) {
				coeff = "/racine/critere/preferenceCrit[" + i + "]/coeff";
				ligne = "/racine/critere/preferenceCrit[" + i + "]/i";
				colonne = "/racine/critere/preferenceCrit[" + i + "]/j";

				Str1 = (String) xml.getPath().evaluate(coeff, xml.getRoot());
				Str2 = (String) xml.getPath().evaluate(ligne, xml.getRoot());
				Str3 = (String) xml.getPath().evaluate(colonne, xml.getRoot());

				if (Str1 != "" && Str2 != null && Str3 != null) {
					matriceOutput[Integer.parseInt(Str2)][Integer.parseInt(Str3)] = Integer.parseInt(Str1);
					matriceOutput[Integer.parseInt(Str3)][Integer.parseInt(Str2)] = 1 / Float.parseFloat(Str1);
				}
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < nbCritere; i++) {
			for (int j = 0; j < nbCritere; j++) {
				// met la diagonale à 1
				if (i == j) {
					matriceOutput[i][j] = 1;
				}
				// les cases non renseignées sont initialisées à 1
				if (matriceOutput[i][j] == 0) {
					matriceOutput[i][j] = 1;
				}
			}
		}

		return matriceOutput;

	}

	public double[][] matricePreferenceIndicateur(int iterator, int nbIndicateur, ManipulationFichierXML xml) {
		matriceOutput = new double[nbIndicateur][nbIndicateur];

		try {
			for (int i = 1; i <= nbIndicateur; i++) {
				coeff = "/racine/indicateur/surCritere[" + iterator + "]/preferenceInd[" + i + "]/coeff";
				ligne = "/racine/indicateur/surCritere[" + iterator + "]/preferenceInd[" + i + "]/i";
				colonne = "/racine/indicateur/surCritere[" + iterator + "]/preferenceInd[" + i + "]/j";

				Str1 = (String) xml.getPath().evaluate(coeff, xml.getRoot());
				Str2 = (String) xml.getPath().evaluate(ligne, xml.getRoot());
				Str3 = (String) xml.getPath().evaluate(colonne, xml.getRoot());
				if (Str2 != null && Str3 != null && Str1 != "") {
					matriceOutput[Integer.parseInt(Str2)][Integer.parseInt(Str3)] = Float.parseFloat(Str1);
					matriceOutput[Integer.parseInt(Str3)][Integer.parseInt(Str2)] = 1 / Float.parseFloat(Str1);
				}
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < nbIndicateur; i++) {
			for (int j = 0; j < nbIndicateur; j++) {
				// met la diagonale à 1
				if (i == j) {
					matriceOutput[i][j] = 1;
				}
				// les cases non renseignées sont initialisées à 1
				if (matriceOutput[i][j] == 0) {
					matriceOutput[i][j] = 1;
				}

			}
		}

		return matriceOutput;
	}

	public Matrix getMatrix(int nbElement, double[][] coeff) {
		return new Matrix(coeff);
	}
}
