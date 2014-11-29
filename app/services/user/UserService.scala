package services.user
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

import domain.user.User
import repositories.users.UserRepositoryComponent

/**
 *
 * @package services.user
 *
 * @author Mehdi Lefebvre <mehdi.lefebvre@gmail.com>
 * @version 0.0.1 
 * @since 11/29/14 9:04 PM
 *
 */

trait UserServiceComponent {

  val userService: UserService

  trait UserService {

    def createUser(user: User): User

    def updateUser(user: User)

    def tryFindById(id: Long): Option[User]

    def delete(id: Long)

  }

}

trait UserServiceComponentImpl extends UserServiceComponent {
  self: UserRepositoryComponent =>

  override val userService = new UserServiceImpl

  class UserServiceImpl extends UserService {

    override def createUser(user: User): User = {
      userRepository.createUser(user)
    }

    override def updateUser(user: User) {
      userRepository.updateUser(user)
    }

    override def tryFindById(id: Long): Option[User] = {
      userRepository.tryFindById(id)
    }

    override def delete(id: Long) {
      userRepository.delete(id)
    }

  }

}