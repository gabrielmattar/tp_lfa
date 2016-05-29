/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import tp_lfa.Main;

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
    
    public boolean insereEstado(EstadoCombinado combinado, String method) {
        String nome = combinado.getNomeCombinado();
        if(estados.get(nome) == null){
            Estado estado = new Estado(nome);
            estado.setFim(combinado.getEstadoM1().isFinal(), combinado.getEstadoM2().isFinal(), method);
            estados.put(nome, estado);
            return true;
        } else {
            return false;
        }
    }
    
    public boolean estadoExists(String nome){
        return estados.get(nome) != null;
    }
    
    public void insereTransicao(Transicao transicao){
        Estado origem = estados.get(transicao.getOrigem());
        Estado destino = estados.get(transicao.getDetino());
        origem.setTransicao(transicao.getSimbolo(), destino);
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
    
    
    public void writeLine(String conteudo, FileOutputStream file) throws IOException{
        conteudo += "\n";
        file.write(conteudo.getBytes());
        
    }
    public void printMaquina(String metodo){
        System.out.println("\n\nNome maquina: " + nome + "   " + metodo + "\n");
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
    
    public void toDot() throws IOException{
        try (FileOutputStream file = new FileOutputStream(new File(Main.saida+"/" + this.nome + ".dot"))) {
            writeLine("digraph \"" + this.nome + "\" {", file);
            writeLine("_nil [style=\"invis\"];", file);
            writeLine("_nil -> \"" + inicial.getNome() + "\" " + "[label=\"\"];" , file);
            for(Map.Entry<String, Estado> estado : estados.entrySet()){
                Map<String, Estado> transicoes = estado.getValue().getTransicoes();
                if(estado.getValue().isFinal()){
                    writeLine("\"" + estado.getKey() + "\" [peripheries=2];",file);
                }

                for(Map.Entry<String, Estado> transicao : transicoes.entrySet()) {
                    writeLine("\"" + estado.getKey() + "\" " + "-> \"" + transicao.getValue().getNome() + "\" [label=" +transicao.getKey() + "];" ,file);
                }
            }
            
            writeLine("}", file);
        }
    }
    
    public void toDot(String metodo) throws IOException {
        //System.out.println("\n\n\n\n");
        try (FileOutputStream file = new FileOutputStream(new File(Main.saida +"/" + metodo + ".dot"))) {
            writeLine("digraph \"" + this.nome + "\" {", file);
            writeLine("_nil [style=\"invis\"];", file);
            writeLine("_nil -> \"[" + inicial.getNome() + "]\" " + "[label=\"\"];" , file);
            for(Map.Entry<String, Estado> estado : estados.entrySet()){
                Map<String, Estado> transicoes = estado.getValue().getTransicoes();
                if(estado.getValue().isFinal()){
                    //System.out.println("FINAL = " + estado.getValue().getNome());
                    writeLine("\"[" + estado.getKey() + "]\" [peripheries=2];",file);
                }
                for(Map.Entry<String, Estado> transicao : transicoes.entrySet()) {
                    writeLine("\"[" + estado.getKey() + "]\" " + "-> \"[" + transicao.getValue().getNome() + "]\" [label=" +transicao.getKey() + "];" ,file);
                }
            }
            
            writeLine("}", file);
        }
    }
    
}
