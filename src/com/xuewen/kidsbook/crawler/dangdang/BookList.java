package com.xuewen.kidsbook.crawler.dangdang;

import com.alibaba.fastjson.serializer.IntegerCodec;
import com.alibaba.fastjson.serializer.StringCodec;
import com.xuewen.kidsbook.lib.HttpVisit;

import java.io.*;
import java.util.*;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

/**
 * Created by root on 16-2-15.
 */

/***********************************************************************************************************************
    {
       "pageinfo":{
            "total":1000,
            "page_count":50,
            "start":0,
            "end":20,
            "next":2,
            "page":"1",
            "pagesize":"20"
        },
       "list":[
        {
            "name":"\u8fd9\u5c31\u662f\u4e8c\u5341\u56db\u8282\u6c14\uff08\u5957\u88c5\u51714\u518c\uff09",
            "product_id":"23808035",
            "author":"\u9ad8\u6625\u9999\uff0c\u90b5\u654f \u8457\uff0c\u8bb8\u660e\u632f\uff0c\u674e\u5a67 \u7ed8",
            "ddprice":"72.00",
            "score":"",
            "totalreview":"2741",
            "stock":"2",
            "publisher":"\u6d77\u8c5a\u51fa\u7248\u793e",
            "seo_url":"product.php?pid=23808035",
            "img_url":"http:\/\/img3x5.ddimg.cn\/20\/15\/23808035-1_b.jpg",
            "rank":1
         },
         {
            "name":"\u5b66\u4f1a\u7ba1\u81ea\u5df1\uff08\u6b6a\u6b6a\u5154\u72ec\u7acb\u6210\u957f\u7ae5\u8bdd\uff09",
            "product_id":"23521768",
            "author":"\u9648\u68a6\u654f\u8457\uff0c\u5f20\u6587\u7eee\u56fe",
            "ddprice":"64.00",
            "score":"",
            "totalreview":"97792",
            "stock":"2",
            "publisher":"\u6d77\u8c5a\u51fa\u7248\u793e",
            "seo_url":"product.php?pid=23521768",
            "img_url":"http:\/\/img3x8.ddimg.cn\/61\/17\/23521768-1_b.jpg",
            "rank":2
          }
        ]
      }
***********************************************************************************************************************/

class BooksInfo {
    private PageInfo pageinfo;
    private List<BookInfo> list = new ArrayList<BookInfo>();

    public List<BookInfo> getList() {
        return list;
    }

    public void setList(List<BookInfo> list) {
        this.list = list;
    }

    public PageInfo getPageinfo() {
        return pageinfo;
    }

    public void setPageinfo(PageInfo pageinfo) {
        this.pageinfo = pageinfo;
    }
}

class PageInfo {
    private Long total;
    private Long page_count;
    private Long start;
    private Long end;
    private Long next;
    private String page;
    private String pagesize;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getPage_count() {
        return page_count;
    }

    public void setPage_count(Long page_count) {
        this.page_count = page_count;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public Long getNext() {
        return next;
    }

    public void setNext(Long next) {
        this.next = next;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }
}

class BookInfo {
    private String name;
    private String product_id;
    private String author;
    private String ddprice;
    private String score;
    private String totalreview;
    private String stock;
    private String publisher;
    private String seo_url;
    private String img_url;
    private Long rank;

    private Map<String, String> info_map = new HashMap<>();

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
        this.info_map.put("rank", Long.toString(rank));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.info_map.put("name", name);
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
        this.info_map.put("product_id", product_id);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
        this.info_map.put("author", author);
    }

    public String getDdprice() {
        return ddprice;
    }

    public void setDdprice(String ddprice) {
        this.ddprice = ddprice;
        this.info_map.put("ddprice", ddprice);
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
        this.info_map.put("score", score);
    }

    public String getTotalreview() {
        return totalreview;
    }

