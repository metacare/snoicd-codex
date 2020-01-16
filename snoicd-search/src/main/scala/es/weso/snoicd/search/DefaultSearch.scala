package es.weso.snoicd.search

import java.util

import es.weso.snoicd.core.utils.StringUtilsKt.normalize
import es.weso.snoicd.core.{Concept, Query}
import es.weso.snoicd.search.persistence.Persistence

import scala.collection.JavaConverters._

class DefaultSearch(val persistence: Persistence) extends AbstractSearch(persistence) {

  override def executeQuery(query: Query): Query = {

    val QUERY_DESCRIPTION_SPLIT_REGEX: String = " " // WS.

    val a: Stream[Concept] = persistence.findByConceptCode(query.getQueryString()).iterator().asScala.toStream;

    val descriptionWords: Array[String] = query.getQueryString.split(QUERY_DESCRIPTION_SPLIT_REGEX)
    util.Arrays.stream(descriptionWords).map((word: String) => normalize(word))

    val b: Stream[Concept] = persistence.findByConceptDescription(descriptionWords).iterator().asScala.toStream

    query.setResult(
      new Right(
        a.zip(b)
          .flatMap { case(a, b) => Stream(a,b) }
      )
    )

    query
  }
}
