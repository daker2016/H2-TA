package example;

/**
 * Some parameters that control the behavior of the client and the server. Be free to modify them is necessary.
 */
public class Parameters {

    public static final boolean SSL = true; // Use HTTPS if true, otherwise HTTP
    public static final int PORT = 8443;
    public static final String SERVER_HOST = "localhost";
    public static final String SERVER_URL = "/";
    public static final boolean CLIENT_ENABLE_HTTP2 = false; // If true, the client talks HTTP/2, other wise HTTP/1.x
}
