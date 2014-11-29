package controllers.users
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

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import services.user.UserServiceComponent
import domain.user.User

/**
 *
 * @param password
 * @param email
 */
case class UserResource(password: String, email: String)

/**
 *
 */
trait UserController extends Controller {
  self: UserServiceComponent =>

  implicit val userReads = ((__ \ "password").read[String] and
    (__ \ "email").read[String])(UserResource)

  implicit val userWrites = new Writes[User] {
    override def writes(user: User): JsValue = {
      Json.obj(
        "id" -> user.id,
        "password" -> user.password,
        "email" -> user.email
      )
    }
  }

  def createUser = Action(parse.json) { request =>
    unmarshallUserResource(request, (resource: UserResource) => {
      val user = User(Option.empty, resource.password, resource.email)
      userService.createUser(user)
      Created
    })
  }

  def updateUser(id: Long) = Action(parse.json) { request =>
    unmarshallUserResource(request, (resource: UserResource) => {
      val user = User(Option(id), resource.password, resource.email)
      userService.updateUser(user)
      NoContent
    })
  }

  def findUserById(id: Long) = Action {
    val user = userService.tryFindById(id)
    if (user.isDefined) {
      Ok(Json.toJson(user))
    } else {
      NotFound
    }
  }

  def deleteUser(id: Long) = Action {
    userService.delete(id)
    NoContent
  }

  private def unmarshallUserResource(request: Request[JsValue],
                                    block: (UserResource) => Result): Result = {
    request.body.validate[UserResource].fold(
      valid = block,
      invalid = (e => {
        val error = e.mkString
        Logger.error(error)
        BadRequest(error)
      })
    )
  }
}