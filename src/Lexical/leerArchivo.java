package Lexical;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class leerArchivo {
    ArrayList<String> cadenasValidas = new ArrayList<String>();
    String cadena = "";
    ArrayList<Integer> posicion = new ArrayList<Integer>();

    public leerArchivo() {

    }

    public ArrayList<String> getCadenasValidas() {
        return cadenasValidas;
    }

    public void leer() {
        String nombreFichero = "src/archivo.txt";
        FileReader fr = null;
        try {
            fr = new FileReader(nombreFichero);
            int caract = fr.read();
            int pos = 1;
            while (caract != -1) {
                if (caract == '\r' || caract == '\n') {
                    pos++;
                    if (caract == '\r' && fr.read() == '\n') {
                        caract = fr.read();
                    }
                }
                if (caract == ';') {
                    if (!cadena.isEmpty()) {
                    	// Si hay una cadena válida, agregarla a la lista
    					caract = fr.read();
    					cadenasValidas.add(cadena);
    					posicion.add(pos);
    					// Limpiar la cadena para la siguiente
    					cadena = "";
                    }
                }
                if (caract == '(' || caract == ')' || caract == '*' || caract == '+') {
                    if (!cadena.isEmpty()) {
                        cadenasValidas.add(cadena);
                        posicion.add(pos);
                        cadena = "";
                    }
                    cadena = cadena + (char) caract;
                    cadenasValidas.add(cadena);
                    posicion.add(pos);
                    cadena = "";
                } else if (caract == '/') {
                    int siguienteCaract = fr.read();
                    if (siguienteCaract == '/') {
                        while (caract != '\n' && caract != '\r' && caract != -1) {
                            caract = fr.read();
                        }
                    } else if (siguienteCaract == '*') {
                        caract = fr.read();
                        if (caract == '\r' || caract == '\n') {
                            pos++;
                            if (caract == '\r' && fr.read() == '\n') {
                                caract = fr.read();
                            }
                        }
                        siguienteCaract = fr.read();
                        while ((caract != '*' || siguienteCaract != '/') && caract != -1) {
                            caract = siguienteCaract;
                            if (caract == '\r' || caract == '\n') {
                                pos++;
                                if (caract == '\r' && fr.read() == '\n') {
                                    caract = fr.read();
                                }
                            }
                            siguienteCaract = fr.read();
                        }
                    } else {
                        cadena = cadena + (char) caract;
                        cadena = cadena + (char) siguienteCaract;
                        caract = siguienteCaract;
                    }
                } else if (caract != ' ' && caract != '\r' && caract != '\n' && caract != '\t') {
                    cadena = cadena + (char) caract;
                }
                caract = fr.read();
            }
            if (!cadena.isEmpty() && caract == -1) {
                cadenasValidas.add(cadena);
                posicion.add(pos);
                cadena = "";
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Fichero no encontrado");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error de lectura del fichero");
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fr != null)
                    fr.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar el fichero");
                System.out.println(e.getMessage());
            }
        }
    }

    public boolean cadenaValida(String comp) {
        boolean valida = true;
        if (comp.equals("")) {
            System.out.println("Cannot verify an empty string");
            valida = false;
        } else if (!onlyZeroOne(comp)) {
            int posicion = getInvalidSymbolPosition(comp);
            System.out.println("La cadena contiene un símbolo no válido en la posición " + (posicion + 1));
            System.out.print(comp + "\n");
            for (int i = 0; i < posicion; i++) {
                System.out.print(" ");
            }
            System.out.println("^");
            valida = false;
        }
        return valida;
    }

    public static boolean onlyZeroOne(String cadena) {
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            if (c != '0' && c != '1' && c != '(' && c != ')' && c != '*' && c != '+') {
                return false;
            }
        }
        return true;
    }

    public static int getInvalidSymbolPosition(String cadena) {
        for (int i = cadena.length() - 1; i >= 0; i--) {
            char c = cadena.charAt(i);
            if (c != '0' && c != '1' && c != '(' && c != ')' && c != '*' && c != '+') {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Integer> getPosicion() {
        return posicion;
    }
}

