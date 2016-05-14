/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_lfa;

import Classes.Maquina;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andre Dornas & Gabriel Mattar
 */
public class Sintatico {
    private final Lexico lexico;
    private Lexema atual;
    
    public Sintatico(Lexico lexico) {
        this.lexico = lexico;
        atual = lexico.nextToken();
    }
    
    private void matchToken(TiposLexicos type) {
        if(atual.getType() == type){
            atual = lexico.nextToken();
        } else {
            if(atual.getType() == TiposLexicos.FIM_DO_ARQUIVO)
                System.out.println(lexico.getLinha() + ": Fim de arquivo inesperado ["+atual.getToken()+"]");
            else 
                System.out.println(lexico.getLinha() + ": Lexema inesperado ["+atual.getToken()+"]");
            System.exit(1);
        }
    }
    
    public void run(List<Maquina> maquinas, List<String> alfabeto) {
        procAlfabeto(alfabeto);
        while(atual.getType() == TiposLexicos.SIMBOLO){
            maquinas.add(procMaquina());
        }
    }
    
    private Maquina procMaquina() {
        Maquina maquina;
        String nome = procNome();
        maquina = new Maquina(nome);
        
        matchToken(TiposLexicos.ABRE_CHAVES);
        
        matchToken(TiposLexicos.SIMBOLO);
        matchToken(TiposLexicos.SETA);        
        matchToken(TiposLexicos.ABRE_CHAVES);
        procEstados(maquina);
        matchToken(TiposLexicos.FECHA_CHAVES);        
        matchToken(TiposLexicos.PONTO_VIRGULA);
        
        matchToken(TiposLexicos.SIMBOLO);
        matchToken(TiposLexicos.SETA);        
        matchToken(TiposLexicos.ABRE_CHAVES);
        procTransicoes(maquina);
        matchToken(TiposLexicos.FECHA_CHAVES);        
        matchToken(TiposLexicos.PONTO_VIRGULA);
        
        //Proc estado inicial
        matchToken(TiposLexicos.SIMBOLO);
        matchToken(TiposLexicos.SETA);
        
        String inicial = procNome();
        maquina.setInicial(inicial);
        
        matchToken(TiposLexicos.PONTO_VIRGULA);
        
        matchToken(TiposLexicos.SIMBOLO);
        matchToken(TiposLexicos.SETA);        
        matchToken(TiposLexicos.ABRE_CHAVES);
        procEstadosFinais(maquina);
        matchToken(TiposLexicos.FECHA_CHAVES);        
        matchToken(TiposLexicos.PONTO_VIRGULA);
        
        
        matchToken(TiposLexicos.FECHA_CHAVES);
        
        return maquina;
        
    }

    private void procAlfabeto(List<String> alfabeto) {
        matchToken(TiposLexicos.SIMBOLO);
        matchToken(TiposLexicos.SETA);
        matchToken(TiposLexicos.ABRE_CHAVES);
        procSimbolos(alfabeto);
        matchToken(TiposLexicos.FECHA_CHAVES);
        matchToken(TiposLexicos.PONTO_VIRGULA);
    }
    
    private void procSimbolos(List<String> alfabeto) {
        alfabeto.add(atual.getToken());
        matchToken(TiposLexicos.SIMBOLO);
        while(atual.getType() == TiposLexicos.VIRGULA) {
            matchToken(TiposLexicos.VIRGULA);
            alfabeto.add(atual.getToken());
            matchToken(TiposLexicos.SIMBOLO);
        }
    }
    
    
    private String procNome() {
        String nome = null;
        nome = atual.getToken();
        matchToken(TiposLexicos.SIMBOLO);
        while(atual.getType() == TiposLexicos.SIMBOLO) {
            nome += atual.getToken();
            matchToken(TiposLexicos.SIMBOLO);
        }
        return nome;
    }
    
    private void  procEstados(Maquina maquina) {
        String nome = procNome();
        maquina.insereEstado(nome);
        
        while(atual.getType() == TiposLexicos.VIRGULA) {
            matchToken(TiposLexicos.VIRGULA);
            nome = procNome();
            maquina.insereEstado(nome);
        }
    }
    
    //Retornar lista de transicoes 
    private void procTransicoes(Maquina maquina) {
        //Pode nao haver transicao
        if(atual.getType() == TiposLexicos.ABRE_PARENTESES) { 
            procTransicao(maquina);
            while(atual.getType() == TiposLexicos.VIRGULA) {
                matchToken(TiposLexicos.VIRGULA);
                procTransicao(maquina);
            }
        }
    }
    
    //Retornar transicao
    private void procTransicao(Maquina maquina) {
        matchToken(TiposLexicos.ABRE_PARENTESES);
        String origem = procNome();
        matchToken(TiposLexicos.VIRGULA);
        String valor = atual.getToken();
        matchToken(TiposLexicos.SIMBOLO);
        matchToken(TiposLexicos.FECHA_PARENTESES);
        matchToken(TiposLexicos.IGUAL);
        String destino = procNome();
        maquina.insereTransicao(origem, valor, destino);
    }
    
    private void procEstadosFinais(Maquina maquina) {
        if(atual.getType() == TiposLexicos.SIMBOLO) {
            String ehfinal = procNome();
            maquina.setFinal(ehfinal);
            while(atual.getType() == TiposLexicos.VIRGULA) {
                matchToken(TiposLexicos.VIRGULA);
                ehfinal = procNome();
                maquina.setFinal(ehfinal);
            }
        }
    }
}
