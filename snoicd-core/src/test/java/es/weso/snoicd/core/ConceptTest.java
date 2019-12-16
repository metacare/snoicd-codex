package es.weso.snoicd.core;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class ConceptTest {

    @Test
    public void automatedTest() {
        assertPojoMethodsFor(Concept.class)
                .testing(
                        Method.GETTER,
                        Method.SETTER,
                        Method.EQUALS,
                        Method.HASH_CODE,
                        Method.TO_STRING,
                        Method.CONSTRUCTOR
                ).areWellImplemented();
    }
}
