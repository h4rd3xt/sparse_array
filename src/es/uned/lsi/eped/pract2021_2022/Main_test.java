package es.uned.lsi.eped.pract2021_2022;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
import es.uned.lsi.eped.DataStructures.IteratorIF;

public class Main_test {

//	/* Comprueba que el fichero de entrada exista y puede ser leido */
//	public static Boolean checkInput(String file) {
//		File f = new File(file);
//		// Se comprueba que el fichero existe y es, realmente, un fichero
//		if ( !f.exists() || !f.isFile() ) {
//			System.out.println("ERROR: no existe el fichero de entrada "+file+".");
//			return false;
//		}
//		// Se comprueba que el fichero puede ser leido
//		if ( !f.canRead() ) {
//			System.out.println("ERROR: el fichero de entrada "+file+" no puede leerse.");
//			return false;
//		}
//		return true;
//	}
//	
//	/* Comprueba que se puede crear el fichero de salida */
//	public static Boolean checkOutput(String file) {
//		File f = new File(file);
//		f=f.getAbsoluteFile();
//		// Se comprueba que la carpeta para escribir el fichero de salida existe
//		if ( !f.getParentFile().exists() ) {
//			System.out.println("ERROR: no existe la carpeta del fichero de salida "+f.getParent()+".");
//			return false;
//		}
//		// Se comprueba que la carpeta para escribir el fichero de salida tenga permisos de escritura
//		if ( !f.getParentFile().canWrite() ) {
//			System.out.println("ERROR: no se puede escribir en la carpeta del fichero de salida "+f.getParent()+".");
//			return false;
//		}
//		// Si el fichero de salida existe...
//		if ( f.exists() ) {
//			// Se comprueba que sea un fichero
//			if ( !f.isFile() ) {
//				System.out.println("ERROR: la salida "+file+" no es un fichero.");
//				return false;
//			}
//			// Se comprueba que pueda sobreescribirse
//			if ( !f.canWrite() ) {
//				System.out.println("ERROR: el fichero de salida "+file+" no puede sobreescribirse");
//				return false;
//			}
//		}
//		return true;
//	}
	
	/*Convierte un iterador en cadena de caracteres
	 * @param it: iterador
	 */
//	public static <E> String toString(IteratorIF<E> it){
//		StringBuilder result = new StringBuilder();
//		while(it.hasNext()){
//			result.append(it.getNext().toString());
//			if(it.hasNext()){
//				result.append(" ");
//			}
//		}
//		return result.toString();
//	}
	
