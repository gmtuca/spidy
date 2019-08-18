package com.spidy.service

import spock.lang.Specification

class LinkNormalizerTest extends Specification {

    def "Link normalizer"(String link, String normalized) {
        expect:
            new LinkNormalizerImpl().invoke(link) == normalized

        where:
            link                                        | normalized
            "#"                                         | "/"
            "/"                                         | "/"
            "../test"                                   | "/../test"
            "./test"                                    | "/./test"
            "/test"                                     | "/test"
            "test.html"                                 | "/test.html"
            "/test?hello=world"                         | "/test?hello=world"
            "/image.jpg"                                | "/image.jpg"
            "https://www.example.com?hello=world"       | "?hello=world"
            "https://www.example.com/test"              | "/test"
            "https://www.example.com/test?hello=world"  | "/test?hello=world"
            "www.example.com/test"                      | "/test"
            "www.example.com"                           | "/"
            "www.example.com"                           | "/"
            "www.example.com"                           | "/"
            "http://example.com"                        | "/"
            "http://www.example.com"                    | "/"
            "https://www.example.com"                   | "/"
    }

}
