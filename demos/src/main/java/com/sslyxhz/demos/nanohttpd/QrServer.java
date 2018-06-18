package com.sslyxhz.demos.nanohttpd;

import android.content.Context;
import android.util.Log;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by xh.zeng on 2017/6/9.
 */

public class QrServer extends NanoHTTPD{
    private static final Logger LOG = Logger.getLogger(QrServer.class.getName());

    private Map<String, QrHttpHandler> handler = new LinkedHashMap<String, QrHttpHandler>();
    private Context mContext;
    private String mRootDocumentPath;
    
    public QrServer(int port, String rootDocumentPath, Context context){
        super(port);
        this.mContext = context;
        this.mRootDocumentPath = rootDocumentPath;

        this.addPathHandler("/", mRootDocumentPath); // 设置默认根目录
    }

    @Override
    public Response serve(IHTTPSession session) {
        Map<String, String> header = session.getHeaders();
        Map<String, String> parms = session.getParms();
        String uri = session.getUri();

        Log.d("HTTP", "Request to URI " + uri);

        //Traverse handlers in reversed order
        //Although I think that a FIFO order makes more sense, we do it in LIFO order
        //to make it compliant with the GDCWebserver used in the iOS version
        LinkedList<String> keyList = new LinkedList<>(this.handler.keySet());
        Iterator<String> keyIterator = keyList.descendingIterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();

            if (uri.startsWith(key)) {
                QrHttpHandler handler = this.handler.get(key);
                Response result = handler.handle(uri);

                //If the handler returns a non-null result, that's our response and we are done
                if (result != null) {
                    return result;
                }
            }
        }

        return QrHttpHandler.notFoundResponse(); //no handler handled this uri
    }

    public Response htmlResponse(IHTTPSession session){
        String msg = "<html><body><h1>Hello server</h1>\n";
        Map<String, String> parms = session.getParms();
        if (parms.get("username") == null) {
            msg += "<form action='?' method='get'>\n" + "  <p>Your name: <input type='text' name='username'></p>\n" + "</form>\n";
        } else {
            msg += "<p>Hello, " + parms.get("username") + "!</p>";
            msg +="<img src=\"http://mssdn.sankuai.com/v1/mss_814dc1610cda4b2e8febd6ea2c809db5/image/1489026813356.png\" alt=\"Big Boat\">\n";
        }

        msg += "</body></html>\n";

        return new NanoHTTPD.Response(NanoHTTPD.Response.Status.OK, "text/html", msg);
//        return newFixedLengthResponse(msg);
    }

//    public Response bitmapResponse(IHTTPSession session){
//        Bitmap qrCode = EncodingUtils.createQRCode("http://www.baidu.com/", 300, 300, null);
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        qrCode.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
//        byte[] qrcodeData = bos.toByteArray();
//        ByteArrayInputStream qrcodeInputStream = new ByteArrayInputStream(qrcodeData);
//
////        Response bigmapResponse = newFixedLengthResponse(Response.Status.OK, "png", qrcodeInputStream, qrcodeData.length);
//        Response bigmapResponse = new Response(Response.Status.OK, "png", qrcodeInputStream);
//        return bigmapResponse;
//    }
//
//    public void addQrCodeHandler(final String path){
//        QrHttpHandler handler = new QrHttpHandler() {
//            @Override
//            public Response handle(String request) {
//                if(request.equals(path)){
//                    Bitmap qrCode = EncodingUtils.createQRCode("http://www.baidu.com/", 300, 300, null);
//                    return qrCodeResponse(qrCode);
//                }
//                return null;
//            }
//        };
//
//        this.addHandler(path, handler);
//    }

    public void addPathHandler(String basePath, String directoryPath) {
        //basePath must be a directory
        if (basePath.endsWith("/") == false) {
            basePath += "/";
        }
        final String aBasePath = basePath;

        //directoryPath must be a directory
        if (directoryPath.endsWith("/") == false) {
            directoryPath += "/";
        }
        final String aDirectoryPath = directoryPath;

        QrHttpHandler handler = new QrHttpHandler() {
            @Override
            public Response handle(String request) {
                //We don't serve directories, only files
                //Therefore, if a directory is requested, try to serve the index.html inside of it
                if (request.endsWith("/")) {
                    request += "index.html";
                }

                String assetPath = request.replaceFirst(aBasePath, aDirectoryPath);

                return assetResponse(mContext, assetPath);
            }
        };

        this.addHandler(aBasePath, handler);
    }

    public void addFileHandler(final String basePath, final String filePath) {
        QrHttpHandler handler = new QrHttpHandler() {
            @Override
            public Response handle(String request) {
                //This handler serves only a single file, therefore the basePath must be exactly
                //the specified basePath
                if (request.equals(basePath)) {
                    return assetResponse(mContext, filePath);
                }

                return null; //we don't handle this request
            }
        };

        this.addHandler(basePath, handler);
    }

    public void addHTMLHandler(final String basePath, final String html) {
        QrHttpHandler handler = new QrHttpHandler() {
            @Override
            public Response handle(String request) {
                //This handler serves a static file. Serve only the specified base path
                if (request.equals(basePath)) {
                    return htmlResponse(html);
                }

                return null; //we don't handle this request
            }
        };

        this.addHandler(basePath, handler);
    }

    public void addTextHandler(final String basePath, final String text) {
        QrHttpHandler handler = new QrHttpHandler() {
            @Override
            public Response handle(String request) {
                //This handler serves a static file. Serve only the specified base path
                if (request.equals(basePath)) {
                    return textResponse(text);
                }

                return null; //we don't handle this request
            }
        };

        this.addHandler(basePath, handler);
    }

    public void addHandler(String path, QrHttpHandler handler) {
        this.handler.put(path, handler);
    }
}
