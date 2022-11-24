/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.totoro.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Connection;
import mx.itson.totoro.persistencia.Conexion;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

/**
 *
 * @author Christian
 */
public class Alumno {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getIdCia() {
        return idCia;
    }

    public void setIdCia(String idCia) {
        this.idCia = idCia;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }
    
    private int id;
    private String nombre;
    private String apellidos;
    private String idCia;
    private Date fechaNacimiento;
    private String apodo;
    
    /**
     * Sirve para llenar un ArrayList con los datos que se obtienen del metodo obtener()
     * @return El ArrayList alumnos con todos los datos de los alumnnos
     */
    public static List<Alumno> obtenerTodos(){
        
        List<Alumno> alumnos = new ArrayList<>();
        
        try{
            
            Connection conexion = Conexion.obtener();
            Statement statement = conexion.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id, nombre, apellidos, idCia, fechaNacimiento, apodo FROM alumno");
            
            while(resultSet.next() == true){
                
                Alumno alumno = new Alumno();
                
                alumno.setId(resultSet.getInt(1));
                alumno.setNombre(resultSet.getString(2));
                alumno.setApellidos(resultSet.getString(3));
                alumno.setIdCia(resultSet.getString(4));
                alumno.setFechaNacimiento(resultSet.getDate(5));
                alumno.setApodo(resultSet.getString(6));
                
                alumnos.add(alumno);
                
            }
            
            
        }catch(Exception ex){
            
            System.err.println("Ocurrio un error: " + ex.getMessage());
            
        }
        
        return alumnos;
        
    }
    
    public boolean guardar(String nombre, String apellidos, String idCia, String apodo){

        boolean resultado = false;
        
        try{
            
            Connection conexion = Conexion.obtener();
            String consulta = "INSERT INTO alumno (nombre, apellidos, idCia, apodo) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, nombre);
            statement.setString(2, apellidos);
            statement.setString(3, idCia);
           //statement.setDate(4, (java.sql.Date) fechaNacimiento);
            statement.setString(4, apodo);
            statement.execute();
            
            resultado = statement.getUpdateCount() == 1;
            
            conexion.close();
            
        }catch(Exception ex){
            
            System.err.println("Ocurrio un error: " + ex.getMessage());
            
        }
        
        return resultado;

    }
    
}
