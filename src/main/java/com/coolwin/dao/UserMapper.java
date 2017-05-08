package com.coolwin.dao;

import com.coolwin.entity.DB.DBUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by dell on 2017/5/4.
 */
@Mapper
public interface UserMapper {
    @Select("SELECT u.*,ur.remark,picture1,picture2,picture3,cover FROM im_user u " +
            "LEFT JOIN im_friend_user fu ON u.uid=fu.uid "+
            "LEFT JOIN (SELECT * FROM `im_user_friend_remark` WHERE uid=#{uid}) ur  ON ur.openid = u.phone " +
            "where u.uid=#{uidOrPhoneOrNickname} or u.phone=#{uidOrPhoneOrNickname} or u.nickname=#{uidOrPhoneOrNickname}")
    DBUser getUser(@Param("uid")String uid,@Param("uidOrPhoneOrNickname") String uidOrPhoneOrNickname);

    @Select("select u.*,picture1,picture2,picture3,cover from im_user u " +
            "left join im_friend_user fu on u.uid=fu.uid  where phone=#{phone}")
    DBUser getUserByYpidAndPhone(@Param("ypid") String ypid,@Param("phone") String phone);

    @Insert("insert into im_user (ypid,sort,phone,nickname,headsmall,openfire,createtime,telephone,province) " +
            "values(#{ypid},#{sort},#{phone},#{nickname},#{headsmall},#{openfire},#{createtime},#{telephone},#{province})")
    void insertUser(@Param("ypid")String ypid,@Param("sort") String sort,@Param("phone")String phone,
                    @Param("nickname")String nickname,@Param("headsmall")String headsmall,@Param("openfire")String openfire,
                    @Param("createtime")String createtime, @Param("telephone")String telephone,@Param("province")String province);
}
