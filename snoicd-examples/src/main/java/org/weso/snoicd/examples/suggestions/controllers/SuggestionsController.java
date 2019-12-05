package org.weso.snoicd.examples.suggestions.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.weso.snoicd.core.Concept;
import org.weso.snoicd.examples.suggestions.StartUp;
import org.weso.snoicd.search.core.AbstractSearchStrategy;
import org.weso.snoicd.search.core.AllFieldsSearchStrategy;
import org.weso.snoicd.search.persistence.BigTablePersistenceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SuggestionsController {

    @RequestMapping(value = "suggestions", method = RequestMethod.GET)
    public @ResponseBody
    List<String> getSuggestions(HttpServletRequest request) {

        List<String> suggestions = new ArrayList<>();

        AbstractSearchStrategy search = new AllFieldsSearchStrategy(BigTablePersistenceImpl.instance);
        search.setQuery(request.getParameter("term"));
        search.run();
        for (Concept c : search.getResult()) {
            suggestions.add(c.getDescriptions().get(0));
        }

        return suggestions;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndexPage() {
        return "index";
    }
}
