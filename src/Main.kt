@file:Suppress("UNREACHABLE_CODE")

import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val MAX_investimentos = 99
val FORMATO_DATA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
var MAX_colunas = 6
const val CONFIGURACAO_FILE = "configuracoes.txt"
const val LIQUIDACAO_FILE = "liquidacoes.txt"
const val INVESTIMENTOS_FILE = "investimentos.txt"

var configuracoes = Array(2) { "" }
var liquidacoes = emptyArray<Double>()
var investimentos: Array<Array<String>?> = arrayOfNulls(99)

//var investimentos = mutableListOf<Array<String>>()
var contadorInvestimentos = 0

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

fun lerLiquidacoes(fileName: String, liquidacoes: Array<Double>): Boolean {
	
	return false
}

fun guardarLiquidacoes(fileName: String, liquidacoes: Array<Double>) {

}

fun lerConfiguracoes(fileName: String, configuracoes: Array<String>): Boolean {
	val file = File(fileName)
	
	if (!file.exists()) return false
	
	try {
		val linha = file.readText().trim()
		
		val valores = linha.split(";")
		
		if (valores.size < 2)
			return false
		
		for (i in valores.indices) {
			if (i < configuracoes.size) {
				configuracoes[i] = valores[i]
			}
		}
		
		return true
	} catch (e: Exception) {
		println("Erro ao ler configurações: ${e.message}")
		return false
	}
}

fun guardarConfiguracoes(fileName: String, configuracoes: Array<String>) {
	val file = File(fileName)
	
	try {
		if (!file.exists()) {
			file.createNewFile()
		}
		
		val linha = configuracoes.joinToString(";")
		file.writeText(linha)
		
		println("Configurações guardadas com sucesso em '$fileName'.")
	} catch (e: Exception) {
		println("Erro ao guardar configurações: ${e.message}")
	}
}


fun lerInvestimentos(fileName: String, investimentos: Array<Array<String>?>): Boolean {
	val file = File(fileName)
	
	if (!file.exists())
		return false
	
	try {
		val linhas = file.readLines()
		
		for (linha in linhas) {
			if (linha.isBlank() || contadorInvestimentos >= MAX_investimentos)
				continue
			
			val dados = linha.split(";")
			
			if (dados.size != MAX_colunas)
				continue
			
			investimentos[contadorInvestimentos] = dados.toTypedArray()
			contadorInvestimentos++
		}
		
		return true
	} catch (e: Exception) {
		return false
	}
}

