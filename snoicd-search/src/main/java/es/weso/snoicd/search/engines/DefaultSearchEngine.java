package es.weso.snoicd.search.engines;

import es.weso.snoicd.core.Concept;
import es.weso.snoicd.core.Query;
import es.weso.snoicd.search.Search;
import es.weso.snoicd.search.persistence.Persistence;
import es.weso.snoicd.search.persistence.impl.InMemmoryPersistence;
import scala.util.Either;
import scala.util.Right;

import java.util.stream.Stream;

public class DefaultSearchEngine implements Search {

    private Persistence persistence = new InMemmoryPersistence();


    @Override
    public Either<Exception, Stream<Concept>> executeQuery(Query query) {
        return new Right<>(persistence.findByConceptCode(query.getQueryString()));
    }
}
