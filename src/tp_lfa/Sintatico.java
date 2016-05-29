/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_lfa;

import Classes.Alfabeto;
import Classes.Estado;
import Classes.Maquina;
import Classes.Transicao;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andre Dornas & Gabriel Mattar
 */
public class Sintatico {
    private final Lexico lexico;
    private Lexema atual;
    private List<Estado> estados;
    private Maquina maquina;
    
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
    
    public void run(List<Maquina> maquinas) {
        Alfabeto alfabeto = procAlfabeto();
        Maquina.alfabeto = alfabeto;
        while(atual.getType() == TiposLexicos.SIMBOLO){
            maquinas.add(procMaquina());
        }
    }
    
    private Maquina procMaquina() {
        String nome = procNome();
        maquina = new Maquina(nome);
        
        matchToken(TiposLexicos.ABRE_CHAVES);
        
        matchToken(TiposLexicos.SIMBOLO);
        matchToken(TiposLexicos.SETA);        
        matchToken(TiposLexicos.ABRE_CHAVES);
        estados = procEstados();
        for (Estado estado : estados) {
            maquina.insereEstado(estado);
        }
        matchToken(TiposLexicos.FECHA_CHAVES);        
        matchToken(TiposLexicos.PONTO_VIRGULA);
        
        matchToken(TiposLexicos.SIMBOLO);
        matchToken(TiposLexicos.SETA);        
        matchToken(TiposLexicos.ABRE_CHAVES);
        List<Transicao> transicoes = procTransicoes();
        for (Transicao transicao : transicoes) {
            maquina.insereTransicao(transicao);
        }
        matchToken(TiposLexicos.FECHA_CHAVES);        
        matchToken(TiposLexicos.PONTO_VIRGULA);
        
        //Proc estado inicial
        matchToken(TiposLexicos.SIMBOLO);
        matchToken(TiposLexicos.SETA);
        
        String inicial = procNome();
        if(!maquina.estadoExists(inicial)){
            System.out.println(lexico.getLinha() + ": Estado não existe ["+inicial+"]");
            System.exit(1);
        }
        maquina.setInicial(inicial);
        
        matchToken(TiposLexicos.PONTO_VIRGULA);
        
        matchToken(TiposLexicos.SIMBOLO);
        matchToken(TiposLexicos.SETA);        
        matchToken(TiposLexicos.ABRE_CHAVES);
        List<String> estadosFinais = procEstadosFinais();
        for (String finais : estadosFinais) {
            if(!maquina.estadoExists(finais)){
                System.out.println(lexico.getLinha() + ": Estado não existe ["+finais+"]");
                System.exit(1);
            }
            maquina.setFinal(finais);
        }
        matchToken(TiposLexicos.FECHA_CHAVES);        
        matchToken(TiposLexicos.PONTO_VIRGULA);
        
        matchToken(TiposLexicos.FECHA_CHAVES);
        return maquina;
        
    }

    private Alfabeto procAlfabeto() {
        //matchToken(TiposLexicos.SIMBOLO);
        String nomeAlfabeto = procNome();
        Alfabeto alfabeto = new Alfabeto(nomeAlfabeto);
        matchToken(TiposLexicos.SETA);
        matchToken(TiposLexicos.ABRE_CHAVES);
        List<String> simbolos = procSimbolos();
        alfabeto.setSimbolos(simbolos);
        matchToken(TiposLexicos.FECHA_CHAVES);
        matchToken(TiposLexicos.PONTO_VIRGULA);
        return alfabeto;
    }
    
    private List<String> procSimbolos() {
        List<String> simbolos = new ArrayList<>();
        simbolos.add(atual.getToken());
        matchToken(TiposLexicos.SIMBOLO);
        while(atual.getType() == TiposLexicos.VIRGULA) {
            matchToken(TiposLexicos.VIRGULA);
            simbolos.add(atual.getToken());
            matchToken(TiposLexicos.SIMBOLO);
        }
        return simbolos;
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
    
    private List<Estado> procEstados() {
        List<Estado> estados = new ArrayList<>();
        String nome = procNome();
        estados.add(new Estado(nome));
        //maquina.insereEstado(nome);
        while(atual.getType() == TiposLexicos.VIRGULA) {
            matchToken(TiposLexicos.VIRGULA);
            nome = procNome();
            estados.add(new Estado(nome));
            //maquina.insereEstado(nome);
        }
        return estados;
    }
    
    //Retornar lista de transicoes 
    private List<Transicao> procTransicoes() {
        //Pode nao haver transicao
        List<Transicao> transicoes = new ArrayList<>();
        if(atual.getType() == TiposLexicos.ABRE_PARENTESES) {
            transicoes.add(procTransicao());
            while(atual.getType() == TiposLexicos.VIRGULA) {
                matchToken(TiposLexicos.VIRGULA);
                transicoes.add(procTransicao());
            }
        }
        return transicoes;
    }
    
    //Retornar transicao
    private Transicao procTransicao() {
        matchToken(TiposLexicos.ABRE_PARENTESES);
        String origem = procNome();
        if(!maquina.estadoExists(origem)){
            System.out.println(lexico.getLinha() + ": Estado não existe ["+origem+"]");
            System.exit(1);
        }
        matchToken(TiposLexicos.VIRGULA);
        String valor = atual.getToken();
        if(!Maquina.alfabeto.getSimbolos().contains(valor)){
            System.out.println(lexico.getLinha() + ": Símbolo não presente no alfabeto ["+valor+"]");
            System.exit(1);
        }
        matchToken(TiposLexicos.SIMBOLO);
        matchToken(TiposLexicos.FECHA_PARENTESES);
        matchToken(TiposLexicos.IGUAL);
        String destino = procNome();
        if(!maquina.estadoExists(destino)){
            System.out.println(lexico.getLinha() + ": Estado não existe ["+destino+"]");
            System.exit(1);
        }
        //Transicao transicao = new Transicao(valor, origem, destino);
        return new Transicao(valor, origem, destino);
        //maquina.insereTransicao(transicao);
    }
    
    private List<String> procEstadosFinais() {
        List<String> finais = new ArrayList<>();
        if(atual.getType() == TiposLexicos.SIMBOLO) {
            finais.add(procNome());
            //maquina.setFinal(ehfinal);
            while(atual.getType() == TiposLexicos.VIRGULA) {
                matchToken(TiposLexicos.VIRGULA);
                finais.add(procNome());
                //maquina.setFinal(ehfinal);
            }
        }
        return finais;
    }
}
