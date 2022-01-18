package com.neo.msocial.rest;

import com.neo.msocial.dto.*;
import com.neo.msocial.groovy.ThueBaoHuyDichVu;
import com.neo.msocial.groovy.sendunicastdf.*;
import com.neo.msocial.request.*;
import com.neo.msocial.request.partnerapi.RequestStep16;
import com.neo.msocial.request.sendunicastdf.RequestChongLoiDung;
import com.neo.msocial.request.sendunicastdf.RequestProcessBusiness;
import com.neo.msocial.utils.GenericsRequest;
import com.neo.msocial.utils.RedisUtils;
import com.neo.msocial.utils.UtilServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sendUnicastDF")
public class SendUnicastDFController {
    private final UtilServices utilServices;
    private final RedisUtils context;
    private final CheckStep2 checkStep2;
    private final GenericsRequest<Soap15> request15;
    private final GenericsRequest<Soap8> request8;
    private final GenericsRequest<Soap9> request9;
    private final GenericsRequest<Soap12> request12;
    private final GenericsRequest<Soap14> request14;
    private final GenericsRequest<Soap28> request28;
    private final GenericsRequest<Soap16> request16;
    private final GenericsRequest<Soap17> request17;
    private final GenericsRequest<Soap19> request19;
    private final GenericsRequest<Soap24> request24;
    private final GenericsRequest<Soap34> request34;
    private final CheckSpam checkSpam;
    private final CheckChongLoiDung checkChongLoiDung;
    private final CheckThueBaoSuDungDichVu checkThueBaoSuDungDichVu;
    private final ThueBaoHuyDichVu thuebaoHuyDichVu;
    private final CheckMiFc checkMiFc;
    private final ProcessBusiness processBusiness;

    public SendUnicastDFController(UtilServices utilServices, RedisUtils context, CheckStep2 checkStep2, GenericsRequest<Soap15> request15, GenericsRequest<Soap8> request8, GenericsRequest<Soap9> request9, GenericsRequest<Soap12> request12, GenericsRequest<Soap14> request14, GenericsRequest<Soap28> request28, GenericsRequest<Soap16> request16, GenericsRequest<Soap17> request17, GenericsRequest<Soap19> request19, GenericsRequest<Soap24> request24, GenericsRequest<Soap34> request34, CheckSpam checkSpam, CheckChongLoiDung checkChongLoiDung, CheckThueBaoSuDungDichVu checkThueBaoSuDungDichVu, ThueBaoHuyDichVu thuebaoHuyDichVu, CheckMiFc checkMiFc, ProcessBusiness processBusiness) {
        this.utilServices = utilServices;
        this.context = context;
        this.checkStep2 = checkStep2;
        this.request15 = request15;
        this.request8 = request8;
        this.request9 = request9;
        this.request12 = request12;
        this.request14 = request14;
        this.request28 = request28;
        this.request16 = request16;
        this.request17 = request17;
        this.request19 = request19;
        this.request24 = request24;
        this.request34 = request34;
        this.checkSpam = checkSpam;
        this.checkChongLoiDung = checkChongLoiDung;
        this.checkThueBaoSuDungDichVu = checkThueBaoSuDungDichVu;
        this.thuebaoHuyDichVu = thuebaoHuyDichVu;
        this.checkMiFc = checkMiFc;
        this.processBusiness = processBusiness;
    }

    @PostMapping("/step1")
    public List<Soap2> validateParameter(@RequestBody ValidateRequest dto) {
        return utilServices.convertStringXmlToObject(dto);
    }

    @PostMapping("/step2")
    public boolean step2(@RequestBody List<Soap2> soap2List){
        return checkStep2.getRetVal(soap2List);
    }

    @GetMapping("/step4")
    public List<Soap15> getSpamInfo(@RequestParam Map<String, String> params) {
        return request15.getData(params);
    }

    @GetMapping("/step5")
    public List<Soap8> getMTInformation(@RequestParam Map<String, String> params) {
        return request8.getData(params);
    }

    @GetMapping("/step6")
    public List<Soap24> getSystemParameter(@RequestParam Map<String, String> params) {
        return request24.getData(params);
    }

    @GetMapping("/step7")
    public List<Soap9> getServicePackage(@RequestParam Map<String, String> params) {
        return request9.getData(params);
    }

    @GetMapping("/step8")
    public List<Soap12> getPartnerType(@RequestParam Map<String, String> params) {
        return request12.getData(params);
    }

    @GetMapping("/step9")
    public List<Soap14> transRefused(@RequestParam Map<String, String> params) {
        return request14.getData(params);
    }

    @GetMapping("/step10")
    public List<Soap28> scriptShopInformation(@RequestParam Map<String, String> params) {
        return request28.getData(params);
    }

    @GetMapping("/step11")
    public List<Soap16> transWait(
            @RequestParam Map<String, String> params
    ) {
        return request16.getData(params);
    }

    @GetMapping("/step12")
    public List<Soap17> transWaitPerService(
            @RequestParam Map<String, String> params
    ) {
        return request17.getData(params);
    }

    @GetMapping("/step13")
    public List<Soap19> transRefusedPerService(@RequestParam Map<String, String> params
    ) {
        return request19.getData(params);
    }

    @GetMapping("/step14")
    public List<Soap34> getMtScriptShop(@RequestParam Map<String, String> params){
        return request34.getData(params);
    }

