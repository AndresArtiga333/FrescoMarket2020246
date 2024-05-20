/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.andresartiga.bean;

/**
 *
 * @author andre
 */
public class Empleados {
    private int idEmpleado;
    private String nombresEmpleado;
    private String apellidosEmpleado;
    private double sueldo;
    private String direccion;
    private String turno;
    private int idCargoEmpleado;

    public Empleados() {
    }

    public Empleados(int idEmpleado, String nombresEmpleado, String apellidosEmpleado, double sueldo, String direccion, String turno, int idCargoEmpleado) {
        this.idEmpleado = idEmpleado;
        this.nombresEmpleado = nombresEmpleado;
        this.apellidosEmpleado = apellidosEmpleado;
        this.sueldo = sueldo;
        this.direccion = direccion;
        this.turno = turno;
        this.idCargoEmpleado = idCargoEmpleado;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombresEmpleado() {
        return nombresEmpleado;
    }

    public void setNombresEmpleado(String nombresEmpleado) {
        this.nombresEmpleado = nombresEmpleado;
    }

    public String getApellidosEmpleado() {
        return apellidosEmpleado;
    }

    public void setApellidosEmpleado(String apellidosEmpleado) {
        this.apellidosEmpleado = apellidosEmpleado;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public int getIdCargoEmpleado() {
        return idCargoEmpleado;
    }

    public void setIdCargoEmpleado(int idCargoEmpleado) {
        this.idCargoEmpleado = idCargoEmpleado;
    }
    
        @Override
    public String toString() {
        return getIdEmpleado() + "    |   "  + getNombresEmpleado() + "    |   " + getApellidosEmpleado();
    }
    
}
