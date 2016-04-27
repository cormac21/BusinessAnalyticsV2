import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import cormacx.prova.Row;


public class Main {
	
	public static ArrayList<Row> rows = new ArrayList<Row>();
	
	static int periodoI = 0;
	static float alphaf, betaf, gammaf = 0;

	public static void main(String[] args) {

		String filePath = args[0];
		String periodo = args[1];
		String alpha = args[2];
		String beta = args[3];
		String gamma = args[4];
		
		periodoI = Integer.parseInt(periodo);
		alphaf = Float.parseFloat(alpha);
		betaf = Float.parseFloat(beta);
		gammaf = Float.parseFloat(gamma);
		
		String linha = null;

		try{
			FileReader fileReader = new FileReader(filePath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while((linha = bufferedReader.readLine()) != null){
				Float aux = Float.parseFloat(linha);
				Row newRow = new Row(aux);
				rows.add(newRow);
			}
			bufferedReader.close();
			fileReader.close();
		} catch (Exception e){
			
		}
	}
	
	public void inicializar(){
		ArrayList<Row> linhas = new ArrayList<Row>();
		float total = 0;
		for (int i = 0; i < periodoI; i++) {
			linhas.add(rows.get(periodoI-1));
			total = total + rows.get(periodoI-1).getValor();
		}
		for (int i = 0; i < periodoI; i++) {
			float aux = rows.get(periodoI-1).getValor() / (total / 3 );
			rows.get(periodoI-1).setComponenteTemporal(aux);
		}
		linhas.get(periodoI-1).setNivel(total/3);
	}
	
	public float calcularNivel(int linha){
		Row linhaAtual = rows.get(linha);
		Row linhaPassada = rows.get(linha-1);
		float nivel = alphaf * (linhaPassada.getValor() / rows.get((linha-periodoI)));
	}
	
	

}
