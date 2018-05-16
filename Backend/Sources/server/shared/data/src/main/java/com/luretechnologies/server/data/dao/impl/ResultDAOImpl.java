/**
 * COPYRIGHT @ Lure Technologies, LLC.
 * ALL RIGHTS RESERVED
 *
 * Developed by Lure Technologies, LLC. (www.luretechnologies.com)
 *
 * Copyright in the whole and every part of this software program belongs to
 * Lure Technologies, LLC (“Lure”).  It may not be used, sold, licensed,
 * transferred, copied or reproduced in whole or in part in any manner or
 * form other than in accordance with and subject to the terms of a written
 * license from Lure or with the prior written consent of Lure or as
 * permitted by applicable law.
 *
 * This software program contains confidential and proprietary information and
 * must not be disclosed, in whole or in part, to any person or organization
 * without the prior written consent of Lure.  If you are neither the
 * intended recipient, nor an agent, employee, nor independent contractor
 * responsible for delivering this message to the intended recipient, you are
 * prohibited from copying, disclosing, distributing, disseminating, and/or
 * using the information in this email in any manner. If you have received
 * this message in error, please advise us immediately at
 * legal@luretechnologies.com by return email and then delete the message from your
 * computer and all other records (whether electronic, hard copy, or
 * otherwise).
 *
 * Any copies or reproductions of this software program (in whole or in part)
 * made by any method must also include a copy of this legend.
 *
 * Inquiries should be made to legal@luretechnologies.com
 *
 */
package com.luretechnologies.server.data.dao.impl;

import com.luretechnologies.server.data.dao.ResultDAO;
import com.luretechnologies.server.data.model.Result;
import org.springframework.stereotype.Repository;

/**
 *
 *
 * @author developer
 */
@Repository
public class ResultDAOImpl extends BaseDAOImpl<Result, Long> implements ResultDAO {

//    @Override
//    public List<ResultReport> analitycsOfSurveys(String search, int firstResult, int maxResult, User user, List<Terminal> terminals) throws PersistenceException {
//
//        Criteria criteria = createCriteria(ResultReport.class)
//                .createAlias("survey", "survey")
//                .add(Restrictions.in("terminal", terminals));
//
//        return criteria.list();
//    }
//
//    @Override
//    public List<ResultDisplayGraphs> questionResult(String search, int firstResult, int maxResult, User user, List<Terminal> terminals) throws PersistenceException {
//
//        Criteria criteria = createCriteria(ResultReport.class)
//                .createAlias("survey", "survey")
//                .createAlias("question", "question")
//                .createAlias("answer", "answer")
//                .add(Restrictions.in("terminal", terminals));
//
//        ProjectionList projections = Projections.projectionList()
//                .add(Projections.groupProperty("question.value").as("questionValue"))
//                .add(Projections.groupProperty("answer.id").as("answerId"))
//                .add(Projections.groupProperty("answer.value").as("answerValue"))
//                .add(Projections.groupProperty("survey.name").as("surveyName"))
//                .add(Projections.groupProperty("question.id").as("questionId"))
//                .add(Projections.count("id").as("Count"));
//
//        criteria.setProjection(projections)
//                .setResultTransformer(new AliasToBeanNestedResultTransformer1(ResultDisplayGraphs.class));
//
//        return criteria.list();
//
//    }
//
//    @Override
//    public List<Result> surveyTotal(String search, int firstResult, int maxResult, User user, List<Terminal> terminals) throws PersistenceException {
//
//        ProjectionList projections = Projections.projectionList()
//                .add(Projections.property("survey.id").as("survey.id"))
//                .add(Projections.property("survey.name").as("survey.name"))
//                .add(Projections.property("terminal.id").as("terminal.id"))
//                .add(Projections.property("terminal.name").as("terminal.name"))
//                .add(Projections.property("terminal.serialNumber").as("terminal.serialNumber"))
//                .add(Projections.property("terminal.description").as("terminal.description"))
//                .add(Projections.property("merchant.id").as("merchant.id"))
//                .add(Projections.property("merchant.name").as("merchant.name"))
//                .add(Projections.property("merchant.description").as("merchant.description"));
//
//        Criteria criteria = createCriteria(Result.class)
//                .createAlias("survey", "survey")
//                .createAlias("terminal", "terminal")
//                .createAlias("terminal.entityRelation", "entityRelation")
//                .createAlias("entityRelation.merchant", "merchant")
//                .add(Restrictions.in("terminal", terminals));
//
//        criteria.setProjection(projections)
//                .setResultTransformer(new AliasToBeanNestedResultTransformer(SurveyTotalReportDisplay.class));
//
//        return criteria.list();
//    }
//
//    @Override
//    public List<Diagnostic> deviceDiagnostics(User user, List<Terminal> terminals) throws PersistenceException {
//
//        Criteria criteria = createCriteria(Diagnostic.class)
//                .add(Restrictions.in("terminal", terminals));
//
//        return criteria.list();
//    }
//
//    @Override
//    public List<Statistic> deviceStatistics(User user, List<Terminal> terminals) throws PersistenceException {
//
//        Criteria criteria = createCriteria(Statistic.class)
//                .add(Restrictions.in("terminal", terminals));
//
//        return criteria.list();
//    }
//
//    @Override
//    public List<Diagnostic> configurationBreakdown(User user, List<Terminal> terminals, List<String> configurationNames) throws PersistenceException {
//
//        /* select terminal, name, count(name) from diagnostic group by terminal, name; */
//        ProjectionList projections = Projections.projectionList()
//                .add(Projections.count("name").as("totalConfiguration"))
//                .add(Projections.groupProperty("terminal").as("terminal"))
//                .add(Projections.groupProperty("name").as("nameConfiguration"))
//                .add(Projections.groupProperty("value").as("valueConfiguration"));
//
//        Criteria criteria = createCriteria(Diagnostic.class)
//                .add(Restrictions.in("terminal", terminals))
//                .add(Restrictions.in("name", configurationNames))
//                .setProjection(projections)
//                .setResultTransformer(new AliasToBeanNestedResultTransformer(ConfigurationBreakdownReportDisplay.class));
//
//        return criteria.list();
//    }
//
//    @Override
//    public List<DeviceInformationDisplay> deviceInformation(String search, int firstResult, int maxResult, User user, List<Terminal> terminals) throws PersistenceException {
//
//        ProjectionList projections = Projections.projectionList()
//                .add(Projections.property("survey.id").as("survey.id"))
//                .add(Projections.property("survey.name").as("survey.name"))
//                .add(Projections.property("terminal.id").as("terminalReportDisplay.id"))
//                .add(Projections.property("terminal.name").as("terminalReportDisplay.name"))
//                .add(Projections.property("terminal.serialNumber").as("terminalReportDisplay.serialNumber"));
//        // .add(Projections.property("terminal.description").as("terminalReportDisplay.description"))
//        // .add(Projections.property("terminal.diagnostic").as("terminalReportDisplay.diagnostic"));
//        //  .add(Projections.property("diagnostic.value").as("terminalReportDisplay.diagnostic.value"));
//
//        Criteria criteria = createCriteria(Result.class)
//                .createAlias("survey", "survey")
//                .createAlias("terminal", "terminal")
//                .createAlias("terminal.diagnostic", "diagnostic");
//
//        criteria.setMaxResults(200);
//
//        criteria.setProjection(projections)
//                .setResultTransformer(new AliasToBeanNestedResultTransformer1(DeviceInformationDisplay.class));
//
//        return criteria.list();
//    }
}
