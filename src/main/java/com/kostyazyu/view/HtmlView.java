package com.kostyazyu.view;

import com.kostyazyu.Controller;
import com.kostyazyu.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HtmlView implements View {
    private Controller controller;
    private final String filePath = "./src/main/java/" + this.getClass().getPackage().toString()
            .substring(8).replace('.','/')+"/vacancies.html";

    @Override
    public void update(List<Vacancy> vacancies) {
        updateFile(getUpdatedFileContent(vacancies));
        System.out.println(vacancies.size() + " vacancies found. File vacancies.html was updated.");

    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod(){
        controller.onCitySelect("Moscow");
    }
    public void selectEmulationMethod(String city){
        controller.onCitySelect(city);
    }

    private String getUpdatedFileContent(List<Vacancy> vacancies){
        try {
            Document doc = getDocument();
            Element template = doc.getElementsByClass("template").first();
            Element emptyTemplate = template.clone();
            emptyTemplate.removeClass("template").removeAttr("style");

            Elements elements = doc.getElementsByClass("vacancy");
            for(Element el : elements){
                if (!el.hasClass("template")) el.remove();
            }

            for(Vacancy vacancy: vacancies){
                Element vacancyElement = emptyTemplate.clone();
                vacancyElement.getElementsByClass("city").append(vacancy.getCity());
                vacancyElement.getElementsByClass("companyName").append(vacancy.getCompanyName());
                vacancyElement.getElementsByClass("salary").append(vacancy.getSalary());
                vacancyElement.getElementsByTag("a").append(vacancy.getTitle()).attr("href", vacancy.getUrl());
                template.before(vacancyElement.outerHtml());
            }

            return doc.html();


        } catch (IOException e) {
            e.printStackTrace();
            return ("Some exception occurred");
        }
    }

    private void updateFile(String content){
        try (FileWriter fileWriter = new FileWriter(filePath)){

            fileWriter.write(content);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected Document getDocument() throws IOException {
        return Jsoup.parse(new File(filePath), "UTF-8");
    }


}
