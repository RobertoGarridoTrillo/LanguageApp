package LanguageApp.model;

import java.util.Objects;

/**
 *
 * @author Roberto Garrido Trillo
 */
public class Item {

   private int id;
   private double start;
   private double end;
   private String text;

   public Item(double start, double end, String text) {
      this.start = start;
      this.end = end;
      this.text = text;
   }


   public Item() {
   }


   public int getId() {
      return id;
   }


   public void setId(int id) {
      this.id = id;
   }


   public double getStart() {
      return start;
   }


   public void setStart(double start) {
      this.start = start;
   }


   public double getEnd() {
      return end;
   }


   public void setEnd(double end) {
      this.end = end;
   }


   public String getText() {
      return text;
   }


   public void setText(String text) {
      this.text = text;
   }

   @Override
   public String toString() {
      return "Item{" + "id=" + id + ", start=" + start + ", end=" + end +
              ", text=" + text + '}';
   }

   @Override
   public int hashCode() {
      int hash = 5;
      hash = 17 * hash + this.id;
      hash =
              17 * hash +
              (int) (Double.doubleToLongBits(this.start) ^
              (Double.doubleToLongBits(this.start) >>> 32));
      hash =
              17 * hash +
              (int) (Double.doubleToLongBits(this.end) ^
              (Double.doubleToLongBits(this.end) >>> 32));
      hash = 17 * hash + Objects.hashCode(this.text);
      return hash;
   }


   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      }
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      final Item other = (Item) obj;
      if (this.id != other.id) {
         return false;
      }
      if (Double.doubleToLongBits(this.start) !=
              Double.doubleToLongBits(other.start)) {
         return false;
      }
      if (Double.doubleToLongBits(this.end) != Double.doubleToLongBits(other.end)) {
         return false;
      }
      if (!Objects.equals(this.text, other.text)) {
         return false;
      }
      return true;
   }


}

