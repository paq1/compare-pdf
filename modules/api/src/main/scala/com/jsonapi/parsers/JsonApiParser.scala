package com.jsonapi.parsers

import com.jsonapi.JsonApi.{Data, Single}
import play.api.libs.json.{Json, Writes}

object JsonApiParser {

  object Implicits {

    implicit def wSchemaData[Attributes](implicit
        ws: Writes[Attributes]
    ): Writes[Data[Attributes]] = { obj: Data[Attributes] =>
      Json.obj(
        "type" -> obj.`type`,
        "id" -> obj.id,
        "attributes" -> Json.toJson(obj.attributes)
      )
    }

    implicit def wSchemaSingle[Attibutes](implicit
        ws: Writes[Attibutes]
    ): Writes[Single[Attibutes]] = { obj: Single[Attibutes] =>
      Json.obj("data" -> Json.toJson(obj.data))
    }

  }

}
