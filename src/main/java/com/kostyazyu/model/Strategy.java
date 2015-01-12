package com.kostyazyu.model;


import com.kostyazyu.vo.Vacancy;

import java.util.List;

public interface Strategy {

    List<Vacancy> getVacancies(String searchString);
}
