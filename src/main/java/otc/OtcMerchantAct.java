package otc;

import com.alibaba.fastjson.JSONObject;
import com.chainup.common.enums.*;
import com.chainup.common.exchange.entity.*;
import com.chainup.common.result.CommonResult;
import com.chainup.common.result.ResultType;
import com.chainup.common.result.Results;
import com.chainup.common.util.ScaleUtils;
import com.chainup.common.utils.AccountTypeUtils;
import com.chainup.common.web.Constants;
import com.chainup.config.entity.*;
import com.chainup.config.enums.AccountType_BC;
import com.chainup.config.enums.AssetType_A_BC;
import com.chainup.config.service.ConfigAccountTypeService;
import com.chainup.config.service.ConfigCoinSymbolService;
import com.chainup.config.service.ConfigKvStoreService;
import com.chainup.operate.action.BaseAct;
import com.chainup.operate.action.otc.OtcCompanyCertificationAct;
import com.chainup.operate.action.otc.otcmerchant.model.MerchantDto;
import com.chainup.operate.jpage.JPageConfig;
import com.chainup.operate.jpage.PageConvertUtil;
import com.chainup.operate.service.*;
import com.chainup.otc.entity.OtcUserExt;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.*;

import static com.chainup.common.enums.OtcUserCompanyApplyType.Release;
import static com.chainup.config.enums.LangConf.*;
import static com.chainup.config.enums.LangConf.otcCompanyAuthEmailSubject;

@RestController
@RequestMapping("/otcCompanyCert")
public class OtcMerchantAct extends BaseAct {

    private static final Logger LOGGER = LoggerFactory.getLogger(OtcCompanyCertificationAct.class);

    @Autowired
    private OtcCompanyCertService otcCompanyCertService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthRealnameService authRealnameService;
    @Autowired
    private ConfigKvStoreService configKvStoreService;
    @Autowired
    private OtcUserCompanyApplyService otcUserCompanyApplyService;
    @Autowired
    private OtcUserExrService otcUserExrService;
    @Autowired
    private ConfigAccountTypeService configAccountTypeService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private ConfigCoinSymbolService configCoinSymbolService;
    @Autowired
    private OtcMessageService otcMessageService;
    @Autowired
    private LangConfService langConfService;


    @PostMapping("/otcCompanyApplyList")
    public CommonResult companyApplyList(@RequestBody MerchantDto dto, HttpServletRequest request) {

        ModelMap model = new ModelMap();
        Byte status = dto.getStatus();
        Byte type = dto.getType();
        Integer pageSize = dto.getPageSize();
        String ctimeStart = dto.getCtimeStart();
        String ctimeEnd = dto.getCtimeEnd();
        String keyword = dto.getKeyword();
        try {
            if (pageSize == null)
                pageSize = CURRENT_PAGE_SIZE;

            OtcUserCompanyApply apply = new OtcUserCompanyApply();
            if (status != null) {
                if (status != 0)
                    apply.setStatus(status);
                model.addAttribute("status", status);
            } else {
                model.addAttribute("status", 1);
            }
            if (type != null) {
                apply.setType(type);
                model.addAttribute("type", type);
            }
            if (StringUtils.isNotEmpty(keyword)) {
                model.addAttribute("keyword", keyword);
                if (keyword.contains("@")) {
                    apply.setEmail(keyword);
                } else {
                    apply.setPhone(keyword);
                }
            }
            if (StringUtils.isNotEmpty(ctimeStart)) {
                apply.setStime(DateUtils.parseDate(ctimeStart, "yyyy-MM-dd HH:mm:ss"));
                model.addAttribute("ctimeStart", ctimeStart);
            }
            if (StringUtils.isNotEmpty(ctimeEnd)) {
                apply.setEtime(DateUtils.parseDate(ctimeEnd, "yyyy-MM-dd HH:mm:ss"));
                model.addAttribute("ctimeEnd", ctimeEnd);
            }
            int total = otcCompanyCertService.countAll(apply);
            JPageConfig jpc = new JPageConfig();
            jpc.setPageSize(pageSize);
            jpc.setRecordTotal(total);
            jpc.setRequest(request);
            jpc.commit();

            List<OtcUserCompanyApply> list = otcCompanyCertService.findAll(apply, jpc.getRecordStart(),
                    jpc.getRecordLength());


            model.addAttribute("list", list);
            model.addAttribute("request", request);

            List<OtcUserCompanyApplyStatus> statuses = Arrays.asList(OtcUserCompanyApplyStatus.Pending,
                    OtcUserCompanyApplyStatus.Refuse, OtcUserCompanyApplyStatus.Approve);
            for (OtcUserCompanyApplyStatus os : statuses) {
                os.setDescription(this.getLocalMsg(os.languageKey, request));
            }
            model.addAttribute("statuses", statuses);
            OtcUserCompanyApplyType[] values = OtcUserCompanyApplyType.values();
            for (OtcUserCompanyApplyType os : values) {
                os.setDescription(this.getLocalMsg(os.languageKey, request));
            }
            model.addAttribute("types", values);

            return Results.succ(PageConvertUtil.get(format(list, request), jpc));
        } catch (ParseException e) {
            LOGGER.error("companyApplyList error:", e);
            model.addAttribute("error", "对不起，程序出现系统错误，请和网站管理员联系，谢谢！");
            return Results.fail();
        } catch (Exception e) {
            LOGGER.error("companyApplyList error:", e);
            return Results.succ();
        }
    }

