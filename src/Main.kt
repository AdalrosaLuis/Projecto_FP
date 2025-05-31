@file:Suppress("UNREACHABLE_CODE")

import java.io.File
import java.time.LocalDate

//var ids = emptyArray<Int>()
//var nome = emptyArray<String>()
//var data = emptyArray<String>()
//var valorInvestido = emptyArray<String>()
//var valorAtual = emptyArray<String>()
//var quantidade = emptyArray<String>()
//var rentabilidade = emptyArray<String>()

const val MAX_investimentos=99
var MAX_colunas=6
const val CONFIGURACAO_FILE = "configuracoes.txt"
const val LIQUIDACAO_FILE = "liquidacoes.txt"
const val INVESTIMENTOS_FILE = "investimentos.txt"

var configuracoes = emptyArray<String>()
var liquidacoes = emptyArray<Double>()
var investimentos: Array<Array<String>?> = arrayOfNulls(MAX_investimentos)
var contadorInvestimentos=0

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

fun consultarInvestimentos(
	investimentos: Array<Array<String>?>,
	configuracoes: Array<String>,
	liquidacoes: Array<Double>
): String {
	val colunas = listOf("Nome", "Data", "Investido", "Atual", "Quantidade", "Rentabilidade")
	val larguraColuna = IntArray(colunas.size) { colunas[it].length }
	
	// 1. Calcular larguras máximas com base nos dados reais
	for (investimento in investimentos) {
		if (investimento != null) {
			larguraColuna[0] = maxOf(larguraColuna[0], investimento[0].length) // Nome
			larguraColuna[1] = maxOf(larguraColuna[1], investimento[1].length) // Data
			larguraColuna[2] = maxOf(larguraColuna[2], "%.2f €".format(investimento[2].toDouble()).length)
			larguraColuna[3] = maxOf(larguraColuna[3], "%.2f €".format(investimento[3].toDouble()).length)
			larguraColuna[4] = maxOf(larguraColuna[4], "%.2f".format(investimento[4].toDouble()).length)
			larguraColuna[5] = maxOf(larguraColuna[5], "%.1f %%".format(0.0).length) // Será atualizado na hora
		}
	}
	
	// 2. Cabeçalho
	val stringBuilder = StringBuilder()
	stringBuilder.append("# ".padEnd(4))
	for (i in colunas.indices) {
		stringBuilder.append(colunas[i].padEnd(larguraColuna[i] + 2))
		if (i != colunas.lastIndex) stringBuilder.append("| ")
	}
	
	stringBuilder.append("\n")
	
	// 3. Dados
	var lucroTotal = 0.0
	var contador = 1
	for (investimento in investimentos) {
		if (investimento != null) {
			val nome = investimento[0]
			val data = investimento[1]
			val investido = investimento[2].toDouble()
			val atual = investimento[3].toDouble()
			val quantidade = investimento[4].toDouble()
			val rentabilidade = if (investido != 0.0) ((atual - investido) / investido) * 100 else 0.0
			
			lucroTotal += (atual - investido)
			
			stringBuilder.append(contador.toString().padEnd(4))
			stringBuilder.append(nome.padEnd(larguraColuna[0] + 2)).append("| ")
			stringBuilder.append(data.padEnd(larguraColuna[1] + 2)).append("| ")
			stringBuilder.append("%.2f €".format(investido).padEnd(larguraColuna[2] + 2)).append("| ")
			stringBuilder.append("%.2f €".format(atual).padEnd(larguraColuna[3] + 2)).append("| ")
			stringBuilder.append("%.2f".format(quantidade).padEnd(larguraColuna[4] + 2)).append("| ")
			stringBuilder.append("%.1f %%".format(rentabilidade).padEnd(larguraColuna[5] + 2))
			stringBuilder.append("\n")
			
			contador++
		}
	}
	
	// 4. Lucro total
	stringBuilder.append("\nLucro: %.2f €".format(lucroTotal))
	return stringBuilder.toString()
}

fun adicionarInvestimento(investimentos: Array<Array<String>?>,
                          nome: String,
                          valorInvestido: Double,
                          valorAtual: Double, ): String {
	
	
	if (contadorInvestimentos >= MAX_investimentos) {
		return "Já tem a carteira de investimentos completa."
	}
	
	var quantidade = valorInvestido / valorAtual
	
	investimentos[contadorInvestimentos]= arrayOf(
		nome,
		LocalDate.now().toString(),
		valorInvestido.toString(),
		valorAtual.toString(),
		quantidade.toString(),
		"")

	contadorInvestimentos++
	
	return "Investimento adicionado com sucesso"
}

fun editarInvestimento(investimentos: Array<Array<String>?>,
                       nome: String,
                       valor: Double): String {
	return ""
}

fun liquidarInvestimento(
	liquidacoes: Array<Double>,
	investimentos: Array<Array<String>?>,
	numero: Int
): String {
	val index = numero - 1
	
	if (index !in investimentos.indices || investimentos[index] == null) {
		return "Nao existem investimentos em carteira com esse numero."
	}
	
	val investimento = investimentos[index]!!
	
	val valorInvestido = investimento[2].toDoubleOrNull() ?: 0.0
	val valorAtual = investimento[3].toDoubleOrNull() ?: 0.0
	
	val lucroOuPrejuizo = valorAtual - valorInvestido
	
	liquidacoes[0] += valorInvestido
	liquidacoes[1] += lucroOuPrejuizo
	
	investimentos[index] = null
	
	return "Investimento liquidado com sucesso!"
}

fun guardarInvestimentos(fileName: String, investimentos: Array<Array<String>?>) {
	val file = File(fileName)
	
	try {

		if (!file.exists()) {
			file.createNewFile()
		}
		
		val conteudo = StringBuilder()
		for (investimento in investimentos) {
			if (investimento != null) {
				conteudo.append(investimento.joinToString(";"))
				conteudo.append("\n")
			}
		}
		
		file.writeText(conteudo.toString())
		
	} catch (e: Exception) {
	}
}

fun inicializarInvestimentos(investimentos: Array<Array<String>?>) {
	adicionarInvestimento(investimentos, "NVIDIA", 500.0, 138.01)
	adicionarInvestimento(investimentos, "BTC", 5000.0, 39801.02)
	adicionarInvestimento(investimentos, "PETR4", 200.0, 250.0)
	adicionarInvestimento(investimentos, "ETH", 800.0, 1600.0)
}

fun main() {
	
	inicializarInvestimentos(investimentos)
	
	var nome: String
	var moeda: String
	var escolha: Int
	var nomeInvestimento :String
	var valorPu:Double

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
				println(consultarInvestimentos(investimentos, configuracoes, liquidacoes))
			}
			2 -> {
				do {
					println("Adicionar investimento\n")
					println("Nome do Investimento:")
					nomeInvestimento = readln().toString()
					if (nomeInvestimento.length < 3) {
						println("Nome inválido, o nome apenas pode  conter letras e tem de ter no minimo 3 caracteres.\n" +
								"(prima enter para continuar)")
					}
				}
			while (nomeInvestimento.length < 3)
			
			
			println("Valor investido:")
				var ValorInvestido = readln().toDouble()
				
				println("Valo atual (PU)")
				 valorPu = readln().toDouble()
				
				var mensagem = adicionarInvestimento(investimentos, nomeInvestimento, ValorInvestido,valorPu )
				
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







