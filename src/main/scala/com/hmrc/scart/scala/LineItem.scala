/**
 *
 */
package com.hmrc.scart.scala


import spray.json._
import spray.json.DefaultJsonProtocol
import com.hmrc.scart.scala.SKUJsonProtocol._


/**
 * @author skotha
 *
 */
case class LineItem(val sku : SKU, val quantity : Int, val unitPrice : BigDecimal, 
    val basePrice:BigDecimal, val discountAmount : BigDecimal, val yourPrice :BigDecimal)


object ManageLineItem{
  
  def apply(sku : SKU) : LineItem = 
  {
    val quantity : Int = qanty(sku.qty); 
    val unitPrice : BigDecimal = sku.unitPrice;
    val basePrice : BigDecimal = unitPrice.*(quantity)
    val discount : BigDecimal = calculateDiscount(sku, quantity)

    new LineItem(sku, quantity, unitPrice, basePrice, discount, (basePrice - discount))      
  }
  
  def updateQuantity(lineItem: LineItem, qty : Int) : LineItem = {
    val quantity = ManageLineItem.qanty(qty) 
    val basePrice = (lineItem.unitPrice.*(quantity))
    val discount = ManageLineItem.calculateDiscount(lineItem.sku, quantity)
    new LineItem(lineItem.sku, quantity, lineItem.unitPrice, basePrice, discount, (basePrice.-(discount)))
    };
  
    def calculateDiscount(sku : SKU, quantity : Int) : BigDecimal = {        
     sku.directive match {
        case Some(directive) => ((quantity / directive.foreach).* (directive.absoluteDiscount)) 
        case None => BigDecimal.apply(0);
      }
    }
  
    def qanty(qty : Int) : Int = if(qty < 0) 0 else qty; 
}

object LineItemJsonProtocol extends DefaultJsonProtocol {
  implicit val LineItemFormat = jsonFormat(LineItem, "sku", "quantity", "unitPrice", "basePrice", "discountAmount", "yourPrice")
  
}

