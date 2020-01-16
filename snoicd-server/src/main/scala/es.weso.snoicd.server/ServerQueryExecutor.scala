package es.weso.snoicd.server

import java.util.stream.Stream

import es.weso.snoicd.core.{Concept, Query}
import es.weso.snoicd.search.Search
import org.springframework.http.{HttpStatus, ResponseEntity}

class ServerQueryExecutor(var query: Query, var searchEngine: Search) {

  def executeSearch(): ResponseEntity[Stream[Concept]] = {
    val concepts = searchEngine.executeQuery(query)

    concepts match {
      case Left(_) => new ResponseEntity[Stream[Concept]](HttpStatus.BAD_REQUEST)
      case Right(concepts) => new ResponseEntity[Stream[Concept]](concepts, HttpStatus.OK);
    }
  }
}