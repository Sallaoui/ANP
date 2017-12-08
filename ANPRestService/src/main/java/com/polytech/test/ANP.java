package com.polytech.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.stereotype.Repository;

import java.util.HashMap;

import com.polytech.Matrice.MatriceDependance;
import com.polytech.Matrice.MatriceDependanceSimple;
import com.polytech.Matrice.MatriceDonneeReelles;
import com.polytech.Matrice.MatricePreference;
import com.polytech.Matrice.SuperMatriceData;
import com.polytech.Outils.ManipulationFichierXML;
import com.polytech.calcule.CalculVecteurPropre;
import com.polytech.calcule.NormalisationMatrice;

import Jama.Matrix;

//@ComponentScan
//@Repository
public class ANP {
	
	private HashMap<Integer, String> map;
	private String coeffXML = "D:\\EsisaProg\\4eme_annee\\Spring_\\SIRProject\\coeff_Test1.xml";
	private String structureXML = "D:\\EsisaProg\\4eme_annee\\Spring_\\SIRProject\\structure_Test1.xml";
	private String donneeReel = "D:\\EsisaProg\\4eme_annee\\Spring_\\SIRProject\\DonneeReel.xml";
	
	private String path = "D:\\EsisaProg\\4eme_annee\\Spring_\\SIRProject\\alternatives.txt";
	
	private double[][] matricesCriteresPreference ;
	private ArrayList<double[][]> listMatricesIndicateursPreference;
	
	private SuperMatriceData superMatriceData;
	
	public ANP() {
		calculate().print(10, 5);
	}
	
