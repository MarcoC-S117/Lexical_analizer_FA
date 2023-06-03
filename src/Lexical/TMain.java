package Lexical;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TMain extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		System.out.println("Programa: Proyecto de asignatura Analizador léxico p/EAS");
        System.out.println("Descripción: ");
        System.out.println("Continuacion del programa 4_2, esta version separar cadenas, identificar operadores, separadores e incluye funciones de la anterior\n");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TMain frame = new TMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TMain() {
		super("Tabla de simbolos");
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("String");
		modelo.addColumn("Token");
		modelo.addColumn("Lexema");
		modelo.addColumn("Valor");
		modelo.addColumn("Linea");
		String[] p1 = { "String", "Token", "Lexema", "Valor", "Linea" };
		modelo.addRow(p1);
		String cadena;
		int posicion;
		leerArchivo texto = new leerArchivo();
		String Tok = "";
		String valor = null;
		texto.leer();
		ArrayList<String> cadenasValidas = texto.getCadenasValidas();
		ArrayList<Integer> pos = texto.getPosicion();
		for (int i = 0; i < cadenasValidas.size(); i++) {
			if (texto.cadenaValida(cadenasValidas.get(i))) {
				cadena = cadenasValidas.get(i);
				posicion = pos.get(i);
				System.out.println(cadena);
				System.out.println("Cadena " + (i + 1) + " termina en la linea " + posicion + " del archivo.");
				System.out.println();
				FA_Begin_0_End1 automata_DFA_3_2 = new FA_Begin_0_End1();
				FA_End_11 automata_DFA_3_3 = new FA_End_11();
				FA_11 automata_DFA_3_4 = new FA_11();
				if (cadena.equals("*") || cadena.equals("+")) {
					System.out.println("Operador encontrado en la linea " +posicion+ " del archivo.");
					Tok = "Operador";
					valor = "Operador";
					String[] p11 = { cadena, Tok, cadena, valor, String.valueOf(posicion) };
					modelo.addRow(p11);
				} else
				if (cadena.equals("(") || cadena.equals(")")) {
					System.out.println("Separador encontrado en la linea " +posicion+ " del archivo.");
					Tok = "Separador";
					valor = "Separador";
					String[] p11 = { cadena, Tok, cadena, valor, String.valueOf(posicion) };
					modelo.addRow(p11);
				} else if (automata_DFA_3_2.process(cadena) || automata_DFA_3_3.process(cadena) || automata_DFA_3_4.process(cadena)) {
					// check if the string is accepted by DFA_3_2
					if (automata_DFA_3_2.process(cadena)) {
						Tok = "A";
						valor = String.valueOf(binaryToDecimal(cadena));
						System.out.println("FA_Begin_0_End_1: " + automata_DFA_3_2.getTransitions());
						System.out.println("Final state: {q" + automata_DFA_3_2.estado() + "}");
						System.out.println();
					}
					// check if the string is accepted by DFA_3_3
					if (automata_DFA_3_3.process(cadena)) {
						Tok = "B";
						valor = null;
						System.out.println("FA_End_11: " + automata_DFA_3_3.getTransitions());
						System.out.println("Final state: {q" + automata_DFA_3_2.estado() + "}");
						System.out.println();
					}
					if (automata_DFA_3_4.process(cadena)) {
						Tok = "C";
						valor = null;
						System.out.println("FA_11: " + automata_DFA_3_4.getTransitions());
						System.out.println("Final state: {q" + automata_DFA_3_4.estado() + "}");
						System.out.println();
					}
					// if no automaton accepted the string, show an error message
					if ((automata_DFA_3_2.process(cadena) && !automata_DFA_3_3.process(cadena)
							&& !automata_DFA_3_4.process(cadena))
							|| (!automata_DFA_3_2.process(cadena) && automata_DFA_3_3.process(cadena)
									&& !automata_DFA_3_4.process(cadena))
							|| (!automata_DFA_3_2.process(cadena) && !automata_DFA_3_3.process(cadena)
									&& automata_DFA_3_4.process(cadena))) {
						System.out.println("The string is accepted by one FA.");
					} else if ((automata_DFA_3_2.process(cadena) && automata_DFA_3_3.process(cadena)
							&& !automata_DFA_3_4.process(cadena))
							|| (automata_DFA_3_2.process(cadena) && !automata_DFA_3_3.process(cadena)
									&& automata_DFA_3_4.process(cadena))
							|| (!automata_DFA_3_2.process(cadena) && automata_DFA_3_3.process(cadena)
									&& automata_DFA_3_4.process(cadena))) {
						System.out.println("The string is accepted by two FAs.");
					} else if (automata_DFA_3_2.process(cadena) && automata_DFA_3_3.process(cadena)
							&& automata_DFA_3_4.process(cadena)) {
						System.out.println("The string is accepted by all three FAs.");
					}
					System.out.println("El valor decimal es: " + binaryToDecimal(cadena));

					String[] p11 = { cadena, Tok, cadena, valor, String.valueOf(posicion) };
					modelo.addRow(p11);
				} if (!(automata_DFA_3_2.process(cadena) || automata_DFA_3_3.process(cadena) || automata_DFA_3_4.process(cadena)) && !(cadena.equals("(") || cadena.equals(")")) && !(cadena.equals("*") || cadena.equals("+"))) {
					System.out.println("The string is not accepted by any FA.");
					Tok="Not valid String";
					valor=null;
					String[] p11 = { cadena, Tok, cadena, valor, String.valueOf(posicion) };
					modelo.addRow(p11);
				}
			}
			System.out.println(
					"--------------------------------------------------------------------------------------------------------------------------------------");
		}

		JTable tabla = new JTable(modelo);
		tabla.setBounds(12, 22, 2000, 500);
		add(tabla);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 400);
		setVisible(true);
	}

	public static int binaryToDecimal(String binary) {
		int decimal = 0;
		int power = 0;
		for (int i = binary.length() - 1; i >= 0; i--) {
			if (binary.charAt(i) == '1') {
				decimal += Math.pow(2, power);
			}
			power++;
		}
		return decimal;
	}

}
