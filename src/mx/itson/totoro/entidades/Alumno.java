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
import javax.swing.JOptionPane;

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
            
            conexion.close();
            
        }catch(Exception ex){
            
            System.err.println("Ocurrio un error: " + ex.getMessage());
            
        }
        
        return alumnos;
        
    }
    
    /**
     * Con este metodo se hace un nuevo registro de un alumno 
     * @param nombre El nombre del alumno a guardar
     * @param apellidos Los apellidos del alumno a guardar
     * @param idCia ID del alumno a guardar
     * @param apodo El apodo del alumno que se va a guardar
     * @return Un tipo boolean que nos dice si el registro se completo exitosamente o no
     */
    public boolean guardar(String nombre, String apellidos, String idCia, String apodo){

        boolean resultado = false;
        
        try{
            
            Connection conexion = Conexion.obtener();
            String consulta = "INSERT INTO alumno (nombre, apellidos, idCia, apodo) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, nombre);
            statement.setString(2, apellidos);
            statement.setString(3, idCia);
            statement.setString(4, apodo);
            statement.execute();
            
            resultado = statement.getUpdateCount() == 1;
            
            conexion.close();
            
        }catch(Exception ex){
            
            System.err.println("Ocurrio un error: " + ex.getMessage());
            
        }
        
        return resultado;

    }
    
    /**
     * Sirve para obtener los datos del alumno por su ID
     * @param id El ID del alumno que queremos buscar
     * @return Los datos del alumno a editar
     */
    public static Alumno obtenerPorId(int id){
        
        Alumno alumno = new Alumno();
        
        try{
        
            Connection conexion = Conexion.obtener();
            PreparedStatement statement = conexion.prepareStatement("SELECT id, nombre, apellidos, idCia, fechaNacimiento, apodo FROM alumno WHERE id = ?");
            statement.setInt(1, id);
            
            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next() == true){
                
                alumno.setId(resultSet.getInt(1));
                alumno.setNombre(resultSet.getString(2));
                alumno.setApellidos(resultSet.getString(3));
                alumno.setIdCia(resultSet.getString(4));
                alumno.setFechaNacimiento(resultSet.getDate(5));
                alumno.setApodo(resultSet.getString(6));
                
            }
            
            conexion.close();
            
        }catch(Exception ex){
            
            System.err.println("Ocurrio un error: " + ex.getMessage());
            
        }
        
        return alumno;
        
    }
    
    /**
     * Con este metodo se edita un registro de un alumno 
     * @param id El ID del alumno que se quiere editar
     * @param nombre El nombre del alumno a editar
     * @param apellidos Los apellidos del alumno a editar
     * @param idCia ID del alumno a editar
     * @param apodo El apodo del alumno que se va a editar
     * @return Un tipo boolean que nos dice si el registro se completo exitosamente o no
     */
    public boolean editar (int id, String nombre, String apellidos, String idCia, String apodo){
        
        boolean resultado = false;
        
        try{
            
            Connection conexion = Conexion.obtener();
            String consulta = "UPDATE alumno SET nombre = ?, apellidos = ?, idCia = ?, apodo = ? WHERE id = ?";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, nombre);
            statement.setString(2, apellidos);
            statement.setString(3, idCia);
            statement.setString(4, apodo);
            statement.setInt(5, id);
            statement.execute();
            
            resultado = statement.getUpdateCount() == 1;
            conexion.close();
            
        }catch(Exception ex){
            
            System.err.println("Ocurrio un error: " + ex.getMessage());
            
        }
        
        return resultado;
        
    }
    
    public void eliminar (int id){
        
        boolean resultado = false;
        
        String[] options = {"Si", "No"};
        int x = JOptionPane.showOptionDialog(null, "Esta seguro que desea eliminar el registro?", "Atencion", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        
        if(x == 0){

            try{

                Connection conexion = Conexion.obtener();
                String consulta = "DELETE FROM alumno WHERE id = ?";
                PreparedStatement statement = conexion.prepareStatement(consulta);
                statement.setInt(1, id);
                statement.execute();

                resultado = statement.getUpdateCount() == 1;
                conexion.close();

            }catch(Exception ex){

                System.err.println("Ocurrio un error: " + ex.getMessage());

            }
            
        }
        
        if(resultado){
            
            JOptionPane.showMessageDialog(null, "El registro se elimiino exitosamente", "Eliminado", JOptionPane.INFORMATION_MESSAGE);
            
        }else{
            
            JOptionPane.showMessageDialog(null, "El registro no se ha podido eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            
        }
        
    }
    
}
