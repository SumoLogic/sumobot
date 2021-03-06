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

import akka.http.scaladsl.model.HttpMethods.{GET, POST}
import akka.http.scaladsl.model.{HttpRequest, Uri}
import com.sumologic.sumobot.test.annotated.SumoBotSpec
import com.typesafe.config.ConfigFactory

class NoAuthenticationTest extends SumoBotSpec {
  val emptyRequest = HttpRequest()
  val rootRequest = HttpRequest(GET, Uri("/"))
  val postRequest = HttpRequest(POST, Uri("/endpoint"))

  val noAuthentication = new NoAuthentication(ConfigFactory.empty())

  "NoAuthentication" should {
    "allow all requests" in {
      noAuthentication.authentication(emptyRequest) shouldBe a[AuthenticationSucceeded]
      noAuthentication.authentication(rootRequest) shouldBe a[AuthenticationSucceeded]
      noAuthentication.authentication(postRequest) shouldBe a[AuthenticationSucceeded]
    }
  }
}
