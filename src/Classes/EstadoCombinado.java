/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author mattar
 */
public class EstadoCombinado {
    private Estado estadoM1;
    private Estado estadoM2;
    
    public EstadoCombinado (Estado estadoM1, Estado estadoM2){
        this.estadoM1 = estadoM1;
        this.estadoM2 = estadoM2;
    }

    public Estado getNomeM1() {
        return estadoM1;
    }

    public Estado getNomeM2() {
        return estadoM2;
    }
    
    public EstadoCombinado getTransicao(String valor){
        Estado t1, t2;
        Estado erro = new Estado("");
        
        if(( t1 = estadoM1.getTransicao(valor)) != null){
            if(( t2 = estadoM2.getTransicao(valor)) != null) {
                return new EstadoCombinado(t1, t2);
            } else {
                return new EstadoCombinado(t1, erro);
            }
        } else {
            if(( t2 = estadoM2.getTransicao(valor)) != null) {
                return new EstadoCombinado(erro, t2);
            } else {
                return null;
            }
        }
    }
    
    
    public String getNomeCombinado(){
        if(estadoM1 == null){
            return "," + estadoM2.getNome();
        } else {
            if(estadoM2 == null) {
                return estadoM1.getNome() + ",";
            }
            return estadoM1.getNome() + "," + estadoM2.getNome();
        } 
    }
    
}
