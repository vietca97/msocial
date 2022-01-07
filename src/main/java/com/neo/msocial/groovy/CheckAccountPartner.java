package com.neo.msocial.groovy;

import com.neo.msocial.dto.Soap37;
import com.neo.msocial.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class CheckAccountPartner {

    @Autowired
    private RedisUtils context;

    boolean checkAccount(List<Soap37> lst) {
        boolean ret = false;
        try {
        } catch (Exception ex) {
            context.put("content", "");
        }
        try {
            if (Integer.parseInt(lst.get(0).getCHECK_ACCOUNT_API()) < 1) ret = false;
            else ret = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            ret = false;
        } finally {
            if (!ret) {
                context.put("ErrorCodeAPI", "-2");
                context.put("ErrorDescAPI", "Username/Password khong dung");
            }
            return ret;
        }
    }


}
