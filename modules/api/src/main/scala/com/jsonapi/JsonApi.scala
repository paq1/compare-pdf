package com.jsonapi

import play.api.libs.json.{Json, Writes}

object JsonApi {

  final case class Single[Attributes](
      data: Data[Attributes]
  )
  object Single {
    implicit def wSchema[Attibutes](implicit ws: Writes[Attibutes]): Writes[Single[Attibutes]] = { obj: Single[Attibutes] =>
      Json.obj("data" -> obj.data)
    }
  }

  final case class Data[Attributes](
      `type`: String,
      id: String,
      attributes: Attributes
  )
  object Data {

    implicit def wSchema[Attributes](implicit ws: Writes[Attributes]): Writes[Data[Attributes]] = { obj: Data[Attributes] =>
      Json.obj(
        "type" -> obj.`type`,
        "id" -> obj.id,
        "attributes" -> obj.attributes
      )
    }

  }

}
