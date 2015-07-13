package com.hmrc.scart.scala


import spray.json.DefaultJsonProtocol

/**
 * @author skotha
 */



case class Directive(foreach: Int, 
    absoluteDiscount: BigDecimal)
  
case class SKU(skuId: String, skuName: String, qty: Int, unitPrice : BigDecimal,  currency: String, directive: Option[Directive])  

    
object SKUJsonProtocol extends DefaultJsonProtocol {
  implicit val directiveFormat = jsonFormat(Directive, "foreach", "absoluteDiscount")
  implicit val skuFormat = jsonFormat(SKU, "skuId", "skuName", "qty", "unitPrice", "currency", "directive")
  
}
