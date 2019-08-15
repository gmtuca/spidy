package com.spidy
import spock.lang.Specification

class LinkFilterTest extends Specification {

    def "Subdomain link filter"(String link, boolean matches) {
        expect:
            new SubdomainFilter("example.com").invoke(link) == matches

        where:
            link                                    | matches
            "/"                                     | true
            "/test"                                 | true
            "/image.jpg"                            | true
            "example.com"                           | true
            "www.example.com"                       | true
            "http://example.com"                    | true
            "http://www.example.com"                | true
            "https://www.example.com"               | true
            "https://www.example.com?hello=world"   | true
            "google.com"                            | false
            "www.google.com"                        | false
            "http://google.com"                     | false
            "http://www.google.com"                 | false
            "https://www.google.com"                | false
            "https://www.google.com?hello=world"    | false

    }

}
