package testejava201707thais;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author thais cdp
 */
public class TesteJava201707Thais {

    public static void main(String[] args) throws FileNotFoundException {
    
        String msg = "Insira a operaÃ§Ã£o."
                    + "\ncount * - Contar total de linhas"
                    + "\ncount distinct [propriedade] - Total de valores distintos"
                    + "\nfilter [propriedade] [valor] - Linhas que o valor aparece em determinada coluna"
                    + "\nexit - Sair\n";
        
        System.out.println(msg);
        Scanner entrada = new Scanner(System.in);
        String parametro = entrada.nextLine();

        while (!parametro.equals("count *") && !parametro.contains("count distinct") && !parametro.contains("filter") && !parametro.equals("exit")) {
            System.out.println("\nComando nÃ£o reconhecido - Tente novamente.\n" + msg);
            entrada = new Scanner(System.in);
            parametro = entrada.nextLine();
        }
        if (parametro.equals("exit")) {
            System.out.println("\nAplicaÃ§Ã£o finalizada.");
        } else {
            System.out.println("\n" + lerArquivo(parametro));
        }
    }
    
    private static String lerArquivo(String comando) {
        File arquivoCSV = new File("C:\\cidadesbr.csv");
        String retorno = null;
        
        try {

            Scanner leitor = new Scanner(arquivoCSV);
            String linhaColunas = leitor.nextLine();
            String txtColunas = linhaColunas.replaceAll(",", " | ");
            String [] propriedades = linhaColunas.split(","); 
            
            if (comando.equals("count *")) { 
                int contador = 0; 
                while (leitor.hasNext()) { 
                    contador++; 
                    leitor.nextLine(); 
                }
                retorno = "Total de linhas: " + contador; 
            }
            if (comando.contains("count distinct")) {
                String propriedade = comando.substring(comando.indexOf("[") + 1, comando.indexOf("]")); 
                int posicaoArray = Arrays.asList(propriedades).indexOf(propriedade); 
                List<String> vD = new ArrayList();   
                
                while (leitor.hasNext()) { 
                    String linhasDoArquivo = leitor.nextLine(); 
                    String[] valoresEntreVirgulas = linhasDoArquivo.split(","); 
                    
                    if(!vD.contains(valoresEntreVirgulas[posicaoArray])) { 
                        vD.add(valoresEntreVirgulas[posicaoArray]); 
                    }
                }                                         
                retorno = "Total de valores distintos: " + vD.size(); 
            }       
            if (comando.contains("filter")) {
                String propriedade = comando.substring(comando.indexOf("[") + 1, comando.indexOf("]"));
                String valor = comando.substring(comando.lastIndexOf("[") + 1, comando.lastIndexOf("]"));
                int coluna = Arrays.asList(propriedades).indexOf(propriedade); 
                
                String linha = txtColunas; 
                
                while (leitor.hasNext()) { 
                    String linhaAtual = leitor.nextLine(); 
                    String[] colunas = linhaAtual.split(","); 
                    String valorColunaAtual = colunas[coluna]; 
                    
                    if (valorColunaAtual.toLowerCase().contains(valor.toLowerCase())) { 
                        linha += "\n" + linhaAtual.replaceAll(",", " | "); 
                    }
                }
                retorno = linha;  
              
            }
        
        } catch (FileNotFoundException e) {
            
        }
        
        return retorno; 
    }
}
