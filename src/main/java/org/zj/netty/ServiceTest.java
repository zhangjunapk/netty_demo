package org.zj.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

/**
 * @Author: ZhangJun
 * @Date: 2018/10/7 3:47
 * @Description:
 */
public class ServiceTest {
    public static void main(String[] args) {
        //事件循环组
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap=new ServerBootstrap();
        serverBootstrap
                .group(nioEventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(final SocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new ChannelHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                System.out.println("服务端");
                                ByteBuf byteBuf= (ByteBuf) msg;
                                byte[] b=new byte[byteBuf.readableBytes()];
                                byteBuf.readBytes(b);
                                System.out.println(new String(b));

/*
                                DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
                                response.content().setBytes(55,"hello".getBytes());
                                ctx.writeAndFlush(response);*/


                                //转换
                                ctx.writeAndFlush(Unpooled.copiedBuffer("sssssss".getBytes()));

                            }
                        });
                    }

                });
        //绑定
        ChannelFuture sync = serverBootstrap.bind(8888).sync();
        sync.channel().closeFuture().sync();
    }catch (Exception e){
            nioEventLoopGroup.shutdownGracefully();
        }
    }
}
