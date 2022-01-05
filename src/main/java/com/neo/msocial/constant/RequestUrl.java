package com.neo.msocial.constant;

public interface RequestUrl {

    interface StepUrl {
        String STEP_2_URL = "http://10.252.12.237:4122/services/SqlServices/query?Service=system_parameter&Provider=default&ParamSize=0";
        String STEP_3_URL = "http://10.252.12.237:4122/services/SqlServices/query?Service=sql_check_password_partner&Provider=default&ParamSize=2&reponse=application/json";
        String STEP_8_URL = "http://10.252.12.237:4122/services/SqlServices/query?Service=spamcheck&Provider=default&ParamSize=1&P1=3178";
        String STEP_9_URL = "http://10.252.12.237:4122/services/SqlServices/query?Service=get_mt&Provider=default&ParamSize=0&reponse=application/json";
        String STEP_10_URL = "http://10.252.12.237:4122/services/SqlServices/query?Service=get_service_service_package&Provider=default&ParamSize=1&reponse=application/json&P1=3178";
        String STEP_11_URL = "http://10.252.12.237:4122/services/SqlServices/query?Service=get_partner_type&Provider=default&ParamSize=1&response=application/json&P1=261";
        String STEP_12_URL = "http://10.252.12.237:4122/services/SqlServices/query?Service=trans_refused&Provider=default&ParamSize=1&P1=sharingkey";
        String STEP_13_URL = "http://10.252.12.237:4122/services/SqlServices/query?Service=script_shop_information&Provider=default&ParamSize=2&response=application/json&P1=2917&P2=SMS";
        String STEP_14_URL = "http://10.252.12.237:4122/services/SqlServices/query?Service=trans_wait&Provider=default&ParamSize=1&P1=sharingkey&response=application/json";
        String STEP_15_URL = "http://10.252.12.237:4122/services/SqlServices/query?Service=trans_wait_per_service&Provider=default&ParamSize=2&P1=msisdn&response=application/json&P2=serviceid";
        String STEP_16_URL = "http://10.252.12.237:4122/services/SqlServices/query?Service=trans_refused_per_service&Provider=default&ParamSize=2&P1=msisdn&response=application/json&P2=serviceid";
    }

    interface Check {
        String LOCK_DB = "http://10.252.12.237:4122/services/SqlServices/update?response=application/json&Service=sharing_key_lock&Provider=default&ParamSize=1";
        String POLICY = "http://10.252.12.237:4122/services/SqlServices/query?response=application/json&Service=checkLoiDungNotSendSms&Provider=default&ParamSize=2";
        String PRICE = "http://10.252.12.237:4122/services/SqlServices/query?response=application/json&Service=checkLoiDungCheckPrice&Provider=default&ParamSize=3";
    }
}