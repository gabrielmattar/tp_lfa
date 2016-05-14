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
    
}
