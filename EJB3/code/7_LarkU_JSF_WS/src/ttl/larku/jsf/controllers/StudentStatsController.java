package ttl.larku.jsf.controllers;


import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.PieChartModel;
import ttl.larku.domain.Student;
import ttl.larku.service.ejb.RegistrationFacade;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Named
public class StudentStatsController implements Serializable {

    @EJB
    private RegistrationFacade regService;

    private ChartModel chartModel;

    private String chartType;


    @PostConstruct()
    public void init(){
        //chartModel = createByStatus();

        chartModel = createCountsBy((Student s) -> s.getStatus().toString(), "Students By Status");
        //chartModel = createCountsBy((Student s) -> s.getName().substring(0, 1));
    }

    public ChartModel getChartModel() {
        return chartModel;
    }

    public void setChartModel(ChartModel chartModel) {
        this.chartModel = chartModel;
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public void chartTypeListener() {
        switch(chartType) {
            case "status":
                chartModel = createCountsBy((Student s) -> s.getStatus().toString(), "Students By Status");
                break;
            case "letter":
                chartModel = createCountsBy((Student s) -> s.getName().substring(0, 1), "Students by Letter");
                break;
            case "letter2":
                chartModel = createCountsBy((Student s) -> s.getName().substring(0, 2), "Students by 2 Letters");
                break;
        }
    }

    private PieChartModel createCountsBy(Function<Student, String> keyExtractor, String header) {
        PieChartModel pieChartModel = new PieChartModel();

        List<Student> students = regService.getAllStudents();

        Map<String, Long> byStatusLong = students.stream()
                .map(keyExtractor::apply)
                .collect(Collectors.groupingBy(Function.identity(),
                        Collectors.counting()));

        //This headache to get a Map<String, Number> instead of
        //Map<String, Long>
        Map<String, Number> byStatus = students.stream()
                .map(keyExtractor::apply)
                .collect(HashMap::new,
                        (Map<String, Number> map, String st) -> {
                            map.computeIfAbsent(st, (s) -> 0);
                            map.put(st, map.get(st).intValue() + 1);
                            /*-
                            if (!map.containsKey(st)) {
                                map.put(st, 1);
                            }
                            else {
                                map.put(st, map.get(st).intValue() + 1);
                            }
                            */
                        },
                        //(m1, m2) -> m1.putAll(m2)
                        Map::putAll
                );


        //because these guys want a Map<String, Number>
        //If they had wanted a Map<String, ? extends NUmber> life
        //would have been simpler
        pieChartModel.setData(byStatus);
        //byStatus.forEach((status, count) -> pieChartModel.set(status, count));

        pieChartModel.setTitle(header);
        pieChartModel.setLegendPosition("w");

        return pieChartModel;
    }

    private PieChartModel createByStatus() {
        PieChartModel pieChartModel = new PieChartModel();

        List<Student> students = regService.getAllStudents();

        Map<String, Long> byStatusLong = students.stream()
                .map(student -> student.getStatus().toString())
                .collect(Collectors.groupingBy(Function.identity(),
                        Collectors.counting()));

        //This headache to get a Map<String, Number> instead of
        //Map<String, Long>
        Map<String, Number> byStatus = students.stream()
                .map(student -> student.getStatus().toString())
                .collect(HashMap::new,
                        (Map<String, Number> map, String st) -> {
                            if (!map.containsKey(st)) {
                                map.put(st, 1);
                            }
                            else {
                                map.put(st, map.get(st).intValue() + 1);
                            }
                        },
                        (m1, m2) -> m1.putAll(m2)
                );


        //because these guys want a Map<String, Number>
        //If they had wanted a Map<String, ? extends NUmber> life
        //would have been simpler
        pieChartModel.setData(byStatus);
        //byStatus.forEach((status, count) -> pieChartModel.set(status, count));

        pieChartModel.setTitle("Students By Status");
        pieChartModel.setLegendPosition("w");

        return pieChartModel;
    }

}
