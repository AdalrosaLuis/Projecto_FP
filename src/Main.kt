@file:Suppress("UNREACHABLE_CODE")

import java.time.LocalDate
import java.time.format.DateTimeFormatter

var ids = emptyArray<Int>()
var nome = emptyArray<String>()
var data = emptyArray<String>()
var valorInvestido = emptyArray<String>()
var valorAtual = emptyArray<String>()
var quantidade = emptyArray<String>()
var rentabilidade = emptyArray<String>()
var investimentos: Array<Array<String>?> = arrayOfNulls(99)
var configuracoes = emptyArray<String>()
var liquidacoes = emptyArray<Double>()


fun obterMenu(): String {
	val menuCarteira = "#  Comando\n" +
			"1. Consultar\n" +
			"2. Adicionar\n" +
			"3. Editar\n" +
			"4. Liquidar\n" +
			"5. Guardar\n" +
			"9. Configuracoes\n" +
			"0. Sair\n"
	return menuCarteira
}

fun lerConfiguracoes(fileName: String, configuracoes: Array<String>): Boolean {
	return false
}

fun guardarConfiguracoes(fileName: String, liquidacoes: Array<String>) {

}

fun lerLiquidacoes(fileName: String, liquidacoes: Array<Double>): Boolean {
	
	return false
}

fun guardarLiquidacoes(fileName: String, liquidacoes: Array<Double>) {

}

fun lerInvestimentos(fileName: String, investimentos: Array<Array<String>?>): Boolean {
	
	return false
	
}

//fun consultarInvestimentos(
//	investimentos: Array<Array<String>?>,
//	configuracoes: Array<String>,
//	liquidacoes: Array<Double>,
//): String {
//	return ""
//}

fun consultarInvestimentos(investimentos: Array<Array<String>?>, configuracoes: Array<String>, liquidacoes: Array<Double>
): String {
	var resultado = "# | Nome     | Data       | Investido | Atual    | Quantidade | Rentabilidade\n"
	resultado += "-------------------------------------------------------------------------------\n"
	
	var lucroTotal = 0.0
	
	for ((indice, investimento) in investimentos.withIndex()) {
		if (investimento != null) {
			val nome = investimento[0]
			val data = investimento[1]
			val investido = investimento[2].toDouble()
			val atual = investimento[3].toDouble()
			val quantidade = investimento[4].toDouble()
			
			val rentabilidade = if (investido != 0.0)
				((atual - investido) / investido) * 100
			else
				0.0
			
			// Linha formatada simples
			resultado += "${indice + 1} | ${nome.padEnd(8)} | ${data} | ${"%.2f".format(investido)} € | " +
					"${"%.2f".format(atual)} € | ${"%.2f".format(quantidade)} | " +
					"${"%.1f".format(rentabilidade)} %\n"
			
			lucroTotal += (atual - investido)
		}
	}
	
	resultado += "\nLucro: %.2f €".format(lucroTotal)
	return resultado
}

fun adicionarInvestimento(investimentos: Array<Array<String>?>, nome: String, valorInvestido: Double, valorAtual: Double, ): String {
	val indice = investimentos.indexOfFirst { it == null }
	
	if (indice == -1) {
		return "Já tem a carteira de investimentos completa."
	}
	
	val data = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
	
	val quantidade = valorInvestido / valorAtual
	
	val investimento = arrayOf(
		nome,
		data,
		valorInvestido.toString(),
		valorAtual.toString(),
		quantidade.toString()
	)
	
	investimentos[indice] = investimento
	
	return "Investimento adicionado com sucesso"
}

fun editarInvestimento(investimentos: Array<Array<String>?>, nome: String, valor: Double): String {
	return ""
}

fun liquidarInvestimento(liquidacoes: Array<Double>, investimentos: Array<Array<String>?>, numero: Int): String {
	return ""
}

fun guardarInvestimentos(fileName: String, investimentos: Array<Array<String>?>) {

}

fun main() {
	var nome: String
	var moeda: String
	var escolha: Int
	
	println(
		"#####################\n" +
				"### Investimentos ###\n" +
				"#####################\n"
	)
	
	while (true) {
		println("Por favor indique o seu nome:")
		nome = readln()
		
		println("Por favor indique a moeda da sua conta(€ ou $):")
		moeda = readln()
		
		val nomeCompleto = nome.split(" ")
		
		
		if (nomeCompleto.size != 2 || (moeda != "$" && moeda != "€")) {
			println(
				"Dados invalidos.\n" +
						"O nome completo deve ser definido por pelomenos " +
						"um espaço vazio e pelo menos 4 caracteres.\n" +
						"A moeda deverá ser em $ ou €.\n"
			)
		}
		else {
			break
		}
	}
	
	println("Olá $nome\n")
	
	do {
		
		println(obterMenu())
		print("Indica o comando que pretendes:\n ")
		escolha = readln().toInt()
		
		when (escolha) {
			1 -> {
				consultarInvestimentos(investimentos, configuracoes, liquidacoes)
			}
			2 -> {
				println("Nome do Investimento:")
				var nomeInvestimento = readln().toString()
				
				println("Valor investido:")
				var ValorInvestido = readln().toDouble()
				
				println("Valo atual (PU)")
				var ValorPu = readln().toDouble()
				
				var mensagem = adicionarInvestimento(investimentos, nomeInvestimento, ValorInvestido, ValorPu )
				
				println(mensagem)
			}
			
			3 -> {
				println("Voce escolheu Editar investimento.\n menu em desenvolvimento")
			}
			
			4 -> {
				println("Voce escolheu liquidar investimento. \n menu em desenvolvimento")
			}
			
			5 -> {
				println("Voce escolheu Guardar. \n menu em desenvolvimento")
			}
			
			9 -> {
				println("Voce escolheu configuracoes. \n menu em desenvolvimento")
			}
		}
		
		println("(prima enter para voltar ao menu.)")
		readln()
		
	}
	while (escolha != 0)
	
	println("Adeus e bons investimentos")
}







