package com.example.lucas.todrobot;
public class Compiler2 {
	int indices;
	String codigoGerado = "";
	char[] codigo;
	String[] reservadas = new String[]{"andarFrente","girarEsquerda","girarDireita","olhar","soltar","pegar"};

	public Compiler2(String codigo) {
		this.codigo = codigo.toCharArray();
		System.out.println(identificarCodigo(this.codigo));
	}
	private String identificarCodigo(char[] c){
		int correto = 0;
		String codigo = "";
		char caractere;
		int rept;
		for(int a = 0; a < c.length; a++){
			caractere = c[a];
			if(caractere != '\n' && caractere != '(' && caractere != ')' && caractere != '}' && caractere != '{' && caractere != ';' && caractere != ' '){
				//if(caractere == 'a' || caractere == 'b' || caractere == 'c' || caractere == 'd' || caractere == 'e' || caractere == 'f' || caractere == 'g' || caractere == 'h' || caractere == 'i' || caractere == 'j' || caractere == 'k' || caractere == 'l' || caractere == 'm' || caractere == 'n' || caractere == 'o' || caractere == 'p' || caractere == 'q' || caractere == 'r' || caractere == 's' || caractere == 't' || caractere == 'u' || caractere == 'v' || caractere == 'w' || caractere == 'x' || caractere == 'y' || caractere == 'z' || caractere == 'A' || caractere == 'B' || caractere == 'C' || caractere == 'D' || caractere == 'E' || caractere == 'F' || caractere == 'G' || caractere == 'H' || caractere == 'I' || caractere == 'J' || caractere == 'K' || caractere == 'L' || caractere == 'M' || caractere == 'N' || caractere == 'O' || caractere == 'P' || caractere == 'Q' || caractere == 'R' || caractere == 'S' || caractere == 'T' || caractere == 'U' || caractere == 'V' || caractere == 'W' || caractere == 'X' || caractere == 'Y' || caractere == 'Z'){
				codigo += caractere;
			}
			else if(caractere == '('){
				rept = repeticao(a, c);
				a = indices;
				System.out.println("Valor retornado: "+rept+"\nCódigo gerado: "+codigoGerado);
				if (rept > 0){
					int situacao = 0;
					for(int i = 0; i < reservadas.length; i++){
						if (codigo.equals(reservadas[i])){
							situacao ++;
							for (int j = 0; j < rept; j ++){
								codigoGerado += (i+1);
							}
						}
					}
					if (situacao == 0){
						if (codigo.equals("se")){

						}
						System.out.println("Erro no código.");
						correto ++;
					}
				}
			}else if (caractere == ';'){
				codigo = "";
			}
			/*else if(caractere == ';' && correto != 0){
				System.out.println("Tudo errado.");
				break;
			}
			else{
				System.out.println("Caractere = "+caractere);
				int situacao = 0;
				for(int i = 0; i < reservadas.length; i++){
					if (codigo.equals(reservadas[i])){
						situacao ++;
						codigoGerado += i+1;
						System.out.println("Veio");
					}
				}
				if (situacao == 0){
					System.out.println("Erro no cÃ³digo.");
					correto ++;
				}
			}*/
		}
		if (correto == 0){
			return codigoGerado;
		}else{
			return "Erros encontrados";
		}
	}
	private int repeticao(int indice, char[] c){
		int retorno = 0;
		String numero = "";
		int num;
		int ind = indice+1;
		System.out.println(ind);
		while (c[ind] != ')' && c[ind] != '\n' && c[ind] != ';'){
			numero += c[ind];
			System.out.println(numero+","+ind);
			ind ++;
		}
		if (c[ind] == ')'){
			try{
				num = Integer.parseInt(numero);
				retorno = num;
			}catch (Exception e){
				if (numero == "temObstaculo"){
					//Pede pro Bluetooth ver a frente
				}
				System.out.println("Deu erro!");
				retorno --;
			}
		}else{
			retorno --;
		}
		indices = ind;
		return retorno;
	}
}
