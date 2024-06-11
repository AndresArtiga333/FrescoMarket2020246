
package org.andresartiga.bean;

/**
 *
 * @author andre
 */
public class DetalleCompra {
    private int idDetalleCo;
    private double precioUnitario;
    private int cantidadCo;
    private int idProducto;
    private int numDoc;

    public DetalleCompra() {
    }

    public DetalleCompra(int idDetalleCo, double precioUnitario, int cantidadCo, int idProducto, int numDoc) {
        this.idDetalleCo = idDetalleCo;
        this.precioUnitario = precioUnitario;
        this.cantidadCo = cantidadCo;
        this.idProducto = idProducto;
        this.numDoc = numDoc;
    }

    public int getIdDetalleCo() {
        return idDetalleCo;
    }

    public void setIdDetalleCo(int idDetalleCo) {
        this.idDetalleCo = idDetalleCo;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCantidadCo() {
        return cantidadCo;
    }

    public void setCantidadCo(int cantidadCo) {
        this.cantidadCo = cantidadCo;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(int numDoc) {
        this.numDoc = numDoc;
    }
    
    
}
