package es.weso.snoicd.core.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringUtilsTest {

    @Test
    public void testNormaalizeDash() {
        String s = "hola-caracola";
        assertEquals("hola caracola", StringUtilsKt.normalize(s));
    }

    @Test
    public void testNormaalizeLeftBar() {
        String s = "hola/caracola";
        assertEquals("hola caracola", StringUtilsKt.normalize(s));
    }

    @Test
    public void testNormaalizePoint() {
        String s = "hola. caracola";
        assertEquals("hola caracola", StringUtilsKt.normalize(s));
    }

    @Test
    public void testNormaalizeComma() {
        String s = "hola, caracola";
        assertEquals("hola caracola", StringUtilsKt.normalize(s));
    }

    @Test
    public void testNormaalizeParenthesis() {
        String s = "hola (caracola)";
        assertEquals("hola caracola", StringUtilsKt.normalize(s));
    }

    @Test
    public void testNormaalizeSquareBraquets() {
        String s = "hola [caracola]";
        assertEquals("hola caracola", StringUtilsKt.normalize(s));
    }

    @Test
    public void testNormaalizeNonAsciiCharacters() {
        String s = "hola caràcólâ";
        assertEquals("hola caracola", StringUtilsKt.normalize(s));
    }
}
