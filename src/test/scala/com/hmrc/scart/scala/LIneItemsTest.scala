/**
 *
 */
package com.hmrc.scart.scala

import org.junit._
import Assert._
import scala.io.Source
import org.skyscreamer.jsonassert.JSONAssert
import com.hmrc.scart.scala._
import spray.json.JsonParser
import spray.json._
import com.hmrc.scart._
import com.hmrc.scart.scala.SKUJsonProtocol._
import com.hmrc.scart.scala.LineItemJsonProtocol._
/**
 * @author skotha
 *
 */
class LIneItemsTest {

  val reqPath : String = "com/hmrc/scart/payloads/request/"
  val resPath : String = "com/hmrc/scart/payloads/lineitem/response/"
  def toLineItem(payload : String) : LineItem = ManageLineItem.apply(TestData.toSKUObject(payload))
  
  @Test
  def testLineItemBasePrice() =
  {
     val request : String  = TestData.getTestData(reqPath, "apple.json");
     val response : String  =  TestData.getTestData(resPath, "basePrice.json");
     
     JSONAssert.assertEquals(toLineItem(request).toJson.toString, response, true);    
  }
  
  @Test
  def testLineItemBasePriceByUpdatingSKUQuantity() =
  {
    val request : String = TestData.getTestData(reqPath, "orange.json");
    val response : String =  TestData.getTestData(resPath, "addsku.json");
    
    JSONAssert.assertEquals(ManageLineItem.updateQuantity(toLineItem(request), 2).toJson.toString, response, true);    
  }
  
  @Test
  def testLineItemPriceToHandleNegativeQty() =
  {
     val request : String = TestData.getTestData(reqPath, "orange.json");
     val response : String =  TestData.getTestData(resPath, "negativeqty.json");
     
     JSONAssert.assertEquals(ManageLineItem.updateQuantity(toLineItem(request), -1).toJson.toString, response, true);   
  }
  
  @Test
  def testWithPriceRule() =
  {
    val request : String = TestData.getTestData(reqPath, "orange.json");
    val response : String =  TestData.getTestData(resPath, "priceRule.json");
    
    JSONAssert.assertEquals(ManageLineItem.updateQuantity(toLineItem(request), 5).toJson.toString, response, true);    
  }
  
}