package com.neo.msocial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;

public class SystemParameterDTO_Step2 {

    String JOB_EXP_DATA_DIR_EXP_LOCAL;
    String JOB_EXP_DATA_DIR_FTP;
    String LIST_EMAIL_REPORT_DUYTRI;
    String LIST_EMAIL_REPORT_JOB;
    String LIST_EMAIL_REPORT_PHATSINH;
    String LOCK_TIME;
    String LOGIN_MAX;
    String LOGSYSTEM;
    String ON_OFF_LOCK;
    String SERVER_FROM;
    String SERVER_PORT;
    String SERVER_HOST;
    String SERVER_PASS;
    String SERVER_USER;

    public SystemParameterDTO_Step2() {
    }

    public SystemParameterDTO_Step2(String JOB_EXP_DATA_DIR_EXP_LOCAL, String JOB_EXP_DATA_DIR_FTP, String LIST_EMAIL_REPORT_DUYTRI, String LIST_EMAIL_REPORT_JOB, String LIST_EMAIL_REPORT_PHATSINH, String LOCK_TIME, String LOGIN_MAX, String LOGSYSTEM, String ON_OFF_LOCK, String SERVER_FROM, String SERVER_HOST, String SERVER_PASS) {
        this.JOB_EXP_DATA_DIR_EXP_LOCAL = JOB_EXP_DATA_DIR_EXP_LOCAL;
        this.JOB_EXP_DATA_DIR_FTP = JOB_EXP_DATA_DIR_FTP;
        this.LIST_EMAIL_REPORT_DUYTRI = LIST_EMAIL_REPORT_DUYTRI;
        this.LIST_EMAIL_REPORT_JOB = LIST_EMAIL_REPORT_JOB;
        this.LIST_EMAIL_REPORT_PHATSINH = LIST_EMAIL_REPORT_PHATSINH;
        this.LOCK_TIME = LOCK_TIME;
        this.LOGIN_MAX = LOGIN_MAX;
        this.LOGSYSTEM = LOGSYSTEM;
        this.ON_OFF_LOCK = ON_OFF_LOCK;
        this.SERVER_FROM = SERVER_FROM;
        this.SERVER_HOST = SERVER_HOST;
        this.SERVER_PASS = SERVER_PASS;
    }

    public String getSERVER_USER() {
        return SERVER_USER;
    }

    public void setSERVER_USER(String SERVER_USER) {
        this.SERVER_USER = SERVER_USER;
    }

    public String getSERVER_PORT() {
        return SERVER_PORT;
    }

    public void setSERVER_PORT(String SERVER_PORT) {
        this.SERVER_PORT = SERVER_PORT;
    }

    public String getJOB_EXP_DATA_DIR_EXP_LOCAL() {
        return JOB_EXP_DATA_DIR_EXP_LOCAL;
    }

    public void setJOB_EXP_DATA_DIR_EXP_LOCAL(String JOB_EXP_DATA_DIR_EXP_LOCAL) {
        this.JOB_EXP_DATA_DIR_EXP_LOCAL = JOB_EXP_DATA_DIR_EXP_LOCAL;
    }

    public String getJOB_EXP_DATA_DIR_FTP() {
        return JOB_EXP_DATA_DIR_FTP;
    }

    public void setJOB_EXP_DATA_DIR_FTP(String JOB_EXP_DATA_DIR_FTP) {
        this.JOB_EXP_DATA_DIR_FTP = JOB_EXP_DATA_DIR_FTP;
    }

    public String getLIST_EMAIL_REPORT_DUYTRI() {
        return LIST_EMAIL_REPORT_DUYTRI;
    }

    public void setLIST_EMAIL_REPORT_DUYTRI(String LIST_EMAIL_REPORT_DUYTRI) {
        this.LIST_EMAIL_REPORT_DUYTRI = LIST_EMAIL_REPORT_DUYTRI;
    }

    public String getLIST_EMAIL_REPORT_JOB() {
        return LIST_EMAIL_REPORT_JOB;
    }

    public void setLIST_EMAIL_REPORT_JOB(String LIST_EMAIL_REPORT_JOB) {
        this.LIST_EMAIL_REPORT_JOB = LIST_EMAIL_REPORT_JOB;
    }

    public String getLIST_EMAIL_REPORT_PHATSINH() {
        return LIST_EMAIL_REPORT_PHATSINH;
    }

    public void setLIST_EMAIL_REPORT_PHATSINH(String LIST_EMAIL_REPORT_PHATSINH) {
        this.LIST_EMAIL_REPORT_PHATSINH = LIST_EMAIL_REPORT_PHATSINH;
    }

    public String getLOCK_TIME() {
        return LOCK_TIME;
    }

    public void setLOCK_TIME(String LOCK_TIME) {
        this.LOCK_TIME = LOCK_TIME;
    }

    public String getLOGIN_MAX() {
        return LOGIN_MAX;
    }

    public void setLOGIN_MAX(String LOGIN_MAX) {
        this.LOGIN_MAX = LOGIN_MAX;
    }

    public String getLOGSYSTEM() {
        return LOGSYSTEM;
    }

    public void setLOGSYSTEM(String LOGSYSTEM) {
        this.LOGSYSTEM = LOGSYSTEM;
    }

    public String getON_OFF_LOCK() {
        return ON_OFF_LOCK;
    }

    public void setON_OFF_LOCK(String ON_OFF_LOCK) {
        this.ON_OFF_LOCK = ON_OFF_LOCK;
    }

    public String getSERVER_FROM() {
        return SERVER_FROM;
    }

    public void setSERVER_FROM(String SERVER_FROM) {
        this.SERVER_FROM = SERVER_FROM;
    }

    public String getSERVER_HOST() {
        return SERVER_HOST;
    }

    public void setSERVER_HOST(String SERVER_HOST) {
        this.SERVER_HOST = SERVER_HOST;
    }

    public String getSERVER_PASS() {
        return SERVER_PASS;
    }

    public void setSERVER_PASS(String SERVER_PASS) {
        this.SERVER_PASS = SERVER_PASS;
    }


    @Override
    public String toString() {
        return "SystemParameterDTO{" +
                "JOB_EXP_DATA_DIR_EXP_LOCAL='" + JOB_EXP_DATA_DIR_EXP_LOCAL + '\'' +
                ", JOB_EXP_DATA_DIR_FTP='" + JOB_EXP_DATA_DIR_FTP + '\'' +
                ", LIST_EMAIL_REPORT_DUYTRI='" + LIST_EMAIL_REPORT_DUYTRI + '\'' +
                ", LIST_EMAIL_REPORT_JOB='" + LIST_EMAIL_REPORT_JOB + '\'' +
                ", LIST_EMAIL_REPORT_PHATSINH='" + LIST_EMAIL_REPORT_PHATSINH + '\'' +
                ", LOCK_TIME='" + LOCK_TIME + '\'' +
                ", LOGIN_MAX='" + LOGIN_MAX + '\'' +
                ", LOGSYSTEM='" + LOGSYSTEM + '\'' +
                ", ON_OFF_LOCK='" + ON_OFF_LOCK + '\'' +
                ", SERVER_FROM='" + SERVER_FROM + '\'' +
                ", SERVER_PORT='" + SERVER_PORT + '\'' +
                ", SERVER_HOST='" + SERVER_HOST + '\'' +
                ", SERVER_PASS='" + SERVER_PASS + '\'' +
                ", SERVER_USER='" + SERVER_USER + '\'' +
                '}';
    }
}