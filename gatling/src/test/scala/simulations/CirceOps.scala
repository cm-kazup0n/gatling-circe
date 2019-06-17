package simulations

import cats.arrow.FunctionK
import cats.data.Kleisli
import cats.implicits._
import io.circe.Decoder
import io.circe.parser.{parse => parseJson}
import io.gatling.commons.validation.{Failure, Success, Validation}


object CirceOps {

  type ParseResult[A] = Either[Throwable, A]
  type RequestBodyType = Option[String]

  //Either[String, A] => Validation[A]の変換
  private val parseResultToValidation = new FunctionK[ParseResult, Validation] {
    override def apply[A](fa: ParseResult[A]): Validation[A] = fa.fold(l => Failure(l.getMessage), r => Success(r))
  }

  //パース(Option[String] => Either[Throwable, Option[A]])
  private def parse[T: Decoder]: Kleisli[ParseResult, RequestBodyType, Option[T]] = Kleisli[ParseResult, RequestBodyType, Option[T]] { (a: RequestBodyType) =>
    a match {
      case Some(s) => parseJson(s).flatMap(_.as[T].map(_.some))
      case None => none[T].asRight
    }
  }


  /**
    * リクエストボディをAとしてパースする
    * @param bodyString
    * @tparam A
    * @return
    */
  def parseRequestBody[A: Decoder](bodyString: RequestBodyType): Validation[Option[A]] = parse.mapK(parseResultToValidation).run(bodyString)


}
