package com.errors

import cats.data.Validated

package object catsLib {
  type ValidatedErr[T] = Validated[Errors, T]
}
