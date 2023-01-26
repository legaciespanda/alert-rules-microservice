package com.ernest.influx.service;

import com.ernest.influx.config.InfluxDBConfig;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class InfluxDBService {

    @Autowired
    private InfluxDBConfig influxDBConfig;

    @PostConstruct
    public void initInfluxDb() {
        this.retentionPolicy = retentionPolicy == null || "".equals(retentionPolicy) ? "autogen" : retentionPolicy;
        this.influxDB = influxDbBuild();
    }
    //retention policy
    private String retentionPolicy;

    private InfluxDB influxDB;

    /**
     * Set the data storage strategy defalut strategy name/database database name/ 30d data storage time limit of 30 days/ 1 copy number is 1/ end DEFAULT
     * Indicates the policy set as the default
     */
    public void createRetentionPolicy() {
        String command = String. format("CREATE RETENTION POLICY \"%s\" ON \"%s\" DURATION %s REPLICATION %s DEFAULT",
                "defalut", influxDBConfig.database, "30d", 1);
        this. query(command);
    }

    /**
     * Connect to time series database; get InfluxDB
     **/
    private InfluxDB influxDbBuild() {
        if (influxDB == null) {
            influxDB = InfluxDBFactory.connect(influxDBConfig.url, influxDBConfig.userName, influxDBConfig.password);
            influxDB.setDatabase(influxDBConfig.database);
        }
        return influxDB;
    }

    /**
     * insert
     * @param measurement table
     * @param tags tags
     * @param fields field
     */
    public void insert(String measurement, Map<String, String> tags, Map<String, Object> fields) {
        influxDbBuild();
        Point.Builder builder = Point.measurement(measurement);
        builder.time(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        builder.tag(tags);
        builder.fields(fields);
        influxDB.write(influxDBConfig.database, "", builder.build());
    }

    /**
     * @desc Insert, with time time
     * @date 2023/1/26
     * @param measurement
     *@param time
     *@param tags
     * @param fields
     * @return void
     */
    public void insert(String measurement, long time, Map<String, String> tags, Map<String, Object> fields) {
        influxDbBuild();
        Point.Builder builder = Point.measurement(measurement);
        builder.time(time, TimeUnit.MILLISECONDS);
        builder.tag(tags);
        builder.fields(fields);
        influxDB.write(influxDBConfig.database, "", builder.build());
    }

    /**
     * @desc influxDB enables UDP function, default port: 8089, default database: udp, does not provide code transfer database function interface
     * @date 2023/1/26
     * @param measurement
     *@param time
     *@param tags
     * @param fields
     * @return void
     */
    public void insertUDP(String measurement, long time, Map<String, String> tags, Map<String, Object> fields) {
        influxDbBuild();
        Point.Builder builder = Point.measurement(measurement);
        builder.time(time, TimeUnit.MILLISECONDS);
        builder.tag(tags);
        builder.fields(fields);
        int udpPort = 8089;
        influxDB.write(udpPort, builder.build());
    }

    /**
     * Inquire
     * @param command query statement
     * @return
     */
    public QueryResult query(String command) {
        influxDbBuild();
        return influxDB. query(new Query(command, influxDBConfig. database));
    }

    /**
     * @desc query result processing
     * @date 2023/1/26
     * @param queryResult
     */
//    public List<Map<String, Object>> queryResultProcess(QueryResult queryResult) {
//        List<Map<String, Object>> mapList = new ArrayList<>();
//        List<QueryResult. Result> resultList = queryResult. getResults();
//        //Convert the query result set into the corresponding entity object and aggregate it into a list
//        for(QueryResult.Result query : resultList){
//            List<QueryResult.Series> seriesList = query.getSeries();
//            if(seriesList != null && seriesList.size() != 0) {
//                for(QueryResult.Series series : seriesList){
//                    List<String> columns = series.getColumns();
//                    String[] keys = columns.toArray(new String[columns.size()]);
//                    List<List<Object>> values ​​ = series.getValues();
//                    if(values ​​ != null && values.size() != 0) {
//                        for(List<Object> value : values){
//                            Map<String, Object> map = new HashMap(keys.length);
//                            for (int i = 0; i < keys. length; i++) {
//                                map.put(keys[i], value.get(i));
//                            }
//                            mapList. add(map);
//                        }
//                    }
//                }
//            }
//        }
//        return mapList;
//    }

    /**
     * @desc InfluxDB query count total number
     * @date 2023/1/26
     */
//    public long countResultProcess(QueryResult queryResult) {
//        long count = 0;
//        List<Map<String, Object>> list = queryResultProcess(queryResult);
//        if(list != null && list. size() != 0) {
//            Map<String, Object> map = list.get(0);
//            double num = (Double) map. get("count");
//            count = new Double(num). longValue();
//        }
//        return count;
//    }

    /**
     * Inquire
     * @param dbName create database
     * @return
     */
    public void createDB(String dbName) {
        influxDbBuild();
        influxDB.createDatabase(dbName);
    }

    /**
     * Batch write measurement points
     *
     * @param batchPoints
     */
    public void batchInsert(BatchPoints batchPoints) {
        influxDbBuild();
        influxDB.write(batchPoints);
    }

    /**
     * Batch write data
     *
     * @param database database
     * @param retentionPolicy retention policy
     * @param consistency consistency
     * @param records The data to be saved (call BatchPoints.lineProtocol() to get a record)
     */
    public void batchInsert(final String database, final String retentionPolicy,
                            final InfluxDB.ConsistencyLevel consistency, final List<String> records) {
        influxDbBuild();
        influxDB.write(database, retentionPolicy, consistency, records);
    }

    /**
     * @desc batch write data
     * @date 2023/1/26
     * @param consistency
     * @param records
     */
    public void batchInsert(final InfluxDB. ConsistencyLevel consistency, final List<String> records) {
        influxDbBuild();
        influxDB.write(influxDBConfig.database, "", consistency, records);
    }

}