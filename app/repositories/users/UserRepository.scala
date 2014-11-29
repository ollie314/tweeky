package repositories.users

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


import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

import domain.user.User

/**
 *
 * @package repositories
 *
 * @author Mehdi Lefebvre <mehdi.lefebvre@gmail.com>
 * @version 0.0.1 
 * @since 11/29/14 8:41 PM
 *
 */
trait UserRepositoryComponent {
  val userRepository: UserRepository

  trait UserRepository {

    def createUser(user: User): User

    def updateUser(user: User)

    def tryFindById(id: Long): Option[User]

    def delete(id: Long)

  }

}

trait UserRepositoryComponentImpl extends UserRepositoryComponent {

  override val userRepository = new UserRepositoryImpl

  class UserRepositoryImpl extends UserRepository {

    val users = new ConcurrentHashMap[Long, User]
    val idSequence = new AtomicLong(0)

    override def createUser(user: User): User = {
      val newId = idSequence.incrementAndGet()
      val createdUser = user.copy(id = Option(newId))
      users.put(newId, createdUser)
      createdUser
    }

    override def updateUser(user: User) {
      users.put(user.id.get, user)
    }

    override def tryFindById(id: Long): Option[User] = {
      Option(users.get(id))
    }

    override def delete(id: Long) {
      users.remove(id)
    }

  }

}
