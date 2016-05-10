/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_lfa;

/**
 *
 * @author AndreDornas
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
}
