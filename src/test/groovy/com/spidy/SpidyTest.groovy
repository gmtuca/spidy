package com.spidy

import com.spidy.domain.Page
import com.spidy.service.WebConnector
import com.spidy.service.WebConnectorImpl
import kotlin.Pair
import spock.lang.Specification

class SpidyTest extends Specification {

    def "Page with no links"() {
        given:
        def domain = "domain.com"
        when:
        def page = SpidyKt.crawl(domain, "/", false, Mock(WebConnector) {
            1 * get("/") >> new Pair(200, "<html></html>")
        })
        then:
        new Page(200, "/", [], false) == page
    }

    def "Page with one link, depth 1"() {
        given:
        def domain = "domain.com"
        def firstLink = "/first.html"

        when:
        def page = SpidyKt.crawl(domain, "/", false, Mock(WebConnector) {
            1 * get("/") >> new Pair(200, "<html><a href='${firstLink}'>First</a></html>".toString())
            1 * get(firstLink) >> new Pair(200, "<html></html>".toString())
        })
        then:
        new Page(200, "/", [
                Page.leaf(200, firstLink)
        ], false) == page
    }

    def "Page with one link, depth 1, starting from /first.html"() {
        given:
        def domain = "domain.com"
        def firstLink = "/first.html"
        def secondLink = "/second.html"

        when:
        def page = SpidyKt.crawl(domain, firstLink, false, Mock(WebConnector) {
            1 * get(firstLink) >> new Pair(200, "<html><a href='${secondLink}'>First</a></html>".toString())
            1 * get(secondLink) >> new Pair(200, "<html></html>".toString())
        })
        then:
        new Page(200, firstLink, [
                Page.leaf(200, secondLink)
        ], false) == page
    }

    def "Page with two links, depth 1"() {
        given:
        def domain = "domain.com"
        def firstLink = "/first.html"
        def secondLink = "/second.html"

        when:
        def page = SpidyKt.crawl(domain, "/", false, Mock(WebConnector) {
            1 * get("/") >> new Pair(200, "<html>" +
                    "<b>Here is a link: <a href='${firstLink}'>First</a></b>" +
                    "<p>Here is another link: <a href='${secondLink}'>Second</a></b>" +
                    "</html>".toString())
            1 * get(firstLink) >> new Pair(200, "<html></html>".toString())
            1 * get(secondLink) >> new Pair(200, "<html></html>".toString())
        })
        then:
        new Page(200, "/", [
                Page.leaf(200, firstLink),
                Page.leaf(200, secondLink)
        ], false) == page
    }

    def "Page with one link, depth 2"() {
        given:
        def domain = "domain.com"
        def firstLink = "/first.html"
        def firstFollowUpLink = "/followup1.com"

        when:
        def page = SpidyKt.crawl(domain, "/", false, Mock(WebConnector) {
            1 * get("/") >> new Pair(200, "<html><a href='${firstLink}'>First</a></html>".toString())
            1 * get(firstLink) >> new Pair(200, "<html><a href='${firstFollowUpLink}'>Follow-up 1</a></html>".toString())
            1 * get(firstFollowUpLink) >> new Pair(200, "<html></html>".toString())
        })
        then:
        new Page(200, "/", [
                new Page(200, firstLink, [
                        Page.leaf(200, firstFollowUpLink)
                ], false)
        ], false) == page
    }

    def "Page with 2 links, depth 3"() {
        given:
        def domain = "domain.com"

        def firstLink = "/test1.com"
        def firstFollowUpLink = "/followup1.com"

        def secondLink = "/test2.com"
        def secondFollowUpLink1 = "/followup2-1.com"
        def secondFollowUpLink2 = "/followup2-2.com"

        when:
        def page = SpidyKt.crawl(domain, "/", false, Mock(WebConnector) {
            1 * get("/") >> new Pair(200, "<html>" +
                    "<a href='${firstLink}'>Test 1</a>" +
                    "<a href='${secondLink}'>Test 2</a>" +
                    "</html>".toString())
            1 * get(firstLink) >> new Pair(200, "<html><a href='${firstFollowUpLink}'>Follow-up 1</a></html>".toString())
            1 * get(firstFollowUpLink) >> new Pair(200, "<html></html>".toString())
            1 * get(secondLink) >> new Pair(200, "<html>" +
                    "<a href='${secondFollowUpLink1}'>Follow-up 2-1</a>" +
                    "<a href='${secondFollowUpLink2}'>Follow-up 2-2</a>" +
                    "</html>".toString())
            1 * get(secondFollowUpLink1) >> new Pair(200, "<html></html>".toString())
            1 * get(secondFollowUpLink2) >> new Pair(200, "<html></html>".toString())
        })
        then:
        new Page(200, "/", [
                new Page(200, firstLink, [
                        Page.leaf(200, firstFollowUpLink)
                ], false),
                new Page(200, secondLink, [
                        Page.leaf(200, secondFollowUpLink1),
                        Page.leaf(200, secondFollowUpLink2)
                ], false),
        ], false) == page
    }


