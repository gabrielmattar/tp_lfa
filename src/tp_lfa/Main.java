/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_lfa;

import java.io.File;
import java.io.FileReader;

/**
 *
 * @author aluno
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try{
            //Lexico l = new Lexico(new FileReader(new File("teste.txt")));
            Lexico l = new Lexico(new FileReader(new File(args[0])));
            /*Sintatico s = new Sintatico(l);
            
            Comando cmd = s.init();
            cmd.Executar();*/
            
        } catch(Exception ex){
            System.out.println("Erro: " + ex);
        }
    }
    
}