	public static void main(String[] args) throws IOException {
		/* Implementacion Array Disperso. Posibles valores: 
		 *   - SEQUENCE 
		 *   - BTREE
		 */
		//String typeSA = args[0];
		//String typeSA = "SEQUENCE";
		String typeSA = "BTREE"; 
		SparseArrayIF<String> SA;
		switch (typeSA) {
		case "SEQUENCE":
			// SparseArray basado en secuencias
			SA = new SparseArraySequence<String>();
			break;
		case "BTREE":
			// SparseArray basado en arboles binarios
			SA = new SparseArrayBTree<String>();
			break;
		default:
			System.out.println("ERROR: el primer argumento debe ser SEQUENCE o BTREE.");
			return;
		}

//		// Fichero de entrada
//		String input = args[1];
		//String input = "C:\\Users\\josem\\Eclipse\\eped2022\\test_set_get\\test_500.dat";
		//String input = "C:\\Users\\josem\\Eclipse\\eped2022\\test_set_get\\test_1000.dat";
		//String input = "C:\\Users\\josem\\Eclipse\\eped2022\\test_set_get\\test_15000.dat";
		//String input = "C:\\Users\\josem\\Eclipse\\eped2022\\test_set_get\\test_30000.dat";
		//String input = "C:\\Users\\josem\\Eclipse\\eped2022\\test_set_get\\test_500_set_get.dat";
		//String input = "C:\\Users\\josem\\Eclipse\\eped2022\\test_set_get\\test_1000_set_get.dat";
		//String input = "C:\\Users\\josem\\Eclipse\\eped2022\\test_set_get\\test_15000_set_get.dat";
		String input = "C:\\Users\\josem\\Eclipse\\eped2022\\test_set_get\\test_30000_set_get.dat";
//		if ( !checkInput(input) ) { return; }	
//		
//		// Fichero de salida
//		String output = args[2];
//		if ( !checkOutput(output) ) { return; }

		// Salto de linea
		String lineFeed = System.getProperty("line.separator");
		// Se abre la lectura del fichero de entrada
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(input), "utf-8"));		
		// Se abre la escritura en el fichero de salida
		//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), "utf-8"));
		
		String line;
		long t0 = System.currentTimeMillis();
		while ((line = br.readLine())!=null) {
			// Cada linea del fichero de entrada es una operacion del array disperso.
			// Separa la linea en tokens por espacios en blancos
			StringTokenizer st = new StringTokenizer(line, " ");
			// El primer token contiene la operacion
			String op = st.nextToken().toLowerCase();
			// Resultado de la operacion (para contains, get, isempty, iterator, indexiterator y size)
			String result = new String("-");
			// Posibles parametros de las operaciones de un array disperso
			int index;
			String elem;
			// Ejecucion de operaciones
			switch(op){
				// OPERACIONES DE SPARSEARRAYIF //
				// Operacion set
				case "set":
					// Tiene dos parametros: el primero el indice
					index = Integer.parseInt(st.nextToken());
					// y el segundo el elemento
					elem = st.nextToken();
					// Se aplica la operacion set sobre el array disperso
					SA.set(index, elem);
					break;
				// Operacion get
				case "get":
					// Tiene un parametro: el indice
					index = Integer.parseInt(st.nextToken());
					// Se aplica la operacion get sobre el array disperso
					result = SA.get(index);
					if ( result == null ) {
						result = "null";
					}
					break;
//				// Operacion delete
//				case "delete":
//					// Tiene un parametro: el indice
//					index = Integer.parseInt(st.nextToken());
//					// Se aplica la operacion delete sobre el array disperso
//					SA.delete(index);
//					break;	
//				// Operacion indexiterator
//				case "indexiterator":
//					/* Se obtiene la secuencia de indices del array disperso 
//					 * y se pasa a cadena de caracteres mediante la funcion
//					 * auxiliar toString
//					 */
//					IteratorIF<Integer> itIndex = SA.indexIterator();
//					result = toString(itIndex);
//					break;
//				// OPERACIONES DE SEQUENCEIF //
//				// Operacion iterator
//				case "iterator":
//					/* Se obtiene la secuencia de elementos del array disperso 
//					 * y se pasa a cadena de caracteres mediante la funcion
//					 * auxiliar toString
//					 */
//					IteratorIF<String> itElems = SA.iterator();
//					result = toString(itElems);
//					break;
//				// OPERACIONES DE COLLECTIONIF //
//				// Operacion size
//				case "size":
//					// Se aplica la operacion size sobre el array disperso
//					result = String.valueOf(SA.size());
//					break;
//				// Operacion isEmpty
//				case "isempty":
//					// Se aplica la operación isempty sobre el array disperso
//					result = String.valueOf(SA.isEmpty());
//					break;
//				// Operacion contains
//				case "contains":
//					// Tiene un parametro: el elemento
//					elem = st.nextToken();
//					// Se aplica la operacion contains sobre el array disperso
//					result = String.valueOf(SA.contains(elem));
//					break;
//				// Operacion clear
//				case "clear":
//					// Se aplica la operacion clear sobre el array disperso
//					SA.clear();
//					break;
			}
			// Se escribe la salida en el fichero de salida 
//			bw.write(line);
//			if(!result.equals("-")){
//				bw.write(": "+result);
//			}
//			bw.write(lineFeed);
		} 
		long t1 = System.currentTimeMillis() - t0;
		// Muestra por consola el tiempo de ejecucion en milisegundos
		System.out.println(t1+" ms");
		// Se cierra el buffer de lectura
		br.close();
		// Se cierra el buffer de escritura
//		bw.close();
	}
}
