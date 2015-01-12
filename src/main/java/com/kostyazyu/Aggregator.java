package com.kostyazyu;

import com.kostyazyu.model.HHStrategy;
import com.kostyazyu.model.Model;
import com.kostyazyu.model.Provider;
import com.kostyazyu.view.HtmlView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Aggregator {
    public static void main(String[] args) {
        Provider [] providers = {new Provider(new HHStrategy())};
        HtmlView view  = new HtmlView();
        Model model = new Model(view, providers);
        Controller controller = new Controller(model);
        view.setController(controller);
//        view.userCitySelectEmulationMethod();

        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in)))
        {
            System.out.println("Input name of city to search Java vacancies in latin letters (Moscow, Krasnodar ...) : ");
            String cityName = br.readLine();
            view.selectEmulationMethod(cityName);
        } catch (IOException ignored) {}

    }
}