    private List<OtcUserCompanyApply> format(List<OtcUserCompanyApply> list, HttpServletRequest request) {

        // 获取认证状态中文
        List<OtcUserCompanyApplyStatus> statusList = Arrays.asList(OtcUserCompanyApplyStatus.Approve,
                OtcUserCompanyApplyStatus.Pending, OtcUserCompanyApplyStatus.Refuse);
        Map<String, String> statusMap = new HashMap<String, String>();
        for (OtcUserCompanyApplyStatus os : statusList) {
            statusMap.put(Byte.toString(os.value), this.getLocalMsg(os.languageKey, request));
        }
        // 获取类型
        Map<Byte, String> typeMap = new HashMap<Byte, String>(2);
        for (OtcUserCompanyApplyType at : OtcUserCompanyApplyType.values()) {
            typeMap.put(at.value, this.getLocalMsg(at.languageKey, request));
        }

        List<Integer> uids = new LinkedList<Integer>();
        for (OtcUserCompanyApply apply : list) {
            uids.add(apply.getUid());
        }
        Map<Integer, User> userMap = userService.findId2UserByUids(uids);

        for (OtcUserCompanyApply apply : list) {
            apply.setTypeDes(typeMap.get(apply.getType()));
            apply.setStatusName(statusMap.get(apply.getStatus().toString()));
            apply.setRealName(authRealnameService.getRealNameByUid(apply.getUid()));
            if (userMap.containsKey(apply.getUid())) {
                User user = userMap.get(apply.getUid());
                apply.setPhone(user.getMobileNumber());
                apply.setEmail(user.getEmail());
            } else {
                apply.setPhone("-");
                apply.setEmail("-");
            }

        }
        return list;
    }


