/**
 *
 */
package com.hmrc.scart.scala

/**
 * @author skotha
 *
 */
trait Cart {

  def products(sku: SKU): Cart

  def checkOut() : Checkout
}