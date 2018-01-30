package ru.siblion.service.controller;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import ru.siblion.service.accessory.SearchInfoService;
import ru.siblion.service.model.response.CorrectionCheckResult;
import ru.siblion.service.model.response.LogSearchResult;
import ru.siblion.util.InputDataValidator;
import ru.siblion.service.model.request.SearchInfo;
import ru.siblion.service.model.response.SearchInfoResult;

@Controller
@RequestMapping(value = "/form")
public class MainFormController {

    private static final String SYNC_URI = "http://localhost:7001/Spring/results";

    private static final String ASYNC_URI = "http://localhost:7001/Spring/creating";

    private InputDataValidator inputDataValidator;

    @RequestMapping(method = RequestMethod.GET)
    public String getIndexPage(Model model) {
        model.addAttribute(new SearchInfoService());
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String getResults(SearchInfoService searchInfoService, Model model) throws ConfigurationException {
        searchInfoService.setDateIntervals();
        SearchInfo searchInfo = searchInfoService.getSearchInfo();
        inputDataValidator.correctionCheck(searchInfo);
        //получить код результа проверки на ошибки
        CorrectionCheckResult correctionCheckResult = inputDataValidator.getCorrectionCheckResult();
        if (!searchInfo.getRealization()) {
            if (correctionCheckResult.getErrorCode() == 0) {
                RestTemplate restTemplate = new RestTemplate();
                SearchInfoResult searchInfoResult = restTemplate.postForObject(SYNC_URI, searchInfo, SearchInfoResult.class);
                model.addAttribute(searchInfoResult);
                return "redirect: results";
            }
            else return "";
        }
        else {
            if (correctionCheckResult.getErrorCode() == 0) {
                RestTemplate restTemplate = new RestTemplate();
                LogSearchResult logSearchResult = restTemplate.postForObject(ASYNC_URI, searchInfo, LogSearchResult.class);
                model.addAttribute(logSearchResult);
                return "";
            }
        }
        return "";

    }



    @Autowired
    public void setInputDataValidator(InputDataValidator inputDataValidator) {
        this.inputDataValidator = inputDataValidator;
    }

}