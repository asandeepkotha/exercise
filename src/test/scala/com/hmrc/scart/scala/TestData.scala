/**
 *
 */
package com.hmrc.scart.scala

import scala.io.Source
import spray.json._
import com.hmrc.scart._
import com.hmrc.scart.scala.SKUJsonProtocol._

/**
 * @author skotha
 *
 */
object TestData {

  
  def getTestData(relPath : String, file :String)  =
  {
    val source = Source.fromFile(getClass.getClassLoader().getResource(relPath).getPath()+file);
    
    val testData :String = source.getLines.mkString    
    source.close()
    testData  
  }
  
  def toSKUObject(payload : String) : SKU = payload.parseJson.convertTo[SKU]
  
}