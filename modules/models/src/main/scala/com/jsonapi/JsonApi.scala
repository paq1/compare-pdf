package com.jsonapi

object JsonApi {

  final case class Single[Attributes](
      data: Data[Attributes]
  )

  final case class Data[Attributes](
      `type`: String,
      id: String,
      attributes: Attributes
  )

}
