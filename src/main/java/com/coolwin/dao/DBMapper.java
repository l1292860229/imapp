package com.coolwin.dao;

import com.coolwin.entity.DB.DBUserMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by dell on 2017/5/4.
 */
@Mapper
public interface DBMapper {
    @Select("select * from im_user_usermenu")
    List<DBUserMenu> getUser();
}
