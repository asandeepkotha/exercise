/**
 *
 */
package com.hmrc.scart.scala

import scala.collection._
import spray.json._
import scala.math.BigDecimal
import java.util.UUID


/**
 * @author skotha
 *
 */
class ShoppingCart(val lineItems: Map[String, LineItem], val basePrice : BigDecimal, val youSave : BigDecimal, val yourPrice : BigDecimal)  extends Cart
{
  
  def products(sku: SKU): Cart = {
    
    val lineItemInCart : Option[LineItem] = if ((this.lineItems != null && this.lineItems.contains(sku.skuId))) 
      Some(this.lineItems.get(sku.skuId).get) else None
      
    val lineItem : LineItem  = lineItemInCart match {
      case Some(item) => ManageLineItem.updateQuantity(item, (item.quantity + sku.qty))
      case None => ManageLineItem.apply(sku)
    }
   
    val lineItemMap : Map[String, LineItem] = Map[String, LineItem]() ++ lineItems + (sku.skuId -> lineItem) 
    
    val _basePrice : BigDecimal = getDifferentiationValue(this.basePrice, lineItemInCart match {
      case Some(item) => item.basePrice
      case None => BigDecimal.apply(0)
    }, lineItem.basePrice)
    
    val _yourPrice : BigDecimal = getDifferentiationValue(this.yourPrice, lineItemInCart match {
      case Some(item) => item.basePrice
      case None => BigDecimal.apply(0)
    }, lineItem.yourPrice)
    
    val _youSave : BigDecimal = getDifferentiationValue(this.youSave, lineItemInCart match {
      case Some(item) => item.discountAmount
      case None => BigDecimal.apply(0)
    }, lineItem.discountAmount)
   
    new ShoppingCart(lineItemMap, _basePrice, _youSave,_yourPrice)       
  }
  
  def  getDifferentiationValue(cartValue : BigDecimal , lineItemValue : BigDecimal, toUpdate : BigDecimal) : BigDecimal = ((cartValue.-(lineItemValue)).+(toUpdate));
  
  def checkOut() : Checkout = new Checkout(this.lineItems.values.iterator.toList, UUID.randomUUID().toString(), this.basePrice, this.youSave, this.yourPrice) 

}

object ShoppingCart {
  
  def apply : ShoppingCart = { new ShoppingCart(Map[String, LineItem](), BigDecimal.apply(0),  BigDecimal.apply(0),  BigDecimal.apply(0))} 
  
}


