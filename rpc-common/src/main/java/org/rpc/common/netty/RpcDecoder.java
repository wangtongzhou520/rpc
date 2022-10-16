package org.rpc.common.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.rpc.common.utils.SerializationUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * decoder
 *
 * @author wangtongzhou
 * @since 2022-10-10 21:04
 */
@Component
public class RpcDecoder extends ByteToMessageDecoder {

    private Class<?> genericClass;

    public RpcDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext,
                          ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        //记录位置方便操作
        in.markReaderIndex();
        int dataLength = in.readInt();
        //没有足够的数据不进行出路
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);
        out.add(SerializationUtil.deserialize(data, genericClass));
    }
}
