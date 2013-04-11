package org.json4s
package macro_readers

import org.json4s.MappingException

/**
 * @author Bryce Anderson
 * Created on 3/23/13 at 12:39 PM
 */

sealed trait JsonReader {
  def failStructure(msg: String, thrower: JsonReader = null) = {
    val fullMessage = if(thrower != null) (msg.concat(" Thrower: " + thrower.getClass.toString())) else msg
    throw new MappingException(fullMessage, null)
  }
}

trait JsonObjectReader extends JsonReader {
  def getKeys: Set[String]

  def hasKey(str: String) = getKeys.exists( _ == str)

  // Option forms
  def optObjectReader(key: String): Option[JsonObjectReader]
  def optArrayReader(key: String): Option[JsonArrayIterator]
  def optInt(key: String): Option[Int]
  def optLong(key: String): Option[Long]
  def optFloat(key: String): Option[Float]
  def optDouble(key: String): Option[Double]
  def optBigInt(key: String): Option[BigInt]
  def optBigDecimal(key: String): Option[BigDecimal]
  def optBool(key: String): Option[Boolean]
  def optString(key: String): Option[String]

  // Direct forms with default impl based on the Option form
  def getObjectReader(key: String): JsonObjectReader =
    optObjectReader(key).getOrElse(failStructure(s"JsonObject doesn't contain an object in field '$key'"))
  def getArrayReader(key: String): JsonArrayIterator =
    optArrayReader(key).getOrElse(failStructure(s"JsonObject doesn't contain an array in field '$key'"))
  def getInt(key: String): Int =
    optInt(key).getOrElse(failStructure(s"JsonObject doesn't contain an Int in field '$key'"))
  def getLong(key: String): Long =
    optLong(key).getOrElse(failStructure(s"JsonObject doesn't contain an Long in field '$key'"))
  def getFloat(key: String): Float =
    optFloat(key).getOrElse(failStructure(s"JsonObject doesn't contain a Float in field '$key'"))
  def getDouble(key: String): Double =
    optDouble(key).getOrElse(failStructure(s"JsonObject doesn't contain a Double in field '$key'"))
  def getBigInt(key: String): BigInt =
    optBigInt(key).getOrElse(failStructure(s"JsonObject doesn't contain a BigInt in field '$key'"))
  def getBigDecimal(key: String): BigDecimal =
    optBigDecimal(key).getOrElse(failStructure(s"JsonObject doesn't contain a BigDecimal in field '$key'"))
  def getBool(key: String): Boolean =
    optBool(key).getOrElse(failStructure(s"JsonObject doesn't contain a Boolean in field '$key'"))
  def getString(key: String): String =
    optString(key).getOrElse(failStructure(s"JsonObject doesn't contain a string in field '$key'"))
}

trait JsonArrayIterator extends JsonReader {

  // Option forms
  def getNextObjectReader: Option[JsonObjectReader]
  def getNextArrayReader: Option[JsonArrayIterator]
  def getNextInt: Option[Int]
  def getNextLong: Option[Long]
  def getNextFloat: Option[Float]
  def getNextDouble: Option[Double]
  def getNextBigInt: Option[BigInt]
  def getNextBigDecimal: Option[BigDecimal]
  def getNextBool: Option[Boolean]
  def getNextString: Option[String]

  // Direct forms with default impl based on the option forms
  def hasNext: Boolean
  def nextObjectReader: JsonObjectReader =
    getNextObjectReader.getOrElse(failStructure("JsonArray next value is not of type 'object'"))
  def nextArrayReader: JsonArrayIterator =
    getNextArrayReader.getOrElse(failStructure("JsonArray next value is not of type 'array'"))
  def nextInt: Int =
    getNextInt.getOrElse(failStructure("JsonArray next value is not of type 'Int'"))
  def nextLong: Long =
    getNextLong.getOrElse(failStructure("JsonArray next value is not of type 'Long'"))
  def nextFloat: Float =
    getNextFloat.getOrElse(failStructure("JsonArray next value is not of type 'Float'"))
  def nextDouble: Double =
    getNextDouble.getOrElse(failStructure("JsonArray next value is not of type 'Double'"))
  def nextBigInt: BigInt =
    getNextBigInt.getOrElse(failStructure("JsonArray next value is not of type 'BigInt'"))
  def nextBigDecimal: BigDecimal =
    getNextBigDecimal.getOrElse(failStructure("JsonArray next value is not of type 'BigDecimal'"))
  def nextBool: Boolean =
    getNextBool.getOrElse(failStructure("JsonArray next value is not of type 'Boolean'"))
  def nextString: String =
    getNextString.getOrElse(failStructure("JsonArray next value is not of type 'String'"))
}
