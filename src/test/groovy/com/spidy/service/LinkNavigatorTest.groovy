package com.spidy.service

import com.spidy.domain.Page
import spock.lang.Specification

class LinkNavigatorTest extends Specification {

    def "Link navigator"(LinksNavigator navigator, List<String> links, List<Page> expect) {
        expect:
        navigator.navigate(links, { link -> Page.leaf(200, link) }) == expect

        where:
        navigator                      | links                           | expect
        new SequentialLinksNavigator() | []                              | []
        new SequentialLinksNavigator() | ["/first", "/second", "/third"] | [Page.leaf(200, "/first"), Page.leaf(200, "/second"), Page.leaf(200, "/third")]
        new ConcurrentLinksNavigator() | []                              | []
        new ConcurrentLinksNavigator() | ["/first", "/second", "/third"] | [Page.leaf(200, "/first"), Page.leaf(200, "/second"), Page.leaf(200, "/third")]
    }
}
