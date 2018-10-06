package org.zj.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author: ZhangJun
 * @Date: 2018/10/7 3:47
 * @Description:
 */
public class ClientTest {
    public static void main(String[] args) {
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        try{
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(nioEventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel channel) throws Exception {
                     channel.pipeline().addLast(new ChannelHandlerAdapter (){
                         @Override
                         public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                             System.out.println("ffffffff");
                             ByteBuf byteBuf= (ByteBuf) msg;
                             byte[] b=new byte[byteBuf.readableBytes()];
                             byteBuf.readBytes(b);
                             System.out.println(new String(b));

                         }
                     });
                    }

        });


        ChannelFuture localhost = bootstrap.connect("localhost", 8888).sync();
            //转换
        localhost.channel().writeAndFlush(Unpooled.copiedBuffer("cccccccccc".getBytes()));
        localhost.channel().closeFuture().sync();

            nioEventLoopGroup.shutdownGracefully();
    }catch (Exception e){
        }
    }
}
