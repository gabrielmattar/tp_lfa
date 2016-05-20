/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mattar
 */
public class Estado {
    private final String nome;
    private Map<String, Estado> transicoes;
    private boolean ehFinal;
    
    
    public Estado (String nome) {
        this.nome = nome;
        transicoes = new HashMap<>();
        ehFinal = false;
    }
    
    public void setTransicao (String valor, Estado destino) {
        transicoes.put(valor, destino);
    }
    
    public Estado getTransicao(String valor) {
        return transicoes.get(valor);
    }

    public boolean isFinal() {
        return ehFinal;
    }
    
    public void setFinal() {
        this.ehFinal = true;
    }

    public void setFim(boolean m1, boolean m2, String method) throws IllegalArgumentException{
        if(method.equals("uniao")){
            ehFinal = m1 || m2;
        } else if(method.equals("intersecao")){
            ehFinal = m1 && m2;
        } else {
            throw new IllegalArgumentException("metodo não encontrado");
        }
    }
            
    public void setTransicoes(Map<String, Estado> transicoes) {
        this.transicoes = transicoes;
    }
        
    public Map<String, Estado> getTransicoes (){
        return transicoes;
    }
    
    
    public String getNome(){
        return nome;
    }

    @Override
    protected Object clone(){
        Estado e = new Estado(nome);
        if(ehFinal) e.setFinal();
        e.setTransicoes(transicoes);
        return e;
    }
    
    
    
}