    @PostMapping(value = "/otcCompanyApplyInfo")
    public CommonResult otcCompanyApplyInfo(@RequestBody MerchantDto dto, HttpServletRequest request) {
        Integer id = dto.getId();
        Byte type = dto.getType();

        ModelMap model = new ModelMap();
        try {
//            if (!this.validateAction(request, AdminActionConst.otcCompanyCertInfo)) {
//                model.addAttribute("error", getLocalMsg("noaction_error", request));
//                return Results.fail();
//            }
            OtcUserCompanyApply apply = otcUserCompanyApplyService.findById(id);
            if (apply == null)
                throw new RuntimeException("non exist otc company apply info");
            User user = userService.findById(apply.getUid());
            if (user == null)
                throw new RuntimeException("non exist otc company apply info");
            AuthRealname authRealname = authRealnameService.findByUId(user.getId());
            if (authRealname == null)
                throw new RuntimeException("non exist otc company apply info");
            OtcUserExt otcUserExt = otcUserExrService.queryByUserId(user.getId().longValue());
            if (otcUserExt == null)
                throw new RuntimeException("non exist otc company apply info");

            // 获取保证金币种
            ConfigKvStore store = configKvStoreService.selectBykey(Constants.coinSymbolKey);
            if (store == null)
                throw new RuntimeException();
            String coinSymbol = store.getValue();
            // accountService.getTotalBalanceByAccountMap(accountMap, totalBalanceSymbol);
            ConfigAccountType otcCompanyMargin = configAccountTypeService.findByAttribute(
                    AssetType_A_BC.U_OTC_COMPANY_MARGIN.getAccount_A(),
                    AssetType_A_BC.U_OTC_COMPANY_MARGIN.getAccount_BC(), coinSymbol, null);
            Account account = accountService.selectOnlyOneByExample(user.getId(), otcCompanyMargin.getAssetType());

            model.addAttribute("id", apply.getId());

            OtcUserCompanyApplyType applyType = OtcUserCompanyApplyType.fromValue(apply.getType());
            model.addAttribute("applyType", this.getLocalMsg(applyType.languageKey, request));
            model.addAttribute("uid", user.getId().toString());
            model.addAttribute("phone", user.getMobileNumber());
            model.addAttribute("email", user.getEmail());

            if (authRealname != null) {
                model.addAttribute("realName", authRealnameService.getRealNameByUid(apply.getUid()));
                Country country = countryService.getByNumberCodeAndDialingCode(authRealname.getCountryCode());
                if (country != null)
                    model.addAttribute("country", country.getCnName());
                model.addAttribute("certificateType", authRealname.getCertificateType().toString());
                model.addAttribute("certificateNumber", authRealname.getCertificateNumber());
            }
            if (otcUserExt != null) {
                model.addAttribute("completeOrders", otcUserExt.getCompleteOrders().toString());
                model.addAttribute("failComplainNum", otcUserExt.getFailComplainNum().toString());
                model.addAttribute("score", new BigDecimal(1).subtract(otcUserExt.getCreditGrade()).multiply(BigDecimal.valueOf(100)).setScale(ScaleUtils.BALANCE_ROUND_NUM_TWO, RoundingMode.HALF_EVEN).toString() + "%");
            }
            if (account != null)
                model.addAttribute("marginBalance", apply.getAmount().setScale(0).toString() + apply.getCoinSymbol());

            /** 获取用户场外交易账户 */
            List<ConfigAccountType> list = AccountTypeUtils.getAllOtcAccount();
            Iterator<ConfigAccountType> it = list.iterator();
            List<Integer> types = new LinkedList<>();
            while (it.hasNext()) {
                ConfigAccountType cur = it.next();
                if (!cur.getAssetBc().equals(AccountType_BC.OTC_NORMAL.value))
                    it.remove();
                types.add(cur.getAssetType());
            }
            AccountExample example = new AccountExample();
            example.createCriteria().andTypeIn(types).andUidEqualTo(apply.getUid());
            List<Account> accounts = accountService.findAll(example);
            HashMap<Integer, BigDecimal> accountMap = new LinkedHashMap<>();
            for (Account account2 : accounts) {
                accountMap.put(account2.getType(), account2.getBalance());
            }
            String key = configKvStoreService.getValueByKey("total_balance_coin_symbol");
            BigDecimal totalBalance = accountService.getTotalBalanceByAccountMap(accountMap, key);

            model.addAttribute("totalBalance", ScaleUtils.scaleBySymbol(totalBalance, key));
            model.addAttribute("type", type);
            if (type.equals((byte) 2)) {
                model.addAttribute("comment", apply.getComment());
                model.addAttribute("address", StringUtils.isEmpty(apply.getAddress()) ? "" : apply.getAddress());
            }
            return Results.succ(model);
        } catch (Exception e) {
            LOGGER.error("companyApplyInfo error:", e);
            return Results.fail();
        }

    }


