/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_lfa;

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
    
    public void run() {
        procAlfabeto();
        while(atual.getType() == TiposLexicos.SIMBOLO){
            procMaquina();
        }
    }
    
    private void procMaquina() {
        procNome();
        matchToken(TiposLexicos.ABRE_CHAVES);
        
        matchToken(TiposLexicos.SIMBOLO);
        matchToken(TiposLexicos.SETA);        
        matchToken(TiposLexicos.ABRE_CHAVES);
        procEstados();
        matchToken(TiposLexicos.FECHA_CHAVES);        
        matchToken(TiposLexicos.PONTO_VIRGULA);
        
        matchToken(TiposLexicos.SIMBOLO);
        matchToken(TiposLexicos.SETA);        
        matchToken(TiposLexicos.ABRE_CHAVES);
        procTransicoes();
        matchToken(TiposLexicos.FECHA_CHAVES);        
        matchToken(TiposLexicos.PONTO_VIRGULA);
        
        //Proc estado inicial
        matchToken(TiposLexicos.SIMBOLO);
        matchToken(TiposLexicos.SETA);
        procNome();
        matchToken(TiposLexicos.PONTO_VIRGULA);
        
        matchToken(TiposLexicos.SIMBOLO);
        matchToken(TiposLexicos.SETA);        
        matchToken(TiposLexicos.ABRE_CHAVES);
        procEstadosFinais();
        matchToken(TiposLexicos.FECHA_CHAVES);        
        matchToken(TiposLexicos.PONTO_VIRGULA);
        
        
        matchToken(TiposLexicos.FECHA_CHAVES);
    }

    private void procAlfabeto() {
        matchToken(TiposLexicos.SIMBOLO);
        matchToken(TiposLexicos.SETA);
        matchToken(TiposLexicos.ABRE_CHAVES);
        procSimbolos();
        matchToken(TiposLexicos.FECHA_CHAVES);
        matchToken(TiposLexicos.PONTO_VIRGULA);
    }
    
    private void procSimbolos() {
        matchToken(TiposLexicos.SIMBOLO);
        while(atual.getType() == TiposLexicos.VIRGULA) {
            matchToken(TiposLexicos.VIRGULA);
            matchToken(TiposLexicos.SIMBOLO);
        }
    }
    
    
    private void procNome() {
        matchToken(TiposLexicos.SIMBOLO);
        while(atual.getType() == TiposLexicos.SIMBOLO) {
            matchToken(TiposLexicos.SIMBOLO);
        }
    }
    
    private void  procEstados() {
        procNome();
        while(atual.getType() == TiposLexicos.VIRGULA) {
            matchToken(TiposLexicos.VIRGULA);
            procNome();
        }
    }
    
    //Retornar lista de transicoes 
    private void procTransicoes() {
        //Pode nao haver transicao
        if(atual.getType() == TiposLexicos.ABRE_PARENTESES) { 
            procTransicao();
            while(atual.getType() == TiposLexicos.VIRGULA) {
                matchToken(TiposLexicos.VIRGULA);
                procTransicao();
            }
        }
    }
    
    //Retornar transicao
    private void procTransicao() {
        matchToken(TiposLexicos.ABRE_PARENTESES);
        procNome();
        matchToken(TiposLexicos.VIRGULA);
        matchToken(TiposLexicos.SIMBOLO);
        matchToken(TiposLexicos.FECHA_PARENTESES);
        matchToken(TiposLexicos.IGUAL);
        procNome();
    }
    
    private void procEstadosFinais() {
        if(atual.getType() == TiposLexicos.SIMBOLO) {
            procNome();
            while(atual.getType() == TiposLexicos.VIRGULA) {
                matchToken(TiposLexicos.VIRGULA);
                procNome();
            }
        }
    }
}
