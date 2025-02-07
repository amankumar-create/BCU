
package com.loduone.damn.core;

import java.util.HashMap;
import java.util.Map;

public class StatusCode {
    public static final int SWITCHING_PROTOCOLS = 101;
    public static final int PROCESSING = 102;
    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int ACCEPTED = 202;
    public static final int NON_AUTHORITATIVE_INFORMATION = 203;
    public static final int RESET_CONTENT = 205;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int PAYMENT_REQUIRED = 402;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int METHOD_NOT_ALLOWED = 405;
    public static final int REGISTRATION_CLOSED = 406;
    public static final int GONE = 410;
    public static final int REQUEST_ENTITY_TOO_LARGE = 413;
    public static final int ENHANCE_YOUR_CALM = 420;
    public static final int UNPROCESSABLE_ENTITY = 422;
    public static final int LOCKED = 423;
    public static final int FAILED_DEPENDENCY = 424;
    public static final int UPGRADE_REQUIRED = 426;
    public static final int RETRY_WITH = 449;
    public static final int SESSION_EXPIRED = 450;
    public static final int SINGLE_SESSION_ALLOWED = 451;
    public static final int RELOGIN_NEEDED_AFTER_ACTIVATION = 452;
    public static final int RELOGIN_NEEDED_AFTER_BOUND = 453;
    public static final int RELOGIN_NEEDED_AFTER_UNBOUND = 454;
    public static final int RELOGIN_NEEDED_AFTER_ECONOMY_UPDATE = 455;
    public static final int FB_UNAVAILABLE = 456;
    public static final int USER_BLOCKED = 460;
    public static final int ALREADY_EXISTS = 461;
    public static final int USER_REMOVED = 470;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int NOT_IMPLEMENTED = 501;
    public static final int SERVICE_UNAVAILABLE = 503;
    public static final int VERSION_NOT_SUPPORTED = 505;
    public static final int EXPIRED = 805;
    private static final Map<Integer, String> defaultMessages = new HashMap<Integer, String>() {
        {
            this.put(101, "switch protocol");
            this.put(102, "processing");
            this.put(200, "ok");
            this.put(201, "created");
            this.put(202, "accepted");
            this.put(203, "non authoritative information");
            this.put(205, "reset content");
            this.put(400, "bad request");
            this.put(401, "unauthorized");
            this.put(403, "forbidden");
            this.put(404, "not found");
            this.put(405, "method not allowed");
            this.put(413, "request entity too large");
            this.put(422, "unprocessable entity");
            this.put(423, "locked");
            this.put(424, "failed dependency");
            this.put(426, "upgrade required");
            this.put(449, "retry with");
            this.put(500, "internal server error");
            this.put(501, "not implemented");
            this.put(503, "service unavailable");
            this.put(505, "version not supported");
        }
    };


    public static String getMessage(int statusCode) {
        String message = (String)defaultMessages.get(statusCode);
        return message == null ? "<no description>" : message;
    }
}
