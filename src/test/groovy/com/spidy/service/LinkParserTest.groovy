package com.spidy.service

import spock.lang.Specification

class LinkParserTest extends Specification {

    def "Link parser"(String html, List<String> links) {
        expect:
            new LinkParserImpl().parse(html) == links

        where:
            html                                              | links
            ""                                                | []
            "test"                                            | []
            new String([1, 2, 3] as byte[])                   | []
            "<html></html>"                                   | []
            "<html><a href='/test'>Test</a></html>"           | ["/test"]
            "<html><a href='http://test.com'>Test</a></html>" | ["http://test.com"]
            "<html>" +
                    "<a href='/test1'>Test 1</a>" +
                    "<a href='/test2'>Test 2</a>" +
            "</html>"                                         | ["/test1", "/test2"]
            "<html>" +
                "<p><a href='/test1'>Test 1</a></p>" +
                "<b><a href='/test2'>Test 2</a></b>" +
                "<i><a href='/test3'>Test 3</a></i>" +
            "</html>"                                         | ["/test1", "/test2", "/test3"]
    }

}
