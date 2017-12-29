package utils

import java.math.BigInteger
import java.security.SecureRandom

class RandomUtil {

  private val random = new SecureRandom()

  def getToken(nrChars: Int = 24): String = {
    new BigInteger(nrChars * 5, random).toString(32)
  }
}
