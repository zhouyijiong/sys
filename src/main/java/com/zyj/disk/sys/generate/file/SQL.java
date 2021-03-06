package com.zyj.disk.sys.generate.file;

import com.zyj.disk.sys.generate.FieldInfo;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author: ZYJ
 * @Date: 2022/4/13
 * @Remark: 生成 SQL 类
 */
public final class SQL extends FileType{
    StringBuilder table;

    public SQL(){
        super("");
        table = new StringBuilder(fieldInfos.size() << 3);
    }

    @Override
    protected BufferedOutputStream createFile(String path)throws IOException{
        return createDirectoryAndFile("./src/main/resources/sql/" + sourceName.toLowerCase());
    }

    @Override
    void createHead(BufferedOutputStream bos,String packageName)throws IOException{
        String tbName = sourceName.toLowerCase();
        table.append("CREATE TABLE ").append(tbName).append("(");
        for(FieldInfo item : fieldInfos) getRow(item);
        table.delete(table.length()-1,table.length()).append("\n);");
        bos.write(table.toString().getBytes(StandardCharsets.UTF_8));
        bos.write(("\nALTER TABLE " + tbName + " DEFAULT CHARSET utf8;").getBytes(StandardCharsets.UTF_8));
    }

    @Override
    void createBody(BufferedOutputStream bos){}

    private void getRow(FieldInfo fieldInfo){
        String key = fieldInfo.getKey();
        String type = fieldInfo.getDataType();
        Object value = fieldInfo.getValue();
        boolean unique = fieldInfo.isUnique();
        boolean required = fieldInfo.isRequired();
        boolean primary = fieldInfo.isPrimary();
        table.append("\n\t").append(key).append(" ").append(type);
        if(primary) table.append(" primary key auto_increment");
        if(value != null) table.append(" DEFAULT ").append(value);
        if(required) table.append(" NOT NULL");
        table.append(unique ? " UNIQUE," : ",");
    }
}