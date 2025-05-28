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
var investimentos = arrayOfNulls<Array<String>>(100)
var configuracoes = emptyArray<String>()
var liquidacoes = emptyArray<Double>()
var nomeInvestimento= emptyArray<String>()
var numeroLinhas = emptyArray<String>()
var indice= emptyArray<Int>()

fun obterMenu():String {
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
fun lerConfiguracoes(fileName: String, configuracoes:Array<String>):Boolean {
	return false
	
}
fun guardarConfiguracoes(fileName: String, liquidacoes:Array<String>) {

}

fun lerLiquidacoes(fileName: String,liquidacoes:Array<Double>):Boolean{
	
	return false
}
fun guardarLiquidacoes(fileName:String,liquidacoes:Array<Double>){

}
fun lerInvestimentos(fileName:String,investimentos:Array<Array<String>?>):Boolean{
	return false
	
}
fun consultarInvestimentos(investimentos: Array<Array<String>?>,
                           configuracoes: Array<String>, liquidacoes: Array<Double>): String{
	return ""
	
}
fun adicionarInvestimento(investimentos: Array<Array<String>?>, nome: String,
                          valorInvestido: Double, valorAtual: Double): String{
	
	return ""
}
fun editarInvestimento(investimentos: Array<Array<String>?>, nome: String, valor: Double): String{
	return ""
}
fun liquidarInvestimento(liquidacoes: Array<Double>, investimentos: Array<Array<String>?>, numero: Int): String{
	return ""
}
fun guardarInvestimentos(fileName:String, investimentos:Array<Array<String>?>) {

}
fun main() {
	println(obterMenu())
	
	var moeda: String
	var escolha: Int
	var nomeCompleto:String
	var valorPu:Double
	
	println(
		"#####################\n" +
				"### Investimentos ###\n" +
				"#####################\n"
	)
	do {
		
		print("Por favor indeque o seu nome:\n")
		nomeCompleto = readln()
		print("Por favor indique a moeda da sua conta(€ ou $):\n")
		moeda = readln()
		
		if ((nomeCompleto.length < 2) || ((moeda != "€") && (moeda != "$"))) {
			println("Dados inválidos.\n" +
					"Nome completo deve ser definido por pelomenos dois nomes e ter pelomenos um espaco vazio e pelo menos 4 caracteres.\n" +
					"A moeda deverá ser € ou \n.")
		}
		
	}while ((nomeCompleto.length < 2) || ((moeda != "€") && (moeda != "$")))
	
	val primeiroNome = nomeCompleto.split("\\s+".toRegex())[0]
	
	println("Olá $primeiroNome\n")
	while (true)
		
		
		do {
			
			println(obterMenu())
			print("Indica o comando que pretendes:\n ")
			escolha = readln().toInt()
			
			when (escolha) {
				1 -> {
					consultarInvestimentos(investimentos, configuracoes, liquidacoes)
				}
				
				2 -> {
					var nomeInvestimento=""
					
					do{
						println("Adicionar investimento\n")
						
						println("Nome do Investimento:")
						nomeInvestimento = readln().toString()
						if (nomeInvestimento.length < 3) {
							println("Nome inválido. o nome apenas pode  conter letras e tem de ter no minimo 3 caracteres.")
						}
					}while (nomeInvestimento.length < 3)
					
					println("Valor investido:")
					var valorInvestido = readln().toDouble()
					
					println("Valo atual (PU)")
					valorPu = readln().toDouble()
					
					var mensagem = adicionarInvestimento(investimentos, nomeInvestimento, valorInvestido, valorPu)
					println(mensagem)
					
				}
				3 -> {
					println("Editar investimento\n")
					println("Nome do investimento:")
					var nomeInvestimento= readln().toString()
					println("Valor atual (PU):")
					valorPu= readln().toDouble()
					
					//if (editarInvestimento != nomeInvestimento && valorPu != valorAtual) {
					println("Nao existe investimentos em carteira com esse nome.")
					
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



