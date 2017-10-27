/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.dbcp2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

import org.apache.commons.pool2.KeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestPoolingConnection {

    private PoolingConnection con = null;

    @Before
    public void setUp() throws Exception {
        con = new PoolingConnection(new TesterConnection("test", "test"));
        final GenericKeyedObjectPoolConfig config = new GenericKeyedObjectPoolConfig();
        config.setMaxTotalPerKey(-1);
        config.setBlockWhenExhausted(false);
        config.setMaxWaitMillis(0);
        config.setMaxIdlePerKey(1);
        config.setMaxTotal(1);
        final KeyedObjectPool<PStmtKey,DelegatingPreparedStatement> stmtPool =
                new GenericKeyedObjectPool<>(con, config);
        con.setStatementPool(stmtPool);
    }

    @After
    public void tearDown() throws Exception {
        con.close();
        con = null;
    }

    @Test
    public void testPrepareStatement() throws Exception {
        final String sql = "select 'a' from dual";
        final DelegatingPreparedStatement statement = (DelegatingPreparedStatement)con.prepareStatement(sql);
        final TesterPreparedStatement testStatement = (TesterPreparedStatement) statement.getInnermostDelegate();
        // assert
        assertEquals(sql, testStatement.getSql());
    }

    @Test
    public void testPrepareStatementWithAutoGeneratedKeys() throws Exception {
        final String sql = "select 'a' from dual";
        final int autoGeneratedKeys = 0;
        final DelegatingPreparedStatement statement = (DelegatingPreparedStatement)con.prepareStatement(sql, autoGeneratedKeys);
        final TesterPreparedStatement testStatement = (TesterPreparedStatement) statement.getInnermostDelegate();
        // assert
        assertEquals(sql, testStatement.getSql());
        assertEquals(autoGeneratedKeys, testStatement.getAutoGeneratedKeys());
    }

    @Test
    public void testPrepareStatementWithResultSetConcurrency() throws Exception {
        final String sql = "select 'a' from dual";
        final int resultSetType = 0;
        final int resultSetConcurrency = 0;
        final DelegatingPreparedStatement statement = (DelegatingPreparedStatement)con.prepareStatement(sql, resultSetType, resultSetConcurrency);
        final TesterPreparedStatement testStatement = (TesterPreparedStatement) statement.getInnermostDelegate();
        // assert
        assertEquals(sql, testStatement.getSql());
        assertEquals(resultSetType, testStatement.getResultSetType());
        assertEquals(resultSetConcurrency, testStatement.getResultSetConcurrency());
    }

    @Test
    public void testPrepareStatementWithResultSetHoldability() throws Exception {
        final String sql = "select 'a' from dual";
        final int resultSetType = 0;
        final int resultSetConcurrency = 0;
        final int resultSetHoldability = 0;
        final DelegatingPreparedStatement statement = (DelegatingPreparedStatement)con.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        final TesterPreparedStatement testStatement = (TesterPreparedStatement) statement.getInnermostDelegate();
        // assert
        assertEquals(sql, testStatement.getSql());
        assertEquals(resultSetType, testStatement.getResultSetType());
        assertEquals(resultSetConcurrency, testStatement.getResultSetConcurrency());
        assertEquals(resultSetHoldability, testStatement.getResultSetHoldability());
    }

    @Test
    public void testPrepareStatementWithColumnIndexes() throws Exception {
        final String sql = "select 'a' from dual";
        final int[] columnIndexes = new int[]{1};
        final DelegatingPreparedStatement statement = (DelegatingPreparedStatement)con.prepareStatement(sql, columnIndexes);
        final TesterPreparedStatement testStatement = (TesterPreparedStatement) statement.getInnermostDelegate();
        // assert
        assertEquals(sql, testStatement.getSql());
        assertArrayEquals(columnIndexes, testStatement.getColumnIndexes());
    }

    @Test
    public void testPrepareStatementWithColumnNames() throws Exception {
        final String sql = "select 'a' from dual";
        final String columnNames[] = new String[]{"columnName1"};
        final DelegatingPreparedStatement statement = (DelegatingPreparedStatement)con.prepareStatement(sql, columnNames);
        final TesterPreparedStatement testStatement = (TesterPreparedStatement) statement.getInnermostDelegate();
        // assert
        assertEquals(sql, testStatement.getSql());
        assertArrayEquals(columnNames, testStatement.getColumnNames());
    }

    @Test
    public void testPrepareCall() throws Exception {
        final String sql = "select 'a' from dual";
        final DelegatingCallableStatement statement = (DelegatingCallableStatement)con.prepareCall(sql);
        final TesterCallableStatement testStatement = (TesterCallableStatement) statement.getInnermostDelegate();
        // assert
        assertEquals(sql, testStatement.getSql());
    }

    @Test
    public void testPrepareCallWithResultSetConcurrency() throws Exception {
        final String sql = "select 'a' from dual";
        final int resultSetType = 0;
        final int resultSetConcurrency = 0;
        final DelegatingCallableStatement statement = (DelegatingCallableStatement)con.prepareCall(sql, resultSetType, resultSetConcurrency);
        final TesterCallableStatement testStatement = (TesterCallableStatement) statement.getInnermostDelegate();
        // assert
        assertEquals(sql, testStatement.getSql());
        assertEquals(resultSetType, testStatement.getResultSetType());
        assertEquals(resultSetConcurrency, testStatement.getResultSetConcurrency());
    }

    @Test
    public void testPrepareCallWithResultSetHoldability() throws Exception {
        final String sql = "select 'a' from dual";
        final int resultSetType = 0;
        final int resultSetConcurrency = 0;
        final int resultSetHoldability = 0;
        final DelegatingCallableStatement statement = (DelegatingCallableStatement)con.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        final TesterCallableStatement testStatement = (TesterCallableStatement) statement.getInnermostDelegate();
        // assert
        assertEquals(sql, testStatement.getSql());
        assertEquals(resultSetType, testStatement.getResultSetType());
        assertEquals(resultSetConcurrency, testStatement.getResultSetConcurrency());
        assertEquals(resultSetHoldability, testStatement.getResultSetHoldability());
    }
}
