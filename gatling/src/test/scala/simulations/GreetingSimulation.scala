package simulations

import com.example.HelloWorld.Greeting
import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._
import simulations.CirceOps._


class GreetingSimulation extends Simulation {


  val httpConf = http.baseUrl("http://localhost:8080")

  val sc = scenario("Hello").exec(
    http("GET /hello/myName")
      .get("/hello/myName")
      //パースしてGreetingのインスタンスと比較
      .check(
        bodyString.transformOption(parseRequestBody[Greeting](_)).is((Greeting("Hello, myName")))
      )
  )

  setUp(sc.inject(atOnceUsers(1)).protocols(httpConf))
}
