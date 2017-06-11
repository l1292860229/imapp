package com.coolwin.dao;

import com.coolwin.entity.DB.DBShopIndex;
import org.apache.ibatis.annotations.*;

/**
 * Created by dell on 2017/5/4.
 */
@Mapper
public interface ShopIndexMapper {
    @Select("SELECT uid,ypid,ka6id,shopindex FROM im_user_shopindex  " +
            "where uid=#{uid} and ypid=#{ypid} and ka6id=#{ka6id}")
    DBShopIndex getShopIndex(@Param("uid") String uid, @Param("ka6id") String ka6id, @Param("ypid") String ypid);

    @Insert("insert into im_user_shopindex values(#{uid},#{ypid},#{ka6id},#{data})")
    void insertShopIndex(@Param("uid") String uid, @Param("ka6id") String ka6id, @Param("ypid") String ypid,@Param("data") String shopindex);

    @Update(" update im_user_shopindex set shopindex=#{data} where uid=#{uid} and ypid=#{ypid} and ka6id=#{ka6id} ")
    void updateShopIndex(@Param("uid") String uid, @Param("ka6id") String ka6id, @Param("ypid") String ypid,@Param("data") String shopindex);
}
