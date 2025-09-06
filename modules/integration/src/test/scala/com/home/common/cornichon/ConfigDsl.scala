package com.home.common.cornichon

import com.typesafe.config.{Config, ConfigFactory}

trait ConfigDsl {
  lazy val _config: Config = ConfigFactory.load()
}
