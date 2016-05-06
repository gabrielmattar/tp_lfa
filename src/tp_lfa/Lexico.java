/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_lfa;

import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aluno
 */
public class Lexico {
    
    private final PushbackReader br;
    private final Map<String, TiposLexicos> tabelaSimbolos;
    private int linha;
    
    public Lexico(FileReader f)  {
        this.br = new PushbackReader(f);
        this.tabelaSimbolos = new HashMap<>();
        criarTabelaSimbolos();
        linha = 1;
    }    
    
    public Lexema nextToken() {
        consultarTabelaDeSimbolos("a");
        Lexema lexema = new Lexema(TiposLexicos.FIM_DO_ARQUIVO, "");
        int estado = 1;
        while(estado != 3){
            try{
                int c = br.read();
                if(c == -1){
                    return lexema;
                }
                switch(estado){
                    case 1:                    
                        switch(c){
                            case '\n': linha++;
                            case '\b':
                            case '\t':
                                estado = 1;
                                break;
                                
                            case ',':
                                lexema.addToToken(c);
                                estado = 3;
                                break;
                            case '{':
                                lexema.addToToken(c);
                                estado = 3;
                                break;
                            case '}':
                                lexema.addToToken(c);
                                estado = 3;
                                break;
                            case '(':
                                lexema.addToToken(c);
                                estado = 3;
                                break;
                            case ')':
                                lexema.addToToken(c);
                                estado = 3;
                                break;
                            case '=':
                                lexema.addToToken(c);
                                estado = 3;
                                break;
                            case ';':
                                lexema.addToToken(c);
                                estado = 3;
                                break;
                            case '-':
                                lexema.addToToken(c);
                                estado = 2;
                                break;
                        }
                        if(Character.isDigit(c) || Character.isLetter(c) ){
                            lexema.addToToken(c);
                            estado = 3;
                            lexema.setType(TiposLexicos.SIMBOLO);
                            break;
                        }
                        break;
                    case 2: // =
                        if(c == '>'){
                            lexema.addToToken(c);
                            estado = 3;
                        } 
                        break;
                    default:
                        lexema.setType(TiposLexicos.TOKEN_INVALIDO);
                        return lexema;
                }
            }catch(IOException ex){
                System.out.println(getLinha()+" Erro:" + ex.toString());
            }
        }
        
        if(lexema.getType() != TiposLexicos.SIMBOLO){
            lexema.setType(consultarTabelaDeSimbolos(lexema.getToken()));
        }
        if(lexema.getType() == TiposLexicos.TOKEN_INVALIDO){
            System.out.println(getLinha() + ": Lexema inválido ["+lexema.getToken()+"]");
            System.exit(1);
        } else if(lexema.getType() == TiposLexicos.FIM_INESPERADO){
            System.out.println(getLinha() + ": Fim inesperado ["+lexema.getToken()+"]");
            System.exit(1);
        }
        return lexema;
    }
    
    public String getLinha() {
        DecimalFormat formatter = new DecimalFormat("00");
        return formatter.format(linha);
    }
    
    private void criarTabelaSimbolos(){
        tabelaSimbolos.put(";", TiposLexicos.PONTO_VIRGULA);
        tabelaSimbolos.put("=", TiposLexicos.IGUAL);
        tabelaSimbolos.put("(", TiposLexicos.ABRE_PARENTESES);
        tabelaSimbolos.put(")", TiposLexicos.FECHA_PARENTESES);
        tabelaSimbolos.put(",", TiposLexicos.VIRGULA);
        tabelaSimbolos.put("{", TiposLexicos.ABRE_CHAVES);
        tabelaSimbolos.put("}", TiposLexicos.FECHA_CHAVES);
        tabelaSimbolos.put("->", TiposLexicos.SETA);
    }
    
    private TiposLexicos consultarTabelaDeSimbolos(String token){
        if(tabelaSimbolos.containsKey(token)){
            return tabelaSimbolos.get(token);
        } 
        return TiposLexicos.TOKEN_INVALIDO;
    }
    
}
