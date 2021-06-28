package base

import org.scalatest.concurrent.Eventually
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

trait IntSpecBase extends AnyFreeSpec with Matchers with Eventually
