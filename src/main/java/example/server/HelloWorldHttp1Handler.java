/*
 * Copyright 2014 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package example.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http2.HttpConversionUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.util.internal.ObjectUtil.checkNotNull;

/**
 * HTTP handler that responds with a "Hello World"
 */
public class HelloWorldHttp1Handler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private final String establishApproach;

    public HelloWorldHttp1Handler(String establishApproach) {
        this.establishApproach = checkNotNull(establishApproach, "establishApproach");
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        if (HttpUtil.is100ContinueExpected(req)) {
            ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
        }
        int streamId = 3;
        if (req.headers().contains(HttpConversionUtil.ExtensionHeaderNames.STREAM_ID.text())) {
            req.headers().getInt(HttpConversionUtil.ExtensionHeaderNames.STREAM_ID.text());
        }
        boolean keepAlive = HttpUtil.isKeepAlive(req);

        String path = req.uri().substring(1);
        URL url = getClass().getClassLoader().getResource(path);
        Path filePath = null;
        try {
            if (url != null) {
                filePath = Paths.get(url.toURI());
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        ByteBuf content = ctx.alloc().buffer();
        boolean html = false;
        if (filePath != null && Files.exists(filePath) && Files.isRegularFile(filePath)) {
            html = true;
            try {
                byte[] bytes = Files.readAllBytes(Paths.get(url.toURI()));
                content.writeBytes(bytes);
            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }
        } else {
            content.writeBytes(HelloWorldHttp2Handler.RESPONSE_BYTES.duplicate());
            ByteBufUtil.writeAscii(content, " - via " + req.protocolVersion() + " (" + establishApproach + ")");
        }
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, content);
        response.headers().set(HttpConversionUtil.ExtensionHeaderNames.STREAM_ID.text(), streamId);
        if (html) {
            response.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
        } else {
            response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
        }
        response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());

        if (!keepAlive) {
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            ctx.write(response);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
