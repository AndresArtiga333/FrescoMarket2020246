/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.andresartiga.report;

import java.io.InputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.andresartiga.db.Conexion;

/**
 *
 * @author andre
 */
public class GenerarReportes {
    public static void mostrarReportes(String nombreReporte, String titulo, Map parametros){
        InputStream reporte = GenerarReportes.class.getResourceAsStream(nombreReporte);
        try{
            JasperReport reportes = (JasperReport)JRLoader.loadObject(reporte);
            JasperPrint reporteImpreso = JasperFillManager.fillReport(reportes, parametros, Conexion.getInstance().getConexion());
            JasperViewer visor = new JasperViewer(reporteImpreso, false);
            visor.setTitle(titulo);
            visor.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    } 
    /* interfaz map
    
    HashMap es uno de los objetos que implementa un conjunto de key-value.
    Tiene un constructor sin parametros new HashMap() y su finalidad suele
    referirse para agrupar informacion en un unico objeto
    Tiene cierta similitud con la coleccion de objetos (ArrayList) pero con 
    la diferencia que estos no tienen orden
    Hash hace referencia a una tecnica de organizacion de archivos, hashing (abierto- cerrado)
    en la cual se almacenan registros en una direccion que es generada por una funcion
    que se aplica a la llave del registro
    
    En java el HashMap posee un espacio de memoria y cunado guarda un objeto en dicho espacio
    se determina su direccion aplicando una funcion a la llave que le indiquemos nosotros. Cada
    objeto se identifica mediante algun identificador apropiado
    
    
    */
}
