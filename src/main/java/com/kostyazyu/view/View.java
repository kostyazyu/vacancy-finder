package com.kostyazyu.view;

import com.kostyazyu.Controller;
import com.kostyazyu.vo.Vacancy;

import java.util.List;

public interface View {
    void update(List<Vacancy> vacancies);
    void setController(Controller controller);
}
