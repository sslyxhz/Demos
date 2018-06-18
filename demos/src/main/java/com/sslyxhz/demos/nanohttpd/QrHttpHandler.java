package com.sslyxhz.demos.nanohttpd;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.sslyxhz.demos.nanohttpd.NanoHTTPD.Response;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * author: zengxinhong
 * email: zengxinhong@meituan.com
 * date: 2017/6/14
 */

public abstract class QrHttpHandler {

    private static final Map<String, String> MIME_TYPES = new HashMap<String, String>() {{
        put("css", "text/css");
        put("htm", "text/html");
        put("html", "text/html");
        put("xml", "text/xml");
        put("java", "text/x-java-source, text/java");
        put("md", "text/plain");
        put("txt", "text/plain");
        put("asc", "text/plain");
        put("gif", "image/gif");
        put("jpg", "image/jpeg");
        put("jpeg", "image/jpeg");
        put("png", "image/png");
        put("mp3", "audio/mpeg");
        put("m3u", "audio/mpeg-url");
        put("mp4", "video/mp4");
        put("ogv", "video/ogg");
        put("flv", "video/x-flv");
        put("mov", "video/quicktime");
        put("swf", "application/x-shockwave-flash");
        put("js", "application/javascript");
        put("pdf", "application/pdf");
        put("doc", "application/msword");
        put("ogg", "application/x-ogg");
        put("zip", "application/octet-stream");
        put("exe", "application/octet-stream");
        put("class", "application/octet-stream");
    }};

    public static Response assetResponse(Context context, String path) {
        path = prepareAssetPath(path);

        try {
            InputStream inputStream = context.getAssets().open(path);
            Log.d("xhz", "return asset response");
            return new Response(Response.Status.OK, getMimeTypeForFile(path), inputStream);
        } catch (FileNotFoundException e) {
            return notFoundResponse();
        } catch (IOException e) {
            e.printStackTrace();
            return errorResponse();
        }
    }

    public static Response qrCodeResponse(Bitmap qrCode){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        qrCode.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] qrcodeData = bos.toByteArray();
        ByteArrayInputStream qrCodeInputStream = new ByteArrayInputStream(qrcodeData);

        return new Response(Response.Status.OK, "png", qrCodeInputStream);
    }

    public static Response htmlResponse(String html) {
        Log.d("xhz", "return html response");
        return new Response(Response.Status.OK, "text/html", html);
    }

    public static Response textResponse(String text) {
        Log.d("xhz", "return text response");
        return new Response(Response.Status.OK, "text/plain", text);
    }

    public static Response notFoundResponse() {
        Log.d("xhz", "return notFound response");
        return new Response(Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "404 Not Found");
    }

    public static Response errorResponse() {
        Log.d("xhz", "return error response");
        return new Response(Response.Status.INTERNAL_ERROR, NanoHTTPD.MIME_PLAINTEXT, "Internal server error");
    }

    public static String prepareAssetPath(String path) {
        //The asset manager expects a path without leading slash
        if (path.startsWith("/")) {
            path = path.substring(1);
        }

        return path;
    }

    public static String getMimeTypeForFile(String uri) {
        int dot = uri.lastIndexOf('.');
        String mime = null;
        if (dot >= 0) {
            mime = MIME_TYPES.get(uri.substring(dot + 1).toLowerCase());
        }
        return mime == null ? NanoHTTPD.MIME_PLAINTEXT : mime;
    }

    public abstract Response handle(String request);
}
