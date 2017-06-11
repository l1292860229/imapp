package com.coolwin.dao;

import com.coolwin.entity.DB.DBCommodity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by dell on 2017/5/4.
 */
@Mapper
public interface CommodityMapper {
    @Select("SELECT id,uid,ypid,content,title,price,picture,shopurl,video FROM im_friend  " +
            "where uid=#{uid} and ypid=#{ypid} and type='商品' LIMIT #{page},20")
    List<DBCommodity> getCommodity(@Param("uid") String uid,  @Param("ypid") String ypid, @Param("page") int page);

    @Insert("insert into im_friend(uid,ypid,content,title,price,picture,shopurl,video,type,createtime,isshow,verify) " +
            " values(#{commodity.uid},#{commodity.ypid},#{commodity.content},#{commodity.title}," +
            " #{commodity.price},#{commodity.picture},#{commodity.shopurl},#{commodity.video},'商品',UNIX_TIMESTAMP(NOW()),1,1)")
    void insertCommodity(@Param("commodity") DBCommodity commodity);

    @Delete(" delete from im_friend where id=#{id}")
    void deleteCommodity(@Param("id") String id);
}
