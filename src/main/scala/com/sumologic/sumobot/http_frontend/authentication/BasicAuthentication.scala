/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.sumologic.sumobot.http_frontend.authentication
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}

class BasicAuthentication(username: String, password: String) extends HttpAuthentication {
  override def routes: PartialFunction[HttpRequest, HttpResponse] = PartialFunction.empty

  override def authentication(request: HttpRequest): AuthenticationResult = {
    request.header[`Authorization`] match {
      case Some(header) =>
        val receivedCredentials = BasicHttpCredentials(header.credentials.token())

        val authenticated = receivedCredentials.username == username && receivedCredentials.password == password
        if (authenticated) AuthenticationSucceeded(AuthenticationInfo(Some(s"Logged in as: $username"), Seq.empty))
        else AuthenticationForbidden(HttpResponse(403))
      case None =>
        val header = `WWW-Authenticate`(List(HttpChallenge("basic", Some("SumoBot"))))
        AuthenticationForbidden(HttpResponse(401, headers = List(header)))
    }
  }
}
