package com.xuewen.kidsbook.lib;

/**
 * Created by root on 16-1-28.
 */

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class HttpVisit {
    private HttpParams httpParams;
    private DefaultHttpClient httpClient;
    public HttpResponse response;
    private int statuscode;
    HttpGet httpget;
    HttpPost hp;
    //CookieStore cs;
    CookieStore cs = new BasicCookieStore();
    private boolean useCookie = false;
    private HttpContext localContext = new BasicHttpContext();

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    /*
     * public HttpVist(boolean redirect,boolean usehttps,CookieStore
     * cookieStore){ httpParams=new BasicHttpParams();
     * HttpClientParams.setRedirecting(httpParams, redirect); httpClient=new
     * DefaultHttpClient(httpParams);
     * httpClient.getParams().setParameter(CoreConnectionPNames
     * .CONNECTION_TIMEOUT, 5000);
     * httpClient.getParams().setParameter("User-Agent"
     * ,"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
     * localContext.setAttribute(ClientContext.COOKIE_STORE, cs);
     * httpClient.setCookieStore(cookieStore); if(usehttps){
     *
     * }
     *
     * }
     */
    public HttpVisit() {
        ClientConnectionManager connManager = new PoolingClientConnectionManager();
        httpClient = new DefaultHttpClient(connManager);
        httpClient.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
        localContext.setAttribute(ClientContext.COOKIE_STORE, cs);

    }

    public HttpVisit(CookieStore cookieStore) {
        ClientConnectionManager connManager = new PoolingClientConnectionManager();
        httpClient = new DefaultHttpClient(connManager);
        httpClient.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
        localContext.setAttribute(ClientContext.COOKIE_STORE, cs);
        httpClient.setCookieStore(cookieStore);
        useCookie = true;

    }


    public HttpVisit(boolean redirect) {
        httpParams = new BasicHttpParams();
        HttpClientParams.setRedirecting(httpParams, redirect);
        httpClient = new DefaultHttpClient(httpParams);

        httpClient.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
        //����cookie����
        //httpClient.getParams().setParameter(
        //	ClientPNames.COOKIE_POLICY, CookiePolicy.RFC_2965);
        // httpClient.getParams().setParameter("User-Agent","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");

        localContext.setAttribute(ClientContext.COOKIE_STORE, cs);

    }

    public HttpVisit(boolean readirect, CookieStore cookieStore) {
        // HttpVist hv=new HttpVist(readirect);
        // hv.httpClient.setCookieStore(cookieStore);

        httpParams = new BasicHttpParams();
        HttpClientParams.setRedirecting(httpParams, readirect);
        httpClient = new DefaultHttpClient(httpParams);
        httpClient.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
        httpClient
                .getParams()
                .setParameter("User-Agent",
                        "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
        //����cookie����
        httpClient.getParams().setParameter(
                ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
        localContext.setAttribute(ClientContext.COOKIE_STORE, cs);
        httpClient.setCookieStore(cookieStore);
        useCookie = true;
    }

    public boolean vistUsePost(String url, Map map) {
        boolean reacheable = true;


        List<NameValuePair> nvps = new ArrayList();
        if (map != null && !map.isEmpty()) {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                NameValuePair nvp = new BasicNameValuePair(key, value);
                nvps.add(nvp);
            }
        }

        URI uri = packurl(url);

        if (hp != null)
            hp.abort();
        hp = new HttpPost(uri);
        try {
            hp.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {

            if (!useCookie) {
                response = httpClient.execute(hp, localContext);
            } else {
                response = httpClient.execute(hp);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            this.setStatuscode(404);
            reacheable = false;
            System.out.println("url:" + url);
            System.out.println("Outer ClientProtocolException catched!");
        } catch (IOException e) {
            e.printStackTrace();
            this.setStatuscode(404);
            reacheable = false;
            System.out.println("url:" + url);
            System.out.println("Outer IOException catched!");
        }

        return reacheable;
    }

    public void vistUsePostWithRedirect(String url, Map map) {
        int status = -1;
        boolean reacheable = true;
        URI uri = packurl(url);

        String domain = uri.getScheme() + "://" + uri.getHost();

        reacheable = vistUsePost(url, map);
        if (reacheable) {
            status = response.getStatusLine().getStatusCode();
        }

        while ((status == HttpStatus.SC_MOVED_PERMANENTLY)
                || (status == HttpStatus.SC_MOVED_TEMPORARILY)
                || (status == HttpStatus.SC_SEE_OTHER)
                || (status == HttpStatus.SC_TEMPORARY_REDIRECT)) {

            String newUri = response.getLastHeader("Location").getValue();
            if (newUri.startsWith("/")) {
                newUri = domain + newUri;
            }
            reacheable = vistUsePost(newUri, map);

            if (reacheable) {
                status = response.getStatusLine().getStatusCode();
            }
            domain = uri.getScheme() + "://" + uri.getHost();
        }

    }

    public void vist(String url, Map paraMap) {
        String tempurl = url;

        if (paraMap.size() > 0) {
            if (!url.contains("?")) {
                tempurl = tempurl + "?";
            }

            Iterator it = paraMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                if (tempurl.endsWith("?")) {
                    tempurl = tempurl + key + "=" + value;
                } else
                    tempurl = tempurl + "&" + key + "=" + value;
            }
        }
        System.out.println("URL Is : " + tempurl);
        this.vist(tempurl);
    }

    public boolean vist(String url) {
        boolean reacheable = true;
        URI uri = packurl(url);
        if (httpget != null)
            httpget.abort();
        httpget = new HttpGet(uri);
        /*
        String cookie = "__permanent_id=20151031192911520240258126836113434; __ddclick_visit=0000000001.23; _jzqco=%7C%7C%7C%7C%7C1.1356933532.1446362672212.1455540537247.1455540672547.1455540537247.1455540672547.0.0.0.58.58; __xsptplus100=100.13.1455539831.1455540672.6%232%7Cwww.baidu.com%7C%7C%7C%7C%23%23o2V_jMPOyu2dhKWIxgLm1YV1TbZtBZBS%23; producthistoryid=23699122%2C23712929%2C23833066%2C22618697%2C23417701%2C23522235%2C23439371%2C22621724%2C23622369%2C23463956; MDD_permanent_id=20151117204039908522879164137137224; dest_area=country_id%3D9000%26province_id%3D111%26city_id%20%3D0%26district_id%3D0%26town_id%3D0; __ddc_charset=utf-8%7C!%7Cutf-8%7C!%7Cutf-8; MDD_producthistoryids=22618697%257C23808035";
        httpget.addHeader(new BasicHeader("Cookie", cookie));
        */
        httpget.setHeader("User-Agent",
                "Mozilla/5.0 (X11; Linux x86_64; rv:44.0) Gecko/20100101 Firefox/44.0");
        httpget.setHeader("Host", "mapi.dangdang.com");
        //httpget.setHeader("Accept-Encoding", "gzip,deflate");
        httpget.setHeader("Cache-Control", "max-age=0");
        httpget.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");

        try {
            if (!useCookie) {
                response = httpClient.execute(httpget, localContext);
            } else {
                response = httpClient.execute(httpget);
            }
            this.setStatuscode(response.getStatusLine().getStatusCode());
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.setStatuscode(404);
            reacheable = false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.setStatuscode(404);
            reacheable = false;
        }

        return reacheable;
    }

    public void vistWithContext(String url) {
        boolean reacheable = true;
        URI uri = packurl(url);
        HttpGet httpget = new HttpGet(uri);
        httpget.setHeader("User-Agent",
                "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");

        try {
            if (!useCookie) {
                response = httpClient.execute(httpget, localContext);
            } else {
                response = httpClient.execute(httpget);
            }

            this.setStatuscode(response.getStatusLine().getStatusCode());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            this.setStatuscode(404);
            reacheable = false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.setStatuscode(404);
            reacheable = false;
            System.out.println("Outer IOException catched!");
        }
    }

    public CookieStore getCookieStore() {

        cs = (CookieStore) localContext
                .getAttribute(ClientContext.COOKIE_STORE);

        return cs;
    }

    public void vistWithRedirect(String url) {
        int status = -1;
        boolean reacheable = true;
        URI uri = packurl(url);

        String domain = uri.getScheme() + "://" + uri.getHost();

        reacheable = vist(url);
        if (reacheable) {
            status = response.getStatusLine().getStatusCode();
        }

        while ((status == HttpStatus.SC_MOVED_PERMANENTLY)
                || (status == HttpStatus.SC_MOVED_TEMPORARILY)
                || (status == HttpStatus.SC_SEE_OTHER)
                || (status == HttpStatus.SC_TEMPORARY_REDIRECT)) {

            String newUri = response.getLastHeader("Location").getValue();

            if (newUri.startsWith("/")) {
                newUri = domain + newUri;
            }
            reacheable = vist(newUri);

            if (reacheable) {
                status = response.getStatusLine().getStatusCode();
            }
            domain = uri.getScheme() + "://" + uri.getHost();
        }
    }

    public String getContent() {
        String result = null;
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            try {
                if (ContentType.getOrDefault(entity).toString()
                        .contains("charset")) {
                    result = EntityUtils.toString(entity);
                } else {
                    result = EntityUtils.toString(entity, "utf-8");
                }
                EntityUtils.consume(entity);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else {

            // Stream content out

        }

        return result;
    }

    public int getstatusCode() {
        return statuscode;
    }

    public static URI packurl(String url) {
        url = url.replace(",", "");
        URL tempurl;
        URI uri = null;
        try {
            tempurl = new URL(url);

            if (tempurl.getQuery() == null || tempurl.getQuery().contains("%")) {
                uri = new URI(url);

            } else {
                uri = new URI(tempurl.getProtocol(), tempurl.getUserInfo(), tempurl.getHost(), tempurl.getPort(),
                        tempurl.getPath(), tempurl.getQuery(), null);

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return uri;
    }


    public static void main(String[] arg) {
        HttpVisit hv = new HttpVisit();
        hv.vist("http://product.dangdang.com/23417701.html");
        System.out.println(hv.getContent());
        //hv.vist("http://jira.qiyi.domain:8088/secure/IssueNavigator.jspa?refreshFilter=false&reset=update&usercreated=true&show=%E6%9F%A5%E7%9C%8B+%3E%3E&summary=true&description=true&pid=10144&query=%E6%B3%A8%E5%86%8C");
        //System.out.println(hv.getContent());
        //packurl("http://jira.qiyi.domain:8088/secure/IssueNavigator.jspa?refreshFilter=false&reset=update&usercreated=true&show=%E6%9F%A5%E7%9C%8B+%3E%3E&summary=true&description=true&pid=10144&query=%E6%B3%A8%E5%86%8C");
        //HttpVist hv=new HttpVist();
        //hv.vist("http://www.qiyi.com");
        //System.out.println(System.currentTimeMillis() );

    }

}

