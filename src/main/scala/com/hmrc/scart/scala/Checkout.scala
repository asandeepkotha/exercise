package com.hmrc.scart.scala

import com.hmrc.scart.scala.LineItemJsonProtocol._
import spray.json.DefaultJsonProtocol

/**
 * @author skotha
 *
 */
case class Checkout(val lineItems: List[LineItem], val orderId : String, val basePrice : BigDecimal, val youSave : BigDecimal, val yourPrice : BigDecimal) 

object CheckoutJsonProtocol extends DefaultJsonProtocol {
  implicit val CheckoutFormat = jsonFormat(Checkout, "lineItems", "orderId", "basePrice", "youSave", "yourPrice")
  
}