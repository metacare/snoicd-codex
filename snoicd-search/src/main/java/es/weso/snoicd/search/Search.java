package es.weso.snoicd.search;

import es.weso.snoicd.core.Concept;
import es.weso.snoicd.core.Query;
import scala.util.Either;

import java.util.stream.Stream;

public interface Search {

    Either<Exception, Stream<Concept>> executeQuery(Query query);
}
