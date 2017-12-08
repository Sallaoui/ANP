package com.polytech.Matrice;

import javax.xml.xpath.XPathExpressionException;

import com.polytech.Outils.ManipulationFichierXML;

public class MatriceDependanceSimple {
	private String coeff = "";
	private String ligne = "";
	private String colonne = "";
	private String Str1 = "";
	private String Str2 = "";
	private String Str3 = "";

	double[][] matriceOutput;

	public double[][] matriceDependanceCritere(int nbCritere, ManipulationFichierXML xml) {
		matriceOutput = new double[nbCritere][nbCritere];

		try {
			for (int i = 1; i <= (nbCritere * nbCritere - nbCritere) / 2; i++) {
				coeff = "/racine/critere/surCritereCrit/dependanceCrit[" + i + "]/coeff";
				ligne = "/racine/critere/surCritereCrit/dependanceCrit[" + i + "]/i";
				colonne = "/racine/critere/surCritereCrit/dependanceCrit[" + i + "]/j";

				Str1 = (String) xml.getPath().evaluate(coeff, xml.getRoot());
				Str2 = (String) xml.getPath().evaluate(ligne, xml.getRoot());
				Str3 = (String) xml.getPath().evaluate(colonne, xml.getRoot());
				if (Str1 != "" && Str2 != null && Str3 != null) {
					matriceOutput[Integer.parseInt(Str2)][Integer.parseInt(Str3)] = Integer.parseInt(Str1);
					matriceOutput[Integer.parseInt(Str3)][Integer.parseInt(Str2)] = 1 / Float.parseFloat(Str1);
					matriceOutput[Integer.parseInt(Str3)][Integer.parseInt(Str3)] = 1;
					matriceOutput[Integer.parseInt(Str2)][Integer.parseInt(Str2)] = 1;

				}
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return matriceOutput;
	}

	public double[][] matriceDependanceIndicateur(int iterator, int nbIndicateur, ManipulationFichierXML xml) {
		matriceOutput = new double[nbIndicateur][nbIndicateur];

		try {
			for (int i = 1; i <= nbIndicateur; i++) {
				coeff = "/racine/indicateur/surCritereDep[" + iterator + "]/dependanceInd[" + i + "]/coeff";
				ligne = "/racine/indicateur/surCritereDep[" + iterator + "]/dependanceInd[" + i + "]/i";
				colonne = "/racine/indicateur/surCritereDep[" + iterator + "]/dependanceInd[" + i + "]/j";

				Str1 = (String) xml.getPath().evaluate(coeff, xml.getRoot());
				Str2 = (String) xml.getPath().evaluate(ligne, xml.getRoot());
				Str3 = (String) xml.getPath().evaluate(colonne, xml.getRoot());

				// System.out.println("fait");

				if (Str2 != null && Str3 != null && Str1 != "") {
					matriceOutput[Integer.parseInt(Str2)][Integer.parseInt(Str3)] = Float.parseFloat(Str1);
					matriceOutput[Integer.parseInt(Str3)][Integer.parseInt(Str2)] = 1 / Float.parseFloat(Str1);
					matriceOutput[Integer.parseInt(Str3)][Integer.parseInt(Str3)] = 1;
					matriceOutput[Integer.parseInt(Str2)][Integer.parseInt(Str2)] = 1;
				}
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return matriceOutput;
	}

	
}
