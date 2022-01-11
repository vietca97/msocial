package com.neo.msocial.rest;

import com.neo.msocial.dto.*;
import com.neo.msocial.groovy.*;
import com.neo.msocial.request.*;
import com.neo.msocial.utils.GenericsRequest;
import com.neo.msocial.utils.RedisUtils;
import com.neo.msocial.utils.SystemParameterServices;
import com.neo.msocial.utils.UtilServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/registerServicePartner")
public class RegisterServicePartnerController {

    private final SystemParameterServices systemParameterServices;
    private final UtilServices utilServices;
    private final RedisUtils context;
    private final Spam spam;
    private final ChongLoiDung chongLoiDung;
    private final CheckStep6 checkStep6;
    private final ThuebaoHuyDichVu thuebaoHuyDichVu;
    private final ThuebaoSudungDichvu thuebaoSudungDichvu;


    private final GenericsRequest<Soap28> request28;

    private final GenericsRequest<Soap15> request15;

    private final GenericsRequest<Soap37> request37;

    private final GenericsRequest<Soap8> request8;

    private final GenericsRequest<Soap16> request16;

    private final GenericsRequest<Soap17> request17;

    private final GenericsRequest<Soap19> request19;


    public RegisterServicePartnerController(UtilServices utilServices, RedisUtils context, SystemParameterServices systemParameterServices, Spam spam, ChongLoiDung chongLoiDung, CheckStep6 checkStep6, ThuebaoHuyDichVu thuebaoHuyDichVu, ThuebaoSudungDichvu thuebaoSudungDichvu, GenericsRequest<Soap28> request28, GenericsRequest<Soap15> request15, GenericsRequest<Soap37> request37, GenericsRequest<Soap8> request8, GenericsRequest<Soap16> request16, GenericsRequest<Soap17> request17, GenericsRequest<Soap19> request19) {

        this.context = context;
        this.utilServices = utilServices;
        this.systemParameterServices = systemParameterServices;
        this.spam = spam;
        this.chongLoiDung = chongLoiDung;
        this.checkStep6 = checkStep6;
        this.thuebaoHuyDichVu = thuebaoHuyDichVu;
        this.thuebaoSudungDichvu = thuebaoSudungDichvu;
        this.request28 = request28;
        this.request15 = request15;
        this.request37 = request37;
        this.request8 = request8;
        this.request16 = request16;
        this.request17 = request17;
        this.request19 = request19;
    }

    @PostMapping("/step1")
    public List<Soap2> validateParameter(@RequestBody ValidateRequest dto) {
        return utilServices.convertStringXmlToObject(dto);
    }

    @GetMapping("/step2")
    public List<Soap35> getSystemParameter() {
        return systemParameterServices.getDataStep2();
    }

    @PostMapping("/step3")
    public List<Soap37> checkAccountPartnerInfo(@RequestParam Map<String, String> params
    ) {
        return request37.getData(params);
    }

