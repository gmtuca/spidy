package com.spidy

import com.spidy.domain.Page
import com.spidy.domain.SubdomainFilter
import com.spidy.service.WebConnector
import spock.lang.Specification

class SpidyTest extends Specification {

    def "Page with no links"() {
        given:
            def root = "index.html"

            def spidy = new Spidey(Mock(WebConnector){
                1 * get(root) >> "<html></html>"
            }, {true})
        when:
            def page = spidy.crawl(root)
        then:
            new Page(root, [], false) == page
    }

    def "Page with one link, depth 1"() {
        given:
            def root = "index.html"
            def firstLink = "first.html"

            def spidy = new Spidey(Mock(WebConnector){
                1 * get(root) >> "<html><a href='${firstLink}'>First</a></html>"
                1 * get(firstLink) >> "<html></html>"
            }, {true})
        when:
            def page = spidy.crawl(root)
        then:
            new Page(root, [
                    Page.empty(firstLink)
            ], false) == page
    }

    def "Page with two links, depth 1"() {
        given:
        def root = "index.html"
        def firstLink = "first.html"
        def secondLink = "second.html"

        def spidy = new Spidey(Mock(WebConnector){
            1 * get(root) >> "<html>" +
                        "<b>Here is a link: <a href='${firstLink}'>First</a></b>" +
                        "<p>Here is another link: <a href='${secondLink}'>Second</a></b>" +
                    "</html>"
            1 * get(firstLink) >> "<html></html>"
            1 * get(secondLink) >> "<html></html>"
        }, {true})
        when:
        def page = spidy.crawl(root)
        then:
        new Page(root, [
                Page.empty(firstLink),
                Page.empty(secondLink)
        ], false) == page
    }

    def "Page with one link, depth 2"() {
        given:
        def root = "index.html"
        def firstLink = "first.html"
        def firstFollowUpLink = "followup1.com"

        def spidy = new Spidey(Mock(WebConnector){
            1 * get(root) >> "<html><a href='${firstLink}'>First</a></html>"
            1 * get(firstLink) >> "<html><a href='${firstFollowUpLink}'>Follow-up 1</a></html>"
            1 * get(firstFollowUpLink) >> "<html></html>"
        }, {true})
        when:
        def page = spidy.crawl(root)
        then:
        new Page(root, [
                new Page(firstLink, [
                        Page.empty(firstFollowUpLink)
                ], false)
        ], false) == page
    }

    def "Page with 2 links, depth 3"() {
        given:
        def root = "index.html"

        def firstLink = "test1.com"
        def firstFollowUpLink = "followup1.com"

        def secondLink = "test2.com"
        def secondFollowUpLink1 = "followup2-1.com"
        def secondFollowUpLink2 = "followup2-2.com"

        def spidey = new Spidey(Mock(WebConnector){
            1 * get(root) >> "<html>" +
                                "<a href='${firstLink}'>Test 1</a>" +
                                "<a href='${secondLink}'>Test 2</a>" +
                            "</html>"
            1 * get(firstLink) >> "<html><a href='${firstFollowUpLink}'>Follow-up 1</a></html>"
            1 * get(firstFollowUpLink) >> "<html></html>"
            1 * get(secondLink) >> "<html>" +
                                "<a href='${secondFollowUpLink1}'>Follow-up 2-1</a>" +
                                "<a href='${secondFollowUpLink2}'>Follow-up 2-2</a>" +
                            "</html>"
            1 * get(secondFollowUpLink1) >> "<html></html>"
            1 * get(secondFollowUpLink2) >> "<html></html>"
        }, {true})
        when:
        def page = spidey.crawl(root)
        then:
        new Page(root, [
                new Page(firstLink, [
                        Page.empty(firstFollowUpLink)
                ], false),
                new Page(secondLink, [
                        Page.empty(secondFollowUpLink1),
                        Page.empty(secondFollowUpLink2)
                ], false),
        ], false) == page
    }


    def "Page with three links, depth 1, applying subdomain filter"() {
        given:
        def root = "example.com"
        def firstLink = "/first"
        def secondLink = "example.com/second"
        def thirdLink = "https://google.com"

        def spidy = new Spidey(Mock(WebConnector){
            1 * get(root) >> "<html>" +
                    "<a href='${firstLink}'>First</a>" +
                    "<a href='${secondLink}'>Second</a>" +
                    "<a href='${thirdLink}'>Third</a>" +
                    "</html>"
            1 * get(firstLink) >> "<html></html>"
            1 * get(secondLink) >> "<html></html>"
            0 * get(thirdLink)
        }, new SubdomainFilter("example.com"))
        when:
        def page = spidy.crawl(root)
        then:
        new Page(root, [
                Page.empty(firstLink),
                Page.empty(secondLink)
        ], false) == page
    }

    def "Page with one link, cyclic dependency"() {
        given:
        def root = "index.html"

        def spidy = new Spidey(Mock(WebConnector){
            1 * get(root) >> "<html><a href='${root}'>Back to root!</a></html>"
        }, {true})
        when:
        def page = spidy.crawl(root)
        then:
        new Page(root, [
                new Page(root, [], true)
        ], false) == page
    }

    def "Page with one link, depth 4, cyclic dependency"() {
        given:
        def root = "index.html"
        def firstLink = "test1.com"
        def firstFollowUpLink = "followup1.com"

        def spidy = new Spidey(Mock(WebConnector){
            1 * get(root) >> "<html><a href='${firstLink}'>Test 1</a></html>"
            1 * get(firstLink) >> "<html><a href='${firstFollowUpLink}'>Follow-up 1</a></html>"
            1 * get(firstFollowUpLink) >> "<html><a href='${root}'>Follow-up 1</a></html>"
        }, {true})

        when:
        def page = spidy.crawl(root)
        then:
        new Page(root, [
                new Page(firstLink, [
                        new Page(firstFollowUpLink, [
                                new Page(root, [], true)
                        ], false)
                ], false)
        ], false) == page
    }

}
