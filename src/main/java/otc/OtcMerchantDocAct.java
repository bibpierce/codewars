package otc;

import com.alibaba.fastjson.JSONObject;
import com.chainup.common.enums.OtcUserCompanyApplyType;
import com.chainup.common.exchange.entity.AdminUser;
import com.chainup.common.exchange.entity.AdminUserExample;
import com.chainup.common.exchange.entity.OtcUserCompanyDoc;
import com.chainup.common.result.CommonResult;
import com.chainup.common.result.Results;
import com.chainup.config.enums.Language;
import com.chainup.operate.action.BaseAct;
import com.chainup.operate.action.otc.otcmerchant.model.MerchantDocDto;
import com.chainup.operate.action.otc.otcmerchant.model.MerchantDto;
import com.chainup.operate.jpage.JPageConfig;
import com.chainup.operate.jpage.PageConvertUtil;
import com.chainup.operate.service.OtcCompanyDocService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/otcCompanyDoc")
public class OtcMerchantDocAct extends BaseAct {

    @Autowired
    private OtcCompanyDocService otcCompanyDocService;

    @PostMapping(value = "/otcCompanyDocList")
    public CommonResult merchantDocList(@RequestBody MerchantDocDto dto, HttpServletRequest request) {

        ModelMap model = new ModelMap();
        Integer pageSize = dto.getPageSize();
        Integer langType = dto.getLangType();
        Byte type = dto.getType();

        try {
            if (pageSize == null) {
                pageSize = CURRENT_PAGE_SIZE;
            }
            OtcUserCompanyDoc doc = new OtcUserCompanyDoc();
            if (langType != null) {
                doc.setLang(langType);
            }
            if (type != null) {
                doc.setType(type.byteValue());
            }
            JPageConfig jpc = new JPageConfig();
            jpc.setPageSize(pageSize);
            jpc.setRequest(request);
            int total = otcCompanyDocService.countByCondition(doc);
            jpc.setRecordTotal(total);
            jpc.commit();
            List<OtcUserCompanyDoc> list = otcCompanyDocService.findAll(doc, jpc.getRecordStart(),
                    jpc.getRecordLength());
            this.format(list, request);

            return Results.succ(PageConvertUtil.get(list, jpc));
        } catch (Exception e) {
            logger.error("otc_company_doc_list error:", e);
            model.addAttribute("error", "对不起，程序出现系统错误，请和网站管理员联系，谢谢！");
            return Results.fail();
        }
    }

    private void format(List<OtcUserCompanyDoc> list, HttpServletRequest request) {
        // 获取nickname
        List<Integer> ids = new LinkedList<Integer>();
        for (OtcUserCompanyDoc otcUserCompanyDoc : list)
            ids.add(otcUserCompanyDoc.getPublisherId());
        AdminUserExample example = new AdminUserExample();
        example.createCriteria().andIdIn(ids);
        List<AdminUser> temp = adminUserService.findAll(example);
        Map<Integer, String> map = new HashMap<Integer, String>(temp.size());
        for (AdminUser adminUser : temp) {
            map.put(adminUser.getId(), adminUser.getNickname());
        }

        // 获取语言名称
        Language[] langs = Language.values();
        Map<Integer, String> langMap = new HashMap<Integer, String>(langs.length);
        for (Language language : langs) {
            langMap.put(language.getLangTypeId(), language.getShowName());
        }

        // 获取类型
        Map<Byte, String> docTypeMap = new HashMap<Byte, String>(2);
        docTypeMap.put(OtcUserCompanyApplyType.Normal_Company.value,
                this.getLocalMsg(OtcUserCompanyApplyType.Normal_Company.languageKey, request));
        docTypeMap.put(OtcUserCompanyApplyType.Super_Company.value,
                this.getLocalMsg(OtcUserCompanyApplyType.Super_Company.languageKey, request));

        for (OtcUserCompanyDoc otcUserCompanyDoc : list) {
            otcUserCompanyDoc.setPublisherName(map.get(otcUserCompanyDoc.getPublisherId()));
            otcUserCompanyDoc.setLangName(langMap.get(otcUserCompanyDoc.getLang()));
            otcUserCompanyDoc.setTypeDes(docTypeMap.get(otcUserCompanyDoc.getType()));
        }
    }