	public HashMap<Integer, String> getAlternatives(){
		
		 map = new HashMap<Integer, String>(0);
		
		try {
			File file = new File(path);
			BufferedReader bf = new BufferedReader(new FileReader(file));
			String line;
			int i = 0;
			while((line=bf.readLine()) != null ){
				map.put(i, line);
				i++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return map;
		
	}
	
	public String getDecision(int index){
		return map.get(index);
	}
	
	public double[][] getMatricesCriteresPreference() {
		return matricesCriteresPreference;
	}

	public void setMatricesCriteresPreference(double[][] matricesCriteresPreference) {
		this.matricesCriteresPreference = matricesCriteresPreference;
	}

	public ArrayList<double[][]> getListMatricesIndicateursPreference() {
		return listMatricesIndicateursPreference;
	}

	public void setListMatricesIndicateursPreference(
			ArrayList<double[][]> listMatricesIndicateursPreference) {
		this.listMatricesIndicateursPreference = listMatricesIndicateursPreference;
	}

	public Matrix calculate(){
//		String coeffXML = "D:\\EsisaProg\\4eme_annee\\Spring_\\SIRProject\\coeff_Test1.xml";
//		String structureXML = "D:\\EsisaProg\\4eme_annee\\Spring_\\SIRProject\\structure_Test1.xml";
//		String donneeReel = "D:\\EsisaProg\\4eme_annee\\Spring_\\SIRProject\\DonneeReel.xml";

		ManipulationFichierXML structure = new ManipulationFichierXML(structureXML);
		ManipulationFichierXML coeff = new ManipulationFichierXML(coeffXML);
		ManipulationFichierXML DR = new ManipulationFichierXML(donneeReel);

		int nbCritere = structure.getNbCritere();
		int nbAlternative = structure.getNbAlternative();
		int nbIndicateurTot = structure.getNbIndicateurTot();

		String preferenceInd = "surCritere";

		String dependanceCrit = "surCritereCrit";
		String dependanceInd = "surCritereDep";

		matricesCriteresPreference = new double[nbCritere][nbCritere];

		ArrayList<Integer> listNbIndicateur = new ArrayList<Integer>();
		// ArrayList<Integer> listPositionDep = new ArrayList<Integer>();
		ArrayList<double[][]> listMatriceIndicateursDependance = new ArrayList<double[][]>();
		ArrayList<double[][]> listMatricesCriteresDependance = new ArrayList<double[][]>();
		listMatricesIndicateursPreference = new ArrayList<double[][]>();

		ArrayList<float[]> listVecteursPropresIIdep = new ArrayList<float[]>();
		ArrayList<float[]> listVecteursPropresCCdep = new ArrayList<float[]>();

		int nbDepedanceInd = coeff.getNbElement(dependanceInd);
		int nbDepedanceCrit = coeff.getNbElement(dependanceCrit);
		int nbIndicateursPreference = coeff.getNbElement(preferenceInd);
		matricesCriteresPreference = new MatricePreference().matricePreferenceCritere(nbCritere, coeff);

		for (int i = 0; i < nbCritere; i++) {
			listNbIndicateur.add(structure.getNbIndicateur(i));
			// System.out.println("nbIndic = " + listNbIndicateur.get(i));
		}

		for (int i = 0; i < nbDepedanceInd; i++) {
			listMatriceIndicateursDependance.add(i,
					new MatriceDependanceSimple().matriceDependanceIndicateur(i + 1, nbIndicateurTot, coeff));
		}

		for (int i = 0; i < nbDepedanceCrit; i++) {
			listMatricesCriteresDependance.add(i, new MatriceDependanceSimple().matriceDependanceCritere(nbCritere, coeff));
		}

		for (int i = 0; i < nbIndicateursPreference; i++) {
			listMatricesIndicateursPreference.add(i,
					new MatricePreference().matricePreferenceIndicateur(i + 1, listNbIndicateur.get(i), coeff));
		}

		System.out.println("Matrices Criteres");

		new Matrix(matricesCriteresPreference).print(10, 2);

		float[] vecteurPropreCrOg = new float[nbCritere];
		CalculVecteurPropre vect = new CalculVecteurPropre();
		vecteurPropreCrOg = vect.calculVecteurPropore(matricesCriteresPreference, nbCritere);
		System.out.print("CrOg : ");
		vect.afficherVecteur(vecteurPropreCrOg);
		System.out.println();

		System.out.print("VP Indicateurs = [   ");
		for (int i = 0; i < nbDepedanceInd; i++) {
			listVecteursPropresIIdep.add(i,
					vect.calculVecteurPropore(listMatriceIndicateursDependance.get(i), nbIndicateurTot));
			for (int j = 0; j < listVecteursPropresIIdep.get(i).length; j++) {
				System.out.print(listVecteursPropresIIdep.get(i)[j] + "   ");
			}
			System.out.print("]");
			System.out.println();
		}

		System.out.print("VP Critere = [   ");
		for (int i = 0; i < nbDepedanceCrit; i++) {
			listVecteursPropresCCdep.add(i,
					vect.calculVecteurPropore(listMatricesCriteresDependance.get(i), nbCritere));
			for (int j = 0; j < listVecteursPropresCCdep.get(i).length; j++) {
				System.out.print(listVecteursPropresCCdep.get(i)[j] + "   ");

			}
			System.out.print("]");
			System.out.println();
		}

		
		System.out.println("Matrices depedences (Indicateurs)");
		for (int i = 0; i < listMatriceIndicateursDependance.size(); i++)
			new Matrix(listMatriceIndicateursDependance.get(i)).print(10, 2);

		System.out.println("Matrices depedences (Criteres)");
		for (int i = 0; i < listMatricesCriteresDependance.size(); i++)
			new Matrix(listMatricesCriteresDependance.get(i)).print(10, 2);

		System.out.println("Matrices preference (Criteres)");
		new Matrix(matricesCriteresPreference).print(10, 2);

		System.out.println("Matrices preference (Indicateurs)");
		for (int i = 0; i < listMatricesIndicateursPreference.size(); i++)
			new Matrix(listMatricesIndicateursPreference.get(i)).print(10, 2);

		System.out.println("Matrice Donnes reelles");
		double[][] matriceAltInd =  new MatriceDonneeReelles().matriceDonneesReelles(nbIndicateurTot, nbAlternative, DR); 
		new Matrix(matriceAltInd).print(10, 2);

		double[][] matriceIndicateurArray = new double[nbIndicateurTot][nbCritere];

		matriceIndicateurArray = new CalculVecteurPropre().matriceComposeeVP(listMatricesIndicateursPreference,
				nbIndicateurTot, nbCritere, listNbIndicateur);

		System.out.println("Matrice ICr");
		new Matrix(matriceIndicateurArray).print(10, 2);

		double[][] matriceCCDep = new MatriceDependance().matriceCCDep(coeff, nbCritere, listVecteursPropresCCdep);
		double[][] matriceIIDep = new MatriceDependance().matriceIIDep(structure, coeff, nbIndicateurTot,
				listVecteursPropresIIdep);

		System.out.println("Matrice [CC]dep");
		new Matrix(matriceCCDep).print(10, 3);

		System.out.println("Matrice [II]dep");
		new Matrix(matriceIIDep).print(10, 3);

		new SuperMatriceData().creationSuperMatrice(nbCritere, nbIndicateurTot, nbAlternative, vecteurPropreCrOg, matriceCCDep, matriceIndicateurArray, matriceAltInd, matriceIIDep).print(10, 5);
		System.out.println("matriceAltInd : ");
		new Matrix(matriceAltInd).print(10, 3);
//		for (int i = 0; i < matriceAltInd.length; i++) {
//			for (int j = 0; j < matriceAltInd[i].length; j++) {
//				System.out.println(matriceAltInd[i][j]);
//			}
//		}
		superMatriceData = new SuperMatriceData();
		return superMatriceData.creationSuperMatrice(nbCritere, nbIndicateurTot, nbAlternative, vecteurPropreCrOg, matriceCCDep, matriceIndicateurArray, matriceAltInd, matriceIIDep);//.print(10, 5);
		
	}
	
	public String getCoeffXML() {
		return coeffXML;
	}

	public void setCoeffXML(String coeffXML) {
		this.coeffXML = coeffXML;
	}

	public String getStructureXML() {
		return structureXML;
	}

	public void setStructureXML(String structureXML) {
		this.structureXML = structureXML;
	}

	public String getDonneeReel() {
		return donneeReel;
	}

	public void setDonneeReel(String donneeReel) {
		this.donneeReel = donneeReel;
	}

	public SuperMatriceData getSuperMatriceData() {
		return superMatriceData;
	}

	public void setSuperMatriceData(SuperMatriceData superMatriceData) {
		this.superMatriceData = superMatriceData;
	}

	public static void main(String[] args) {

		new ANP();
		
		
	}
}