    @PostMapping(value = "/otcCompanyCertRuleSet")
    public CommonResult otcCompanyCertificationRuleSetting(HttpServletRequest request) {

        ModelMap model = new ModelMap();
        try {
            // 获取保证金币种
            ConfigKvStore store = configKvStoreService.selectBykey(Constants.coinSymbolKey);
            if (store == null)
                throw new RuntimeException();
            model.addAttribute("coinSymbol", store.getValue());
            // 获取普通商户设置
            ConfigKvStore normalCompany = configKvStoreService.selectBykey(Constants.openNormalCompanyKey);
            if (normalCompany != null && StringUtils.isNotEmpty(normalCompany.getValue())) {
                model.addAttribute("isNormalCompanyOpen", normalCompany.getValue());
                ConfigKvStore normalCompanyMargin = configKvStoreService.selectBykey(Constants.normalCompanyMarginKey);
                model.addAttribute("normalCompanyMargin", normalCompanyMargin.getValue());
            }
            ConfigKvStore superCompany = configKvStoreService.selectBykey(Constants.openSuperCompanyKey);
            if (superCompany != null && StringUtils.isNotEmpty(superCompany.getValue())) {
                model.addAttribute("isSuperCompanyOpen", superCompany.getValue());
                ConfigKvStore superCompanyMargin = configKvStoreService.selectBykey(Constants.superCompanyMarginKey);
                model.addAttribute("superCompanyMargin", superCompanyMargin.getValue());
            }

            ConfigKvStore normalTradeLimit = configKvStoreService.selectBykey(Constants.otcNormalTradeLimit);
            model.addAttribute("normalTradeLimit", normalTradeLimit.getValue());

            ConfigKvStore otcCompanyApplyEmail = configKvStoreService.selectBykey(Constants.otcCompanyApplyEmail);
            model.addAttribute("otcCompanyApplyEmail", otcCompanyApplyEmail.getValue());

            ConfigCoinSymbolExample example = new ConfigCoinSymbolExample();
            example.createCriteria().andIsOpenEqualTo((byte) 1);
            List<ConfigCoinSymbol> list = configCoinSymbolService.findAll(example);
            model.addAttribute("coinSymbols", list);
//            MyEConfig config = CommonContainer.getSite();
//            FrontUtils.frontData(request, model, config);
//            model.addAttribute("request", request);
            return Results.succ(model);
        } catch (Exception e) {
            LOGGER.error("otcCompanyCertificationRuleSetting error", e);
//            MyEConfig config = CommonContainer.getSite();
//            FrontUtils.frontData(request, model, config);
            model.addAttribute("error", "对不起，程序出现系统错误，请和网站管理员联系，谢谢！");
            return Results.fail();
        }
    }

    @PostMapping(value = "/otcCompanyCertRuleSave")
    public CommonResult otcCompanyCertificatetRuleSave(@RequestBody @Valid MerchantDto dto) {

        String coinSymbol = dto.getCoinSymbol();
        Byte isOpenNormal = dto.getIsNormalCompanyOpen();
        Integer normalCompanyMargin = dto.getNormalCompanyMargin();
        Byte isOpenSuper = dto.getIsSuperCompanyOpen();
        Integer superCompanyMargin = dto.getSuperCompanyMargin();
        Integer normalTradeLimit = dto.getNormalTradeLimit();
        String otcCompanyApplyEmail = dto.getOtcCompanyApplyEmail();

        JSONObject json = new JSONObject();
        try {
            if (normalTradeLimit == null || normalTradeLimit == 0) {
                return Results.failMsg("参数错误: Normal Merchant Trade Limit should not be null or 0");
            }

            if (!otcCompanyApplyEmail.contains(".com") || !otcCompanyApplyEmail.contains("@")){
                return Results.failMsg("参数错误: Email should be in a Valid format");
            }
            if (StringUtils.isEmpty(coinSymbol)) {
                ConfigKvStore store = configKvStoreService.selectBykey(Constants.coinSymbolKey);
                if (store == null)
                    throw new RuntimeException();
                coinSymbol = store.getValue();
            }
            if (normalCompanyMargin == null || normalCompanyMargin == 0) {
                //Normal Merchant Margin cannot be 0
                return Results.failMsg("参数错误: Normal Merchant Margin cannot be 0");
            }

            if (isOpenSuper == (byte) 1 && (superCompanyMargin == null || superCompanyMargin == 0)) {
                //Super Merchant Margin cannot be 0
                return Results.failMsg("参数错误: Super Merchant Margin cannot be 0");
            }
            if (isOpenSuper == (byte) 1 && normalCompanyMargin > superCompanyMargin) {
                //The Super Merchant deposit amount must be larger than the Normal merchant deposit.
                return Results.failMsg("参数错误: The Super Merchant deposit amount must be larger than the Normal merchant deposit");
            }
            otcCompanyCertService.updateCertRule(coinSymbol, isOpenNormal,
                    normalCompanyMargin, isOpenSuper, superCompanyMargin,
                    normalTradeLimit, otcCompanyApplyEmail);
            json.put("result", SUCCESS_ONE);
            return Results.succ(json.values());
        } catch (Exception e) {
            LOGGER.error("otcCompanyCertificationRuleSave error:", e);
            return Results.fail();
        }
    }

