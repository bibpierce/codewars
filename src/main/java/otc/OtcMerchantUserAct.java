package otc;

import com.chainup.common.enums.*;
import com.chainup.common.exchange.vo.AttrModel;
import com.chainup.common.exchange.vo.OtcLanguage;
import com.chainup.common.result.CommonResult;
import com.chainup.common.result.Results;
import com.chainup.config.entity.ConfigCoinSymbol;
import com.chainup.config.entity.ConfigCoinSymbolExample;
import com.chainup.config.enums.Language;
import com.chainup.config.service.ConfigCoinSymbolService;
import com.chainup.operate.action.BaseAct;
import com.chainup.operate.action.otc.otcmerchant.model.MerchantUserDto;
import com.chainup.operate.enums.PaycoinTypes;
import com.chainup.operate.enums.PaymentMethods;
import com.chainup.operate.jpage.JPage;
import com.chainup.operate.jpage.JPageConfig;
import com.chainup.operate.jpage.PageConvertUtil;
import com.chainup.operate.service.AuthRealnameService;
import com.chainup.operate.service.OtcAdService;
import com.chainup.operate.service.OtcOrderService;
import com.chainup.operate.service.OtcUserCompanyService;
import com.chainup.otc.entity.OtcOrderExample;
import com.chainup.otc.entity.OtcUserCompany;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OtcMerchantUserAct extends BaseAct {

    @Autowired
    private OtcUserCompanyService otcUserCompanyService;
    @Autowired
    private AuthRealnameService authRealnameService;
    @Autowired
    private OtcAdService otcAdService;
    @Autowired
    private OtcOrderService otcOrderService;
    @Autowired
    private ConfigCoinSymbolService configCoinSymbolService;


    @PostMapping(value = "/endpointEnums")
    public CommonResult EndpointEnum(){

        try {
            ModelMap model = new ModelMap();

            PaymentMethods[] paymentMethods = PaymentMethods.values();
            model.addAttribute("paymentMethods", paymentMethods);

            OrderSide[] orderSides = OrderSide.values();
            model.addAttribute("orderSides", orderSides);

            OtcAdvertStatus[] advertStatuses = OtcAdvertStatus.values();
            model.addAttribute("advertStatuses", advertStatuses);

            PaycoinTypes[] paycoinTypes = PaycoinTypes.values();
            model.addAttribute("paycoinTypes", paycoinTypes);

            Language language = Language.fromLang(getLocalLanguage(request));
            OtcLanguage otcLanguage = exLanguageService.getOtcLangByKey(language.getLangType());

            List<AttrModel> countryList = otcLanguage.getOtcCountries();
            List countries = new ArrayList();
            for (AttrModel country1 : countryList) {
                countries.add(country1.getTitle());
            }
            model.addAttribute("Countries", countries);

            String[] companyStatus = new String[]{getLocalMsg(OtcUserCompanyStatus.NormalCompany.languageKey, request), getLocalMsg(OtcUserCompanyStatus.SuperCompany.languageKey, request)};
            model.addAttribute("companyStatus", companyStatus);

            ConfigCoinSymbolExample example = new ConfigCoinSymbolExample();
            List<ConfigCoinSymbol> list = configCoinSymbolService.findAll(example);
            model.addAttribute("coinSymbols", list);

            OtcOrderStatus[] otcOrderStatuses = OtcOrderStatus.values();
            model.addAttribute("otcOrderStatuses", otcOrderStatuses);

            OtcUserCompanyApplyType[] applyType = OtcUserCompanyApplyType.values();
            model.addAttribute("applyTypes", applyType);

            OtcUserCompanyApplyStatus[] merStatus = OtcUserCompanyApplyStatus.values();
            model.addAttribute("statuses", merStatus);

            Language[] languageLists = Language.values();
            model.addAttribute("languageType", languageLists);



            return Results.succ(model);
        } catch (Exception e) {
            return Results.fail();
        }

    }

    @PostMapping("/otcCompanyUser")
    public CommonResult companyDocList(@RequestBody MerchantUserDto dto, HttpServletRequest request, HttpServletResponse response) {

        ModelMap model = new ModelMap();
        Integer pageSize = dto.getPageSize();
        Integer userId = dto.getUserId();
        Integer companyType = dto.getCompanyType();
        String nickName = dto.getNickName();

        try {
            List<OtcUserCompanyStatus> statusList = new ArrayList<>();
            statusList.add(OtcUserCompanyStatus.NormalCompany);
            statusList.add(OtcUserCompanyStatus.SuperCompany);
            List<Byte> statusValueList = new ArrayList<>();
            for (OtcUserCompanyStatus otcUserCompanyStatus : statusList) {
                otcUserCompanyStatus.setDescription(getLocalMsg(otcUserCompanyStatus.languageKey, request));
                statusValueList.add(otcUserCompanyStatus.value);
            }

            Map<String, Object> queryMap = new HashMap<>();
            if (userId != null) {
                queryMap.put("userId", userId);
            }
            if (companyType != null) {
                queryMap.put("status", companyType.byteValue());
            } else {
                queryMap.put("statusList", statusValueList);
            }
            if (StringUtils.isNotBlank(nickName)) {
                queryMap.put("nickName", nickName);
            }
            int count = otcUserCompanyService.findJoinUerCount(queryMap);
            JPageConfig jpc = getJPage(pageSize, count, request);

            JPage jpage = jpc.getJpage();
            jpage.setUrlServlet(request.getContextPath() + "/otc_company_user.html");

            queryMap.put("limitStart", jpc.getRecordStart());
            queryMap.put("limitEnd", jpc.getRecordLength());
            List<Map<String, Object>> list = formatList(otcUserCompanyService.findJoinUerList(queryMap), request);

            model.addAttribute("list", list);
            model.addAttribute("userId", userId);
            model.addAttribute("companyType", companyType);
            model.addAttribute("nickName", nickName);
            model.addAttribute("statusList", statusList);
            return Results.succ(PageConvertUtil.get(list, jpc));
        } catch (Exception e) {
            logger.error("otc_company_user error", e);
            model.addAttribute("error", "对不起，程序出现系统错误，请和网站管理员联系，谢谢！");
            return Results.fail();
        }
    }

    private List<Map<String, Object>> formatList(List<OtcUserCompany> list, HttpServletRequest request) {
        List<Map<String, Object>> mapList = new ArrayList<>();


        List<Byte> statusEqList = new ArrayList<>();
        statusEqList.add((byte) OtcOrderStatus.PAY_PENDING.value);
        statusEqList.add((byte) OtcOrderStatus.PAID.value);
        statusEqList.add((byte) OtcOrderStatus.APPEAL.value);
        statusEqList.add((byte) OtcOrderStatus.PAY_COIN.value);
        statusEqList.add((byte) OtcOrderStatus.EXCEPTION_ORDER.value);

        String[] statusStrArr = new String[]{"", getLocalMsg(OtcUserCompanyStatus.NormalCompany.languageKey, request), "", getLocalMsg(OtcUserCompanyStatus.SuperCompany.languageKey, request)};

        for (OtcUserCompany userCompany : list) {

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("userId", userCompany.getUserId());
            dataMap.put("statusStr", statusStrArr[userCompany.getStatus()]);
            dataMap.put("nickName", userCompany.getNickName());
            dataMap.put("realName", authRealnameService.getRealNameByUid(userCompany.getUserId()));
            dataMap.put("tradeVol", userCompany.getCompleteOrders());
            dataMap.put("applyCount", userCompany.getComplainNum());
            dataMap.put("applyWinCount", userCompany.getSucComplainNum());
            dataMap.put("creditGrade", userCompany.getCreditGrade());

            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("uid", userCompany.getUserId());
            queryMap.put("status", OtcAdvertStatus.PUBLISHING.value);
            Integer advertCount=otcAdService.getCountByMap(queryMap);
            dataMap.put("advertCount", advertCount);

            OtcOrderExample orderExample = new OtcOrderExample();
            OtcOrderExample.Criteria orderCriteria = orderExample.createCriteria();
            orderCriteria.andSellerIdOrBuyerIdEqualTo(userCompany.getUserId().longValue()).andStatusIn(statusEqList);
            try {
                long orderCount = otcOrderService.countOrder(orderExample);
                dataMap.put("orderCount", orderCount);
            } catch (Exception e) {

            }
            mapList.add(dataMap);
        }
        return mapList;
    }
}
