package com.changhaismile.opengl.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * @author changhaismile
 * @name Utils
 * @comment //TODO
 * @date 2017/10/24
 */

public class Utils {
    public static FloatBuffer getFloatBuff(float[] vertexs) {
        FloatBuffer buffer;
        //初始化buffer，数组长度 * 4，因为一个 float 占4个字节
        ByteBuffer bf = ByteBuffer.allocateDirect(vertexs.length * 4);
        //数组排序用nativeOrder
        bf.order(ByteOrder.nativeOrder());
        buffer = bf.asFloatBuffer();
        //写入数组
        buffer.put(vertexs);
        //数组默认的读取位置
        buffer.position(0);
        return buffer;
    }

    public static int byte4ToInt(byte[] bytes, int offset) {
        int b3 = bytes[offset + 3] & 0xFF;
        int b2 = bytes[offset + 2] & 0xFF;
        int b1 = bytes[offset + 1] & 0xFF;
        int b0 = bytes[offset + 0] & 0xFF;
        return (b3 << 24) | (b2 << 16) | (b1 << 8) | b0;
    }

    public static short byte2ToShort(byte[] bytes, int offset) {
        int b1 = bytes[offset + 1] & 0xFF;
        int b0 = bytes[offset + 0] & 0xFF;
        return (short) ((b1 << 8) | b0);
    }

    public static float byte4ToFloat(byte[] bytes, int offset) {
        return Float.intBitsToFloat(byte4ToInt(bytes, offset));
    }
}
