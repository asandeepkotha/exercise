package com.hmrc.scart.scala

import org.junit._
import Assert._
import scala.io.Source
import org.skyscreamer.jsonassert.JSONAssert
import com.hmrc.scart.scala._
import spray.json._
import com.hmrc.scart.scala.CheckoutJsonProtocol._
import com.hmrc.scart._

/**
 * @author skotha
 *
 */
class ShoppingCartTest {

  private val reqPath : String = "com/hmrc/scart/payloads/request/"
  private val resPath : String = "com/hmrc/scart/payloads/checkout/response/"
  
  def toSKUObj(payload : String) : SKU = TestData.toSKUObject(payload)
  
  def mockCheckOut(checkOut : Checkout) : String = {
    assertNotNull("order Id cannot be null", checkOut.orderId) 
    new Checkout(checkOut.lineItems, "ord-id-1234", checkOut.basePrice, checkOut.youSave, checkOut.yourPrice).toJson.toString
  }
    
  @Test
  def testEmptyCart() =
  {
    val response : String =  TestData.getTestData(resPath, "emptyCart.json");
    
    val cart : Cart = ShoppingCart.apply
    val checkOut : Checkout = cart.checkOut

    JSONAssert.assertEquals(mockCheckOut(checkOut), response.toString(), true);
  } 
  
  
  
  @Test
  def testOnlyBasePrice() =
  {
    val request : String = TestData.getTestData(reqPath, "apple.json");
    val response : String =  TestData.getTestData(resPath, "basePrice.json");
    
    val cart : Cart = ShoppingCart.apply;
    val sku : SKU = toSKUObj(request)
    
    JSONAssert.assertEquals(mockCheckOut(cart.products(sku).products(sku).products(sku).checkOut), response.toString(), true);   
  }
  
  @Test
  def testWithPriceRule() =
  {
    val request : String = TestData.getTestData(reqPath, "orange.json");
    val response : String =  TestData.getTestData(resPath, "priceRule.json");
    
    val cart : Cart =  ShoppingCart.apply;
    val sku : SKU = toSKUObj(request)
 
    JSONAssert.assertEquals(mockCheckOut(cart.products(sku).products(sku).products(sku).checkOut), response.toString(), true);   
  }
  
  @Test
  def testPriceRuleWithComboSkus() =
  {
    val apple : String = TestData.getTestData(reqPath, "apple.json");
    val orange : String = TestData.getTestData(reqPath, "orange.json");
    val response : String =  TestData.getTestData(resPath, "priceRuleWithComboSkus.json");
    
    val cart : Cart = ShoppingCart.apply;
    
    val appleSKU : SKU = toSKUObj(apple)
    val orangeSKU : SKU = toSKUObj(orange)
    
   JSONAssert.assertEquals(mockCheckOut(cart.products(appleSKU).products(orangeSKU).products(orangeSKU).products(appleSKU).products(orangeSKU).
    products(appleSKU).products(orangeSKU).products(appleSKU).products(orangeSKU).checkOut), response.toString(), true);    
  }


}


