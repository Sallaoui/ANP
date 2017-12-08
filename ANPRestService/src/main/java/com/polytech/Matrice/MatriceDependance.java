package com.polytech.Matrice;

import java.util.ArrayList;

import javax.xml.xpath.XPathExpressionException;

import com.polytech.Outils.ManipulationFichierXML;

public class MatriceDependance {

	public double[][] matriceCCDep(ManipulationFichierXML xml, int nbCritere, ArrayList<float[]> listMatriceInput) {
		double[][] matriceOutput = new double[nbCritere][nbCritere];

		try {
			int pos = 0;
			for (int i = 0; i < nbCritere+1; i++) {
				String str = "/racine/critere/surCritereCrit[" + i + "]/@parRapportAuCritere";
				String Str1 = (String) xml.getPath().evaluate(str, xml.getRoot());
				if (Str1 != "") {
					pos = (Integer.parseInt(Str1) - 1);
					// System.out.println(pos);
					for (int j = 0; j < nbCritere; j++) {
						for (int k = 0; k < listMatriceInput.size(); k++)
							matriceOutput[j][pos] = listMatriceInput.get(pos)[j];
					}
				}
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return matriceOutput;
	}

	/* A FAIRE */
	public double[][] matriceIIDep(ManipulationFichierXML xmlStructure, ManipulationFichierXML xmlCoeff,
			int nbIndicateurTot, ArrayList<float[]> listMatriceInput) {
		double[][] matriceOutput = new double[nbIndicateurTot][nbIndicateurTot];

		try {
			int pos = 0;
			for (int i = 0; i < nbIndicateurTot+1; i++) {

				String crit = (String) xmlCoeff.getPath()
						.evaluate("/racine/indicateur/surCritereDep[" + i + "]/@critere", xmlCoeff.getRoot());
				String indic = (String) xmlCoeff.getPath()
						.evaluate("/racine/indicateur/surCritereDep[" + i + "]/@indicateur", xmlCoeff.getRoot());
				if (crit != "" && indic != "") {
					int critere = (Integer.parseInt(crit));
					int indicateur = (Integer.parseInt(indic));

					String numCrit = (String) xmlCoeff.getPath().evaluate(
							"/racine/critere[" + critere + "]/indicateur[" + indicateur + "]/@numIndic",
							xmlStructure.getRoot());
					pos = (Integer.parseInt(numCrit) - 1);

					// System.out.println(pos);
					for (int j = 0; j < nbIndicateurTot; j++) {
						for (int k = 0; k < listMatriceInput.size(); k++)
							matriceOutput[j][pos] = listMatriceInput.get(pos)[j];
					}
				}
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return matriceOutput;
	}

}
