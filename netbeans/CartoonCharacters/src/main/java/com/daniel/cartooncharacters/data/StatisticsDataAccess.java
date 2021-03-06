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
package com.daniel.cartooncharacters.data;

import com.daniel.cartooncharacters.entity.Cartoon;
import java.util.List;
import javafx.scene.chart.PieChart.Data;

/**
 * This interface provides the functionality for accessing the cartoon database
 * to collect statistical information.
 *
 * @author Bryan Daniel
 */
public interface StatisticsDataAccess {

    /**
     * This method collects for cartoon information for the given cartoon and
     * statistic.
     *
     * @param cartoon the cartoon
     * @return the list of pie chart data
     */
    public List<Data> findStatistics(Cartoon cartoon);
}
