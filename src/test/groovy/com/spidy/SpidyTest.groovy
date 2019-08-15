package com.spidy
import spock.lang.Specification

class SpidyTest extends Specification {

    def "Page with links"() {
        given:
            def url = "index.html"

            def spidy = new Spidey(Mock(WebConnector){
                get(url) >> "<html></html>"
            })
        when:
            def page = spidy.crawl(url)
        then:
            new Page() == page
    }

}
