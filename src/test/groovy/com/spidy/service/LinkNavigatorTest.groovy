package com.spidy.service

import com.spidy.domain.Page
import spock.lang.Specification

class LinkNavigatorTest extends Specification {

    def "Link navigator"(LinksNavigator navigator, List<String> links, List<Page> expect) {
        expect:
        def i = 1
        navigator.navigate(links, { Page.leaf(200, "" + i++) }) == expect

        where:
        navigator                      | links                           | expect
        new SequentialLinksNavigator() | []                              | []
        new SequentialLinksNavigator() | ["/first", "/second", "/third"] | [Page.leaf(200, "1"), Page.leaf(200, "2"), Page.leaf(200, "3")]
        new ConcurrentLinksNavigator() | []                              | []
        new ConcurrentLinksNavigator() | ["/first", "/second", "/third"] | [Page.leaf(200, "1"), Page.leaf(200, "2"), Page.leaf(200, "3")]
    }
}
