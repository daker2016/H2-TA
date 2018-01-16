MOTIVATION
===
HTTP/2 based remote procedure call (RPC) applications (i.e. gRPC) carry deadline information for each stream.
I.g. a client initiate a stream with the deadline set as 10 ms. Then the server should transfer the response back within 10 ms.
Otherwise the stream will be abandoned by the client, causing bandwidth waste. 

One good practice is to prioritize near-deadline streams over far-deadline streams in the server side. 
So we need to make the HTTP/2 stream carry the deadline information.

GOAL
===
Compose a protocol change to HTTP/2 that makes streams be with deadline information at the time of initiation. Then make the server schedule the streams with EDF algorithm (earliest deadline first, https://en.wikipedia.org/wiki/Earliest_deadline_first_scheduling). 

GUIDES
===
There is no step-by-step guide, you should be familiar with following things before starting your design.
1. The lifecycle of HTTP/2 streams.
2. The format for HTTP/2 packets.
3. The framework of Netty, especially the codec part.
4. The implementation of HTTP/2's default scheduling algorithm.

When designing, please try to answer the following questions.

INSIGHTS
===
1. How to represent deadline?
1. Where to put the deadline value, inside the HTTP header or the HTTP/2 header?
1. How to keep compatibility with the legacy HTTP/2?