    @PostMapping("/step15")
    public String putData(@RequestBody RequestStep16 request) {
        for (Soap24 record : request.getLstSoap24()) {
            context.put(Soap24.jobExpDataDirExpLocal, record.getJOB_EXP_DATA_DIR_EXP_LOCAL());
            context.put(Soap24.jobExpDataDirFtp, record.getJOB_EXP_DATA_DIR_FTP());
            context.put(Soap24.listEmailReportDuytri, record.getLIST_EMAIL_REPORT_DUYTRI());
            context.put(Soap24.listEmailReportJob, record.getLIST_EMAIL_REPORT_JOB());
            context.put(Soap24.listEmailReportPhatsinh, record.getLIST_EMAIL_REPORT_PHATSINH());
            context.put(Soap24.lockTime, record.getLOCK_TIME());
            context.put(Soap24.loginMax, record.getLOGIN_MAX());
            context.put(Soap24.logSystem, record.getLOGSYSTEM());
            context.put(Soap24.onOffLock, record.getON_OFF_LOCK());
            context.put(Soap24.serverFrom, record.getSERVER_FROM());
            context.put(Soap24.serverHost, record.getSERVER_HOST());
            context.put(Soap24.serverPass, record.getSERVER_PASS());
            context.put(Soap24.serverPort, record.getSERVER_PORT());
            context.put(Soap24.serverUser, record.getSERVER_USER());
        }

        for (Soap9 record : request.getLstSoap9()) {
            context.put(Soap9.serviceKey, record.getSERVICE_KEY());
            context.put(Soap9.serviceKeySms, record.getSERVICE_KEY_SMS());
            context.put(Soap9.capacity, record.getCAPACITY());
            context.put(Soap9.changePackage, record.getCHANGE_PACKAGE());
            context.put(Soap9.haveChangePackage, record.getHAVE_CHANGE_PACKAGE());

            context.put(Soap9.haveCheckHuy, record.getHAVE_CHECK_HUY());
            context.put(Soap9.haveCheckHuyWith5000, record.getHAVE_CHECK_HUY_WITH_5000());
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
        return "Put redis successful step 15 !!!";
    }

    @PostMapping("/step16")
    public String step16(@RequestBody List<Soap34> soap34List){
        try{
            for(Soap34 record : soap34List){
                context.put(Soap34.mtTypeKey, record.getMT_TYPE_KEY());
                context.put(Soap34.mtTypeValue, record.getMT_TYPE_VALUE());
            }

        }catch(Exception ex){
            System.out.println(ex.getMessage());
            context.set("SCRIPT_MT_EMPTY","true");
        }
        return "Put redis successful step 16 !!!";
    }

    @PostMapping("/step17")
    public boolean checkSpam(RequestStep20 request){
        return checkSpam.checkSendSms(
                request.getLstSoap8(),
                request.getLstSoap12(),
                request.getLstSoap14(),
                request.getLstSoap15(),
                request.getLstSoap16(),
                request.getLstSoap17(),
                request.getLstSoap19(),
                request.getLstSoap34(),
                request.getChannel(), request.getSharingKeyId(), request.getMsisdn(), "");
    }

    @PostMapping("/step19")
    public boolean checkChongLoiDung(RequestChongLoiDung request){
        return checkChongLoiDung.bussiness(request.getLstSoap8(),
                request.getLstSoap12(),
                request.getLstSoap34(),
                request.getScript_shop_id(),
                request.getMsisdn(),
                request.getServiceid(),
                request.getSharingkey());
    }

    @PostMapping("/step22")
    public boolean checkThuebaoSudungDichvu(@RequestBody RequestStep25 request) {
        return checkThueBaoSuDungDichVu.checkThueBao(
                request.getLstSoap34(),
                request.getLstSoap8(),
                request.getScriptShopId(),
                request.getSharingKey(),
                request.getMsisdn(),
                request.getPackageCode(),
                request.getChannel()
        );
    }

    @PostMapping("/step23")
    public boolean resultThuebaoUseService(@RequestBody RequestStep26 request) {
        // ket qua cua step 22
        System.out.println("checkUsedService:"+request.isCheckUsedService());
        return request.isCheckUsedService();
    }

    @PostMapping("/step25")
    public boolean checkThueBaoHuy(@RequestBody RequestStep28 request) {
        // false => NEXT
        return thuebaoHuyDichVu.checkThueBaoHuy(
                request.getLstSoap34(),
                request.getLstSoap8(),
                request.getScriptShopId(),
                request.getMsisdn(),
                request.getSharingKey(),
                request.getPackageCode(),
                request.getChannel()
        );
    }

    @PostMapping("/step26")
    public boolean step26(@RequestBody RequestStep29 request) {
        // ket qua cua step 25
        System.out.println("FALSE::::"+ request.isCheckHuyStatus());
        if("MOBILEINTERNET".equals(context.get("SERVICE_KEY")) || "FASTCONNECT".equals(context.get("SERVICE_KEY")) ) return true;
        else
        {
            return request.isCheckHuyStatus();
        }
    }

    @PostMapping("/step28")
    public boolean checkMiFc(@RequestBody RequestStep31 request) {
        // true => NEXT
        return checkMiFc.checkMifc(
                request.getLstSoap8(),
                request.getLstSoap34(),
                request.getMsisdn(),
                request.getSharingKey(),
                request.getServiceId(),
                request.getPackageCode(),
                request.getChannel(),
                request.getScriptShopId(),
                request.getCheckStartDate()
        );
    }

    @PostMapping("/step29")
    public boolean processBusiness(@RequestBody RequestProcessBusiness request) {
        // true => NEXT
        return processBusiness.business(
                request.getLstSoap8(),
                request.getLstSoap34(),
                request.getMsisdn(),
                request.getChannel(),
                request.getScript_shop_id(),
                request.getMaChiNhanh(),
                request.getSharing_key_id(),
                request.getChannelId(),
                request.getPartnerId(),
                request.getAgentId()
        );
    }

}