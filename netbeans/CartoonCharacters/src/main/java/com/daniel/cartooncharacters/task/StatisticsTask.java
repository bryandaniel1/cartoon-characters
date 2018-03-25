/*
 * Copyright 2018 Bryan Daniel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.daniel.cartooncharacters.task;

import com.daniel.cartooncharacters.data.GenderStatisticsDataAccess;
import com.daniel.cartooncharacters.data.GoodVsEvilStatisticsDataAccess;
import com.daniel.cartooncharacters.data.StatisticsDataAccess;
import com.daniel.cartooncharacters.entity.Cartoon;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class contains the logic for executing calls to the data access object
 * in a background thread and showing a chart to represent statistical
 * information.
 *
 * @author Bryan Daniel
 */
public class StatisticsTask extends Task<Void> {

    /**
     * The types of statistics to present
     */
    public enum StatisticsType {
        GENDER, GOOD_VS_EVIL
    }

    /**
     * The data list
     */
    private final ObservableList<PieChart.Data> dataList = FXCollections.observableArrayList();

    /**
     * The cartoon
     */
    private final Cartoon cartoon;

    /**
     * The data access object to retrieve character information
     */
    private final StatisticsDataAccess dataAccess;

    /**
     * The type of picture
     */
    private final StatisticsType type;

    /**
     * The main stage
     */
    private final Stage primaryStage;

    /**
     * This parameterized constructor sets values for instance variables.
     *
     * @param cartoon the cartoon
     * @param type the statistics type
     * @param primaryStage the main stage
     */
    public StatisticsTask(Cartoon cartoon, StatisticsType type, Stage primaryStage) {
        this.cartoon = cartoon;
        this.type = type;
        this.primaryStage = primaryStage;
        if (type == StatisticsType.GENDER) {
            dataAccess = new GenderStatisticsDataAccess();
        } else {
            dataAccess = new GoodVsEvilStatisticsDataAccess();
        }
    }

    /**
     * This method calls the data access object's findStatistics method. This
     * data-access task is not performed on the FX application thread.
     *
     * @return null
     * @throws Exception
     */
    @Override
    protected Void call() throws Exception {
        ObservableList<PieChart.Data> results = getChartData();
        String title = cartoon.getTitle() + (type == StatisticsType.GENDER ? " Gender Statistics"
                : " Good vs. Evil Statistics");
        Platform.runLater(() -> {
            Stage chartStage = new Stage();
            chartStage.initModality(Modality.WINDOW_MODAL);
            chartStage.initOwner(primaryStage);
            PieChart pieChart = new PieChart();
            pieChart.setData(results);
            chartStage.setTitle(title);
            StackPane root = new StackPane();
            root.getChildren().add(pieChart);
            chartStage.setScene(new Scene(root, 400, 250));
            chartStage.show();
        });
        return null;
    }

    /**
     * This method utilizes the data access class to collect gender statistics.
     *
     * @return the data list
     */
    private ObservableList<PieChart.Data> getChartData() {
        List<PieChart.Data> pieData = dataAccess.findStatistics(cartoon);
        dataList.addAll(pieData);
        return dataList;
    }
}
