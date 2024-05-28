
package org.andresartiga.bean;

/**
 *
 * @author andre
 */
public class DetalleFactura {
    private int idDetalleFa;
    private double precioUnitario;
    private int cantidad;
    private int numeroFac;
    private int codigoProducto;

    public DetalleFactura() {
    }

    public DetalleFactura(int idDetalleFa, double precioUnitario, int cantidad, int numeroFac, int codigoProducto) {
        this.idDetalleFa = idDetalleFa;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.numeroFac = numeroFac;
        this.codigoProducto = codigoProducto;
    }

    public int getIdDetalleFa() {
        return idDetalleFa;
    }

    public void setIdDetalleFa(int idDetalleFa) {
        this.idDetalleFa = idDetalleFa;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getNumeroFac() {
        return numeroFac;
    }

    public void setNumeroFac(int numeroFac) {
        this.numeroFac = numeroFac;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }
    
    
}
