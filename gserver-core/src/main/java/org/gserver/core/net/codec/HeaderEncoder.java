package org.gserver.core.net.codec;

import org.gserver.core.net.Header;
import org.gserver.core.net.Message;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * 对包的头文件进行编码
 * 
 * @author zhaohui
 * 
 */
public class HeaderEncoder extends OneToOneEncoder {

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {
		if (!(msg instanceof Message)) {
			return msg;
		}

		Message message = (Message) msg;
		byte[] buffer = (byte[]) message.getData();
		Header header = message.getHeader();

		ChannelBuffer allBuffer = ChannelBuffers.dynamicBuffer();
		allBuffer.writeByte(HeaderDecoder.PACKAGE_TAG);
		allBuffer.writeByte(header.getEncode());
		allBuffer.writeByte(header.getEncrypt());
		allBuffer.writeByte(header.getExtend1());
		allBuffer.writeByte(header.getExtend2());
		allBuffer.writeInt(header.getSessionId());
		allBuffer.writeInt(buffer.length);
		allBuffer.writeInt(header.getCommandId());
		allBuffer.writeBytes(buffer);
		return allBuffer;
	}
}