fun liquidarInvestimento(
	liquidacoes: Array<Double>,
	investimentos: Array<Array<String>?>,
	numero: Int,
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

fun consultarInvestimentos(
	investimentos: Array<Array<String>?>,
	configuracoes: Array<String>,
	liquidacoes: Array<Double>,
): String {
	
	var lucroTotal = 0.0
	var contador = 1
	
	val ativos = investimentos.filterNotNull()
	
	if (ativos.size <= 0)
		return "Nao existem investomentos em carteira \n" + "\nLucro: %.2f ${configuracoes[1]}".format(lucroTotal)
	
	val colunas = listOf("Nome", "Data", "Investido", "Atual", "Quantidade", "Rentabilidade")
	val larguraColuna = IntArray(colunas.size) { colunas[it].length }
	
	
	for (investimento in investimentos) {
		if (investimento != null) {
			larguraColuna[0] = maxOf(larguraColuna[0], investimento[0].length)
			larguraColuna[1] = maxOf(larguraColuna[1], investimento[1].length)
			larguraColuna[2] = maxOf(larguraColuna[2], "%.2f ${configuracoes[1]}".format(investimento[2].toDouble()).length)
			larguraColuna[3] = maxOf(larguraColuna[3], "%.2f ${configuracoes[1]}".format(investimento[3].toDouble()).length)
			larguraColuna[4] = maxOf(larguraColuna[4], "%.2f".format(investimento[4].toDouble()).length)
			larguraColuna[5] = maxOf(larguraColuna[5], "%.1f %%".format(0.0).length)
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
	
	for (investimento in investimentos) {
		if (investimento != null) {
			val nome = investimento[0]
			val data = investimento[1]
			val investido = investimento[2].toDouble()
			val atual = investimento[3].toDouble()
			val quantidade = investimento[4].toDouble()
//			val rentabilidade = if (investido != 0.0) ((atual - investido) / investido) * 100 else 0.0
			val rentabilidade = investimento[5].toDouble()
			
//			lucroTotal += (atual - investido)
			
			stringBuilder.append(contador.toString().padEnd(4))
			stringBuilder.append(nome.padEnd(larguraColuna[0] + 2)).append("| ")
			stringBuilder.append(data.padEnd(larguraColuna[1] + 2)).append("| ")
			stringBuilder.append("%.2f ${configuracoes[1]}".format(investido).padEnd(larguraColuna[2] + 2)).append("| ")
			stringBuilder.append("%.2f ${configuracoes[1]}".format(atual).padEnd(larguraColuna[3] + 2)).append("| ")
			stringBuilder.append("%.2f".format(quantidade).padEnd(larguraColuna[4] + 2)).append("| ")
			stringBuilder.append("%.1f %%".format(rentabilidade).padEnd(larguraColuna[5] + 2))
			stringBuilder.append("\n")
			
			contador++
		}
	}
	// TODO: calcular o lucro total badeando no vetor de liquidacoes
	stringBuilder.append("\nLucro: %.2f ${configuracoes[1]}".format(lucroTotal))
	return stringBuilder.toString()
}

fun adicionarInvestimento(
	investimentos: Array<Array<String>?>,
	nome: String,
	valorInvestido: Double,
	valorAtual: Double,
): String {
	
	var quantidade = valorInvestido / valorAtual
	
	investimentos[contadorInvestimentos] = arrayOf(
		nome,
		LocalDateTime.now().format(FORMATO_DATA),
		valorInvestido.toString(),
		valorAtual.toString(),
		quantidade.toString(),
		0.toString()
	)
	
	contadorInvestimentos++
	
	return "Investimento adicionado com sucesso"
}

fun editarInvestimento(
	investimentos: Array<Array<String>?>,
	nome: String,
	valor: Double
): String {
	var atualizado = false
	
	for (investimento in investimentos.filterNotNull()) {
		if (investimento[0] == nome) {
			
			var valorInvestido = investimento[2].toDouble()
			
			var quantidade = investimento[4].toDouble()
			
			var rentabilidade = (((valor * quantidade) - valorInvestido) / valorInvestido) * 100
			
			investimento[3] = valor.toString()
			investimento[5] = rentabilidade.toString()
			
			atualizado = true
		}
	}
	
	return if(atualizado) "Investimentos atualizados com sucesso!" else "Não existem investimentos em carteira com esse nome.";
}

fun main() {
	
	var dadosConfiguracao = lerConfiguracoes(CONFIGURACAO_FILE, configuracoes)
	lerInvestimentos(INVESTIMENTOS_FILE, investimentos)
	
	var nome: String = ""
	var moeda: String = ""
	var escolha: Int
	var nomeInvestimento: String
	var valorPu: Double
	
	println(
		"#####################\n" +
				"### Investimentos ###\n" +
				"#####################\n"
	)
	
	
	if (!dadosConfiguracao) {
		var dadosValidos = false
		
		while (!dadosValidos) {
			println("Por favor indique o seu nome:")
			nome = readln()
			
			println("Por favor indique a moeda da sua conta(€ ou $):")
			moeda = readln()
			
			val nomeCompleto = nome.split(" ")
			
			if (nomeCompleto.size != 2 || (moeda != "$" && moeda != "€")) {
				println(
					"Dados invalidos.\n" +
							"O nome completo deve ser definido por pelo menos " +
							"dois nomes e ter pelo menos um espaço vazio e pelo menos 4 caracteres.\n" +
							"A moeda deverá ser em € ou $.\n"
				)
			}
			else {
				dadosValidos = true
			}
		}
		
		configuracoes[0] = nome
		configuracoes[1] = moeda
		
		guardarConfiguracoes(CONFIGURACAO_FILE, configuracoes)
	}
	
	println("Olá ${configuracoes[0]}\n")
	
	do {
		println(obterMenu())
		println("Indica o comando que pretende:\n")
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
						println(
							"Nome invalido, o nome apenas pode conter letras e tem de ter no minimo 3 caracteres.\n" +
									"(prima enter para continuar)"
						)
					}
				}
				while (nomeInvestimento.length < 3)
				
				println("Valor investido:")
				var ValorInvestido = readln().toDouble()
				
				println("Valor atual (PU)")
				valorPu = readln().toDouble()
				
				var mensagem = adicionarInvestimento(investimentos, nomeInvestimento, ValorInvestido, valorPu)
				
				guardarInvestimentos(INVESTIMENTOS_FILE, investimentos)
				
				println(mensagem)
			}
			3 -> {
				do {
					println("Editar investimento\n")
					println("Nome do Investimento:")
					nomeInvestimento = readln().toString()
					
					if (nomeInvestimento.length < 3) {
						println(
							"Nome invalido, o nome apenas pode conter letras e tem de ter no minimo 3 caracteres.\n" +
									"(prima enter para continuar)"
						)
					}
				}
				while (nomeInvestimento.length < 3)
				

				println("Valor atual (PU)")
				valorPu = readln().toDouble()
				
				var mensagem = editarInvestimento(investimentos, nomeInvestimento, valorPu)
				
				guardarInvestimentos(INVESTIMENTOS_FILE, investimentos)
				
				println(mensagem)
			}
			4 -> {
				println("Voce escolheu liquidar investimento. \n menu em desenvolvimento")
			}
			5 -> {
				guardarInvestimentos(INVESTIMENTOS_FILE, investimentos)
				println("Investimentos salvos com sucesso")
			}
			9 -> {
				println("Voce escolheu configuracoes. \n menu em desenvolvimento")
			}
		}
		
		if (escolha != 0) {
			println("(prima enter para voltar ao menu.)")
			readln()
		}
	}
	while (escolha != 0)
	
	println("Adeus e bons investimentos!")
}







