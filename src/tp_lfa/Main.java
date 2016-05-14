/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_lfa;

import Classes.Estado;
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
            
            //Produtando as maquinas
            produtoAFD(maquinas.get(0), maquinas.get(1), alfabeto);
            
            
        } catch(Exception ex){
            System.out.println("Erro: " + ex);
        }
        
        
    }
    
    public static Maquina produtoAFD(Maquina m1, Maquina m2,  List<String> alfabeto){
        Maquina m3 = new Maquina("Produto");
        Estado inicialm1 = m1.getInicial(), inicialm2 = m2.getInicial();
        String inicial = inicialm1.getNome() + ", " + inicialm2.getNome();
        
        //Estados iniciais de m1 e m2 para definir o inicial do produto
        m3.insereEstado(inicialm1, inicialm2);
        m3.setInicial(inicial);
        
        
        
        
        m3.printMaquina();
        return m3;
    }
    
}
