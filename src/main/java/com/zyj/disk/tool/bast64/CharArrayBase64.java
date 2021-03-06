package com.zyj.disk.tool.bast64;

/**
 * @Author: ZYJ
 * @Date: 2022/6/14 9:16
 * @Remark:
 */
public final class CharArrayBase64 extends AbstractBase64{
    public static String encodeToString(char[] chars){
        return new String(encode(toBytes(chars)));
    }

    private static byte[] toBytes(char[] chars){
        byte[] temp = new byte[chars.length];
        for(int i=0;i<temp.length;++i) temp[i] = (byte)chars[i];
        return temp;
    }
}