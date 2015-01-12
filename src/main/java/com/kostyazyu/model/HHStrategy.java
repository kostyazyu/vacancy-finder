package com.kostyazyu.model;

import com.kostyazyu.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HHStrategy implements Strategy {
    private static final String URL_FORMAT = "http://hh.ru/search/vacancy?text=java+%s&page=%d";

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> vacancyList = new ArrayList<>();
        Document doc;
        int page = 0;

        while (true) {
            try {
                doc = getDocument(searchString, page);
                Elements elements = doc.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");
                if (elements.size() == 0) break;

                for(Element element: elements){
                    Vacancy vacancy = new Vacancy();

                    vacancy.setTitle(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").text());
                    vacancy.setCompanyName(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").text());
                    vacancy.setCity(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address").text());
                    vacancy.setSiteName(doc.title());
                    vacancy.setUrl(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").attr("href"));
                    vacancy.setSalary(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation").text());

                    vacancyList.add(vacancy);

                }
                page++;
            } catch (IOException ignored) { }
        }

        return vacancyList;
    }

    protected Document getDocument(String searchString, int page) throws IOException {
        String url = String.format(URL_FORMAT, searchString, page);
        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.124 Safari/537.36")
                .referrer("http://hh.ua/search/vacancy?text=java+%D0%BA%D0%B8%D0%B5%D0%B2")
                .get();
    }

}