    @PostMapping("/step4")
    public boolean checkAccountPartner(@RequestBody List<Soap37> soap37) {
        boolean ret = false;
        try {
            if (Integer.parseInt(soap37.get(0).getCHECK_ACCOUNT_API()) < 1) ret = false;
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

    @PostMapping("/step6")
    public boolean checkAccountPartnerIsTrue(@RequestBody RequestStep6 request
    ) {
        return checkStep6.getRetValue(request.getLstSoap35(), request.getLstSoap2());
    }

    @GetMapping("/step8")
    public List<Soap15> getSpamInfo(
            @RequestParam Map<String, String> params
    ) {
        return request15.getData(params);
        //return systemParameterServices.getDataStep8();
    }

    @GetMapping("/step9")
    public List<Soap8> getMTInformation(
            @RequestParam Map<String, String> params
    ) {
        return request8.getData(params);
    }

    @GetMapping("/step10")
    public List<Soap9> getServicePackage(
    ) {
        return systemParameterServices.getDataStep10();
    }

    @GetMapping("/step11")
    public List<Soap12> getPartnerType(
    ) {
        return systemParameterServices.getDataStep11();
    }

    @GetMapping("/step12")
    public List<Soap14> transRefused(
    ) {
        return systemParameterServices.getDataStep12();
    }

    @GetMapping("/step13")
    public List<Soap28> scriptShopInformation(
            @RequestParam Map<String, String> params
    ) {
        return request28.getData(params);
    }

    @GetMapping("/step14")
    public List<Soap16> transWait(
            @RequestParam Map<String, String> params
    ) {
        return request16.getData(params);
    }

    @GetMapping("/step15")
    public List<Soap17> transWaitPerService(
            @RequestParam Map<String, String> params
    ) {
        return request17.getData(params);
    }

    @GetMapping("/step16")
    public List<Soap19> transRefusedPerService(@RequestParam Map<String, String> params
    ) {
        return request19.getData(params);
    }

    @PostMapping("/step18")
    public void putData(
            @RequestBody RequestStep18 request
    ) {
        for (Soap35 record : request.getLstSoap35()) {
            context.put(Soap35.jobExpDataDirExpLocal, record.getJOB_EXP_DATA_DIR_EXP_LOCAL());
            context.put(Soap35.jobExpDataDirFtp, record.getJOB_EXP_DATA_DIR_FTP());
            context.put(Soap35.listEmailReportDuytri, record.getLIST_EMAIL_REPORT_DUYTRI());
            context.put(Soap35.listEmailReportJob, record.getLIST_EMAIL_REPORT_JOB());
            context.put(Soap35.listEmailReportPhatsinh, record.getLIST_EMAIL_REPORT_PHATSINH());
            context.put(Soap35.lockTime, record.getLOCK_TIME());
            context.put(Soap35.loginMax, record.getLOGIN_MAX());
            context.put(Soap35.logSystem, record.getLOGSYSTEM());
            context.put(Soap35.onOffLock, record.getON_OFF_LOCK());
            context.put(Soap35.serverFrom, record.getSERVER_FROM());
            context.put(Soap35.serverHost, record.getSERVER_HOST());
            context.put(Soap35.serverPass, record.getSERVER_PASS());
            context.put(Soap35.serverPort, record.getSERVER_PORT());
            context.put(Soap35.serverUser, record.getSERVER_USER());
        }

        for (Soap9 record : request.getLstSoap9()) {
            context.put(Soap9.serviceKey, record.getSERVICE_KEY());
            context.put(Soap9.serviceKeySms, record.getSERVICE_KEY_SMS());
            context.put(Soap9.capacity, record.getCAPACITY());
            context.put(Soap9.changePackage, record.getCHANGE_PACKAGE());
            context.put(Soap9.haveChangePackage, record.getHAVE_CHANGE_PACKAGE());

            context.put(Soap9.haveCheckHuy, record.getHAVE_CHECK_HUY());
            context.put(Soap9.haveCheckHuyWith5, record.getHAVE_CHECK_HUY_WITH_5());
            context.put(Soap9.haveMaintain, record.getHAVE_MAINTAIN());
            context.put(Soap9.likeMiOrVas, record.getLIKE_MI_OR_VAS());
            context.put(Soap9.needCheckService, record.getNEED_CHECK_SERVICE());

            context.put(Soap9.packageCode, record.getPACKAGE_CODE());
            context.put(Soap9.packageCodeApi, record.getPACKAGE_CODE_API());
            context.put(Soap9.packageCodeSms, record.getPACKAGE_CODE_SMS());
            context.put(Soap9.packageCodeStatus, record.getPACKAGE_CODE_STATUS());
            context.put(Soap9.packageCycle, record.getPACKAGE_CYCLE());

            context.put(Soap9.packageDynamic, record.getPACKAGE_DYNAMIC());
            context.put(Soap9.packagePrice, record.getPACKAGE_PRICE());
            context.put(Soap9.packageType, record.getPACKAGE_TYPE());
            context.put(Soap9.serviceId, record.getSERVICE_ID());
            context.put(Soap9.serviceInfo, record.getSERVICE_INFO());

            context.put(Soap9.serviceName, record.getSERVICE_NAME());
            context.put(Soap9.serviceNameSms, record.getSERVICE_NAME_SMS());
            context.put(Soap9.splitTip, record.getSPLIT_TIP());
        }

        for (Soap12 record : request.getLstSoap12()) {
            context.put(Soap12.partnerKey, record.getPARTNER_KEY());
            context.put(Soap12.partnerName, record.getPARTNER_NAME());
            context.put(Soap12.partnerType, record.getPARTNER_TYPE());
            context.put(Soap12.sendSms, record.getSEND_SMS());
        }

        for (Soap28 record : request.getLstSoap28()) {
            context.put(Soap28.scriptShopTypeKey, record.getSCRIPT_SHOP_TYPE_KEY());
            context.put(Soap28.sendSmsSharingKey, record.getSEND_SMS_SHARING_KEY());
            context.put(Soap28.channelId, record.getCHANNEL_ID());
            context.put(Soap28.chargingType, record.getCHARGING_TYPE());
            context.put(Soap28.endTime, record.getEND_TIME());

            context.put(Soap28.haveMaintain, record.getHAVE_MAINTAIN0());
            context.put(Soap28.maintainCycle, record.getMAINTAIN_CYCLE());
            context.put(Soap28.maintainDays, record.getMAINTAIN_DAYS());
            context.put(Soap28.packageCodeId, record.getPACKAGE_CODE_ID());
            context.put(Soap28.registersDays, record.getREGISTERS_DAYS());

            context.put(Soap28.scriptShopStatus, record.getSCRIPT_SHOP_STATUS());
            context.put(Soap28.scriptShopTypeValue, record.getSCRIPT_SHOP_TYPE_VALUE());
            context.put(Soap28.scriptTypeId, record.getSCRIPT_TYPE_ID());
            context.put(Soap28.serviceId, record.getSERVICE_ID());
            context.put(Soap28.soNgayCheckhuy, record.getSO_NGAY_CHECKHUY());
            context.put(Soap28.startTime, record.getSTART_TIME());
        }
    }

    @PostMapping("/step19")
    public void putData(@RequestBody List<Soap34> lst) {
        for (Soap34 record : lst) {
            context.put(Soap34.mtTypeKey, record.getMT_TYPE_KEY());
            context.put(Soap34.mtTypeValue, record.getMT_TYPE_VALUE());
        }
    }

    @PostMapping("/step20")
    public boolean checkSpam(@RequestBody RequestStep20 request) {
        return spam.checksendSms(
                request.getLstSoap8(),
                request.getLstSoap12(),
                request.getLstSoap14(),
                request.getLstSoap15(),
                request.getLstSoap16(),
                request.getLstSoap17(),
                request.getLstSoap19(),
                request.getLstSoap34(),
                request.getChannel(), request.getSharingKeyId(), request.getMsisdn());
    }

    @PostMapping("/step22")
    public boolean checkChongLoiDung(@RequestBody RequestStep22 request) {
        return chongLoiDung.bussiness(request.getLstSoap8(),
                request.getLstSoap12(),
                request.getLstSoap34(),
                request.getScript_shop_id(),
                request.getMsisdn(),
                request.getServiceid());
    }

    @PostMapping("/step25")
    public boolean checkThuebaoSudungDichvu(@RequestBody RequestStep25 request) {
        return thuebaoSudungDichvu.checkThueBao(
                request.getLstSoap34(),
                request.getLstSoap8(),
                request.getScriptShopId(),
                request.getSharingKey(),
                request.getMsisdn(),
                request.getPackageCode(),
                request.getChannel()
        );
    }

    @PostMapping("/step28")
    public boolean step28() {
        return thuebaoHuyDichVu.checkThueBaoHuy();
    }

}
