package org.thp.thehive.controllers.v1

import scala.language.implicitConversions

import io.scalaland.chimney.dsl._
import org.thp.scalligraph.controllers.Output
import org.thp.scalligraph.models.Entity
import org.thp.thehive.dto.v1.{InputCustomFieldValue, OutputCustomField, OutputCustomFieldValue}
import org.thp.thehive.models.{CustomField, RichCustomField}

object CustomFieldConversion {

  def fromInputCustomField(inputCustomFieldValue: InputCustomFieldValue): (String, Option[Any]) =
    inputCustomFieldValue.name -> inputCustomFieldValue.value

  implicit def toOutputCustomField(customFieldValue: RichCustomField): Output[OutputCustomFieldValue] =
    Output[OutputCustomFieldValue](
      customFieldValue
        .into[OutputCustomFieldValue]
        .withFieldComputed(_.value, _.value.map(_.toString))
        .withFieldComputed(_.tpe, _.typeName)
        .transform
    )

  implicit def toOutputCustomField(customField: CustomField with Entity): Output[OutputCustomField] =
    Output[OutputCustomField](
      customField
        .asInstanceOf[CustomField]
        .into[OutputCustomField]
        .withFieldComputed(_.`type`, _.`type`.toString)
        .withFieldComputed(_.mandatory, _.mandatory)
        .transform
    )
}