    @PostMapping(value = "/approval")
    public CommonResult companyApplyApprove(@RequestBody MerchantDto dto) {

        Integer id = dto.getId();
        Byte status = dto.getStatus();
        String comment = dto.getComment();
        String ossFullFileName = dto.getOssFullFileName();

        JSONObject json = new JSONObject();
        logger.info("companyApplyApprove params id:{}，status:{},comment:{}", id, status, comment);
        int result = 0;
        try {
            if (status == null || !(status.equals(OtcUserCompanyApplyStatus.Approve.value)
                    || status.equals(OtcUserCompanyApplyStatus.Refuse.value))) {
                return Results.fail(EXCEPTION_TWO);
            }
            OtcUserCompanyApply apply = otcUserCompanyApplyService.findById(id);
            if (apply == null) {
                throw new RuntimeException("apply is null");
            }
            if (apply.getType().equals(OtcUserCompanyApplyType.Normal_Company.value)) {
                apply.setAddress(ossFullFileName);
            }
            apply.setComment(comment);
            if (otcUserCompanyApplyService.approval(apply, status) > 0) {
                logger.info("result", SUCCESS_ONE);
                if (status == 2)
                    json.put("Application", OtcUserCompanyApplyStatus.Refuse);
                else if (status == 3)
                    json.put("Application", OtcUserCompanyApplyStatus.Approve);
            }
            Integer uid = apply.getUid();
            User applyer = userService.findById(uid);
            if (!apply.getType().equals(Release.getValue())) {
                if (OtcUserCompanyApplyStatus.Refuse.getValue() == status) {
                    //申请商家审批不过
                    otcMessageService.sendSmsOrEmail(applyer, otcCompanyApplyFail.getValue(),
                            otcCompanyApplyFailEmail.getValue(), otcCompanyAuthEmailSubject.getValue(), getLang(uid));
                    json.put("Application", OtcUserCompanyApplyStatus.Refuse);
                } else if (OtcUserCompanyApplyStatus.Approve.getValue() == status) {
                    //申请商家审批通过
                    otcMessageService.sendSmsOrEmail(applyer, otcCompanyApplySuccess.getValue(),
                            otcCompanyApplySuccessEmail.getValue(), otcCompanyAuthEmailSubject.getValue(), getLang(uid));
                    json.put("Application", OtcUserCompanyApplyStatus.Approve);
                }
            }
            return Results.succ(json);

        } catch (SecurityException e) {
            logger.error("companyApplyApprove SecurityException error:", e);
            if (ResultType.RISK_CHECK_DEPOSIT_FAIL.languageKey.equals(e.getMessageKey())) {
                logger.error("Exception Deposit:", EXCEPTION_DEPOSIT, e);
                return Results.failMsg("Exception Deposit Error");
            } else if (ResultType.RISK_CHECK_BALANCE_FAIL.languageKey.equals(e.getMessageKey())) {
                logger.error("Exception Balance:", EXCEPTION_BALANCE, e);
                return Results.failMsg("Exception Balance Error");
            }
            return Results.failMsg(json.toString());
        } catch (Exception e) {
            logger.error("companyApplyApprove error:", e);
            return Results.fail(1);
        }
    }

}
