package com.jsonapi

import play.api.libs.json.{JsValue, Json, Reads, Writes}

object JsonApi {

  final case class Single[Attributes](
      data: Data[Attributes]
  )
  object Single {
    implicit def wSchema[Attibutes](implicit
        ws: Writes[Attibutes]
    ): Writes[Single[Attibutes]] = { obj: Single[Attibutes] =>
      Json.obj("data" -> Json.toJson(obj.data))
    }

    implicit def rSchema[Attributes](implicit
        ws: Reads[Attributes]
    ): Reads[Single[Attributes]] = { js: JsValue =>
      for {
        data <- (js \ "data").validate[Data[Attributes]]
      } yield {
        Single(data)
      }
    }
  }

  final case class Data[Attributes](
      `type`: String,
      id: String,
      attributes: Attributes
  )
  object Data {
    implicit def wSchema[Attributes](implicit
        ws: Writes[Attributes]
    ): Writes[Data[Attributes]] = { obj: Data[Attributes] =>
      Json.obj(
        "type" -> obj.`type`,
        "id" -> obj.id,
        "attributes" -> Json.toJson(obj.attributes)
      )
    }

    implicit def rSchema[Attributes](implicit
        ws: Reads[Attributes]
    ): Reads[Data[Attributes]] = { js: JsValue =>
      for {
        dataType <- (js \ "type").validate[String]
        id <- (js \ "id").validate[String]
        attributes <- (js \ "attributes").validate[Attributes]
      } yield {
        Data(dataType, id, attributes)
      }
    }
  }

}
