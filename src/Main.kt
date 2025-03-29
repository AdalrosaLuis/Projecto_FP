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
	
	}
	
	
	
