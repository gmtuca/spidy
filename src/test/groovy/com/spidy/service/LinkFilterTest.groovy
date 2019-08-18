package com.spidy.service

import spock.lang.Specification

class LinkFilterTest extends Specification {

    def "Subdomain link filter"(String root, boolean matches) {
        expect:
            new SubdomainFilter("example.com").invoke(root) == matches

        where:
            root                                    | matches
            "http://example.com"                    | true
            "http://example.com"                    | true
            "http://example.com/test"               | true
            "http://example.com/test?hello=world"   | true
            "https://example.com"                   | true
            "https://www.example.com"               | true
            "www.example.com"                       | true
            "/test"                                 | true
            "www.example.co.uk"                     | false
            "http://google.com"                     | false
            "http://www.google.com"                 | false
            "https://www.google.com"                | false
            "https://www.google.com?hello=world"    | false

    }

}
