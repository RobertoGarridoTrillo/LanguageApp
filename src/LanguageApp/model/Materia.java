package LanguageApp.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Roberto Garrido Trillo
 */
public class Materia implements Serializable
 {
  
  private int materia_id;
  private String usuario_nombre;
  private String materia_nombre;
  private String directorio;

  public Materia(int materia_id, String usuario_nombre, String materia_nombre, String directorio)
   {
    this.materia_id = materia_id;
    this.usuario_nombre = usuario_nombre;
    this.materia_nombre = materia_nombre;
    this.directorio = directorio;
   }


  public Materia()
   {
   }

  //<editor-fold defaultstate="collapsed" desc="Getters and Setters">

  public int getMateria_id()
   {
    return materia_id;
   }


  public void setMateria_id(int materia_id)
   {
    this.materia_id = materia_id;
   }


  public String getUsuario_nombre()
   {
    return usuario_nombre;
   }


  public void setUsuario_nombre(String usuario_nombre)
   {
    this.usuario_nombre = usuario_nombre;
   }


  public String getMateria_nombre()
   {
    return materia_nombre;
   }


  public void setMateria_nombre(String materia_nombre)
   {
    this.materia_nombre = materia_nombre;
   }


  public String getDirectorio()
   {
    return directorio;
   }


  public void setDirectorio(String directorio)
   {
    this.directorio = directorio;
   }  
  
  //</editor-fold>
  
  //<editor-fold defaultstate="collapsed" desc="HashCode and Equals">

  @Override
  public int hashCode()
   {
    int hash = 5;
    hash = 43 * hash + this.materia_id;
    hash = 43 * hash + Objects.hashCode(this.usuario_nombre);
    hash = 43 * hash + Objects.hashCode(this.materia_nombre);
    hash = 43 * hash + Objects.hashCode(this.directorio);
    return hash;
   }


  @Override
  public boolean equals(Object obj)
   {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    final Materia other = (Materia) obj;
    if (this.materia_id != other.materia_id) return false;
    if (!Objects.equals(this.usuario_nombre, other.usuario_nombre)) return false;
    if (!Objects.equals(this.materia_nombre, other.materia_nombre)) return false;
    if (!Objects.equals(this.directorio, other.directorio)) return false;
    return true;
   }

  //</editor-fold>
  
 }
