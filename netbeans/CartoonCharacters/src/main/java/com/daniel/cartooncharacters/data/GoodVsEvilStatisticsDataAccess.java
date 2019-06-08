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
import com.daniel.cartooncharacters.util.SessionUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * This StatisticsDataAccess implementation provides the functionality for
 * accessing the cartoon database to collect statistics on good characters and
 * evil characters for a cartoon.
 *
 * @author Bryan Daniel
 */
public class GoodVsEvilStatisticsDataAccess implements StatisticsDataAccess {

    /**
     * The logger for this class
     */
    private Logger logger;

    /**
     * Sets the value for the logger.
     */
    public GoodVsEvilStatisticsDataAccess() {
        logger = LogManager.getLogger(GoodVsEvilStatisticsDataAccess.class);
    }

    /**
     * This method collects good/evil statistics for the given cartoon.
     *
     * @param cartoon the cartoon
     * @return the pie chart data list
     */
    @Override
    public List<Data> findStatistics(Cartoon cartoon) {
        ObservableList<PieChart.Data> statistics = FXCollections.observableArrayList();
        Session session = null;
        try {
            session = SessionUtil.getNewSession();

            StringBuilder queryString = new StringBuilder();
            queryString.append("SELECT COUNT(*) FROM CharacterDemographic");
            Query query = session.createQuery(queryString.toString());
            Long longTotalCount = (Long) query.uniqueResult();

            queryString.setLength(0);
            queryString.append("SELECT COUNT(*) FROM CharacterDemographic cd ");
            queryString.append("WHERE cd.villain = TRUE");
            query = session.createQuery(queryString.toString());
            Long evilCount = (Long) query.uniqueResult();

            queryString.setLength(0);
            queryString.append("SELECT COUNT(*) FROM CharacterDemographic cd ");
            queryString.append("WHERE cd.villain = FALSE");
            query = session.createQuery(queryString.toString());
            Long goodCount = (Long) query.uniqueResult();

            queryString.setLength(0);
            queryString.append("SELECT COUNT(*) FROM CharacterDemographic cd ");
            queryString.append("WHERE cd.villain = NULL");
            query = session.createQuery(queryString.toString());
            Long unknownCount = (Long) query.uniqueResult();

            BigDecimal totalCount = new BigDecimal(longTotalCount);

            statistics.addAll(new PieChart.Data("evil", new BigDecimal(evilCount).divide(totalCount, 2, RoundingMode.HALF_UP).doubleValue()),
                    new PieChart.Data("good", new BigDecimal(goodCount).divide(totalCount, 2, RoundingMode.HALF_UP).doubleValue()),
                    new PieChart.Data("unknown", new BigDecimal(unknownCount).divide(totalCount, 2, RoundingMode.HALF_UP).doubleValue()));

        } catch (HibernateException he) {
            logger.error("HibernateException exception occurred during GenderStatisticsDataAccess.findStatistics.", he);
        } catch (Exception e) {
            logger.error("Exception occurred during GenderStatisticsDataAccess.findStatistics.", e);
        } finally {
            SessionUtil.close(session);
        }
        return statistics;
    }
}
