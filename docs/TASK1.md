GOAL
===
Implement a client that supports both HTTP/1.x and HTTP/2, make HTTP GET and HTTP POST requests with it.


GUIDE
===
1. Follow the [installation guide](../INSTALL.md) to make the dev environment.
1. Run the demo application, which makes an HTTP `GET` request to a mirror server.
    1. The default is a HTTPS to the server.
    1. In [Parameters.java](../src/main/java/example/Parameters.java), change `SSL` to `false` to make an HTTP request.
    1. In [Parameters.java](../src/main/java/example/Parameters.java), change `CLIENT_ENABLE_HTTP2` to `false` to make HTTP/1.x requests
1. Modify the demo application to make an HTTP `POST` request to the mirror server.
    1. Change code in [Http2Client.java](../src/main/java/example/client/Http2Client.java) and fill in the block to send one HTTP POST request.
    1. Like the previous step, change the parameters `SSL` and `CLIENT_ENABLE_HTTP2` to use different protocols.
