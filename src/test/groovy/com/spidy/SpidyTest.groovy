package com.spidy
import spock.lang.Specification

class SpidyTest extends Specification {

    def "Page with no links"() {
        given:
            def root = "index.html"

            def spidy = new Spidey(Mock(WebConnector){
                1 * get(root) >> "<html></html>"
            })
        when:
            def page = spidy.crawl(root)
        then:
            new Page(root, []) == page
    }

    def "Page with one link"() {
        given:
            def root = "index.html"
            def firstLink = "first.html"

            def spidy = new Spidey(Mock(WebConnector){
                1 * get(root) >> "<html><a href='${firstLink}'>First</a></html>"
                1 * get(firstLink) >> "<html></html>"
            })
        when:
            def page = spidy.crawl(root)
        then:
            new Page(root, [
                    new Page(firstLink, [])
            ]) == page
    }

    def "Page with two links"() {
        given:
        def root = "index.html"
        def firstLink = "first.html"
        def secondLink = "second.html"

        def spidy = new Spidey(Mock(WebConnector){
            1 * get(root) >> "<html>" +
                    "<a href='${firstLink}'>First</a>" +
                    "<a href='${secondLink}'>Second</a>" +
                    "</html>"
            1 * get(firstLink) >> "<html></html>"
            1 * get(secondLink) >> "<html></html>"
        })
        when:
        def page = spidy.crawl(root)
        then:
        new Page(root, [
                new Page(firstLink, []),
                new Page(secondLink, [])
        ]) == page
    }

}
