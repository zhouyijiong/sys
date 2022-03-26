package com.zyj.disk.sys.hikari.mapper.explain;

import com.zyj.disk.sys.annotation.mapper.Select;
import com.zyj.disk.sys.entity.BaseEntity;
import com.zyj.disk.sys.entity.Record;
import com.zyj.disk.sys.hikari.Actuator;
import com.zyj.disk.sys.tool.ClassTool;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import java.lang.annotation.Annotation;

/**
 * @Author: ZYJ
 * @Date: 2022/3/25 9:44
 * @Remark: select mapper
 */
@Component
public final class SelectMapper extends Mapper{
    private Select select;
    private final ClassTool classTool;
    private final Record record = Record.initialize(this.getClass());

    public SelectMapper(Actuator actuator,ClassTool classTool){
        super(actuator);
        this.classTool = classTool;
    }

    @Override
    public boolean check(ProceedingJoinPoint joinPoint,Annotation annotation){
        select = (Select) annotation;
        return select.mapperMatch().MATCH.check(joinPoint,select);
    }

    @Override
    public String explain(ProceedingJoinPoint joinPoint,Annotation annotation){
        return check(joinPoint,annotation) ? select.mapperMatch().MATCH.explain(joinPoint,select) : null;
    }

    @Override
    public Object handle(ProceedingJoinPoint joinPoint,Annotation annotation){
        try{
            BaseEntity[] result = actuator.select(classTool.getEntity(select.result()),explain(joinPoint,annotation));
            if(result == null) return null;
            switch(result.length){
                case 0 : return null;
                case 1 : return result[0];
                default: return result;
            }
        }catch(Exception e){
            record.error(e);
        }
        return null;
    }
}