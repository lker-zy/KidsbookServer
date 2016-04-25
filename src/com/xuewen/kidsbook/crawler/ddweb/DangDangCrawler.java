package com.xuewen.kidsbook.crawler.ddweb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.util.NodeList;

import org.htmlparser.Parser;

/**
 * Created by root on 16-1-25.
 */
public class DangDangCrawler {
    private static String ENCODE = "GB2312";

    private static void message( String szMsg ) {
        try{
            System.out.println(new String(szMsg.getBytes(ENCODE), System.getProperty("file.encoding")));
        } catch(Exception e ){

        }
    }

    public static String openFile( String szFileName ) {
        String szContent="";
        String szTemp;

        try {
            BufferedReader bis = new BufferedReader(new InputStreamReader(new FileInputStream( new File(szFileName)), ENCODE) );

            while ( (szTemp = bis.readLine()) != null) {
                szContent+=szTemp+"\n";
            }
            bis.close();
            return szContent;
        }
        catch( Exception e ) {
            e.printStackTrace();
            return "";
        }
    }

    private static void bookNodeHandler(Node node) {
        String id = node.getText().split("id=")[1];
        id = id.split("\"")[1];
        System.out.println("book id: " + id);
        Book book = new Book(id);
    }

    public void crawler() {
        System.out.println("Test CrawlData");
        String szContent = openFile("/root/IdeaProjects/KidsbookServer/src/dangdang_children_books_list.html");

        NodeFilter bookNodeFilter = new NodeFilter() {
            @Override
            public boolean accept(Node node) {

                if (node.getText().startsWith("li class=\"line")) {
                    return true;
                }
                return false;
            }
        };


        try{
            Parser parser = Parser.createParser(szContent, ENCODE);
            //Parser parser = new Parser( szContent );
            //Parser parser = new Parser( (HttpURLConnection) (new URL("http://127.0.0.1:8080/HTMLParserTester.html")).openConnection() );

            AndFilter attr_filter = new AndFilter(new HasAttributeFilter("id"), new HasAttributeFilter("class"));
            AndFilter books_filter = new AndFilter(attr_filter, bookNodeFilter);
            NodeList nodes = parser.extractAllNodesThatMatch(books_filter);


            int i = 0;
            for (; i < nodes.size(); ++i) {
                Node node = nodes.elementAt(i);
                bookNodeHandler(node);
                System.out.println("=========================================");
            }

        } catch( Exception e ) {
        }

    }

    public static void main(String [] args) {
        /*
        DangDangCrawler crawler = new DangDangCrawler();
        crawler.crawler();
        */
        BookDetail detail = new BookDetail("22618697");
        detail.fetchFromDD();

    }
}
