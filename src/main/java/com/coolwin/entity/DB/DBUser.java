package com.coolwin.entity.DB;

import com.coolwin.entity.appentity.AppUser;
import com.coolwin.entity.thirdentity.ThirdUser;
import com.coolwin.entity.thirdentity.ThirdUserDeta;
import com.coolwin.util.GsonUtil;
import com.coolwin.util.StringUtil;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by dell on 2017/5/5.
 */
public class DBUser {
    private Integer uid;
    private Integer ypid;
    private String sort;
    private String phone;
    private String nickname;
    private String headsmall;
    private String sign;
    private String openfire;
    private String password;
    private Integer gender;
    private String province;
    private String city;
    private Integer verify;
    private Long createtime;
    private String companywebsite;
    private String industry;
    private String company;
    private String companyaddress;
    private String job;
    private String provide;
    private String demand;
    private String telephone;
    private String usermenu;
    private String picture1;
    private String picture2;
    private String picture3;
    private String cover;
    private String remark;
    public AppUser getAppUser(){
        AppUser appUser = new AppUser();
        appUser.setUid(uid.toString());
        appUser.setPhone(phone);
        appUser.setNickname(nickname);
        appUser.setHeadsmall(headsmall);
        appUser.setHeadlarge(headsmall.replace("s_",""));
        appUser.setSign(sign);
        appUser.setPassword(openfire);
        appUser.setGender(gender.toString());
        appUser.setProvince(province);
        appUser.setCity(city);
        appUser.setVerify(verify.toString());
        appUser.setCreatetime(createtime.toString());
        appUser.setCompanywebsite(companywebsite);
        appUser.setIndustry(industry);
        appUser.setCompany(company);
        appUser.setCompanyaddress(companyaddress);
        appUser.setJob(job);
        appUser.setProvide(provide);
        appUser.setDemand(demand);
        appUser.setTelephone(telephone);
        appUser.setPicture1(picture1);
        appUser.setPicture2(picture2);
        appUser.setPicture3(picture3);
        ArrayList<String> pictureList = new ArrayList<>();
        if(!StringUtil.isNull(picture1)){
            pictureList.add(picture1);
        }
        if(!StringUtil.isNull(picture2)){
            pictureList.add(picture2);
        }
        if(!StringUtil.isNull(picture3)){
            pictureList.add(picture3);
        }
        appUser.setPicturelist(pictureList);
        appUser.setCover(cover);
        appUser.setUsermenu(GsonUtil.parseJsonWithGsonObject(usermenu,new TypeToken<ArrayList<DBUserMenu>>() {}.getType()));
        appUser.setRemark(remark);
        appUser.setUrltitle("商城");
        return appUser;
    }
    public AppUser getAppUser(ThirdUser thirdUser){
        AppUser appUser = getAppUser();
        //third
        if(thirdUser==null){
            return appUser;
        }
        appUser.setToken(thirdUser.getToken());
        appUser.setUrl(thirdUser.getUrl());
        appUser.setQuid(thirdUser.getQu_id());
        appUser.setUserdj(thirdUser.getUser_dj());
        appUser.setYpid(thirdUser.getYpid());
        appUser.setYpid2(thirdUser.getYpid2());
        appUser.setTid(thirdUser.getUser_id());
        appUser.setKa6id(thirdUser.getKa6_id());
        return appUser;
    }
    public AppUser getAppUser(ThirdUserDeta thirdUserDeta){
        AppUser appUser = getAppUser();
        //third
        if(thirdUserDeta==null){
            return appUser;
        }
        appUser.setLinkname(thirdUserDeta.getLinkname());
        appUser.setNickname(thirdUserDeta.getUsername());
        appUser.setHeadsmall(thirdUserDeta.getUserface());
        return appUser;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPicture1() {
        return picture1;
    }

    public void setPicture1(String picture1) {
        this.picture1 = picture1;
    }

    public String getPicture2() {
        return picture2;
    }

    public void setPicture2(String picture2) {
        this.picture2 = picture2;
    }

    public String getPicture3() {
        return picture3;
    }

    public void setPicture3(String picture3) {
        this.picture3 = picture3;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getYpid() {
        return ypid;
    }

    public void setYpid(Integer ypid) {
        this.ypid = ypid;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadsmall() {
        return headsmall;
    }

    public void setHeadsmall(String headsmall) {
        this.headsmall = headsmall;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOpenfire() {
        return openfire;
    }

    public void setOpenfire(String openfire) {
        this.openfire = openfire;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getVerify() {
        return verify;
    }

    public void setVerify(Integer verify) {
        this.verify = verify;
    }

    public Long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }

    public String getCompanywebsite() {
        return companywebsite;
    }

    public void setCompanywebsite(String companywebsite) {
        this.companywebsite = companywebsite;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyaddress() {
        return companyaddress;
    }

    public void setCompanyaddress(String companyaddress) {
        this.companyaddress = companyaddress;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getProvide() {
        return provide;
    }

    public void setProvide(String provide) {
        this.provide = provide;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUsermenu() {
        return usermenu;
    }

    public void setUsermenu(String usermenu) {
        this.usermenu = usermenu;
    }
}
