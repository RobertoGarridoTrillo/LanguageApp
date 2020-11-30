package LanguageApp.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Roberto Garrido Trillo
 */

public class Usuario implements Serializable
 {

  private int usuario_id;
  private String usuario_nombre;
  private String password;
  private boolean usuario_activo;
  private String pregunta;
  private String respuesta;


  public Usuario(int usuario_id, String usuario_nombre, String password, boolean usuario_activo,
          String pregunta, String respuesta)
   {
    this.usuario_id = usuario_id;
    this.usuario_nombre = usuario_nombre;
    this.password = password;
    this.usuario_activo = usuario_activo;
    this.pregunta = pregunta;
    this.respuesta = respuesta;
   }


  public Usuario()
   {
   }

  //<editor-fold defaultstate="collapsed" desc="Getters and Setters">

  public int getUsuario_id()
   {
    return usuario_id;
   }


  public void setUsuario_id(int usuario_id)
   {
    this.usuario_id = usuario_id;
   }


  public String getUsuario_nombre()
   {
    return usuario_nombre;
   }


  public void setUsuario_nombre(String usuario_nombre)
   {
    this.usuario_nombre = usuario_nombre;
   }


  public String getPassword()
   {
    return password;
   }


  public void setPassword(String password)
   {
    this.password = password;
   }


  public boolean getUsuario_activo()
   {
    return usuario_activo;
   }


  public void setUsuario_activo(boolean usuario_activo)
   {
    this.usuario_activo = usuario_activo;
   }


  public String getPregunta()
   {
    return pregunta;
   }


  public void setPregunta(String pregunta)
   {
    this.pregunta = pregunta;
   }


  public String getRespuesta()
   {
    return respuesta;
   }


  public void setRespuesta(String respuesta)
   {
    this.respuesta = respuesta;
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="HashCode and Equals">

  @Override
  public int hashCode()
   {
    int hash = 7;
    hash = 29 * hash + this.usuario_id;
    return hash;
   }


  @Override
  public boolean equals(Object obj)
   {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    final Usuario other = (Usuario) obj;
    if (this.usuario_id != other.usuario_id) return false;
    return true;
   }

 }

//</editor-fold>

