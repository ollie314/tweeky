/**
 * Tweeky nlp program
 *
 * Copyright (C) 2014  Nehdi Lefebvre
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 * @see http://opensource.org/licenses/GPL-3.0
 * @author Mehdi Lefebvre <mehdi.lefebvre@gmail.com>
 * @version 0.01
 */
//import play.PlayScala
name := """playsance"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws
)

val appDependencies = Seq(
  // Application
  "mysql" % "mysql-connector-java" % "5.1.34"
)
