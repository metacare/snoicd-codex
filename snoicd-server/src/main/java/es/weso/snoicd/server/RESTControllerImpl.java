package es.weso.snoicd.server;

import es.weso.snoicd.core.Concept;
import es.weso.snoicd.core.Query;
import es.weso.snoicd.core.impl.DefaultQuery;
import es.weso.snoicd.search.Search;
import es.weso.snoicd.search.engines.DefaultSearchEngine;
import es.weso.snoicd.search.persistence.impl.InMemmoryPersistence;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
public class RESTControllerImpl implements RESTControllerOperations {

    private Search searchEngine = new DefaultSearchEngine();

    @Override
    @GetMapping("/search")
    public DeferredResult<ResponseEntity<Stream<Concept>>> search
            (@NotNull @RequestParam(name = "q") String query)
    {
        DeferredResult<ResponseEntity<Stream<Concept>>> response = new DeferredResult<>();
        Query q = new DefaultQuery(query);

        response.setResult(new ServerQueryExecutor(q, searchEngine).executeSearch());

        return response;
    }
}
