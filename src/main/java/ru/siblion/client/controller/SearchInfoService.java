package ru.siblion.client.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import ru.siblion.service.entity.request.SearchInfo;
import ru.siblion.service.entity.request.SignificantDateInterval;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
public class SearchInfoService implements Serializable {

    @Autowired
    private SearchInfo searchInfo;

    private SignificantDateInterval dateInterval = new SignificantDateInterval();

    private List<SignificantDateInterval> dateIntervalList = new ArrayList<>();

    @PostConstruct
    public void init() {
        dateIntervalList.add(dateInterval);
    }

    public void addAnotherInterval() {
        dateInterval = new SignificantDateInterval();
        dateIntervalList.add(dateInterval);
    }

    public SearchInfoService() {
    }

    public SignificantDateInterval getDateInterval() {

        return dateInterval;
    }

    public SearchInfo getSearchInfo() {

        return searchInfo;
    }

    public List<SignificantDateInterval> getDateIntervalList() {

        return dateIntervalList;
    }

    public void setDateInterval(SignificantDateInterval dateInterval) {

        this.dateInterval = dateInterval;
    }

    public void clean() {
        dateIntervalList = new ArrayList<SignificantDateInterval>();
        dateIntervalList.add(new SignificantDateInterval());
    }

    public void deleteInterval(SignificantDateInterval significantDateInterval) {
        dateIntervalList.remove(significantDateInterval);
    }

    public void setDateIntervalList(List<SignificantDateInterval> dateIntervalList) {
        this.dateIntervalList = dateIntervalList;
    }
}