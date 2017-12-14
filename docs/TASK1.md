GOAL
===
Implement a client that supports both HTTP/1.x and HTTP/2, make HTTP GET and HTTP POST requests with it.


Demo Server
===
We have built a demo server, the host name is prometheus-1153.appspot.com.
There are two web pages:

1. Request info page (https://prometheus-1153.appspot.com/): show the info of the request

1. Mirror page (https://prometheus-1153.appspot.com/mirror/): respond with what the request contains
    - For the HTTP GET request, it mirrors the request parameter value of the key `response`
    - For the HTTP POST request, it mirrors the request body

Both web pages support HTTP & HTTPS, HTTP/1.x & HTTP/2, GET & POST.


GUIDE
===
1. Follow the [installation guide](../INSTALL.md) to make the dev environment.
1. Run the demo application, which makes an HTTP `GET` request to the demo server, the URL is [https://prometheus-1153.appspot.com/mirror/](https://prometheus-1153.appspot.com/mirror/).
    1. The default is a HTTPS request to the server.
    1. In [Parameters.java](../src/main/java/example/Parameters.java), change `SSL` to `false` to make an HTTP request.
    1. In [Parameters.java](../src/main/java/example/Parameters.java), change `CLIENT_ENABLE_HTTP2` to `false` to make HTTP/1.x requests
1. Modify the demo application to make an HTTP `POST` request to the mirror server.
    1. Change code in [Http2Client.java](../src/main/java/example/client/Http2Client.java) and fill in the block to send one HTTP POST request.
    1. Like the previous step, change the parameters `SSL` and `CLIENT_ENABLE_HTTP2` to use different protocols.
