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
public class Maquina {
    private String nome;
    private Map<String, Estado> estados;
    private Estado inicial;
    

    public Maquina(String nome) {
        this.nome = nome;
        estados = new HashMap();
    }
    
    public void insereEstado(String nome){
        Estado estado = new Estado(nome);
        estados.put(nome, estado);
    }
    
    public void insereTransicao(String nomeOrigem, String valor, String nomeDestino){
        Estado origem = estados.get(nomeOrigem);
        Estado destino = estados.get(nomeDestino);
        origem.setTransicao(valor, destino);
        System.out.println("Transicao " + nomeOrigem + " " +  valor + " " +  nomeDestino);
    }
    
    public void setInicial(String nome){
        inicial = estados.get(nome);
    }
    
    public void setFinal(String nome){
        estados.get(nome).setFinal();
        System.out.println("Final " + nome);
    }
}
