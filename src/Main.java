import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import cormacx.prova.Row;

public class Main {

	public static ArrayList<Row> rows = new ArrayList<Row>();

	static int periodoI = 0;
	static float alphaf, betaf, gammaf = 0;

	public static void main(String[] args) {

		String file = args[0];
		String filePath = file.substring(0, file.lastIndexOf("."));
		String periodo = args[1];
		String alpha = args[2];
		String beta = args[3];
		String gamma = args[4];

		periodoI = Integer.parseInt(periodo);
		alphaf = Float.parseFloat(alpha);
		betaf = Float.parseFloat(beta);
		gammaf = Float.parseFloat(gamma);

		String linha = null;

		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((linha = bufferedReader.readLine()) != null) {
				if (linha.contains(",")) {
					linha = linha.replace(",", ".");
				}
				Float aux = Float.parseFloat(linha);
				Row newRow = new Row(aux);
				rows.add(newRow);
			}
			bufferedReader.close();
			fileReader.close();

			inicializar();
			for (int i = 0; i <= (rows.size() -periodoI - 1); i++) {
				rows.get(i + periodoI).setNivel(calcularNivel(i + periodoI));
				rows.get(i + periodoI).setTendencia(calcularTendencia(i + periodoI));
				rows.get(i + periodoI).setComponenteTemporal(calcularComponenteTemporal(i + periodoI));
				rows.get(i + periodoI).setForecasting(calcularForecastingAprendizado(i + periodoI));
				rows.get(i + periodoI).setErroMedio(calcularErroMedio(i + periodoI));
				rows.get(i + periodoI).setErroMedioPercentual(calcularErroMedioPercentual(i + periodoI));
			}

			for (int i = 0; i < periodoI; i++) {
				rows.add(new Row());
			}

			for (int i = 0; i < periodoI; i++) {
				rows.get(i + (rows.size() -periodoI )).setForecasting(calcularForecastingProximoPeriodo(i + rows.size() - periodoI + 1));
			}
			
			File saida = new File(filePath + "-resultado.txt");
			if(!saida.exists()){
				saida.createNewFile();
			}
			
			FileWriter fileWriter = new FileWriter(saida);
			BufferedWriter writer = new BufferedWriter(fileWriter);
			writer.write("Linha;Valor;L;b;S;Forecasting;Erro;ErroPercentual;");
			writer.newLine();
			for (int i = 0; i < rows.size(); i++) {
				Row row = rows.get(i);
				writer.write((i+1) + ";" + row.getValor() + ";" + row.getNivel() + ";" + row.getTendencia() 
						+ ";" + row.getComponenteTemporal() + ";" + row.getForecasting() + ";" + row.getErroMedio() + ";" + row.getErroMedioPercentual() + ";");
				writer.newLine();
			}
			writer.close();
			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static float calcularForecastingProximoPeriodo(int linha) {
		Row ultimaLinha = rows.get(rows.size() - periodoI - 1);
		Row linhaXAnterior = rows.get((rows.size() - periodoI - 1 - (rows.size() - linha)));

		float forecasting = (ultimaLinha.getNivel() + (linha - (rows.size() - periodoI)) * ultimaLinha.getTendencia())
				* linhaXAnterior.getComponenteTemporal();
		return forecasting;

	}

	public static void inicializar() {
		ArrayList<Row> linhas = new ArrayList<Row>();
		float total = 0;
		for (int i = 0; i < periodoI; i++) {
			linhas.add(rows.get(i));
			total = total + rows.get(i).getValor();
		}
		for (int i = 0; i < periodoI; i++) {
			float aux = rows.get(i).getValor() / (total / 3);
			rows.get(i).setComponenteTemporal(aux);
		}
		rows.get(periodoI - 1).setNivel(total / 3);
		rows.get(periodoI - 1).setTendencia(calcularTendenciaInicial());
	}

	private static float calcularTendenciaInicial() {

		ArrayList<Float> auxiliar = new ArrayList<Float>();
		for (int i = 0; i < periodoI; i++) {
			Row linhaAnterior = rows.get(i);
			Row linhaPosterior = rows.get(i + periodoI);
			auxiliar.add((linhaPosterior.getValor() - linhaAnterior.getValor()) / periodoI);
		}

		float total = 0;

		for (Float float1 : auxiliar) {
			total = total + float1;
		}

		return total / periodoI;
	}

	public static float calcularNivel(int linha) {
		Row linhaXAnterior = rows.get(linha - periodoI);
		Row linhaPassada = rows.get(linha - 1);
		Row linhaAtual = rows.get(linha);
		float nivel = alphaf * (linhaAtual.getValor() / linhaXAnterior.getComponenteTemporal())
				+ ((1 - alphaf) * (linhaPassada.getNivel() + linhaPassada.getTendencia()));
		return nivel;
	}

	public static float calcularTendencia(int linha) {
		Row linhaAtual = rows.get(linha);
		Row linhaPassada = rows.get(linha - 1);
		float tendencia = betaf * (linhaAtual.getNivel() - linhaPassada.getNivel())
				+ (1 - betaf) * (linhaPassada.getTendencia());
		return tendencia;
	}

	public static float calcularComponenteTemporal(int linha) {
		Row linhaAtual = rows.get(linha);
		Row linhaXAnterior = rows.get(linha - periodoI);
		float componenteTemporal = gammaf * (linhaAtual.getValor() / linhaAtual.getNivel())
				+ (1 - gammaf) * linhaXAnterior.getComponenteTemporal();
		return componenteTemporal;
	}

	private static float calcularForecastingAprendizado(int linha) {
		Row linhaXAnterior = rows.get(linha - periodoI);
		Row linhaPassada = rows.get(linha - 1);
		float forecasting = (linhaPassada.getNivel() + 1 * linhaPassada.getTendencia())
				* linhaXAnterior.getComponenteTemporal();
		return forecasting;

	}

	private static float calcularErroMedio(int i) {
		Row linhaAtual = rows.get(i);
		float erroMedio = Math.abs(linhaAtual.valor - linhaAtual.forecasting);
		return erroMedio;
	}

	private static float calcularErroMedioPercentual(int i) {
		Row linhaAtual = rows.get(i);
		float erroMedioPercentual = (Math.abs(linhaAtual.valor - linhaAtual.forecasting) / linhaAtual.valor) * 100;
		return erroMedioPercentual;
	}

}
