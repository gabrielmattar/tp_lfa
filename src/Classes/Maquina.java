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
    
    public static Alfabeto alfabeto;
    private String nome;
    private Map<String, Estado> estados;
    private Estado inicial;
    

    public Maquina(String nome) {
        this.nome = nome;
        estados = new HashMap();
    }
    
    public boolean insereEstado(String nome){
        if(estados.get(nome) == null){
            Estado estado = new Estado(nome);
            estados.put(nome, estado);
            return true;
        } else {
            return false;
        }
    }
    
    public boolean insereEstado(Estado estado){
        if(estados.get(estado.getNome()) == null){
            estados.put(estado.getNome(), estado);
            return true;
        } else {
            return false;
        }
    }
    
    public boolean insereEstado(String nome, boolean m1, boolean m2){
        if(estados.get(nome) == null){
            Estado estado = new Estado(nome);
            estado.setFimUniaoInter(m1, m2);
            estados.put(nome, estado);
            return true;
        } else {
            return false;
        }
    }
    
    
    public void insereTransicao(String nomeOrigem, String valor, String nomeDestino){
        Estado origem = estados.get(nomeOrigem);
        Estado destino = estados.get(nomeDestino);
        origem.setTransicao(valor, destino);
    }
    
    public void setInicial(String nome){
        inicial = estados.get(nome);
    }
    
    public void setFinal(String nome){
        estados.get(nome).setFinal();
    }
    
    public Estado getInicial(){
        return inicial;
    }
    
    public void printMaquina(){
        System.out.println("\n\nNome maquina: " + nome + "\n");
        for(Map.Entry<String, Estado> estado : estados.entrySet()){
            Map<String, Estado> transicoes = estado.getValue().getTransicoes();
            if(estado.getValue().isFinal()){
                System.out.println(" " + estado.getKey() + " (final)");
            } else{ 
                System.out.println(" " + estado.getKey());
            }
            for(Map.Entry<String, Estado> transicao : transicoes.entrySet()) {
                System.out.println("   " + transicao.getKey() + " " + transicao.getValue().getNome());
            }
        }
    }
    
    public void printMaquina(String metodo){
        System.out.println("\n\nNome maquina: " + nome + "   -> " + metodo + "\n");
        for(Map.Entry<String, Estado> estado : estados.entrySet()){
            Map<String, Estado> transicoes = estado.getValue().getTransicoes();
            if("uniao".equals(metodo)){
                if(estado.getValue().isFimUniao()){
                    System.out.println(" " + estado.getKey() + " (final)");
                } else{ 
                    System.out.println(" " + estado.getKey());
                }
            } else if("inter".equals(metodo)){
                if(estado.getValue().isFimInter()){
                    System.out.println(" " + estado.getKey() + " (final)");
                } else{ 
                    System.out.println(" " + estado.getKey());
                }
            }
            for(Map.Entry<String, Estado> transicao : transicoes.entrySet()) {
                System.out.println("   " + transicao.getKey() + " " + transicao.getValue().getNome());
            }
        }
    }
}
