/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_lfa;

import Classes.Maquina;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

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
            Lexico l = new Lexico(new FileReader(new File("entrada/entrada.afd")));
            Lexema lex = null;
            Sintatico s = new Sintatico(l);
            
            List<Maquina> maquinas = new ArrayList<>() ;
            List<String> alfabeto = new ArrayList<>() ;
            s.run(maquinas, alfabeto);
           
            for(Maquina maquina : maquinas){
                maquina.printMaquina();
            }
            for(String nome : alfabeto){
                System.out.println(nome);
            }
            
        } catch(Exception ex){
            System.out.println("Erro: " + ex);
        }
    }
    
}