    public void setTotalreview(String totalreview) {
        this.totalreview = totalreview;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
        this.info_map.put("publisher", publisher);
    }

    public String getSeo_url() {
        return seo_url;
    }

    public void setSeo_url(String seo_url) {
        this.seo_url = seo_url;
        this.info_map.put("seo_url", seo_url);
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
        this.info_map.put("img_url", img_url);
    }

    public Map<String, String> getAll() {
        return this.info_map;
    }

}


public class BookList {
    private static String ENCODE = "GB2312";
    private static String REDIS_BOOK_PREFIX = "book_";
    private static int TOTAL_PAGES = 10;
    private Jedis jedis = null;

    public static String openFile( String szFileName ) {
        String szContent = "";
        String szTemp = "";
        BufferedReader bis = null;

        try {
            bis = new BufferedReader(new InputStreamReader(new FileInputStream( new File(szFileName)), ENCODE) );

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

    private void connectRedis() {
        jedis = new Jedis("127.0.0.1", 6379);
    }

    private String open(int page_index) {
        System.out.println("Test CrawlData");
        String content;
        String list_url = "http://mapi.dangdang.com/index.php?catPath=01.41.00.00.00.00&time_code=e390b256b2248b51f3526310980c68ed&method=childrens&img_size=b&client_version=6.0.9&action=list_bastsell&max_prix=&union_id=537-100114&timestamp=1455547274&time_region=recent7&permanent_id=20160215210106878724230811076647536&page_size=20&udid=87e5b86170039e1338e8a78c6cd4c066&user_client=android&page=1&min_prix=";

        Map<String,String> params = new HashMap<String,String>();
        params.put("catPath", "01.41.00.00.00.00");
        params.put("time_code", "e390b256b2248b51f3526310980c68ed");
        params.put("method", "childrens");
        params.put("img_size", "b");
        params.put("client_version", "6.0.9");
        params.put("action", "list_bastsell");
        params.put("max_prix", "");
        params.put("union_id", "537-100114");
        params.put("timestamp", "1455547274");
        params.put("time_region", "recent7");
        params.put("permanent_id", "20160215210106878724230811076647536");
        params.put("page_size", "20");
        params.put("udid", "87e5b86170039e1338e8a78c6cd4c066");
        params.put("user_client", "android");
        params.put("page", Integer.toString(page_index));
        params.put("min_prix", "");

        HttpVisit hv = new HttpVisit();
        //hv.vistWithContext(list_url);
        hv.vist(list_url, params);
        content = hv.getContent();

        System.out.println(content);

        return content;
    }

    private void handleJsonContent(String json) {
        BooksInfo books = JSON.parseObject(json, BooksInfo.class);

        PageInfo page = books.getPageinfo();
        System.out.println("page info: total_count = " + page.getTotal());

        connectRedis();

        List<BookInfo> book_list = books.getList();
        for (int i = 0; i < book_list.size(); ++i) {
            BookInfo book = book_list.get(i);
            String book_id = book.getProduct_id();
            System.out.println("book is: " + book_id);

            this.jedis.hmset(REDIS_BOOK_PREFIX + book_id, book.getAll());
            //System.out.println(this.jedis.hmget(book_id, "name"));
        }
    }

    public void fetchAll() {
        int cur_page = 1;
        while (cur_page <= TOTAL_PAGES) {
            String json_content = open(cur_page);
            this.handleJsonContent(json_content);
            cur_page++;
        }
    }

    public Set<String> listAll() {
        Set<String> idset = new HashSet<>();
        connectRedis();

        Set<String> books_set = this.jedis.keys(REDIS_BOOK_PREFIX + "*");
        System.out.println(books_set);

        for (Iterator<String> iter = books_set.iterator(); iter.hasNext();) {
            String id = iter.next();
            idset.add(id.split("_")[1]);
        }

        return idset;
    }

    public static void main(String [] args) {
        BookList books = new BookList();
        books.fetchAll();
        books.listAll();
    }
}
