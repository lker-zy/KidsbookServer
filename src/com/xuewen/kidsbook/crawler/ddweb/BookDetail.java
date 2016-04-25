package com.xuewen.kidsbook.crawler.ddweb;

import com.xuewen.kidsbook.lib.HttpVisit;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.util.NodeList;

/**
 * Created by root on 16-1-27.
 */
public class BookDetail {
    private Book book;
    private String detailURL;
    private String content;

    public BookDetail(String id) {
        this.book = new Book(id);
        this.detailURL = "http://product.dangdang.com/" + id + ".html";
    }

    public void fetchFromDD() {
        Parser parser = null;
        System.out.println("url is: " + this.detailURL);

        HttpVisit hv = new HttpVisit();
        hv.vistWithContext(this.detailURL);
        this.content = hv.getContent();

        try {
            parser = new Parser(this.content);

            /*
            TextExtractingVisitor visitor = new TextExtractingVisitor();
            parser.visitAllNodesWith(visitor);
            String textInPage = visitor.getExtractedText();
            */

            Node node = null;
            NodeList nodes = null;

            // abstract
            HasAttributeFilter idFilter = new HasAttributeFilter("id", "abstract");
            nodes = parser.extractAllNodesThatMatch(idFilter);
            if ((nodes.size() > 1) || (nodes.size() == 0)) {
                System.out.println("Exception: nodes number more than expected");
                return;
            }
            node = nodes.elementAt(0);
            System.out.println("abstract is: " + node.toPlainTextString());

            // content
            idFilter = new HasAttributeFilter("id", "content");
            parser = new Parser(this.content);
            nodes = parser.extractAllNodesThatMatch(idFilter);
            if ((nodes.size() > 1) || (nodes.size() == 0)) {
                System.out.println("Exception: nodes number more than expected: " + nodes.size());
                return;
            }
            node = nodes.elementAt(0);
            System.out.println("content is: " + node.toPlainTextString());

            // author
            idFilter = new HasAttributeFilter("id", "authorintro");
            parser = new Parser(this.content);
            nodes = parser.extractAllNodesThatMatch(idFilter);
            if ((nodes.size() > 1) || (nodes.size() == 0)) {
                System.out.println("Exception: nodes number more than expected: " + nodes.size());
                return;
            }
            node = nodes.elementAt(0);
            System.out.println("author info is: " + node.toPlainTextString());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }


    }

    public void store() {

    }

    public void main(String[] args) {
        BookDetail detail = new BookDetail("22618697");
        detail.fetchFromDD();
    }
}
