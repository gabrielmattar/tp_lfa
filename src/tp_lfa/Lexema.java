/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_lfa;

/**
 *
 * @author Andre
 */
public class Lexema {
    
    private TiposLexicos type;
    private String token;

    public Lexema(TiposLexicos type, String token) {
        this.type = type;
        this.token = token;
    }

    public TiposLexicos getType() {
        return type;
    }

    public void setType(TiposLexicos type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public void addToToken(int c) {
        token += (char) c;
    }
    
    
}
