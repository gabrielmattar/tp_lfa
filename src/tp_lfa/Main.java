/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_lfa;

import Classes.EstadoCombinado;
import Classes.Estado;
import Classes.Maquina;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
            
            Main m = new Main();
            //Produtando as maquinas
            
            Maquina produtoAFD = m.produtoAFD(maquinas.get(0), maquinas.get(1), alfabeto);
            for (int i = 2; i < maquinas.size(); i++) {
                produtoAFD = m.produtoAFD(produtoAFD, maquinas.get(i), alfabeto);
            }
            
            m.Uniao(produtoAFD);
            m.Intersecao(produtoAFD);
            
            
        } catch(Exception ex){
            System.out.println("Erro: " + ex);
        }
    }
    
    public void Uniao(Maquina m){
        m.printMaquina("uniao");
    }
    
    public void Intersecao(Maquina m){
        m.printMaquina("inter");
    }
    
    public Maquina produtoAFD(Maquina m1, Maquina m2,  List<String> alfabeto){
        //Maquina produto
        Maquina m3 = new Maquina("Produto");
        
        //Estados iniciais de m1 e m2 para definir o inicial do produto
        Estado inicialM1 = m1.getInicial();
        Estado inicialM2 = m2.getInicial();
        EstadoCombinado inicial = new EstadoCombinado(inicialM1, inicialM2);
        //Estados auxiliares para percorrer as maquinas
        EstadoCombinado origem, destino;
        
        //Comecamos adicionando o incial na fila de estados a serem analisados
        Queue <EstadoCombinado> estados = new LinkedList();
        estados.add(inicial);
        
        //Maquina m3 recebe o estado inicial e tem o estado inicial definido
        m3.insereEstado(inicial);
        m3.setInicial(inicial.getNomeCombinado()); 
        
        while(!estados.isEmpty()){
            origem = estados.remove();
            for(String simbolo : alfabeto){
                //Caso a transicao nao va para estado de erro
                if((destino = origem.getTransicao(simbolo)) != null) {
                    //Caso a insercao for bem sucedida significa q o estado nao foi visitado antes 
                    //logo adicionamos ele na fila
                    if(m3.insereEstado(destino))
                        estados.add(destino);
                    //Inserimos a respectiva transicao na maquina produto
                    m3.insereTransicao(origem.getNomeCombinado(),  simbolo, destino.getNomeCombinado());
                }   
            }
        }
        m3.printMaquina();
        return m3;
    }
 
}
