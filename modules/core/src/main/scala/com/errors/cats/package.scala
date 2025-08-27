package com.errors

import _root_.cats.data.Validated

package object cats {
  type ValidatedErr[T] = Validated[Errors, T]
}
