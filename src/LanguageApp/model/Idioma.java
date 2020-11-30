/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LanguageApp.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Roberto Garrido Trillo
 */
public class Idioma implements Serializable
 {
  private int idioma_id;
  private String materia_nombre;
  private String idioma_nombre;
  private String escribir;
  private String traducir;


  public Idioma(int idioma_id, String materia_nombre, String idioma_nombre, String escribir, String traducir)
   {
    this.idioma_id = idioma_id;
    this.materia_nombre = materia_nombre;
    this.idioma_nombre = idioma_nombre;
    this.escribir = escribir;
    this.traducir = traducir;
   }


  public Idioma()
   {
   }

  //<editor-fold defaultstate="collapsed" desc="Getters and Setters">

  public int getIdioma_id()
   {
    return idioma_id;
   }


  public void setIdioma_id(int idioma_id)
   {
    this.idioma_id = idioma_id;
   }


  public String getMateria_nombre()
   {
    return materia_nombre;
   }


  public void setMateria_nombre(String materia_nombre)
   {
    this.materia_nombre = materia_nombre;
   }


  public String getIdioma_nombre()
   {
    return idioma_nombre;
   }


  public void setIdioma_nombre(String idioma_nombre)
   {
    this.idioma_nombre = idioma_nombre;
   }


  public String getEscribir()
   {
    return escribir;
   }


  public void setEscribir(String escribir)
   {
    this.escribir = escribir;
   }


  public String getTraducir()
   {
    return traducir;
   }


  public void setTraducir(String traducir)
   {
    this.traducir = traducir;
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="HashCode and Equals">

  @Override
  public int hashCode()
   {
    int hash = 7;
    hash = 19 * hash + this.idioma_id;
    hash = 19 * hash + Objects.hashCode(this.materia_nombre);
    hash = 19 * hash + Objects.hashCode(this.idioma_nombre);
    hash = 19 * hash + Objects.hashCode(this.escribir);
    hash = 19 * hash + Objects.hashCode(this.traducir);
    return hash;
   }


  @Override
  public boolean equals(Object obj)
   {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    final Idioma other = (Idioma) obj;
    if (this.idioma_id != other.idioma_id) return false;
    if (!Objects.equals(this.materia_nombre, other.materia_nombre)) return false;
    if (!Objects.equals(this.idioma_nombre, other.idioma_nombre)) return false;
    if (!Objects.equals(this.escribir, other.escribir)) return false;
    if (!Objects.equals(this.traducir, other.traducir)) return false;
    return true;
   }
  
  //</editor-fold>  
 }
