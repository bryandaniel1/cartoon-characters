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
import com.daniel.cartooncharacters.util.DatabaseUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * This StatisticsDataAccess implementation provides the functionality for
 * accessing the cartoon database to collect gender statistics for cartoon
 * characters.
 *
 * @author Bryan Daniel
 */
public class GenderStatisticsDataAccess implements StatisticsDataAccess {

    /**
     * This method collects gender statistics for the given cartoon.
     *
     * @param cartoon the cartoon
     * @return the pie chart data list
     */
    @Override
    public List<Data> findStatistics(Cartoon cartoon) {
        ObservableList<Data> statistics = FXCollections.observableArrayList();
        Session session = null;
        try {
            session = DatabaseUtil.getNewSession();
            
            StringBuilder queryString = new StringBuilder();
            queryString.append("SELECT COUNT(*) FROM CharacterDemographic");
            Query query = session.createQuery(queryString.toString());
            Long longTotalCount = (Long) query.uniqueResult();

            queryString.setLength(0);
            queryString.append("SELECT COUNT(*) FROM CharacterDemographic cd ");
            queryString.append("WHERE cd.gender LIKE 'M'");
            query = session.createQuery(queryString.toString());
            Long maleCount = (Long) query.uniqueResult();

            queryString.setLength(0);
            queryString.append("SELECT COUNT(*) FROM CharacterDemographic cd ");
            queryString.append("WHERE cd.gender LIKE 'F'");
            query = session.createQuery(queryString.toString());
            Long femaleCount = (Long) query.uniqueResult();

            queryString.setLength(0);
            queryString.append("SELECT COUNT(*) FROM CharacterDemographic cd ");
            queryString.append("WHERE cd.gender LIKE 'U'");
            query = session.createQuery(queryString.toString());
            Long unknownCount = (Long) query.uniqueResult();

            queryString.setLength(0);
            queryString.append("SELECT COUNT(*) FROM CharacterDemographic cd ");
            queryString.append("WHERE cd.gender LIKE 'N'");
            query = session.createQuery(queryString.toString());
            Long notApplicableCount = (Long) query.uniqueResult();

            BigDecimal totalCount = new BigDecimal(longTotalCount);

            statistics.addAll(new PieChart.Data("male", new BigDecimal(maleCount).divide(totalCount, 2, RoundingMode.HALF_UP).doubleValue()),
                    new PieChart.Data("female", new BigDecimal(femaleCount).divide(totalCount, 2, RoundingMode.HALF_UP).doubleValue()),
                    new PieChart.Data("unknown", new BigDecimal(unknownCount).divide(totalCount, 2, RoundingMode.HALF_UP).doubleValue()),
                    new PieChart.Data("not applicable", new BigDecimal(notApplicableCount).divide(totalCount, 2, RoundingMode.HALF_UP).doubleValue()));

        } catch (HibernateException he) {
            Logger.getLogger(GenderStatisticsDataAccess.class.getName()).log(Level.INFO,
                    "HibernateException exception occurred during GenderStatisticsDataAccess.findStatistics.", he);
        } catch (Exception e) {
            Logger.getLogger(GenderStatisticsDataAccess.class.getName()).log(Level.INFO,
                    "Exception occurred during GenderStatisticsDataAccess.findStatistics.", e);
        } finally {
            DatabaseUtil.close(session);
        }
        return statistics;
    }
}
