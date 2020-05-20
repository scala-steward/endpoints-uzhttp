package sample

import java.net.InetSocketAddress

import uzhttp.server.Server
import zio.{ App, ZIO }

object Main extends App {
  override def run(args: List[String]): ZIO[zio.ZEnv, Nothing, Int] =
    Server
      .builder(new InetSocketAddress("127.0.0.1", 8080))
      .handleSome(
        Api.handler
      )
      .serve
      .useForever
      .orDie
}
