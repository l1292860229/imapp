package com.coolwin.dao;

import com.coolwin.entity.DB.DBUser;
import com.coolwin.entity.thirdentity.ThirdUserList;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.List;

/**
 * Created by dell on 2017/5/4.
 */
@Mapper
public interface UserMapper {
    @Select("SELECT u.*,ur.remark,picture1,picture2,picture3,cover,getmsg FROM im_user u " +
            "LEFT JOIN im_friend_user fu ON u.uid=fu.uid "+
            "LEFT JOIN (SELECT * FROM `im_user_friend_remark` WHERE uid=#{uid}) ur  ON ur.openid = u.phone " +
            "LEFT JOIN (SELECT * FROM `im_user_friend_getmsg` WHERE uid=#{uid}) ug  ON ug.openid = u.phone " +
            "where u.uid=#{uidOrPhoneOrNickname} or u.phone=#{uidOrPhoneOrNickname} or u.nickname=#{uidOrPhoneOrNickname}")
    DBUser getUser(@Param("uid")String uid,@Param("uidOrPhoneOrNickname") String uidOrPhoneOrNickname);

    @Select("select u.*,picture1,picture2,picture3,cover from im_user u " +
            "left join im_friend_user fu on u.uid=fu.uid  where phone=#{phone}")
    DBUser getUserByYpidAndPhone(@Param("ypid") String ypid,@Param("phone") String phone);

     @Select("<script>"+
             "SELECT u.*,ur.remark,picture1,picture2,picture3,cover,getmsg FROM im_user u " +
             "LEFT JOIN im_friend_user fu ON u.uid=fu.uid "+
             "LEFT JOIN (SELECT * FROM `im_user_friend_remark` WHERE uid=#{uid}) ur  ON ur.openid = u.phone " +
             "LEFT JOIN (SELECT * FROM `im_user_friend_getmsg` WHERE uid=#{uid}) ug  ON ug.openid = u.phone " +
             "where u.phone in "
             + "<foreach item='item' index='index' collection='thirdUserLists' open='(' separator=',' close=')'>"
             + "#{item.openid}"
             + "</foreach>"
             + "</script>")
    List<DBUser> getUserListByThirdUserList(@Param("uid") String uid, @Param("thirdUserLists") Collection<ThirdUserList> thirdUserLists);

    @Insert("insert into im_user (ypid,sort,phone,nickname,headsmall,openfire,createtime,telephone,province) " +
            "values(#{ypid},#{sort},#{phone},#{nickname},#{headsmall},#{openfire},#{createtime},#{telephone},#{province})")
    void insertUser(@Param("ypid")String ypid,@Param("sort") String sort,@Param("phone")String phone,
                    @Param("nickname")String nickname,@Param("headsmall")String headsmall,@Param("openfire")String openfire,
                    @Param("createtime")String createtime, @Param("telephone")String telephone,@Param("province")String province);

    @Update("update im_user set sign=#{sign},city=#{city},province=#{province},gender=#{gender},nickname=#{nickname}," +
            "headsmall=#{headsmall},companywebsite=#{companywebsite},industry=#{industry},company=#{company},companyaddress=#{companyaddress}," +
            "company=#{company},companyaddress=#{companyaddress} ,job=#{job},provide=#{provide},demand=#{demand},telephone=#{telephone}" +
            " where uid=#{uid}")
    void updateUser(@Param("uid")String uid, @Param("sign")String sign, @Param("city") String city,@Param("province") String province,
                    @Param("gender") String gender,@Param("nickname") String nickname,@Param("headsmall") String headsmall,
                    @Param("companywebsite") String companywebsite,@Param("industry") String industry, @Param("company") String  company,
                    @Param("companyaddress")String companyaddress,@Param("job")String job,@Param("provide")String provide,
                    @Param("demand") String demand,@Param("telephone")  String telephone);
}