    def "Page with three links, depth 1, ignoring outside domain filter"() {
        given:
        def domain = "domain.com"
        def firstLink = "/first"
        def secondLink = "https://domain.com/second"
        def thirdLink = "https://google.com"

        when:

        def page = SpidyKt.crawl(domain, "/", false, Mock(WebConnector) {
            1 * get("/") >> new Pair(200, "<html>" +
                    "<a href='${firstLink}'>First</a>" +
                    "<a href='${secondLink}'>Second</a>" +
                    "<a href='${thirdLink}'>Third</a>" +
                    "</html>".toString())
            1 * get("/first") >> new Pair(200, "<html></html>".toString())
            1 * get("/second") >> new Pair(200, "<html></html>".toString())
            0 * get(thirdLink)
        })

        then:
        new Page(200, "/", [
                Page.leaf(200, "/first"),
                Page.leaf(200, "/second")
        ], false) == page
    }

    def "Page with three links, depth 1, different status codes"() {
        given:
        def domain = "example.com"
        def firstLink = "/not-found"
        def secondLink = "/forbidden"
        def thirdLink = "/moved-permanently"
        when:
        def page = SpidyKt.crawl(domain, "/", false, Mock(WebConnector) {
            1 * get("/") >> new Pair(200, "<html>" +
                    "<a href='${firstLink}'>Not found</a>" +
                    "<a href='${secondLink}'>Forbidden</a>" +
                    "<a href='${thirdLink}'>Moved permanently</a>" +
                    "</html>".toString())
            1 * get(firstLink) >> new Pair(404, "<html><b>Not found</b></html>".toString())
            1 * get(secondLink) >> new Pair(403, "<html><p>Forbidden</b></html>".toString())
            1 * get(thirdLink) >> new Pair(301, "<html><p>Moved</b></html>".toString())
        })

        then:
        new Page(200, "/", [
                Page.leaf(404, firstLink),
                Page.leaf(403, secondLink),
                Page.leaf(301, thirdLink)
        ], false) == page
    }

    def "Page with one link, depth 1, follows non-2xx status codes"() {
        given:
        def domain = "domain.com"
        def notFound = "/not-found"
        def other = "/other"
        when:
        def page = SpidyKt.crawl(domain, "/", false, Mock(WebConnector) {
            1 * get("/") >> new Pair(200, "<html>Click <a href='${notFound}'>here</a></html>".toString())
            1 * get(notFound) >> new Pair(404, "<html>Not found - go to <a href='${other}'>other</a></html>".toString())
            1 * get(other) >> new Pair(200, "<html></html>".toString())
        })
        then:
        new Page(200, "/", [
                new Page(404, notFound, [
                        Page.leaf(200, other)
                ], false)
        ], false) == page
    }

    def "Page with one link, cyclic dependency"() {
        given:
        def domain = "domain.com"
        when:
        def page = SpidyKt.crawl(domain, "/", false, Mock(WebConnector) {
            1 * get("/") >> new Pair(200, "<html><a href='/'>Back to domain!</a></html>".toString())
        })

        then:
        new Page(200, "/", [
                new Page(0, "/", [], true)
        ], false) == page
    }

    def "Page with one link, depth 4, cyclic dependency"() {
        given:
        def domain = "domain.com"
        def firstLink = "/test1.com"
        def firstFollowUpLink = "/followup1.com"
        when:
        def page = SpidyKt.crawl(domain, "/", false, Mock(WebConnector) {
            1 * get("/") >> new Pair(200, "<html><a href='${firstLink}'>Test 1</a></html>".toString())
            1 * get(firstLink) >> new Pair(200, "<html><a href='${firstFollowUpLink}'>Follow-up 1</a></html>".toString())
            1 * get(firstFollowUpLink) >> new Pair(200, "<html><a href='/'>Follow-up 1</a></html>".toString())
        })

        then:
        new Page(200, "/", [
                new Page(200, firstLink, [
                        new Page(200, firstFollowUpLink, [
                                new Page(0, "/", [], true)
                        ], false)
                ], false)
        ], false) == page
    }

    def "Crawl live page with no links"() {
        given:
        def domain = "example.com"
        when:
        def page = SpidyKt.crawl(domain, "/", false, new WebConnectorImpl(domain))
        then:
        new Page(200, "/", [], false) == page
    }

}
