/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author AndreDornas
 */
public class Transicao {
    
    private String simbolo;
    private String origem, detino;

    public Transicao(String simbolo, String origem, String detino) {
        this.simbolo = simbolo;
        this.origem = origem;
        this.detino = detino;
    }
    
    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDetino() {
        return detino;
    }

    public void setDetino(String detino) {
        this.detino = detino;
    }
    
}
