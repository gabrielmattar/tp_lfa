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
        Maquina m3 = new Maquina("Produto");
        
        //Estados iniciais de m1 e m2 para definir o inicial do produto
        Estado inicialm1 = m1.getInicial(), inicialm2 = m2.getInicial();
        
        EstadoCombinado inicial = new EstadoCombinado(inicialm1, inicialm2);
        
        
        //Comecamos adicionando o incial na fila de estados a serem analisados
        Queue <EstadoCombinado> estados = new LinkedList();
        estados.add(inicial);
        
        EstadoCombinado atual, transitado;
        
        m3.insereEstado(inicial.getNomeCombinado(), inicial.getEstadoM1().isFinal(), inicial.getEstadoM2().isFinal());
            
        
        while(!estados.isEmpty()){
            atual = estados.remove();
            for(String simbolo : alfabeto){
                if((transitado = atual.getTransicao(simbolo)) != null) {
                    if(m3.insereEstado(transitado.getNomeCombinado(), 
                            transitado.getEstadoM1().isFinal(), transitado.getEstadoM2().isFinal())){
                        estados.add(transitado);
                    }
                    m3.insereTransicao(atual.getNomeCombinado(),  simbolo, transitado.getNomeCombinado());
                }   
            }
        }
        m3.setInicial(inicial.getNomeCombinado()); 
        
        m3.printMaquina();
        return m3;
    }
    
    
    
}