    @PostMapping(value = "/otcCompanyDocAdd")
    public CommonResult merchantDocAdd(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        try {
            Language[] langs = Language.values();
            model.addAttribute("langList", langs);

            Map<String, String> typeMap = new HashMap<String, String>(2);
            typeMap.put(OtcUserCompanyApplyType.Normal_Company.value.toString(),
                    this.getLocalMsg(OtcUserCompanyApplyType.Normal_Company.languageKey, request));
            typeMap.put(OtcUserCompanyApplyType.Super_Company.value.toString(),
                    this.getLocalMsg(OtcUserCompanyApplyType.Super_Company.languageKey, request));
            model.addAttribute("typeMap", typeMap);

//            MyEConfig config = CommonContainer.getSite();
//            FrontUtils.frontData(request, model, config);
//            model.addAttribute("request", request);
            return Results.succ(model);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("otc_company_doc_add error:", e);
            return Results.fail();
        }
    }

    @PostMapping(value = "/otcCompanyDocSave")
    public CommonResult merchantDocSave(@RequestBody MerchantDocDto dto) {

        Integer langType = dto.getLangType();
        String title = dto.getTitle();

        JSONObject json = new JSONObject();
        int result = 0;
        try {
            logger.debug("before countByCondition");
            if (otcCompanyDocService.countByCondition(new OtcUserCompanyDoc.Builder().lang(langType).build()) > 0) {
                logger.error("inside countByCondition - error");
                json.put("result", "Language Type already exists");
                writeJson(json.toString(), request, response);
                return Results.fail(json);
            }
            if (dto.getOssFullFileName() == null) {
                dto.setOssFullFileName("");
            }
                OtcUserCompanyDoc doc = new OtcUserCompanyDoc.Builder().lang(langType).publisherId(this.adminUser.getId())
                        .title(title).address(dto.getOssFullFileName()).build();
                if (otcCompanyDocService.add(doc) == 1) {
                    result = 1;
            }

            json.put("result", result);
            logger.info("Result Success");
            return Results.succ(json);
        } catch (Exception e) {
            logger.error("otc_company_doc_save error:", e);
            return Results.fail();
        }
    }

    @PostMapping(value = "/otcCompanyDocEdit")
    public CommonResult merchantDocEdit(@RequestBody MerchantDto dto, HttpServletRequest request) {

        Integer userId = dto.getId();

        ModelMap model = new ModelMap();
        try {

            OtcUserCompanyDoc doc = otcCompanyDocService.findById(userId);
            doc.setTypeDes(doc.getType().toString());
            model.addAttribute("doc", doc);

            Language[] langs = Language.values();
            model.addAttribute("langList", langs);
            Map<String, String> typeMap = new HashMap<String, String>(2);
            typeMap.put(OtcUserCompanyApplyType.Normal_Company.value.toString(),
                    this.getLocalMsg(OtcUserCompanyApplyType.Normal_Company.languageKey, request));
            typeMap.put(OtcUserCompanyApplyType.Super_Company.value.toString(),
                    this.getLocalMsg(OtcUserCompanyApplyType.Super_Company.languageKey, request));
            model.addAttribute("typeMap", typeMap);

            return Results.succ(model);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("otc_company_doc_edit error:", e);
            return Results.fail();

        }
    }

    @PostMapping(value = "/otcCompanyDocEditSave")
    public CommonResult merchantDocEditSave(@RequestBody OtcUserCompanyDoc doc) {
        JSONObject json = new JSONObject();
        try {
            if (otcCompanyDocService.countByCondition(new OtcUserCompanyDoc.Builder().id(doc.getId()).lang(doc.getLang()).build()) > 0) {
                return Results.failMsg("Language Already Existing");
            }
            if (otcCompanyDocService.update(doc) == 1)
                json.put("Edit Saved", 1);
            else json.put("Edit Failed", 0);
            return Results.succ(json);
        } catch (Exception e) {
            logger.error("otc_company_doc_edit_save error:", e);
            return Results.fail();
        }
    }


    @PostMapping(value = "/otcCompanyDocDel")
    public CommonResult companyDocDel(@RequestBody MerchantDocDto dto) {
        JSONObject jsonStr = new JSONObject();
        String ids = dto.getIds();
        Integer result = null;
        try {

            if (StringUtils.isNotEmpty(ids)) {
                String[] idSplit = ids.split(",");
                List<Integer> list = new ArrayList<Integer>();
                for (String str : idSplit) {
                    list.add(Integer.parseInt(str));
                }
                if (otcCompanyDocService.delete(list) != idSplit.length)
                    result = SUCCESS_ONE;
            } else
                result = SUCCESS_ONE;
            jsonStr.put("Deleted", result);
            return Results.succ(jsonStr);
        } catch (Exception e) {
            logger.error("otc_company_doc_del error:", e);
            return Results.fail();
        }
    }
}
