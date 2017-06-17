package com.coolwin.dao;

import com.coolwin.entity.DB.DBInformation;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by dell on 2017/5/4.
 */
@Mapper
public interface InformationMapper {
    @Select("SELECT id,uid,ypid,content,title,price,picture,shopurl,video,type FROM im_friend  " +
            "where uid=#{uid} and ypid=#{ypid} and type=#{type} LIMIT #{page},20")
    List<DBInformation> getInformation(@Param("uid") String uid, @Param("ypid") String ypid,
                                       @Param("type") String type, @Param("page") int page);

    @Insert("insert into im_friend(uid,ypid,content,title,price,picture,shopurl,video,type,createtime,isshow,verify) " +
            " values(#{commodity.uid},#{commodity.ypid},#{commodity.content},#{commodity.title}," +
            " #{commodity.price},#{commodity.picture},#{commodity.shopurl},#{commodity.video},#{commodity.type},UNIX_TIMESTAMP(NOW()),1,1)")
    void insertInformation(@Param("commodity") DBInformation commodity);

    @Delete(" delete from im_friend where id=#{id}")
    void deleteInformation(@Param("id") String id);
}
